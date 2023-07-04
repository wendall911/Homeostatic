package homeostatic.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraftforge.registries.RegisterEvent;

import homeostatic.common.fluid.HomeostaticFluids;

public class HomeostaticBlocks {

    public static RegisterEvent.RegisterHelper<Block>  BLOCK_REGISTRY;
    public static LiquidBlock PURIFIED_WATER_FLUID;

    public static void init(RegisterEvent.RegisterHelper<Block> registryHelper) {
        BLOCK_REGISTRY = registryHelper;

        PURIFIED_WATER_FLUID = registerBlock(
                "purified_water_fluid",
                new LiquidBlock(() -> HomeostaticFluids.PURIFIED_WATER, BlockBehaviour.Properties.copy(Blocks.WATER).noCollission().strength(100.0F).noLootTable()));
    }

    public static LiquidBlock registerBlock(String name, LiquidBlock block) {
        BLOCK_REGISTRY.register(name, block);

        return block;
    }

}
