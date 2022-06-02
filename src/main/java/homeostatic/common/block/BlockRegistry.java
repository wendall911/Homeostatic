package homeostatic.common.block;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BlockRegistry {

    public static final Map<Block, BlockRadiation> RADIATION_BLOCKS = new HashMap<>();

    public static void init() {
        RADIATION_BLOCKS.put(Blocks.LAVA, new BlockRadiation(1550, true));
        RADIATION_BLOCKS.put(Blocks.CAMPFIRE, new BlockRadiation(5550, false));
        RADIATION_BLOCKS.put(Blocks.SOUL_CAMPFIRE, new BlockRadiation(8325, false));
        RADIATION_BLOCKS.put(Blocks.FIRE, new BlockRadiation(1300, false));
        RADIATION_BLOCKS.put(Blocks.SOUL_FIRE, new BlockRadiation(1950, false));
        RADIATION_BLOCKS.put(Blocks.MAGMA_BLOCK, new BlockRadiation(1200, false));
        RADIATION_BLOCKS.put(Blocks.TORCH, new BlockRadiation(350, false));
        RADIATION_BLOCKS.put(Blocks.SOUL_TORCH, new BlockRadiation(525, false));
        RADIATION_BLOCKS.put(Blocks.WALL_TORCH, new BlockRadiation(350, false));
        RADIATION_BLOCKS.put(Blocks.SOUL_WALL_TORCH, new BlockRadiation(525, false));
        RADIATION_BLOCKS.put(Blocks.LANTERN, new BlockRadiation(350, false));
        RADIATION_BLOCKS.put(Blocks.SOUL_LANTERN, new BlockRadiation(525, false));
    }

}
