package homeostatic.common.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import net.minecraftforge.registries.IForgeRegistry;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.Homeostatic;

public class HomeostaticBlocks {

    public static IForgeRegistry<Block>  BLOCK_REGISTRY;
    public static LiquidBlock PURIFIED_WATER_FLUID;

    public static void init(IForgeRegistry<Block> registry) {
        BLOCK_REGISTRY = registry;
        PURIFIED_WATER_FLUID = registerBlock(
                "purified_water_fluid",
                new LiquidBlock(() -> HomeostaticFluids.PURIFIED_WATER, BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));
    }

    public static LiquidBlock registerBlock(String name, LiquidBlock block) {
        Block blockConfigured = (block).setRegistryName(Homeostatic.loc(name));

        BLOCK_REGISTRY.register(blockConfigured);

        return block;
    }

}
