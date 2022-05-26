package homeostatic.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import net.minecraftforge.fml.ModList;

import homeostatic.config.ConfigHandler;

public class OverlayManager {

    public static final OverlayManager INSTANCE = new OverlayManager();
    public final List<Info> lines = new ArrayList<>();

    private OverlayManager() {}

    public void init() {
        int lineNum = 1;

        this.lines.clear();

        for (String fieldName : ConfigHandler.Client.fields()) {
            boolean skip = false;

            switch (fieldName) {
                case "season":
                    if (ModList.get().isLoaded("sereneseasons")) {
                        lines.add(new SeasonInfo("", lineNum));
                    }
                    else {
                        skip = true;
                    }
                    break;
                case "temperature":
                    lines.add(new TemperatureInfo(ConfigHandler.Client.temperatureLabel(), lineNum));
                    break;
                case "water":
                    lines.add(new WaterInfo("Water: ", lineNum));
                    break;
                case "time":
                    lines.add(new TimeInfo(ConfigHandler.Client.timeLabel(), lineNum));
                    break;
            }

            if (!skip) {
                lineNum++;
            }
        }
    }

    public void renderOverlay(PoseStack matrix, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        BlockPos pos = Objects.requireNonNull(mc.getCameraEntity()).blockPosition();

        if (mc.level != null && mc.level.isLoaded(pos)) {
            float scale = (float) ConfigHandler.Client.scale();
            int scaledWidth = (int) (mc.getWindow().getGuiScaledWidth() / scale);
            int scaledHeight = (int) (mc.getWindow().getGuiScaledHeight() / scale);

            matrix.pushPose();
            matrix.scale(scale, scale, scale);

            for (final Info line : this.lines) {
                line.renderText(matrix, mc, pos, scaledWidth, scaledHeight);
            }

            matrix.popPose();
        }
    }

}
