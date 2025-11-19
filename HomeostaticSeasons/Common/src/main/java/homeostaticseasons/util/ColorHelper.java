package homeostaticseasons.util;

import java.awt.Color;

public class ColorHelper extends technology.roughness.whitenoise.util.ColorHelper {

    public static int multiply(int color, int otherColor) {
        return (int) ((color / 255F) * (otherColor / 255F) * 255F);
    }

    public static int blendComponent(int color, int otherColor) {
        int blended;

        if (color < 128) {
            blended = multiply(color * 2, otherColor);
        }
        else {
            blended = 255 - (multiply((255 - color) * 2, 255 - otherColor));
        }

        return blended;
    }

    public static int blend(int color, int otherColor) {
        int r = blendComponent((color >> 16) & 255, (otherColor >> 16) & 255);
        int g = blendComponent((color >> 8) & 255, (otherColor >> 8) & 255);
        int b = blendComponent(color & 255, otherColor & 255);

        return (r & 255) << 16 | (g & 255) << 8 | (b & 255);
    }

    public static int mix(int color, int otherColor, float ratio) {
        Color c1 = new Color(color);
        Color c2 = new Color(otherColor);
        Color mixed = mix(c1, c2, ratio);

        return mixed.getRGB();
    }

    public static Color mix(Color color1, Color color2, double ratio) {
        // Clamp ratio between 0 and 1
        ratio = Math.max(0.0, Math.min(1.0, ratio));
        double inverseRatio = 1.0 - ratio;

        int r = (int) (color1.getRed()   * ratio + color2.getRed()   * inverseRatio);
        int g = (int) (color1.getGreen() * ratio + color2.getGreen() * inverseRatio);
        int b = (int) (color1.getBlue()  * ratio + color2.getBlue()  * inverseRatio);
        int a = (int) (color1.getAlpha() * ratio + color2.getAlpha() * inverseRatio);

        return new Color(r, g, b, a);
    }

    public static int saturate(int color, float saturation) {
        Color c = new Color(color);
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        int saturatedColor = Color.HSBtoRGB(hsb[0], Math.min(1.0f, hsb[1] * saturation), hsb[2]);

        return (saturatedColor & 0x00FFFFFF) | (c.getAlpha() << 24);
    }

}
