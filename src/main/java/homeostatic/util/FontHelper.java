package homeostatic.util;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

public class FontHelper {
    
    public static void draw(Minecraft mc, PoseStack matrix, String label, int x, int y, int color) {
        mc.font.draw(matrix, label, x, y, color);
    }

}
