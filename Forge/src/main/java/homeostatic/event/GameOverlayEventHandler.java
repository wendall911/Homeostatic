package homeostatic.event;

import net.minecraft.client.Minecraft;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import homeostatic.config.ConfigHandler;
import homeostatic.overlay.OverlayManager;
import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(modid=Homeostatic.MODID, value= Dist.CLIENT)
public class GameOverlayEventHandler {

    private final OverlayManager overlayManager = OverlayManager.INSTANCE;
    public static final GameOverlayEventHandler INSTANCE = new GameOverlayEventHandler();

    private static boolean enabled = false;

    private final IGuiOverlay OVERLAY;
    private final IGuiOverlay WATER_LEVEL_OVERLAY;
    private final IGuiOverlay TEMPERATURE_OVERLAY;
    private final IGuiOverlay ENHANCED_VISUALS_OVERLAY;
    private final IGuiOverlay HYDRATION_OVERLAY;

    static {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(INSTANCE::onRegisterOverlays);
    }

    public GameOverlayEventHandler() {
        Minecraft mc = Minecraft.getInstance();

        OVERLAY = (gui, guiGraphics, partialTick, width, height) -> {
            if (enabled && ConfigHandler.Common.debugEnabled() && !mc.options.renderDebug) {
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

    @SubscribeEvent
    public void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("overlay", INSTANCE.OVERLAY);
        event.registerAbove(VanillaGuiOverlay.MOUNT_HEALTH.id(), "water_level", INSTANCE.WATER_LEVEL_OVERLAY);
        event.registerAbove(VanillaGuiOverlay.MOUNT_HEALTH.id(), "temperature", INSTANCE.TEMPERATURE_OVERLAY);
        event.registerBelowAll("visuals", INSTANCE.ENHANCED_VISUALS_OVERLAY);
        event.registerAbove(Homeostatic.loc("water_level"), "hydration", INSTANCE.HYDRATION_OVERLAY);
    }

}