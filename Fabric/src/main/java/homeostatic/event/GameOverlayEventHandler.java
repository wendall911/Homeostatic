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

        if (ConfigHandler.loaded && !mc.getDebugOverlay().showDebugScreen() && !mc.options.hideGui) {
            if (ConfigHandler.Common.debugEnabled()) {
                overlayManager.renderOverlay(guiGraphics);
            }

            overlayManager.renderWaterOverlay(guiGraphics, rightHeight);
            overlayManager.renderTemperatureOverlay(guiGraphics);
            overlayManager.renderEnhancedVisualsOverlay(guiGraphics);
            overlayManager.renderHydrationOverlay(guiGraphics, rightHeight);
        }
    }

    public static boolean shouldDrawSurvivalElements(Minecraft mc) {
        return mc.gameMode != null && mc.gameMode.canHurtPlayer() && mc.getCameraEntity() instanceof Player;
    }

}
