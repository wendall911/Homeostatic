package homeostatic.common.fluid;

import java.util.function.BiConsumer;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import static homeostatic.Homeostatic.prefix;

public final class HomeostaticFluids {

    // TOOO: fix textures on NeoForge

    public static Identifier PURIFIED_WATER_ID = prefix("purified_water");
    public static Fluid PURIFIED_WATER = new PurifiedWater.Source();
    public static FlowingFluid PURIFIED_WATER_FLOWING = new PurifiedWater.Flowing();

    public static final Identifier STILL_FLUID_TEXTURE = prefix("block/purified_water_fluid");
    public static final Identifier FLOWING_FLUID_TEXTURE = prefix("block/purified_water_flowing");
    public static final Identifier OVERLAY_FLUID_TEXTURE = prefix("block/purified_water_fluid");

    public static void init(BiConsumer<Fluid, Identifier> consumer) {
        consumer.accept(PURIFIED_WATER, PURIFIED_WATER_ID);
        consumer.accept(PURIFIED_WATER_FLOWING, FLOWING_FLUID_TEXTURE);
    }

}
