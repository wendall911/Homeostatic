package homeostatic.mixin;

import net.minecraft.server.level.ServerPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import homeostatic.event.PlayerEventHandler;
import homeostatic.util.WaterHelper;

@Mixin(ServerPlayer.class)
public class FabricServerPlayerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void homeostatic$tick(CallbackInfo ci) {
        PlayerEventHandler.onPlayerTickEvent((ServerPlayer) (Object) this);
    }

    @Inject(method = "restoreFrom", at = @At("RETURN"))
    private void homeostatic$restoreFrom(ServerPlayer original, boolean keepEverything, CallbackInfo ci) {
        PlayerEventHandler.onPlayerRespawn((ServerPlayer) (Object) this);
    }

    @Inject(method = "completeUsingItem", at = @At("HEAD"))
    private void homeostatic$completeUsingItem(CallbackInfo ci) {
        ServerPlayer sp = (ServerPlayer) (Object) this;

        if (!sp.getUseItem().isEmpty() && sp.isUsingItem()) {
            WaterHelper.drink(sp, sp.getUseItem(), true);
        }
    }

}
