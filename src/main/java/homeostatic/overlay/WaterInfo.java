package homeostatic.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.Locale;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.config.ConfigHandler;
import homeostatic.util.Alignment;
import homeostatic.util.ColorHelper;
import homeostatic.util.FontHelper;
import homeostatic.util.TempHelper;

public class WaterInfo extends Info {

    public WaterInfo(String label, int lineNum) {
        super(label, lineNum);
    }

    @Override
    public void renderText(PoseStack matrix, Minecraft mc, BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        player.getCapability(CapabilityRegistry.STATS_CAPABILITY).ifPresent(data -> {
            int temperature = 5;
            String formattedTemp;

            String waterLevel = String.format("%d", data.getWaterLevel());
            String saturation = String.format("%.2f", data.getWaterSaturationLevel());

            formattedTemp = String.format(Locale.ENGLISH, "%s %s", waterLevel, saturation);

            int x = Alignment.getX(scaledWidth, mc.font.width(super.label) + mc.font.width(formattedTemp));
            int y = Alignment.getY(scaledHeight, super.lineNum, mc.font.lineHeight);

            FontHelper.draw(mc, matrix, super.label, x, y, ConfigHandler.Client.labelColor().getRGB());

            x = x + mc.font.width(super.label);

            FontHelper.draw(mc, matrix, formattedTemp, x, y, ColorHelper.getTemperatureColor(temperature));
        });
    }

}
