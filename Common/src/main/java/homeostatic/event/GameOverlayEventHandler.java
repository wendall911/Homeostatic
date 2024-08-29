package homeostatic.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import homeostatic.config.ConfigHandler;
import homeostatic.overlay.OverlayManager;

public class GameOverlayEventHandler {

    public static void onHudRender(GuiGraphics guiGraphics, float tickDelta, int rightHeight) {
        OverlayManager overlayManager = OverlayManager.INSTANCE;
        Minecraft mc = Minecraft.getInstance();

        if (ConfigHandler.loaded && !mc.options.hideGui) {
            if (ConfigHandler.Common.debugEnabled()) {
                overlayManager.renderOverlay(guiGraphics);
            }

            overlayManager.renderWaterOverlay(guiGraphics, rightHeight);
            overlayManager.renderTemperatureOverlay(guiGraphics);
            overlayManager.renderEnhancedVisualsOverlay(guiGraphics);
            overlayManager.renderHydrationOverlay(guiGraphics, rightHeight);
        }
    }

}
