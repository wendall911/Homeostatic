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

    static {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(INSTANCE::onLoadComplete);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(INSTANCE::onModConfigReloading);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(INSTANCE::onRegisterOverlays);
    }

    public GameOverlayEventHandler() {
        OVERLAY = (gui, poseStack, partialTick, width, height) -> {
            if (enabled && ConfigHandler.Client.debugEnabled() && !Minecraft.getInstance().options.renderDebug) {
                overlayManager.renderOverlay(poseStack);
            }
        };

        WATER_LEVEL_OVERLAY = (gui, poseStack, partialTick, width, height) -> {
            if (!Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements()) {
                overlayManager.renderWaterOverlay(poseStack);
            }
        };

        TEMPERATURE_OVERLAY = (gui, poseStack, partialTick, width, height) -> {
            if (!Minecraft.getInstance().options.hideGui && gui.shouldDrawSurvivalElements()) {
                overlayManager.renderTemperatureOverlay(poseStack);
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
        event.registerAboveAll(Homeostatic.MODID + "_overlay", INSTANCE.OVERLAY);
        event.registerBelowAll("water_level", INSTANCE.WATER_LEVEL_OVERLAY);
        event.registerAboveAll("temperature", INSTANCE.TEMPERATURE_OVERLAY);
    }

}
