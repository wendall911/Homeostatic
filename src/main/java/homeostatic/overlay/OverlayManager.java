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

    private OverlayManager() {}

    public void renderOverlay(PoseStack matrix) {
        Minecraft mc = Minecraft.getInstance();
        BlockPos pos = Objects.requireNonNull(mc.getCameraEntity()).blockPosition();

        if (mc.level != null && mc.level.isLoaded(pos)) {
            float scale = (float) ConfigHandler.Client.scale();
            int scaledWidth = (int) (mc.getWindow().getGuiScaledWidth() / scale);
            int scaledHeight = (int) (mc.getWindow().getGuiScaledHeight() / scale);

            matrix.pushPose();
            matrix.scale(scale, scale, scale);

            temperatureInfo.renderText(matrix, mc, pos, scaledWidth, scaledHeight);

            matrix.popPose();
        }
    }

    public void renderHud(PoseStack matrix) {
        Minecraft mc = Minecraft.getInstance();
        BlockPos pos = Objects.requireNonNull(mc.getCameraEntity()).blockPosition();

        if (mc.level != null && mc.level.isLoaded(pos)) {
            int scaledWidth = mc.getWindow().getGuiScaledWidth();
            int scaledHeight = mc.getWindow().getGuiScaledHeight();

            matrix.pushPose();

            waterHud.render(matrix, mc, scaledWidth, scaledHeight);
            wetnessOverlay.render(mc, scaledWidth, scaledHeight);
            temperatureOverlay.render(mc, scaledWidth, scaledHeight);

            matrix.popPose();
        }
    }

}
