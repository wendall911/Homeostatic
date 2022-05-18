package homeostatic.util;

import java.awt.Color;

import sereneseasons.api.season.Season.SubSeason;

import homeostatic.config.ConfigHandler;

public class ColorHelper {

    public static int rgb(int r, int g, int b, int a) {
        return new Color(r, g, b, a).getRGB();
    }

    public static int getTemperatureColor(int step) {
        Color bright = ConfigHandler.Client.temperatureColorBright();
        Color dark = ConfigHandler.Client.temperatureColorDark();

        return getRangeColor(dark, bright, 16, step + 1);
    }

    public static int getTimeColor(long hour, long minute) {
        Color bright = ConfigHandler.Client.timeColorBright();
        Color dark = ConfigHandler.Client.timeColorDark();

        //Sunrise/Sunset
        if (hour == 5 || hour == 18) {
            if (hour == 5) {
                return getRangeColor(dark, bright, 60, (int)minute + 1);
            }
            else {
                return getRangeColor(bright, dark, 60, (int)minute + 1);
            }
        }
        //Night
        else if (hour < 5 || hour > 18) {
            return dark.getRGB();
        }

        //Day
        return bright.getRGB();
    }

    public static int getSeasonColor(SubSeason subSeason) {
        Color spring = decode("#80c71f");
        Color summer = decode("#ffd83d");
        Color fall = decode("#f9801d");
        Color winter = decode("#3ab3da");
        winter.getRGB();

        return switch (subSeason) {
            case EARLY_SPRING -> getRangeColor(winter, spring, 4, 3);
            case MID_SPRING -> getRangeColor(winter, spring, 4, 4);
            case LATE_SPRING -> getRangeColor(spring, summer, 4, 2);
            case EARLY_SUMMER -> getRangeColor(spring, summer, 4, 3);
            case MID_SUMMER -> getRangeColor(spring, summer, 4, 4);
            case LATE_SUMMER -> getRangeColor(summer, fall, 4, 2);
            case EARLY_AUTUMN -> getRangeColor(summer, fall, 4, 3);
            case MID_AUTUMN -> getRangeColor(summer, fall, 4, 4);
            case LATE_AUTUMN -> getRangeColor(fall, winter, 4, 2);
            case EARLY_WINTER -> getRangeColor(fall, winter, 4, 3);
            case MID_WINTER -> getRangeColor(fall, winter, 4, 4);
            case LATE_WINTER -> getRangeColor(winter, spring, 4, 2);
        };
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
