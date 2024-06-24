package homeostatic.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

import homeostatic.config.ConfigHandler;
import homeostatic.overlay.OverlayManager;

public class GameOverlayEventHandler {

    public static void onHudRender(GuiGraphics guiGraphics, float tickDelta, int rightHeight) {
        OverlayManager overlayManager = OverlayManager.INSTANCE;
        Minecraft mc = Minecraft.getInstance();

        if (ConfigHandler.Common.debugEnabled() && !mc.options.renderDebug) {
            overlayManager.renderOverlay(guiGraphics);
        }

        if (!mc.options.hideGui && shouldDrawSurvivalElements(mc)) {
            overlayManager.renderWaterOverlay(guiGraphics, rightHeight);
        }

        if (!mc.options.hideGui && shouldDrawSurvivalElements(mc)) {
            overlayManager.renderTemperatureOverlay(guiGraphics);
        }

        if (!mc.options.hideGui && shouldDrawSurvivalElements(mc)) {
            overlayManager.renderEnhancedVisualsOverlay(guiGraphics);
        }

        if (!mc.options.hideGui && shouldDrawSurvivalElements(mc)) {
            overlayManager.renderHydrationOverlay(guiGraphics, rightHeight);
        }
    }

    public static boolean shouldDrawSurvivalElements(Minecraft mc) {
        return mc.gameMode.canHurtPlayer() && mc.getCameraEntity() instanceof Player;
    }

}
