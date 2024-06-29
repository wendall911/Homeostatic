package homeostatic.event;

import net.minecraft.client.Minecraft;

import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;

import homeostatic.config.ConfigHandler;
import homeostatic.overlay.OverlayManager;

import static homeostatic.Homeostatic.loc;

public class GameOverlayEventHandler {

    private final OverlayManager overlayManager = OverlayManager.INSTANCE;
    public static final GameOverlayEventHandler INSTANCE = new GameOverlayEventHandler();

    private static boolean enabled = false;

    private final IGuiOverlay OVERLAY;
    private final IGuiOverlay WATER_LEVEL_OVERLAY;
    private final IGuiOverlay TEMPERATURE_OVERLAY;
    private final IGuiOverlay ENHANCED_VISUALS_OVERLAY;
    private final IGuiOverlay HYDRATION_OVERLAY;

    public GameOverlayEventHandler() {
        Minecraft mc = Minecraft.getInstance();

        OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
            if (enabled && ConfigHandler.Common.debugEnabled() && !mc.getDebugOverlay().showDebugScreen()) {
                overlayManager.renderOverlay(guiGraphics);
            }
        };

        WATER_LEVEL_OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
            if (!mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
                overlayManager.renderWaterOverlay(guiGraphics, gui.rightHeight);
            }
        };

        TEMPERATURE_OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
            if (!mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
                overlayManager.renderTemperatureOverlay(guiGraphics);
            }
        };

        ENHANCED_VISUALS_OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
            if (!mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
                overlayManager.renderEnhancedVisualsOverlay(guiGraphics);
            }
        };

        HYDRATION_OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
            if (!mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
                overlayManager.renderHydrationOverlay(guiGraphics, gui.rightHeight);
            }
        };
    }

    public void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(loc("overlay"), INSTANCE.OVERLAY);
        event.registerAbove(VanillaGuiOverlay.MOUNT_HEALTH.id(), loc("water_level"), INSTANCE.WATER_LEVEL_OVERLAY);
        event.registerAbove(VanillaGuiOverlay.MOUNT_HEALTH.id(), loc("temperature"), INSTANCE.TEMPERATURE_OVERLAY);
        event.registerBelowAll(loc("visuals"), INSTANCE.ENHANCED_VISUALS_OVERLAY);
        event.registerAbove(loc("water_level"), loc("hydration"), INSTANCE.HYDRATION_OVERLAY);
    }

}