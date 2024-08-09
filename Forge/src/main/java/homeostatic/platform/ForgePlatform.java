package homeostatic.platform;

import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.ServerLevelData;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import homeostatic.common.biome.ClimateSettings;
import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.capabilities.ITemperature;
import homeostatic.common.capabilities.IThermometer;
import homeostatic.common.capabilities.IWater;
import homeostatic.common.capabilities.TemperatureCapability;
import homeostatic.common.capabilities.ThermometerCapability;
import homeostatic.common.capabilities.WaterCapability;
import homeostatic.common.capabilities.IWetness;
import homeostatic.common.capabilities.WetnessCapability;
import homeostatic.common.fluid.FluidInfo;
import homeostatic.common.temperature.SubSeason;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.EnvironmentData;
import homeostatic.common.temperature.ThermometerInfo;
import homeostatic.common.water.WaterInfo;
import homeostatic.common.wetness.WetnessInfo;
import homeostatic.data.integration.ModIntegration;
import homeostatic.mixin.ServerLevelAccessor;
import homeostatic.network.ForgeThermometerData;
import homeostatic.network.ForgeWaterData;
import homeostatic.network.ForgeWetnessData;
import homeostatic.network.ForgeTemperatureData;
import homeostatic.network.NetworkHandler;
import homeostatic.platform.services.IPlatform;
import homeostatic.util.CreateHelper;
import homeostatic.util.SereneSeasonsHelper;

public class ForgePlatform implements IPlatform {

    @Override
    public ResourceLocation getFluidResourceLocation(Fluid fluid) {
        return ForgeRegistries.FLUIDS.getKey(fluid);
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
    public Ingredient getStrictNBTIngredient(ItemStack stack) {
        return StrictNBTIngredient.of(stack);
    }

    @Override
    public double getCreateBlockRadiation(BlockState state, Double radiation) {
        return CreateHelper.getBlockRadiation(state, radiation);
    }

    @Override
    public Block getBlock(ResourceLocation loc) {
        return ForgeRegistries.BLOCKS.getValue(loc);
    }

    @Override
    public Fluid getFluid(ResourceLocation loc) {
        return ForgeRegistries.FLUIDS.getValue(loc);
    }

    @Override
    public Item getItem(ResourceLocation loc) {
        return ForgeRegistries.ITEMS.getValue(loc);
    }

    @Override
    public String fluidStackTag() {
        return FluidHandlerItemStack.FLUID_NBT_KEY;
    }

    @Override
    public Optional<FluidInfo> getFluidInfo(ItemStack stack) {
        return stack.getCapability(CapabilityRegistry.FLUID_ITEM_CAPABILITY)
            .map(handler -> handler.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.SIMULATE))
            .map(fluidStack -> new FluidInfo(
                fluidStack.getFluid(),
                fluidStack.getAmount(),
                fluidStack.isEmpty() ? new CompoundTag() : fluidStack.getOrCreateTag()
            ));
    }

    @Override
    public ItemStack drainFluid(ItemStack stack, long amount) {
        stack.getCapability(CapabilityRegistry.FLUID_ITEM_CAPABILITY).ifPresent(handler -> handler.drain((int) amount, IFluidHandler.FluidAction.EXECUTE));

        updateDamage(stack);

        return stack;
    }

    @Override
    public ItemStack fillFluid(ItemStack stack, Fluid fluid, long amount) {
        FluidStack fluidStack = new FluidStack(fluid, (int) amount);

        stack.getCapability(CapabilityRegistry.FLUID_ITEM_CAPABILITY).ifPresent(handler -> handler.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE));

        updateDamage(stack);

        return stack;
    }

    @Override
    public long getFluidCapacity(ItemStack stack) {
        IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityRegistry.FLUID_ITEM_CAPABILITY).orElse(null);

        return (long) fluidHandlerItem.getTankCapacity(0);
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
        if (isModLoaded(ModIntegration.SS_MODID) && SereneSeasonsHelper.isSeasonDimension(level)) {
            return SereneSeasonsHelper.getSubSeason(level);
        }

        return null;
    }

    @Override
    public Optional<? extends ITemperature> getTemperatureData(Player player) {
        return TemperatureCapability.getCapability(player).resolve();
    }

    @Override
    public void syncTemperatureData(ServerPlayer sp, EnvironmentData environmentData, BodyTemperature bodyTemperature) {
        NetworkHandler.INSTANCE.send(
            PacketDistributor.PLAYER.with(() -> sp),
            new ForgeTemperatureData(environmentData.getLocalTemperature(), bodyTemperature)
        );
    }

    @Override
    public Optional<? extends IThermometer> getThermometerCapability(Player player) {
        return ThermometerCapability.getCapability(player).resolve();
    }

    @Override
    public void syncThermometerData(ServerPlayer sp, ThermometerInfo info) {
        NetworkHandler.INSTANCE.send(
            PacketDistributor.PLAYER.with(() -> sp),
            new ForgeThermometerData(info)
        );
    }

    @Override
    public Optional<? extends IWater> getWaterCapabilty(Player player) {
        return WaterCapability.getCapability(player).resolve();
    }

    @Override
    public void syncWaterData(ServerPlayer sp, WaterInfo waterInfo) {
        NetworkHandler.INSTANCE.send(
            PacketDistributor.PLAYER.with(() -> sp),
            new ForgeWaterData(waterInfo)
        );
    }

    @Override
    public Optional<? extends IWetness> getWetnessCapability(Player player) {
        return WetnessCapability.getCapability(player).resolve();
    }

    @Override
    public void syncWetnessData(ServerPlayer sp, WetnessInfo wetnessInfo) {
        NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> sp),
                new ForgeWetnessData(wetnessInfo)
        );
    }

    @Override
    public ServerLevelData getServerLevelData(ServerLevel level) {
        ServerLevelAccessor serverLevel = (ServerLevelAccessor) level;

        return serverLevel.getServerLevelData();
    }

    public void updateDamage(ItemStack stack) {
        if (stack.isDamageableItem()) {
            IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityRegistry.FLUID_ITEM_CAPABILITY).orElse(null);

            stack.setDamageValue(Math.min(stack.getMaxDamage(), stack.getMaxDamage() - fluidHandlerItem.getFluidInTank(0).getAmount()));
        }
    }

}
