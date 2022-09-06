package homeostatic.overlay;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.core.BlockPos;

public abstract class Overlay extends GuiComponent {

    public void render(PoseStack matrix, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {}

}
