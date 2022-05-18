package homeostatic.util;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;

import homeostatic.config.ConfigHandler;

public class FontHelper {

    public static void draw(Minecraft mc, PoseStack matrix, TranslatableComponent label, int x, int y, int color) {
        if (ConfigHandler.Client.textShadow()) {
            mc.font.drawShadow(matrix, label, x, y, color);
        }
        else {
            mc.font.draw(matrix, label, x, y, color);
        }
    }
    
    public static void draw(Minecraft mc, PoseStack matrix, String label, int x, int y, int color) {
        draw(mc, matrix, label, x, y, color, ConfigHandler.Client.textShadow());
    }

    public static void draw(Minecraft mc, PoseStack matrix, String label, int x, int y, int color, boolean shadow) {
        if (shadow) {
            mc.font.drawShadow(matrix, label, x, y, color);
        }
        else {
            mc.font.draw(matrix, label, x, y, color);
        }
    }

}
