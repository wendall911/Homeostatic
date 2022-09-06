package homeostatic.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import homeostatic.config.ConfigHandler;

public class OverlayManager {

    public static final OverlayManager INSTANCE = new OverlayManager();
    public final WaterHud waterHud = new WaterHud();
    public final WetnessOverlay wetnessOverlay = new WetnessOverlay();
    public final TemperatureOverlay temperatureOverlay = new TemperatureOverlay();
    public final TemperatureInfo temperatureInfo = new TemperatureInfo();
    public final EnhancedVisualsOverlay enhancedVisualsOverlay = new EnhancedVisualsOverlay();

    private OverlayManager() {}

    public void render(PoseStack matrix, Overlay overlay, boolean scaled) {
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
                scaledHeight = mc.getWindow().getGuiScaledHeight();
                matrix.pushPose();
            }

            overlay.render(matrix, mc, pos, scaledWidth, scaledHeight);

            matrix.popPose();
        }

    }

    public void renderOverlay(PoseStack matrix) {
        render(matrix, temperatureInfo, true);
    }

    public void renderWaterOverlay(PoseStack matrix) {
        render(matrix, waterHud, false);
        render(matrix, wetnessOverlay, false);
    }

    public void renderTemperatureOverlay(PoseStack matrix) {
        render(matrix, temperatureOverlay, false);
    }

    public void renderEnhancedVisualsOverlay(PoseStack matrix) {
        render(matrix, enhancedVisualsOverlay, false);
    }

}
