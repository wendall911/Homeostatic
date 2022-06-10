package homeostatic.common.block;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BlockRegistry {

    public static final Map<Block, BlockRadiation> RADIATION_BLOCKS = new HashMap<>();

    public static void init() {
        RADIATION_BLOCKS.put(Blocks.LAVA, new BlockRadiation(1550));
        RADIATION_BLOCKS.put(Blocks.CAMPFIRE, new BlockRadiation(5550));
        RADIATION_BLOCKS.put(Blocks.SOUL_CAMPFIRE, new BlockRadiation(8325));
        RADIATION_BLOCKS.put(Blocks.FURNACE, new BlockRadiation(1300));
        RADIATION_BLOCKS.put(Blocks.SMOKER, new BlockRadiation(1100));
        RADIATION_BLOCKS.put(Blocks.BLAST_FURNACE, new BlockRadiation(1800));
        RADIATION_BLOCKS.put(Blocks.NETHER_PORTAL, new BlockRadiation(350));
        RADIATION_BLOCKS.put(Blocks.FIRE, new BlockRadiation(1300));
        RADIATION_BLOCKS.put(Blocks.SOUL_FIRE, new BlockRadiation(1950));
        RADIATION_BLOCKS.put(Blocks.MAGMA_BLOCK, new BlockRadiation(1200));
        RADIATION_BLOCKS.put(Blocks.TORCH, new BlockRadiation(350));
        RADIATION_BLOCKS.put(Blocks.SOUL_TORCH, new BlockRadiation(525));
        RADIATION_BLOCKS.put(Blocks.WALL_TORCH, new BlockRadiation(350));
        RADIATION_BLOCKS.put(Blocks.SOUL_WALL_TORCH, new BlockRadiation(525));
        RADIATION_BLOCKS.put(Blocks.LANTERN, new BlockRadiation(350));
        RADIATION_BLOCKS.put(Blocks.SOUL_LANTERN, new BlockRadiation(525));
    }

}