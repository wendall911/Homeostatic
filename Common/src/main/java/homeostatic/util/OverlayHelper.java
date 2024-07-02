package homeostatic.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class OverlayHelper {

    public static void renderTexture(ResourceLocation res, int scaledWidth, int scaledHeight, float alpha) {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = null;
        float width = scaledWidth;
        float height = scaledHeight;

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.setShaderTexture(0, res);
        bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(0.0F, height, -90.0F).setUv(0.0F, 1.0F);
        bufferbuilder.addVertex(width, height, -90.0F).setUv(1.0F, 1.0F);
        bufferbuilder.addVertex(width, 0.0F, -90.0F).setUv(1.0F, 0.0F);
        bufferbuilder.addVertex(0.0F, 0.0F, -90.0F).setUv(0.0F, 0.0F);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
