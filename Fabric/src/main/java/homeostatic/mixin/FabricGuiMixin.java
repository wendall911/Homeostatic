package homeostatic.mixin;

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

import homeostatic.data.integration.ModIntegration;
import homeostatic.event.GameOverlayEventHandler;
import homeostatic.platform.Services;

@Mixin(Gui.class)
public abstract class FabricGuiMixin {

    @Shadow private int tickCount;
    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "renderPlayerHealth", at = @At("HEAD"))
    private void homeostatic$renderPlayerHealth(GuiGraphics guiGraphics, CallbackInfo ci) {
        Player player = this.getCameraPlayer();

        if (player != null) {
            GameOverlayEventHandler.onHudRender(guiGraphics, this.tickCount, homeostatic$getRightHeight(player));
        }
    }

    @Unique
    private int homeostatic$getRightHeight(Player player) {
        LivingEntity livingEntity = this.getPlayerVehicleWithHealth();
        int x = this.getVisibleVehicleHeartRows(this.getVehicleMaxHearts(livingEntity));

        if (Services.PLATFORM.isModLoaded(ModIntegration.LMBA_MODID)) {
            x = x + 1;
        }

        if (!this.minecraft.gameMode.canHurtPlayer()) return x * 10;

        int rightHeight = Math.max(1, x) * 10;
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
