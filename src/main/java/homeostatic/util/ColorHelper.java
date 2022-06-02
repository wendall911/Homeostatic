package homeostatic.util;

import java.awt.Color;

import homeostatic.config.ConfigHandler;

public class ColorHelper {

    public static int getTemperatureColor(int step) {
        Color bright = ConfigHandler.Client.temperatureColorBright();
        Color dark = ConfigHandler.Client.temperatureColorDark();

        return getRangeColor(dark, bright, 16, step + 1);
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
