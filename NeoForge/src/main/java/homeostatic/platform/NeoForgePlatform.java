package homeostatic.platform;

import java.util.Objects;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
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
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import homeostatic.common.attachments.TemperatureData;
import homeostatic.common.attachments.ThermometerData;
import homeostatic.common.attachments.WaterData;
import homeostatic.common.attachments.WetnessData;
import homeostatic.common.biome.ClimateSettings;
import homeostatic.common.fluid.FluidInfo;
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
import homeostatic.network.NeoForgeThermometerData;
import homeostatic.network.NeoForgeWaterData;
import homeostatic.network.NeoForgeWetnessData;
import homeostatic.network.NeoForgeTemperatureData;
import homeostatic.mixin.ServerLevelAccessor;
import homeostatic.platform.services.IPlatform;
import homeostatic.util.CreateHelper;
import homeostatic.util.SereneSeasonsForgeHelper;

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
        return FluidHandlerItemStack.FLUID_NBT_KEY;
    }

    @Override
    public Optional<FluidInfo> getFluidInfo(ItemStack stack) {
        @Nullable IFluidHandlerItem capablity = stack.getCapability(Capabilities.FluidHandler.ITEM);

        if (capablity != null) {
            FluidStack fluidStack = capablity.getFluidInTank(0);

            return Optional.of(new FluidInfo(
                fluidStack.getFluid(),
                fluidStack.getAmount(),
                fluidStack.isEmpty() ? new CompoundTag() : fluidStack.getOrCreateTag()
            ));
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public ItemStack drainFluid(ItemStack stack, long amount) {
        Objects.requireNonNull(stack.getCapability(Capabilities.FluidHandler.ITEM)).drain((int) amount, IFluidHandler.FluidAction.EXECUTE);

        updateDamage(stack);

        return stack;
    }

    @Override
    public ItemStack fillFluid(ItemStack stack, Fluid fluid, long amount) {
        FluidStack fluidStack = new FluidStack(fluid, (int) amount);

        Objects.requireNonNull(stack.getCapability(Capabilities.FluidHandler.ITEM)).fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);

        updateDamage(stack);

        return stack;
    }

    @Override
    public long getFluidCapacity(ItemStack stack) {
        return Objects.requireNonNull(stack.getCapability(Capabilities.FluidHandler.ITEM)).getTankCapacity(0);
    }

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
        PacketDistributor.PLAYER.with(sp).send(new NeoForgeTemperatureData(environmentData.getLocalTemperature(), bodyTemperature));
    }

    @Override
    public Optional<? extends IThermometer> getThermometerCapability(Player player) {
        return ThermometerData.getData(player);
    }

    @Override
    public void syncThermometerData(ServerPlayer sp, ThermometerInfo info) {
        PacketDistributor.PLAYER.with(sp).send(new NeoForgeThermometerData(info));
    }

    @Override
    public Optional<? extends IWater> getWaterCapabilty(Player player) {
        return WaterData.getData(player);
    }

    @Override
    public void syncWaterData(ServerPlayer sp, WaterInfo waterInfo) {
        PacketDistributor.PLAYER.with(sp).send(new NeoForgeWaterData(waterInfo));
    }

    @Override
    public Optional<? extends IWetness> getWetnessCapability(Player player) {
        return WetnessData.getData(player);
    }

    @Override
    public void syncWetnessData(ServerPlayer sp, WetnessInfo wetnessInfo) {
        PacketDistributor.PLAYER.with(sp).send(new NeoForgeWetnessData(wetnessInfo));
    }

    @Override
    public ServerLevelData getServerLevelData(ServerLevel level) {
        ServerLevelAccessor serverLevel = (ServerLevelAccessor) level;

        return serverLevel.getServerLevelData();
    }

    public void updateDamage(ItemStack stack) {
        if (stack.isDamageableItem()) {
            IFluidHandlerItem fluidHandlerItem = stack.getCapability(Capabilities.FluidHandler.ITEM);

            assert fluidHandlerItem != null;
            stack.setDamageValue(Math.min(stack.getMaxDamage(), stack.getMaxDamage() - fluidHandlerItem.getFluidInTank(0).getAmount()));
        }
    }

}
