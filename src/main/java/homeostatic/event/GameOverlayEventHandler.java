package homeostatic.event;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

import net.minecraft.world.entity.LivingEntity;
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
    private final IIngameOverlay HUD_OVERLAY;

    static {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(INSTANCE::onLoadComplete);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(INSTANCE::onModConfigReloading);
    }

    public GameOverlayEventHandler() {
        OVERLAY = OverlayRegistry.registerOverlayAbove(
            ForgeIngameGui.HUD_TEXT_ELEMENT,
            Homeostatic.MODID + ":overlay",
            (matrix, partialTicks, width, height, height2) -> callRenderOverlay(partialTicks, width)
        );

        HUD_OVERLAY = OverlayRegistry.registerOverlayTop("Water Level", (gui, poseStack, partialTick, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            boolean isMounted = minecraft.player.getVehicle() instanceof LivingEntity;

            if (!isMounted && !minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                overlayManager.renderHud(poseStack);
            }
        });
    }

    public void callRenderOverlay(PoseStack matrix, float partialTicks) {
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
            OverlayRegistry.enableOverlay(HUD_OVERLAY, true);
        }
    }

}
