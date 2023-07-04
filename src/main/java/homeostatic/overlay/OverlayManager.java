package homeostatic.overlay;

import java.util.Objects;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;

import homeostatic.config.ConfigHandler;

public class OverlayManager {

    public static final OverlayManager INSTANCE = new OverlayManager();
    public final WaterHud waterHud = new WaterHud();
    public final WetnessOverlay wetnessOverlay = new WetnessOverlay();
    public final TemperatureOverlay temperatureOverlay = new TemperatureOverlay();
    public final TemperatureGlobeOverlay temperatureGlobeOverlay = new TemperatureGlobeOverlay();
    public final TemperatureInfo temperatureInfo = new TemperatureInfo();
    public final EnhancedVisualsOverlay enhancedVisualsOverlay = new EnhancedVisualsOverlay();
    public final HydrationOverlay hydrationOverlay = new HydrationOverlay();

    private OverlayManager() {}

    public void render(GuiGraphics guiGraphics, Overlay overlay, boolean scaled, int rightHeight) {
        PoseStack matrix = guiGraphics.pose();
        Minecraft mc = Minecraft.getInstance();
        BlockPos pos = Objects.requireNonNull(mc.getCameraEntity()).blockPosition();
        int scaledWidth;
        int scaledHeight;

        if (mc.level != null && mc.level.isLoaded(pos)) {
            if (scaled) {
                float scale = (float) ConfigHandler.Client.scale();
                scaledWidth = (int) (mc.getWindow().getGuiScaledWidth() / scale);
                scaledHeight = (int) (mc.getWindow().getGuiScaledHeight() / scale);

                matrix.pushPose();
                matrix.scale(scale, scale, scale);
            }
            else {
                scaledWidth = mc.getWindow().getGuiScaledWidth();
                scaledHeight = mc.getWindow().getGuiScaledHeight() - rightHeight;
                matrix.pushPose();
            }

            overlay.render(guiGraphics, mc, pos, scaledWidth, scaledHeight);

            matrix.popPose();
        }

    }

    public void renderOverlay(GuiGraphics guiGraphics) {
        render(guiGraphics, temperatureInfo, true, 0);
    }

    public void renderWaterOverlay(GuiGraphics guiGraphics, int rightHeight) {
        if (ConfigHandler.Client.forceWaterBarPosition()) {
            rightHeight = 0;
        }

        render(guiGraphics, waterHud, false, rightHeight);
        render(guiGraphics, wetnessOverlay, false, 0);
    }

    public void renderTemperatureOverlay(GuiGraphics guiGraphics) {
        switch (ConfigHandler.Client.temperatureHudOption()) {
            case "RIGHT_THERMOMETER":
                render(guiGraphics, temperatureOverlay, false, 0);
                break;
            case "CENTER_GLOBE":
            default:
                render(guiGraphics, temperatureGlobeOverlay, false, 0);
                break;
        }
    }

    public void renderEnhancedVisualsOverlay(GuiGraphics guiGraphics) {
        render(guiGraphics, enhancedVisualsOverlay, false, 0);
    }

    public void renderHydrationOverlay(GuiGraphics guiGraphics, int rightHeight) {
        render(guiGraphics, hydrationOverlay, false, rightHeight);
    }

}