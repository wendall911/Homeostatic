package homeostatic.platform.services;

import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
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
import homeostatic.common.fluid.FluidInfo;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.EnvironmentData;
import homeostatic.common.temperature.SubSeason;
import homeostatic.common.temperature.ThermometerInfo;
import homeostatic.common.water.WaterInfo;
import homeostatic.common.wetness.WetnessInfo;

public interface IPlatform {

    ResourceLocation getFluidResourceLocation(Fluid fluid);

    boolean isModLoaded(String name);

    boolean isPhysicalClient();

    double getCreateBlockRadiation(BlockState state, Double radiation);

    String fluidStackTag();

    Optional<FluidInfo> getFluidInfo(ItemStack stack);

    ItemStack drainFluid(ItemStack stack, long amount);

    ItemStack fillFluid(ItemStack stack, Fluid fluid, long amount);

    long getFluidCapacity(ItemStack stack);

    Component getDisplayName(Fluid fluid);

    ClimateSettings getClimateSettings(Holder<Biome> biomeHolder);

    SubSeason getSubSeason(ServerLevel level, Holder<Biome> biomeHolder);

    Optional<? extends ITemperature> getTemperatureData(Player player);

    void syncTemperatureData(ServerPlayer sp, EnvironmentData environmentData, BodyTemperature bodyTemperature);

    Optional<? extends IThermometer> getThermometerCapability(Player player);

    void syncThermometerData(ServerPlayer sp, ThermometerInfo info);

    Optional<? extends IWater> getWaterCapabilty(Player player);

    void syncWaterData(ServerPlayer sp, WaterInfo waterInfo);

    Optional<? extends IWetness> getWetnessCapability(Player player);

    void syncWetnessData(ServerPlayer sp, WetnessInfo wetnessInfo);

    ServerLevelData getServerLevelData(ServerLevel level);

    <T> void registerDataComponent(ResourceLocation name, DataComponentType<T> component);

    boolean isVampire(Player player);

}
