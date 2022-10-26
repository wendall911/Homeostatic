package homeostatic.overlay;

import java.util.Objects;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import homeostatic.config.ConfigHandler;

public class OverlayManager {

    public static final OverlayManager INSTANCE = new OverlayManager();
    public final WaterHud waterHud = new WaterHud();
    public final WetnessOverlay wetnessOverlay = new WetnessOverlay();
    public final TemperatureOverlay temperatureOverlay = new TemperatureOverlay();
    public final TemperatureCenterOverlay temperatureCenterOverlay = new TemperatureCenterOverlay();
    public final TemperatureInfo temperatureInfo = new TemperatureInfo();
    public final EnhancedVisualsOverlay enhancedVisualsOverlay = new EnhancedVisualsOverlay();
    public final HydrationOverlay hydrationOverlay = new HydrationOverlay();

    private OverlayManager() {}

    public void render(PoseStack matrix, Overlay overlay, boolean scaled, int rightHeight) {
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

            overlay.render(matrix, mc, pos, scaledWidth, scaledHeight);

            matrix.popPose();
        }

    }

    public void renderOverlay(PoseStack matrix) {
        render(matrix, temperatureInfo, true, 0);
    }

    public void renderWaterOverlay(PoseStack matrix, int rightHeight) {
        render(matrix, waterHud, false, rightHeight);
        render(matrix, wetnessOverlay, false, 0);
    }

    public void renderTemperatureOverlay(PoseStack matrix) {
        switch (ConfigHandler.Client.hudOption()) {
            case "RIGHT_THERMOMETER":
                render(matrix, temperatureOverlay, false, 0);
                break;
            case "CENTER_GLOBE":
            default:
                render(matrix, temperatureCenterOverlay, false, 0);
                break;
        }
    }

    public void renderEnhancedVisualsOverlay(PoseStack matrix) {
        render(matrix, enhancedVisualsOverlay, false, 0);
    }

    public void renderHydrationOverlay(PoseStack matrix, int rightHeight) {
        render(matrix, hydrationOverlay, false, rightHeight);
    }

}