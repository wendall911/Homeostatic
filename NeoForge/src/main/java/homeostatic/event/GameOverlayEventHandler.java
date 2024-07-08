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

        /*
         * For some reason "rightHeight" is only correct during phase when drawing the Health bar. I may just go with
         * using the same Mixin as Fabric and just ignore this, as it is not as well implemented as it was before.
         */
        if (ConfigHandler.loaded && !mc.getDebugOverlay().showDebugScreen() && !mc.options.hideGui && mc.gui.rightHeight != 39) {
            if (ConfigHandler.Common.debugEnabled()) {
                overlayManager.renderOverlay(guiGraphics);
            }

            overlayManager.renderWaterOverlay(guiGraphics, mc.gui.rightHeight);
            overlayManager.renderTemperatureOverlay(guiGraphics);
            overlayManager.renderEnhancedVisualsOverlay(guiGraphics);
            overlayManager.renderHydrationOverlay(guiGraphics, mc.gui.rightHeight);
        }

    }

    public void onRegisterOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(loc("overlay"), INSTANCE);
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, loc("water_level"), INSTANCE);
        event.registerAbove(VanillaGuiLayers.PLAYER_HEALTH, loc("temperature"), INSTANCE);
        event.registerBelowAll(loc("visuals"), INSTANCE);
        event.registerAbove(loc("water_level"), loc("hydration"), INSTANCE);
    }

}