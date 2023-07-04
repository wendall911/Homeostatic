package homeostatic.event;

import net.minecraft.client.Minecraft;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import homeostatic.config.ConfigHandler;
import homeostatic.overlay.OverlayManager;
import homeostatic.Homeostatic;

@EventBusSubscriber(modid=Homeostatic.MODID, value=Dist.CLIENT)
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
        FMLJavaModLoadingContext.get().getModEventBus().addListener(INSTANCE::onLoadComplete);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(INSTANCE::onModConfigReloading);
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

    public void onLoadComplete(FMLLoadCompleteEvent event) {
        ConfigHandler.Client.init();
        enabled = true;
    }

    public void onModConfigReloading(ModConfigEvent.Reloading event) {
        if (enabled && event.getConfig().getSpec() == ConfigHandler.Client.CONFIG_SPEC) {
            ConfigHandler.Client.init();
        }
    }

    public void onRegisterOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("overlay", INSTANCE.OVERLAY);
        event.registerAboveAll("water_level", INSTANCE.WATER_LEVEL_OVERLAY);
        event.registerAboveAll("temperature", INSTANCE.TEMPERATURE_OVERLAY);
        event.registerBelowAll("visuals", INSTANCE.ENHANCED_VISUALS_OVERLAY);
        event.registerAboveAll("hydration", INSTANCE.HYDRATION_OVERLAY);
    }

}