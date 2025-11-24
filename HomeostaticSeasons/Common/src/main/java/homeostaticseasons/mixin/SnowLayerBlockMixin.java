package homeostaticseasons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import homeostaticseasons.HomeostaticSeasons;
import homeostaticseasons.api.SeasonWeather;
import homeostaticseasons.common.block.Meltable;
import homeostaticseasons.event.SnowAndIceEventHandler;

@Mixin(SnowLayerBlock.class)
public abstract class SnowLayerBlockMixin extends Block implements Meltable {

    @Shadow
    @Final
    public static int HEIGHT_IMPASSABLE;

    public SnowLayerBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void homeostaticseasons$onRandomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {

        /*
         * TODO a lot more testing on this. Not sure if we should check for manually placed or cache those as well.
         * It appears as though the temperature check does not work here, even after invalidating the cache.
         */
        if (SnowAndIceEventHandler.getPlacedMeltablesSavedData(level).isManuallyPlaced(pos)) {
            return;
        }

        SeasonWeather.invalidateCacheAt(pos);

        if (SeasonWeather.warmEnoughToRain(level.getBiome(pos).value(), pos, level)) {
            Block.dropResources(state, level, pos);
            BlockState replacedState = SnowAndIceEventHandler.getReplacedMeltablesSavedData(level).getReplaced(pos);

            if (replacedState != null) {
                if (replacedState.getProperties().contains(DoublePlantBlock.HALF)
                        && replacedState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    BlockState replacedUpperState = replacedState.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);

                    if (level.isUnobstructed(replacedState, pos, CollisionContext.empty())
                            && level.isUnobstructed(replacedUpperState, pos.above(), CollisionContext.empty())) {
                        HomeostaticSeasons.LOGGER.warn("Restoring double-block state");
                        level.setBlockAndUpdate(pos, replacedState);
                        level.setBlockAndUpdate(pos.above(), replacedUpperState);
                    }
                    else {
                        HomeostaticSeasons.LOGGER.warn("Unable to restore double-block state, removing block instead");
                        level.removeBlock(pos, false);
                    }
                }
                else if (level.isUnobstructed(replacedState, pos, CollisionContext.empty())) {
                    HomeostaticSeasons.LOGGER.warn("Restoring state {}: {}", pos, replacedState);
                    level.setBlockAndUpdate(pos, replacedState);
                }
                else {
                    HomeostaticSeasons.LOGGER.warn("Removing block instead");
                    level.removeBlock(pos, false);
                }
            }
            else {
                level.removeBlock(pos, false);
            }
        }
    }

}
