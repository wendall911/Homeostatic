package homeostatic.util;

import java.awt.Color;

import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.Environment;
import homeostatic.config.ConfigHandler;

public class ColorHelper {

    private static final Color neutral = ColorHelper.decode("#f9ffff");

    public static int getTemperatureColor(float temperature) {
        Color hot = ConfigHandler.Client.temperatureColorHot();
        Color cold = ConfigHandler.Client.temperatureColorCold();
        int step;

        if (temperature > BodyTemperature.NORMAL) {
            if (temperature > BodyTemperature.HIGH) {
                step = 16;
            }
            else {
                step = (int) Math.floor((temperature - BodyTemperature.NORMAL) / 0.0103F);
            }

            return getRangeColor(neutral, hot, 16, step);
        }
        else {
            if (temperature < BodyTemperature.LOW) {
                step = 16;
            }
            else {
                step = (int) Math.floor((BodyTemperature.NORMAL - temperature) / 0.005F);
            }
            return getRangeColor(neutral, cold, 16, step);
        }
    }

    public static int getLocalTemperatureColor(float temperature) {
        Color hot = ConfigHandler.Client.temperatureColorHot();
        Color cold = ConfigHandler.Client.temperatureColorCold();
        int step;

        if (temperature > Environment.PARITY) {
            if (temperature > Environment.EXTREME_HEAT) {
                step = 16;
            }
            else {
                step = (int) Math.floor((temperature - Environment.PARITY) / 0.0905F);
            }

            return getRangeColor(neutral, hot, 16, step);
        }
        else {
            if (temperature < Environment.EXTREME_COLD) {
                step = 16;
            }
            else {
                step = (int) Math.floor((Environment.PARITY - temperature) / 4.0F);
            }
            return getRangeColor(neutral, cold, 16, step);
        }
    }

    public static Color decode(String color) {
        return Color.decode(color);
    }

    private static int getRangeColor(Color from, Color to, int steps, int step) {
        int diffRed = to.getRed() - from.getRed();
        int diffGreen = to.getGreen() - from.getGreen();
        int diffBlue = to.getBlue() - from.getBlue();

        return new Color(
            from.getRed() + ((diffRed * step) / steps),
            from.getGreen() + ((diffGreen * step) / steps),
            from.getBlue() + ((diffBlue * step) / steps)).getRGB();
    }

}
