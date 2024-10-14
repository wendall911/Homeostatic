package homeostatic.mixin;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import homeostatic.event.GameOverlayEventHandler;

@Mixin(Gui.class)
public abstract class NeoForgeGuiMixin {

    @Shadow private int tickCount;
    @Shadow
    @Final
    private Minecraft minecraft;

    /*
     * The NeoForge layering system makes absolutely zero sense given other mods can cancel events for rendering.
     * I suppose I could just render a layer above everything, but then we are just doing what this mixin does with
     * less headache and more compat with future versions? At minimum, it needs more documentation and explanation on
     * exactly what they were trying to achieve by adding the ability for other mods to disable UI elements from
     * this mod. Makes no sense. This ensures they can't bust our mod with canceling events.
     */
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/client/gui/GuiLayerManager;render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V"))
    private void homeostatic$renderAirLevel(GuiGraphics guiGraphics, DeltaTracker pDeltaTracker, CallbackInfo ci) {
        Player player = this.getCameraPlayer();

        if (player != null) {
            GameOverlayEventHandler.onHudRender(guiGraphics, this.tickCount, homeostatic$getRightHeight(player));
        }
    }

    @Unique
    private int homeostatic$getRightHeight(Player player) {
        LivingEntity livingEntity = this.getPlayerVehicleWithHealth();
        int x = this.getVisibleVehicleHeartRows(this.getVehicleMaxHearts(livingEntity));

        if (!this.minecraft.gameMode.canHurtPlayer()) return x * 10;

        int rightHeight = Math.max(1, x + 1) * 10;
        int y = player.getMaxAirSupply();
        int z = Math.min(player.getAirSupply(), y);

        if (player.isEyeInFluid(FluidTags.WATER) || z < y) {
            rightHeight += 10;
        }

        return 39 + rightHeight;
    }

    @Shadow
    private Player getCameraPlayer() {
        throw new IllegalStateException();
    }

    @Shadow
    private LivingEntity getPlayerVehicleWithHealth() {
        throw new IllegalStateException();
    }

    @Shadow
    private int getVehicleMaxHearts(LivingEntity mountEntity) {
        throw new IllegalStateException();
    }

    @Shadow
    private int getVisibleVehicleHeartRows(int mountHealth) {
        throw new IllegalStateException();
    }

}
