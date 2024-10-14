package homeostatic.platform;

import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.ServerLevelData;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.network.PacketDistributor;

import homeostatic.common.attachments.TemperatureData;
import homeostatic.common.attachments.ThermometerData;
import homeostatic.common.attachments.WaterData;
import homeostatic.common.attachments.WetnessData;
import homeostatic.common.biome.ClimateSettings;
import homeostatic.common.fluid.FluidInfo;
import homeostatic.common.item.IItemStackFluid;
import homeostatic.common.item.LeatherFlask;
import homeostatic.common.temperature.SubSeason;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.EnvironmentData;
import homeostatic.common.temperature.ThermometerInfo;
import homeostatic.common.water.WaterInfo;
import homeostatic.common.wetness.WetnessInfo;
import homeostatic.data.integration.ModIntegration;
import homeostatic.network.ITemperature;
import homeostatic.network.IThermometer;
import homeostatic.network.IWater;
import homeostatic.network.IWetness;
import homeostatic.network.NeoForgeTemperatureData;
import homeostatic.network.NeoForgeThermometerData;
import homeostatic.network.NeoForgeWaterData;
import homeostatic.network.NeoForgeWetnessData;
import homeostatic.mixin.ServerLevelAccessor;
import homeostatic.platform.services.IPlatform;
import homeostatic.registries.HomeostaticNeoForgeRegistries;
import homeostatic.util.CreateHelper;
import homeostatic.util.ItemStackFluidHelper;
import homeostatic.util.SereneSeasonsForgeHelper;
import homeostatic.util.VampirismHelperNeoForge;

public class NeoForgePlatform implements IPlatform {

    @Override
    public ResourceLocation getFluidResourceLocation(Fluid fluid) {
        return BuiltInRegistries.FLUID.getKey(fluid);
    }

    @Override
    public boolean isModLoaded(String name) {
        return ModList.get().isLoaded(name);
    }

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    @Override
    public double getCreateBlockRadiation(BlockState state, Double radiation) {
        return CreateHelper.getBlockRadiation(state, radiation);
    }

    @Override
    public String fluidStackTag() {
        return "Fluid";
    }

    @Override
    public Optional<FluidInfo> getFluidInfo(ItemStack stack) {
        if (stack.getItem() instanceof IItemStackFluid) {
            return Optional.of(ItemStackFluidHelper.getFluidInfo(stack));
        }

        return Optional.empty();
    }

    @Override
    public ItemStack drainFluid(ItemStack stack, long amount) {
        if (stack.getItem() instanceof IItemStackFluid) {
            ItemStackFluidHelper.drainFluid(stack, amount);
        }

        return stack;
    }

    @Override
    public ItemStack fillFluid(ItemStack stack, Fluid fluid, long amount) {
        if (stack.getItem() instanceof IItemStackFluid) {
            ItemStackFluidHelper.fillFluid(stack, fluid, amount);
        }

        return stack;
    }

    @Override
    public long getFluidCapacity(ItemStack stack) {
        if (stack.getItem() instanceof LeatherFlask) {
            return LeatherFlask.LEATHER_FLASK_CAPACITY;
        }

        return 0L;    }

    @Override
    public Component getDisplayName(Fluid fluid) {
        FluidInfo fluidInfo = new FluidInfo(fluid);

        return fluid.getFluidType().getDescription(new FluidStack(fluidInfo.fluid(), (int) fluidInfo.amount()));
    }

    @Override
    public ClimateSettings getClimateSettings(Holder<Biome> biomeHolder) {
        Biome.ClimateSettings climateSettings = biomeHolder.value().getModifiedClimateSettings();

        return new ClimateSettings(
            biomeHolder,
            climateSettings.hasPrecipitation(),
            climateSettings.temperature(),
            climateSettings.temperatureModifier(),
            climateSettings.downfall()
        );
    }

    @Override
    public SubSeason getSubSeason(ServerLevel level, Holder<Biome> biomeHolder) {
        if (isModLoaded(ModIntegration.SS_MODID) && SereneSeasonsForgeHelper.isSeasonDimension(level)) {
            return SereneSeasonsForgeHelper.getSubSeason(level);
        }

        return null;
    }

    @Override
    public Optional<? extends ITemperature> getTemperatureData(Player player) {
        return TemperatureData.getData(player);
    }

    @Override
    public void syncTemperatureData(ServerPlayer sp, EnvironmentData environmentData, BodyTemperature bodyTemperature) {
        PacketDistributor.sendToPlayer(sp, new NeoForgeTemperatureData(environmentData.getLocalTemperature(), bodyTemperature));
    }

    @Override
    public Optional<? extends IThermometer> getThermometerCapability(Player player) {
        return ThermometerData.getData(player);
    }

    @Override
    public void syncThermometerData(ServerPlayer sp, ThermometerInfo info) {
        PacketDistributor.sendToPlayer(sp, new NeoForgeThermometerData(info));
    }

    @Override
    public Optional<? extends IWater> getWaterCapabilty(Player player) {
        return WaterData.getData(player);
    }

    @Override
    public void syncWaterData(ServerPlayer sp, WaterInfo waterInfo) {
        PacketDistributor.sendToPlayer(sp, new NeoForgeWaterData(waterInfo));
    }

    @Override
    public Optional<? extends IWetness> getWetnessCapability(Player player) {
        return WetnessData.getData(player);
    }

    @Override
    public void syncWetnessData(ServerPlayer sp, WetnessInfo wetnessInfo) {
        PacketDistributor.sendToPlayer(sp, new NeoForgeWetnessData(wetnessInfo));
    }

    @Override
    public ServerLevelData getServerLevelData(ServerLevel level) {
        ServerLevelAccessor serverLevel = (ServerLevelAccessor) level;

        return serverLevel.getServerLevelData();
    }

    @Override
    public <T> void registerDataComponent(net.minecraft.resources.ResourceLocation name, net.minecraft.core.component.DataComponentType<T> component) {
        HomeostaticNeoForgeRegistries.COMPONENT_TYPE_DEFERRED_REGISTER.register(
            name.getPath(),
            () -> component
        );
    }

    @Override
    public boolean isVampire(Player player) {
        return VampirismHelperNeoForge.isVampire(player);
    }

}
