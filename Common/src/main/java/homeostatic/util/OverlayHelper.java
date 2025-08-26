package homeostatic.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;

public class OverlayHelper {

    public static void renderTexture(GuiGraphics guiGraphics, ResourceLocation res, int scaledWidth, int scaledHeight, float alpha) {
        int i = ARGB.white(alpha);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, res, 0, 0, 0, 0, scaledWidth, scaledHeight, scaledWidth, scaledHeight, i);
    }

}
