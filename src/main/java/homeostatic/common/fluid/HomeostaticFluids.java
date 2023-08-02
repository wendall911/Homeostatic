package homeostatic.common.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.IForgeRegistry;

import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.Homeostatic;

public final class HomeostaticFluids {

    public static IForgeRegistry<Fluid> FLUID_REGISTRY;
    public static FlowingFluid PURIFIED_WATER;
    public static FlowingFluid PURIFIED_WATER_FLOWING;

    public static final ResourceLocation STILL_FLUID_TEXTURE = Homeostatic.loc("block/fluid/still_water");
    public static final ResourceLocation FLOWING_FLUID_TEXTURE = Homeostatic.loc("block/fluid/flowing_water");

    public static void init(IForgeRegistry<Fluid> registry) {
        FLUID_REGISTRY = registry;
        ForgeFlowingFluid.Properties PURIFIED_WATER_PROPS =
                new ForgeFlowingFluid.Properties(() -> PURIFIED_WATER, () -> PURIFIED_WATER_FLOWING, FluidAttributes.builder(STILL_FLUID_TEXTURE, FLOWING_FLUID_TEXTURE)
                        .color(0xCC3ABDFF).viscosity(1000)).bucket(() -> HomeostaticItems.PURIFIED_WATER_BUCKET).block(() -> HomeostaticBlocks.PURIFIED_WATER_FLUID)
                        .slopeFindDistance(3).explosionResistance(100F);

        PURIFIED_WATER = registerFluid("purified_water", new ForgeFlowingFluid.Source(PURIFIED_WATER_PROPS));
        PURIFIED_WATER_FLOWING = registerFluid("purified_water_flowing", new ForgeFlowingFluid.Flowing(PURIFIED_WATER_PROPS));
    }

    public static FlowingFluid registerFluid(String name, FlowingFluid fluid) {
        Fluid fluidConfigured = fluid.setRegistryName(Homeostatic.loc(name));

        FLUID_REGISTRY.register(fluidConfigured);

        return fluid;
    }

}
