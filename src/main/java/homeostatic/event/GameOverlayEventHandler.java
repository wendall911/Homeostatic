package homeostatic.event;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
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

    private final IIngameOverlay OVERLAY;
    private final IIngameOverlay WATER_LEVEL_OVERLAY;
    private final IIngameOverlay TEMPERATURE_OVERLAY;
    private final IIngameOverlay ENHANCED_VISUALS_OVERLAY;
    private final IIngameOverlay HYDRATION_OVERLAY;

    static {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(INSTANCE::onLoadComplete);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(INSTANCE::onModConfigReloading);
    }

    public GameOverlayEventHandler() {
        OVERLAY = OverlayRegistry.registerOverlayAbove(
            ForgeIngameGui.HUD_TEXT_ELEMENT,
            Homeostatic.MODID + ":overlay",
            (matrix, partialTicks, width, height, height2) -> callRenderOverlay(partialTicks)
        );

        WATER_LEVEL_OVERLAY = OverlayRegistry.registerOverlayBottom("Water Level", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();

            if (!minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                overlayManager.renderWaterOverlay(poseStack);
            }
        });

        TEMPERATURE_OVERLAY = OverlayRegistry.registerOverlayTop("Temperature", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();

            if (!minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                overlayManager.renderTemperatureOverlay(poseStack);
            }
        });

        ENHANCED_VISUALS_OVERLAY = OverlayRegistry.registerOverlayBottom("Enhanced Visuals", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();

            if (!minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                overlayManager.renderEnhancedVisualsOverlay(poseStack);
            }
        });

        HYDRATION_OVERLAY = OverlayRegistry.registerOverlayTop("Hydration", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();

            if (!minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                overlayManager.renderHydrationOverlay(poseStack);
            }
        });
    }

    public void callRenderOverlay(PoseStack matrix) {
        if (enabled && ConfigHandler.Client.debugEnabled() && !Minecraft.getInstance().options.renderDebug) {
            overlayManager.renderOverlay(matrix);
        }
    }

    public void onLoadComplete(FMLLoadCompleteEvent event) {
        ConfigHandler.Client.init();
        enabled = true;
    }

    public void onModConfigReloading(ModConfigEvent.Reloading event) {
        if (enabled && event.getConfig().getSpec() == ConfigHandler.Client.CONFIG_SPEC) {
            ConfigHandler.Client.init();
            OverlayRegistry.enableOverlay(OVERLAY, ConfigHandler.Client.debugEnabled());
            OverlayRegistry.enableOverlay(WATER_LEVEL_OVERLAY, true);
            OverlayRegistry.enableOverlay(TEMPERATURE_OVERLAY, true);
            OverlayRegistry.enableOverlay(ENHANCED_VISUALS_OVERLAY, true);
            OverlayRegistry.enableOverlay(HYDRATION_OVERLAY, true);
        }
    }

}
