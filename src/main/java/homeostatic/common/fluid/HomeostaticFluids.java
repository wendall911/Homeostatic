package homeostatic.common.fluid;

import java.util.function.Consumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.client.IFluidTypeRenderProperties;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.Homeostatic;

public final class HomeostaticFluids {

    public HomeostaticFluids() {}

    public static FluidType PURIFIED_WATER_TYPE;

    public static FlowingFluid PURIFIED_WATER;
    public static FlowingFluid PURIFIED_WATER_FLOWING;

    public static final ResourceLocation STILL_FLUID_TEXTURE = new ResourceLocation(Homeostatic.MODID, "block/fluid/still_water");
    public static final ResourceLocation FLOWING_FLUID_TEXTURE = new ResourceLocation(Homeostatic.MODID, "block/fluid/flowing_water");

    public static ForgeFlowingFluid.Properties fluidProperties() {
        return new ForgeFlowingFluid.Properties(
                () -> PURIFIED_WATER_TYPE,
                () -> PURIFIED_WATER,
                () -> PURIFIED_WATER_FLOWING).bucket(() -> HomeostaticItems.PURIFIED_WATER_BUCKET)
                .block(() -> HomeostaticBlocks.PURIFIED_WATER_FLUID)
                .slopeFindDistance(3)
                .explosionResistance(100F);
    }

    public static void init(RegisterEvent.RegisterHelper<Fluid> registerHelper) {
        PURIFIED_WATER = new ForgeFlowingFluid.Source(fluidProperties());
        PURIFIED_WATER_FLOWING = new ForgeFlowingFluid.Flowing(fluidProperties());

        registerHelper.register("purified_water_flowing", PURIFIED_WATER_FLOWING);
        registerHelper.register("purified_water", PURIFIED_WATER);
    }

    public static void initTypes(RegisterEvent.RegisterHelper<FluidType> registryHelper) {
        PURIFIED_WATER_TYPE = new FluidType(FluidType.Properties.create()
                .viscosity(1000)
                .lightLevel(3)
                .density(4000)
                .rarity(Rarity.UNCOMMON)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)) {

            @Override
            public void initializeClient(Consumer<IFluidTypeRenderProperties> consumer) {
                consumer.accept(new IFluidTypeRenderProperties() {
                    @Override
                    public ResourceLocation getStillTexture() {
                        return STILL_FLUID_TEXTURE;
                    }

                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return FLOWING_FLUID_TEXTURE;
                    }

                });
            }

        };

        registryHelper.register("purified_water_type", PURIFIED_WATER_TYPE);
    }

}
