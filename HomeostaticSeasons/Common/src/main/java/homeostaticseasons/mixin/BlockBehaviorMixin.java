package homeostaticseasons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import homeostaticseasons.common.block.Meltable;
import homeostaticseasons.event.SnowAndIceEventHandler;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviorMixin {

    @Inject(method = "onPlace", at = @At("HEAD"))
    public void checkIfMeltablePlaced(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel && !SnowAndIceEventHandler.isCachedMeltableBlock(pos) && state.getBlock() instanceof Meltable meltable) {
            meltable.onMeltableManuallyPlaced(serverLevel, pos);
        }
    }

    @Inject(method = "onRemove", at = @At("HEAD"))
    public void checkIfMeltableRemoved(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo ci) {
        if (level instanceof ServerLevel serverLevel && state.getBlock() instanceof Meltable meltable && newState.isAir()) {
            meltable.onMeltableReplaced(serverLevel, pos);
        }
    }

}
