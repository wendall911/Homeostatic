package homeostatic.common.temperature;

import com.mojang.math.Vector3d;

import java.util.HashMap;
import java.util.Map;

import homeostatic.Homeostatic;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

import homeostatic.common.block.BlockRadiation;
import homeostatic.common.block.BlockRegistry;
import homeostatic.util.VecMath;

public class Environment {

    public static EnvironmentData getData(ServerLevel world, ServerPlayer sp) {
        double radiation = 0.0;
        double waterVolume = 0;
        double waterBlocks = 0;
        double totalBlocks = 0;
        Map<ChunkPos, LevelChunk> chunkMap = new HashMap<>();
        BlockPos pos = sp.blockPosition();
        BlockPos eyePos = sp.eyeBlockPosition();
        ResourceKey<Level> worldKey = world.dimension();
        boolean inOverworld = worldKey.location().toString().contains(DimensionType.OVERWORLD_EFFECTS.toString());
        boolean isSubmerged = sp.isUnderWater() && sp.isInWater() && sp.isInWaterRainOrBubble();
        boolean isSheltered = true;
        boolean isUnderground = true;
        ChunkPos chunkPos;
        LevelChunk chunk;

        if (!inOverworld) {
            isUnderground = false;
            isSheltered = false;
        }

        if (isSubmerged) {
            isSheltered = false;
        }

        for (int x = -12; x <= 12; x++) {
            for (int z = -12; z <= 12; z++) {
                if (isSheltered && (x <= 2 && x >= -2) && (z <= 2 && z >= -2)) {
                    isSheltered = !world.canSeeSky(eyePos.offset(x, 0, z).above());
                }
                for (int y = -3; y <= 11; y++) {
                    chunkPos = new ChunkPos((pos.getX() + x) >> 4,(pos.getZ() + z) >> 4);
                    chunk = getChunk(world, chunkPos, chunkMap);

                    if (chunk == null) continue;

                    BlockPos blockpos = pos.offset(x, y, z);
                    PalettedContainer<BlockState> palette;

                    // If in the void, this gets weird, let's just catch and move on.
                    try {
                        palette = chunk.getSection((blockpos.getY() >> 4) - chunk.getMinSection()).getStates();

                    }
                    catch (Exception e) {
                        continue;
                    }

                    BlockState state = palette.get(blockpos.getX() & 15, blockpos.getY() & 15, blockpos.getZ() & 15);
                    boolean isWater = state.getMaterial().equals(Material.WATER);

                    if (isUnderground && y >= 0 && !isWater) {
                        isUnderground = !world.canSeeSky(eyePos.offset(x, y, z).above());
                    }

                    if ((x <= 5 && x >= -5) && (y <= 5) && (z <= 5 && z <= -5)) {
                        totalBlocks++;

                        if (isWater) {
                            waterBlocks++;
                        }
                    }

                    if (state.isAir()) continue;

                    if (y <= 3) {
                        BlockRadiation blockRadiation = BlockRegistry.RADIATION_BLOCKS.get(state.getBlock());

                        if (blockRadiation != null) {
                            Vec3 vPos = new Vec3(blockpos.getX() + 0.5, blockpos.getY() + 0.5, blockpos.getZ() + 0.5);
                            double distance = VecMath.getDistance(sp, new Vector3d(vPos.x, vPos.y, vPos.z));
                            boolean obscured = VecMath.isBlockObscured(sp, vPos);

                            if (blockRadiation.isFluid()) {
                                double amount = state.getFluidState().getAmount() / 8;
                                radiation += blockRadiation.getBlockRadiation(distance, obscured, amount, y);
                            } else {
                                radiation += blockRadiation.getBlockRadiation(distance, obscured, y);
                            }
                        }
                    }
                }
            }
        }

        waterVolume = waterBlocks == 0 ? 0 : waterBlocks / totalBlocks;

        return new EnvironmentData(isUnderground, isSheltered, radiation, waterVolume);
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
