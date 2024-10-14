package homeostatic.platform;

import java.util.Optional;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
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

import homeostatic.common.biome.ClimateSettings;
import homeostatic.network.ITemperature;
import homeostatic.network.IThermometer;
import homeostatic.network.IWater;
import homeostatic.network.IWetness;
import homeostatic.common.components.HomeostaticCardinalComponents;
import homeostatic.common.fluid.FluidInfo;
import homeostatic.common.item.IItemStackFluid;
import homeostatic.common.item.LeatherFlask;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.EnvironmentData;
import homeostatic.common.temperature.SubSeason;
import homeostatic.common.temperature.ThermometerInfo;
import homeostatic.common.water.WaterInfo;
import homeostatic.common.wetness.WetnessInfo;
import homeostatic.data.integration.ModIntegration;
import homeostatic.mixin.FabricBiomeAccessor;
import homeostatic.mixin.ServerLevelAccessor;
import homeostatic.platform.services.IPlatform;
import homeostatic.util.CreateHelper;
import homeostatic.util.FabricSeasonsHelper;
import homeostatic.util.ItemStackFluidHelper;
import homeostatic.util.SereneSeasonsFabricHelper;

public class FabricPlatform implements IPlatform {

    @Override
    public ResourceLocation getFluidResourceLocation(Fluid fluid) {
        return BuiltInRegistries.FLUID.getKey(fluid);
    }

    @Override
    public boolean isModLoaded(String name) {
        return FabricLoader.getInstance().isModLoaded(name);
    }

    @Override
    public boolean isPhysicalClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
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

        return 0L;
    }

    @Override
    public Component getDisplayName(Fluid fluid) {
        return fluid.defaultFluidState().createLegacyBlock().getBlock().getName();
    }

    @Override
    public ClimateSettings getClimateSettings(Holder<Biome> biomeHolder) {
        Biome.ClimateSettings climateSettings = ((FabricBiomeAccessor) (Object) biomeHolder.value()).homoestatic$getClimateSettings();

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
        if (isModLoaded(ModIntegration.SS_MODID) && SereneSeasonsFabricHelper.isSeasonDimension(level)) {
            return SereneSeasonsFabricHelper.getSubSeason(level);
        }
        else if (isModLoaded(ModIntegration.SEASONS_MODID) && FabricSeasonsHelper.isSeasonDimension(level)) {
            return SubSeason.getSubSeason(level, FabricSeasonsHelper.getSeasonDuration());
        }

        return null;
    }

    @Override
    public Optional<? extends ITemperature> getTemperatureData(Player player) {
        return HomeostaticCardinalComponents.TEMPERATURE_DATA.maybeGet(player);
    }

    @Override
    public void syncTemperatureData(ServerPlayer sp, EnvironmentData environmentData, BodyTemperature bodyTemperature) {
        HomeostaticCardinalComponents.TEMPERATURE_DATA.sync(sp);
    }

    @Override
    public Optional<? extends IThermometer> getThermometerCapability(Player player) {
        return HomeostaticCardinalComponents.THERMOMETER_DATA.maybeGet(player);
    }

    @Override
    public void syncThermometerData(ServerPlayer sp, ThermometerInfo info) {
        HomeostaticCardinalComponents.THERMOMETER_DATA.sync(sp);
    }

    @Override
    public Optional<? extends IWater> getWaterCapabilty(Player player) {
        return HomeostaticCardinalComponents.WATER_DATA.maybeGet(player);
    }

    @Override
    public void syncWaterData(ServerPlayer sp, WaterInfo waterInfo) {
        HomeostaticCardinalComponents.WATER_DATA.sync(sp);
    }

    @Override
    public Optional<? extends IWetness> getWetnessCapability(Player player) {
        return HomeostaticCardinalComponents.WETNESS_DATA.maybeGet(player);
    }

    @Override
    public void syncWetnessData(ServerPlayer sp, WetnessInfo wetnessInfo) {
       HomeostaticCardinalComponents.WETNESS_DATA.sync(sp);
    }

    @Override
    public ServerLevelData getServerLevelData(ServerLevel level) {
        ServerLevelAccessor serverLevel = (ServerLevelAccessor) level;

        return serverLevel.getServerLevelData();
    }

    @Override
    public <T> void registerDataComponent(ResourceLocation name, DataComponentType<T> component) {
        Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            name,
            component
        );
    }

    @Override
    public boolean isVampire(Player player) {
        return false;
    }

}
