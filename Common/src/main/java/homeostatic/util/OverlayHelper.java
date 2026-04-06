package homeostatic.util;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

public class OverlayHelper {

    public static void renderTexture(GuiGraphicsExtractor guiGraphics, Identifier res, int scaledWidth, int scaledHeight, float alpha) {
        int i = ARGB.white(alpha);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, res, 0, 0, 0, 0, scaledWidth, scaledHeight, scaledWidth, scaledHeight, i);
    }

}
