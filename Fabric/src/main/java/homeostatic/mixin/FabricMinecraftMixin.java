package homeostatic.mixin;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import homeostatic.event.ClientPlayerEventHandler;

@Mixin(Minecraft.class)
public class FabricMinecraftMixin {

    @Shadow @Nullable
    public LocalPlayer player;
    @Shadow @Nullable public HitResult hitResult;

    @Shadow @Nullable public MultiPlayerGameMode gameMode;

    @Inject(method = "startUseItem", at = @At(value = "HEAD"))
    private void homeostatic$rightClickAir(CallbackInfo ci) {
        if (!this.gameMode.isDestroying()) {
            if (!this.player.isHandsBusy()) {
                for(InteractionHand interactionHand : InteractionHand.values()) {
                    ItemStack itemstack = this.player.getItemInHand(interactionHand);
                    boolean drinkWater = false;

                    if (this.hitResult != null) {
                        switch (this.hitResult.getType()) {
                            case BLOCK:
                                drinkWater = true;
                                break;
                        }
                    }

                    if (!drinkWater && itemstack.isEmpty() && (this.hitResult == null || this.hitResult.getType() == HitResult.Type.MISS)) {
                        drinkWater = true;
                    }

                    if (drinkWater) {
                        ClientPlayerEventHandler.drinkWater(player, player.level(), interactionHand);
                    }
                }
            }
        }
    }

}
