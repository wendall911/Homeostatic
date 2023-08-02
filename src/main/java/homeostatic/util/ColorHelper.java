package homeostatic.util;

import java.awt.Color;

import net.minecraft.util.Tuple;

import homeostatic.config.ConfigHandler;
import homeostatic.common.temperature.TemperatureRange;

public class ColorHelper {

    public static final Color neutral = ColorHelper.decode("#d9d8d4");

    public static int getTemperatureColor(Tuple<TemperatureRange, Integer> rangeStep) {
        Color hot = ConfigHandler.Client.temperatureColorHot();
        Color cold = ConfigHandler.Client.temperatureColorCold();

        return getTemperatureColorFromRange(rangeStep, hot, cold, false);
    }

    public static int getGlobeTemperatureColor(Tuple<TemperatureRange, Integer> rangeStep) {
        Color color = decode("#FFFFFF");

        return getTemperatureColorFromRange(rangeStep, color, color, true);
    }

    public static int getTemperatureColorFromRange(Tuple<TemperatureRange, Integer> rangeStep, Color hot, Color cold, boolean invert) {
        TemperatureRange range = rangeStep.getA();
        Color stepColor;

        if (range == TemperatureRange.HOT) {
            stepColor = hot;
        }
        else {
            stepColor = cold;
        }

        if (invert) {
            return getRangeColor(stepColor, neutral, 18, rangeStep.getB() + 1);
        }
        else {
            return getRangeColor(neutral, stepColor, 18, rangeStep.getB() + 1);
        }
    }

    public static int getLocalTemperatureColor(Tuple<TemperatureRange, Integer> rangeStep) {
        TemperatureRange range = rangeStep.getA();
        Color tempColor;

        if (range == TemperatureRange.COLD) {
            tempColor = ConfigHandler.Client.temperatureColorCold();
        }
        else {
            tempColor = ConfigHandler.Client.temperatureColorHot();
        }

        return getRangeColor(neutral, tempColor, 18, rangeStep.getB() + 1);
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
