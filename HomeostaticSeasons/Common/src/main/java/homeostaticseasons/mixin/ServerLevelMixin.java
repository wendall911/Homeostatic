package homeostaticseasons.mixin;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import homeostaticseasons.HomeostaticSeasons;
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
     * Determine the precipitation type based on the current season and biome.
     */
    @Redirect(method="tickPrecipitation", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    public Biome.Precipitation homeostaticseasons$tickPrecipitation(Biome biome, BlockPos pos) {
        return SeasonWeather.getPrecipitationType(biome, pos, (ServerLevel)(Object) this);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0), method = "tickPrecipitation(Lnet/minecraft/core/BlockPos;)V", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void homeostaticseasons$addMeltableIce(BlockPos pos, CallbackInfo ci, BlockPos blockPos, BlockPos blockPos2, Biome biome) {
        SnowAndIceEventHandler.trackMeltableBlock(blockPos2);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 1), method = "tickPrecipitation(Lnet/minecraft/core/BlockPos;)V", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void homeostaticseasons$addMeltableLayeredSnow(BlockPos pos, CallbackInfo ci, BlockPos blockPos, BlockPos blockPos2, Biome biome, int i, BlockState blockState, int j, BlockState blockState2) {
        SnowAndIceEventHandler.trackMeltableBlock(blockPos);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 2), method = "tickPrecipitation(Lnet/minecraft/core/BlockPos;)V", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void homeostaticseasons$addMeltableSnow(BlockPos pos, CallbackInfo ci, BlockPos blockPos, BlockPos blockPos2, Biome biome) {
        SnowAndIceEventHandler.trackMeltableBlock(blockPos);
    }

    @Inject(
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/Block;handlePrecipitation(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/biome/Biome$Precipitation;)V"
        ),
        method = "tickPrecipitation(Lnet/minecraft/core/BlockPos;)V",
        locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void homeostaticseasons$setReplacedMeltable(BlockPos pos, CallbackInfo ci, BlockPos blockPos, BlockPos blockPos2, Biome biome, int i, Biome.Precipitation precipitation, BlockState blockState3) {
        if (ConfigHandler.Common.seasonalSnowReplaceVegetation() && precipitation == Biome.Precipitation.SNOW) {
            Meltable.replaceBlockOnSnow((ServerLevel)(Object)this, blockPos, biome);
        }
    }


}
