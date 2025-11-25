package homeostaticseasons.mixin;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.storage.WritableLevelData;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import homeostaticseasons.api.SeasonWeather;
import homeostaticseasons.common.block.Meltable;
import homeostaticseasons.config.ConfigHandler;
import homeostaticseasons.event.SnowAndIceEventHandler;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements WorldGenLevel {

    protected ServerLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, Supplier<ProfilerFiller> profiler, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, profiler, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    /*
     * Here we are effectively disabling the vanilla precipitation tick handling
     * so that we can implement our own via a separate functionality taking into account seasonal weather.
     */
    @Inject(method = "tickPrecipitation", at = @At("HEAD"), cancellable = true)
    private void homeostaticseasons$tickPrecipitationOverride(BlockPos pos, CallbackInfo ci) {
        BlockPos blockpos = this.getHeightmapPos(Types.MOTION_BLOCKING, pos);
        BlockPos blockpos1 = blockpos.below();
        Biome biome = this.getBiome(blockpos).value();

        if (biome.shouldFreeze(this.getLevel(), blockpos1)) {
            SnowAndIceEventHandler.cacheMeltableBlock(blockpos1);
            this.setBlockAndUpdate(blockpos1, Blocks.ICE.defaultBlockState());
        }

        if (this.isRaining()) {
            int i = this.getGameRules().getInt(GameRules.RULE_SNOW_ACCUMULATION_HEIGHT);

            Biome.Precipitation biome$precipitation = SeasonWeather.getPrecipitationType(biome, blockpos, this.getLevel());

            boolean shouldPlaceSnow = SeasonWeather.canPlaceSnow(biome, blockpos, this.getLevel());

            if (i > 0 && shouldPlaceSnow) {
                BlockState blockstate = this.getBlockState(blockpos);

                if (blockstate.is(Blocks.SNOW)) {
                    int j = blockstate.getValue(SnowLayerBlock.LAYERS);

                    if (j < Math.min(i, 8)) {
                        BlockState blockstate1 = blockstate.setValue(SnowLayerBlock.LAYERS, j + 1);
                        Block.pushEntitiesUp(blockstate, blockstate1, this.getLevel(), blockpos);
                        SnowAndIceEventHandler.cacheMeltableBlock(blockpos);
                        this.setBlockAndUpdate(blockpos, blockstate1);
                    }
                }
                else {
                    SnowAndIceEventHandler.cacheMeltableBlock(blockpos);
                    this.setBlockAndUpdate(blockpos, Blocks.SNOW.defaultBlockState());
                }
            }

            if (biome$precipitation != Precipitation.NONE) {
                BlockState blockstate2 = this.getBlockState(blockpos1);
                blockstate2.getBlock().handlePrecipitation(blockstate2, this.getLevel(), blockpos1, biome$precipitation);

                if (ConfigHandler.Common.seasonalSnowReplaceVegetation() && biome$precipitation == Biome.Precipitation.SNOW) {
                    Meltable.replaceBlockOnSnow(this.getLevel(), blockpos);
                }
            }
        }

        ci.cancel();
    }

}
