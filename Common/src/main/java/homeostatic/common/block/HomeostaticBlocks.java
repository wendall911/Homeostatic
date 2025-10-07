package homeostatic.common.block;

import java.util.function.BiConsumer;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import homeostatic.common.fluid.HomeostaticFluids;

import static homeostatic.Homeostatic.prefix;

public class HomeostaticBlocks {

    public static final ResourceLocation PURIFIED_WATER_FLUID_ID = prefix("purified_water_fluid");
    public static final LiquidBlock PURIFIED_WATER_FLUID = new PurifiedWaterBlock(
        HomeostaticFluids.PURIFIED_WATER_FLOWING,
        BlockBehaviour.Properties
            .ofFullCopy(Blocks.WATER)
            .noCollision()
            .strength(100.0F)
            .noLootTable()
            .setId(ResourceKey.create(Registries.BLOCK, PURIFIED_WATER_FLUID_ID))
    );

    public static void init(BiConsumer<Block, ResourceLocation> consumer) {
        consumer.accept(PURIFIED_WATER_FLUID, PURIFIED_WATER_FLUID_ID);
    }

}
