package homeostatic.common.fluid;

import java.util.function.BiConsumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import static homeostatic.Homeostatic.prefix;

public final class HomeostaticFluids {

    public static Fluid PURIFIED_WATER = new PurifiedWater.Source();
    public static FlowingFluid PURIFIED_WATER_FLOWING = new PurifiedWater.Flowing();

    public static final ResourceLocation STILL_FLUID_TEXTURE = prefix("block/fluid/still_water");
    public static final ResourceLocation FLOWING_FLUID_TEXTURE = prefix("block/fluid/flowing_water");

    public static void init(BiConsumer<Fluid, ResourceLocation> consumer) {
        consumer.accept(PURIFIED_WATER, prefix("purified_water"));
        consumer.accept(PURIFIED_WATER_FLOWING, prefix("purified_water_flowing"));
    }


}
