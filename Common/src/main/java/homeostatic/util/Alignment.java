package homeostatic.util;

public class Alignment {

    public static int getTextX(String pos, int screenWidth, int contentWidth, int offset, float textScale) {
        if (pos.endsWith("RIGHT")) {
            offset *= -1;
            offset = offset + (int) (screenWidth / textScale) - contentWidth;
        }
        else if (pos.endsWith("CENTER")) {
            offset = ((int) ((screenWidth - (contentWidth / 2)) / textScale) / 2) + offset;
        }

        return offset;
    }

    public static int getX(String pos, int screenWidth, int contentWidth, int offset) {
        if (!pos.endsWith("RIGHT")) {
            contentWidth *= 2;
        }

        return getTextX(pos, screenWidth, contentWidth, offset, 1.0F);
    }


    public static int getIconTextX(String pos, int screenWidth, int textWidth, int offset, float textScale, int iconWidth) {
        if (pos.endsWith("RIGHT") || pos.endsWith("LEFT")) {
            offset += (iconWidth + offset) - (int) (textWidth * textScale);
        }

        return getTextX(pos, screenWidth, textWidth, offset, textScale);
    }

    public static int getTextY(String pos, int screenHeight, int lineNum, int lineHeight, int offset, float textScale) {
        if (pos.startsWith("BOTTOM")) {
            offset *= -1;
            offset = offset + (int) (screenHeight / textScale) - (lineNum * (lineHeight + 1));
        }
        else {
            offset = offset + ((lineNum - 1) * lineHeight + 1);
        }

        return offset;
    }

    public static int getY(String pos, int screenHeight, int offset) {
        return getTextY(pos, screenHeight, 0, 0, offset, 1.0F);
    }

    public static int getIconTextY(String pos, int screenHeight, int offset, float textScale) {
        return getTextY(pos, screenHeight, 0, 0, offset, textScale);
    }

}