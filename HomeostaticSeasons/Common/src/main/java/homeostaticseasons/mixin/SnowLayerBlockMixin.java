package homeostaticseasons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import homeostaticseasons.api.SeasonWeather;
import homeostaticseasons.common.block.Meltable;
import homeostaticseasons.event.SnowAndIceEventHandler;

@Mixin(SnowLayerBlock.class)
public abstract class SnowLayerBlockMixin extends Block implements Meltable {

    public SnowLayerBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void homeostaticseasons$onRandomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (SnowAndIceEventHandler.getPlacedMeltablesSavedData(level).isManuallyPlaced(pos)) {
            return;
        }

        if (SeasonWeather.getPrecipitationType(level.getBiome(pos).value(), pos, level) != Biome.Precipitation.SNOW) {
            Block.dropResources(state, level, pos);
            BlockState replacedState = SnowAndIceEventHandler.getReplacedMeltablesSavedData(level).getReplaced(pos);

            if (replacedState != null) {
                if (replacedState.getProperties().contains(DoublePlantBlock.HALF)
                        && replacedState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                    BlockState replacedUpperState = replacedState.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);

                    if (level.isUnobstructed(replacedState, pos, CollisionContext.empty())
                            && level.isUnobstructed(replacedUpperState, pos.above(), CollisionContext.empty())) {
                        level.setBlockAndUpdate(pos, replacedState);
                        level.setBlockAndUpdate(pos.above(), replacedUpperState);
                    }
                    else {
                        level.removeBlock(pos, false);
                    }
                }
                else if (level.isUnobstructed(replacedState, pos, CollisionContext.empty())) {
                    level.setBlockAndUpdate(pos, replacedState);
                }
                else {
                    level.removeBlock(pos, false);
                }
            }
            else {
                level.removeBlock(pos, false);
            }
        }
    }

}
