package homeostatic.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class FontHelper {
    
    public static void draw(Minecraft mc, GuiGraphics guiGraphics, String label, int x, int y, int color, boolean drawShadow) {
        guiGraphics.drawString(mc.font, label, x, y, color, drawShadow);
    }

}
