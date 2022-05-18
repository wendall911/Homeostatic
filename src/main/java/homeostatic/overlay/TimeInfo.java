package homeostatic.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.Locale;
import java.util.Objects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import homeostatic.config.ConfigHandler;
import homeostatic.util.Alignment;
import homeostatic.util.ColorHelper;
import homeostatic.util.FontHelper;

public class TimeInfo extends Info {

    public TimeInfo(String label, int lineNum) {
        super(label, lineNum);
    }

    @Override
    public void renderText(PoseStack matrix, Minecraft mc, BlockPos pos, int scaledWidth, int scaledHeight) {
        long time = Objects.requireNonNull(mc.getCameraEntity()).getLevel().getDayTime();
        long hour = (time / 1000 + 6) % 24;
        long ampmHour = hour;
        long minute = (time % 1000) * 60 / 1000;
        String formattedTime;
        String ampm = "AM";

        if (ampmHour >= 12) {
            ampmHour -= 12;
            ampm = "PM";
        }
        if (ampmHour == 0) {
            ampmHour += 12;
        }
        formattedTime = String.format(Locale.ENGLISH, "%d:%02d %s", ampmHour, minute, ampm);


        int x = Alignment.getX(scaledWidth, mc.font.width(super.label + formattedTime));
        int y = Alignment.getY(scaledHeight, super.lineNum, mc.font.lineHeight);

        FontHelper.draw(mc, matrix, super.label, x, y, ConfigHandler.Client.labelColor().getRGB());

        x = x + mc.font.width(super.label);

        FontHelper.draw(mc, matrix, formattedTime, x, y, ColorHelper.getTimeColor(hour, minute));
    }

}
