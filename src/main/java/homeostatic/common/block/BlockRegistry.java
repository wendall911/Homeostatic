package homeostatic.common.block;

import com.mojang.math.Vector3d;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.phys.Vec3;

import homeostatic.Homeostatic;
import homeostatic.util.VecMath;

public class BlockRegistry {

    public static final Map<Block, BlockRadiation> RADIATION_BLOCKS = new HashMap<>();

    public static void init() {
        RADIATION_BLOCKS.put(Blocks.LAVA, new BlockRadiation(1550, true));
        RADIATION_BLOCKS.put(Blocks.CAMPFIRE, new BlockRadiation(5550, false));
        RADIATION_BLOCKS.put(Blocks.SOUL_CAMPFIRE, new BlockRadiation(1200, false));
        RADIATION_BLOCKS.put(Blocks.FIRE, new BlockRadiation(1300, false));
        RADIATION_BLOCKS.put(Blocks.MAGMA_BLOCK, new BlockRadiation(1200, false));
        RADIATION_BLOCKS.put(Blocks.TORCH, new BlockRadiation(350, false));
        RADIATION_BLOCKS.put(Blocks.WALL_TORCH, new BlockRadiation(350, false));
        RADIATION_BLOCKS.put(Blocks.LANTERN, new BlockRadiation(350, false));
    }

}
