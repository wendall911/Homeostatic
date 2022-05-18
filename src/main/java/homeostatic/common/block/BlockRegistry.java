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

    private static final Map<Block, BlockRadiation> RADIATION_BLOCKS = new HashMap<>();

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

    public static double getBlockRadiation(ServerLevel world, ServerPlayer sp) {
        double radiation = 0.0;
        Map<ChunkPos, LevelChunk> chunkMap = new HashMap<>();
        BlockPos pos = sp.blockPosition();

        for (int x = -6; x <= 6; x++) {
            for (int z = -6; z <= 6; z++) {
                ChunkPos chunkPos = new ChunkPos((pos.getX() + x) >> 4,(pos.getZ() + z) >> 4);
                LevelChunk chunk = getChunk(world, chunkPos, chunkMap);
                for (int y = -3; y <= 1; y++) {
                    if (chunk == null) continue;

                    BlockPos blockpos = pos.offset(x, y, z);
                    PalettedContainer<BlockState> palette = chunk.getSection((blockpos.getY() >> 4) - chunk.getMinSection()).getStates();
                    BlockState state = palette.get(blockpos.getX() & 15, blockpos.getY() & 15, blockpos.getZ() & 15);

                    if (state.isAir()) continue;

                    BlockRadiation blockRadiation = RADIATION_BLOCKS.get(state.getBlock());

                    if (blockRadiation != null) {
                        Vec3 vPos = new Vec3(blockpos.getX() + 0.5, blockpos.getY() + 0.5, blockpos.getZ() + 0.5);
                        double distance = VecMath.getDistance(sp, new Vector3d(vPos.x, vPos.y, vPos.z));
                        boolean obscured = VecMath.isBlockObscured(sp, vPos);

                        if (blockRadiation.isFluid()) {
                            double amount = state.getFluidState().getAmount() / 8;
                            radiation += blockRadiation.getBlockRadiation(distance, obscured, amount);
                        }
                        else {
                            radiation += blockRadiation.getBlockRadiation(distance, obscured);
                        }
                    }
                }
            }
        }
        return radiation;
    }

    private static LevelChunk getChunk(Level world, ChunkPos pos, Map<ChunkPos, LevelChunk> chunks) {
        ChunkPos chunkPos = new ChunkPos(pos.x, pos.z);
        LevelChunk chunk;

        if (chunks.containsKey(chunkPos)) {
            chunk = chunks.get(chunkPos);
        }
        else {
            chunk = world.getChunkSource().getChunkNow(chunkPos.x, chunkPos.z);
            chunks.put(chunkPos, chunk);
        }

        return chunk;
    }

}
