package homeostaticseasons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import homeostaticseasons.api.SeasonWeather;
import homeostaticseasons.common.block.Meltable;
import homeostaticseasons.event.SnowAndIceEventHandler;

@Mixin(IceBlock.class)
public abstract class IceBlockMixin extends Block implements Meltable {

    @Shadow
    protected abstract void melt(BlockState state, Level level, BlockPos pos);

    public IceBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void homeostaticseasons$onRandomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if (this == Blocks.ICE && SeasonWeather.warmEnoughToRain(level.getBiome(pos).value(), pos, level)) {
            if (!SnowAndIceEventHandler.getPlacedMeltablesSavedData(level).isManuallyPlaced(pos)) {
                this.melt(state, level, pos);
            }
            else {
                boolean neaarWater = false;

                for (BlockPos nearbyPos : BlockPos.withinManhattan(pos, 1, 1, 1)) {
                    if (level.getFluidState(nearbyPos).is(FluidTags.WATER)) {
                        neaarWater = true;
                        break;
                    }
                }

                if (neaarWater) {
                    this.melt(state, level, pos);
                }
            }
        }
    }

}
