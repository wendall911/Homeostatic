package homeostatic.overlay;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;

public abstract class Overlay {

    public void render(GuiGraphics guiGraphics, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {}

}
