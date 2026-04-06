package homeostatic.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import technology.roughness.whitenoise.util.ColorHelper;

public class FontHelper {
    
    public static void draw(Minecraft mc, GuiGraphicsExtractor guiGraphics, String label, int x, int y, int color, boolean drawShadow) {
        guiGraphics.text(mc.font, label, x, y, ColorHelper.RGBAtoARGB(color), drawShadow);
    }

}
