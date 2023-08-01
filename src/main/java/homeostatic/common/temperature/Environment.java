package homeostatic.common.temperature;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.joml.Vector3d;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

import homeostatic.common.TagManager;
import homeostatic.common.block.BlockRadiation;
import homeostatic.common.block.BlockRadiationManager;
import homeostatic.config.ConfigHandler;
import homeostatic.util.VecMath;

public class Environment {

    public static final float PARITY = TemperatureRange.PARITY.temperature;
    public static final float PARITY_LOW = TemperatureRange.PARITY_LOW.temperature;
    public static final float PARITY_HIGH = TemperatureRange.PARITY_HIGH.temperature;
    public static final float HOT = TemperatureRange.HOT.temperature;
    public static final float EXTREME_HEAT = TemperatureRange.EXTREME_HEAT.temperature;
    public static final float EXTREME_COLD = TemperatureRange.EXTREME_COLD.temperature;

    public static EnvironmentInfo get(ServerLevel world, ServerPlayer sp) {
        double radiation = 0.0;
        double waterVolume = 0;
        double waterBlocks = 0;
        double totalBlocks = 0;
        AtomicReference<Double> radiationReduction = new AtomicReference<>(1.0);
        Map<ChunkPos, LevelChunk> chunkMap = new HashMap<>();
        BlockPos pos = sp.blockPosition();
        Vec3 spPos = sp.getEyePosition(1.0F);
        BlockPos eyePos = new BlockPos((int)spPos.x(), (int)spPos.y(), (int)spPos.z());
        ResourceKey<Level> worldKey = world.dimension();
        MobEffectInstance effectInstance = sp.getEffect(MobEffects.FIRE_RESISTANCE);
        boolean inOverworld = worldKey.location().toString().contains(BuiltinDimensionTypes.OVERWORLD.location().toString());
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

        sp.getArmorSlots().forEach(armor -> {
            CompoundTag tags = armor.getTag();

            if ((tags != null && tags.contains("radiation_protection")) || armor.is(TagManager.Items.RADIATION_PROTECTED_ARMOR)) {
                radiationReduction.updateAndGet(v -> (double) (v - ConfigHandler.Common.getRadiationReductionPercent()));
            }
        });

        /*
         * Check blocks and calculate radiation.
         */
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

                    if ((x <= 5 && x >= -5) && (y <= 5) && (z <= 5 && z >= -5)) {
                        totalBlocks++;

                        if (isWater) {
                            waterBlocks++;
                        }
                    }

                    if (state.isAir()) continue;

                    // Only check up to three blocks up, and ignore radiation if fire resistance is active.
                    if (y <= 3 && effectInstance == null) {
                        BlockRadiation blockRadiation = BlockRadiationManager.getBlockRadiation(state.getBlock());

                        if (blockRadiation != null) {
                            boolean hasRadiation = true;

                            if (state.hasProperty(BlockStateProperties.LIT)) {
                                hasRadiation = state.getValue(BlockStateProperties.LIT);
                            }

                            /*
                             * Ignore campfire heat if campfire is being used as a smoker.
                             */
                            if (hasRadiation && Objects.requireNonNull(state.getBlock().toString()).contains("campfire")) {
                                for (int i = 1; i <= 5; i++) {
                                    if (hasRadiation && world.getBlockState(blockpos.above(i)).is(BlockTags.BEEHIVES)) {
                                        hasRadiation = false;
                                    }
                                }
                            }

                            if (hasRadiation) {
                                Vec3 vPos = new Vec3(blockpos.getX() + 0.5, blockpos.getY() + 0.5, blockpos.getZ() + 0.5);
                                double distance = VecMath.getDistance(sp, new Vector3d(vPos.x, vPos.y, vPos.z));
                                boolean obscured = VecMath.isBlockObscured(sp, vPos);

                                if (!state.getFluidState().isEmpty()) {
                                    double amount = state.getFluidState().getAmount() / 8.0;
                                    radiation += blockRadiation.getBlockRadiation(distance, obscured, amount, y);
                                } else {
                                    radiation += blockRadiation.getBlockRadiation(distance, obscured, y);
                                }
                            }
                        }
                    }
                }
            }
        }

        radiation = radiation * radiationReduction.get();
        waterVolume = waterBlocks == 0 ? 0 : waterBlocks / totalBlocks;

        return new EnvironmentInfo(isUnderground, isSheltered, radiation, waterVolume);
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
