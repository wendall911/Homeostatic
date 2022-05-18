package homeostatic.util;

import homeostatic.config.ConfigHandler;

public class Alignment {

    public static int getX(int screenWidth, int textWidth) {
        String pos = ConfigHandler.Client.position();
        int x = ConfigHandler.Client.offsetX();

        if (pos.endsWith("RIGHT")) {
            x *= -1;
            x = x + screenWidth - textWidth;
        }

        return x;
    }

    public static int getY(int screenHeight, int lineNum, int lineHeight) {
        String pos = ConfigHandler.Client.position();
        int y = ConfigHandler.Client.offsetY() - 1;

        if (pos.startsWith("BOTTOM")) {
            y *= -1;
            y = y + screenHeight - (lineNum * (lineHeight + 1));
        }
        else {
            y = y + ((lineNum - 1) * lineHeight + 1);
        }

        return y;
    }

    public static int getCompassX(int screenWidth, int textWidth) {
        return (screenWidth - textWidth) / 2;
    }

    public static int getCompassY() {
        return ConfigHandler.Client.offsetY();
    }

}
