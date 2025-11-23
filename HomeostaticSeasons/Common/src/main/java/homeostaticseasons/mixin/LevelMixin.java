package homeostaticseasons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import homeostaticseasons.api.SeasonWeather;

@Mixin(Level.class)
public abstract class LevelMixin {

    @Inject(method = "isRainingAt", at = @At("HEAD"), cancellable = true)
    public void homeostaticseasons$isRainingAt(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Level level = (Level)(Object)this;

        cir.setReturnValue(SeasonWeather.isRainingAt(level, pos));
    }

}
