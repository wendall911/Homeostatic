package homeostatic.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public abstract class Info {

    public String label;
    public int lineNum;

    protected Info(String label, int lineNum) {
        this.label = label;
        this.lineNum = lineNum;
    }

    public abstract void renderText(PoseStack matrix, Minecraft mc, BlockPos pos, int scaledWidth, int scaledHeight);

}
