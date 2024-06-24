package homeostatic.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import homeostatic.event.PlayerEventHandler;

@Mixin(ServerLevel.class)
public class FabricServerLevelMixin {

    @Inject(method = "addPlayer", at = @At("HEAD"))
    private void homeostatic$addPlayer(ServerPlayer player, CallbackInfo ci) {
        PlayerEventHandler.onEntityJoinLevel(player);
    }

}
