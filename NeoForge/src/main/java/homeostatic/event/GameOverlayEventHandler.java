package homeostatic.event;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.Minecraft;

import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import homeostatic.config.ConfigHandler;
import homeostatic.overlay.OverlayManager;

import static homeostatic.Homeostatic.loc;

public class GameOverlayEventHandler implements LayeredDraw.Layer {

    private final OverlayManager overlayManager = OverlayManager.INSTANCE;
    public static final GameOverlayEventHandler INSTANCE = new GameOverlayEventHandler();

    private static boolean enabled = false;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();

        if (ConfigHandler.loaded && !mc.getDebugOverlay().showDebugScreen() && !mc.options.hideGui) {
            overlayManager.renderOverlay(guiGraphics);
            overlayManager.renderWaterOverlay(guiGraphics, mc.gui.rightHeight);
            overlayManager.renderTemperatureOverlay(guiGraphics);
            overlayManager.renderEnhancedVisualsOverlay(guiGraphics);
            overlayManager.renderHydrationOverlay(guiGraphics, mc.gui.rightHeight);
        }

    }

    public void onRegisterOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(loc("overlay"), INSTANCE);
        event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, loc("water_level"), INSTANCE);
        event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, loc("temperature"), INSTANCE);
        event.registerBelowAll(loc("visuals"), INSTANCE);
        event.registerAbove(loc("water_level"), loc("hydration"), INSTANCE);
    }

}