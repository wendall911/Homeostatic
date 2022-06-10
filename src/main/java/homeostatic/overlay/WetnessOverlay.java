package homeostatic.overlay;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.wetness.WetnessInfo;
import homeostatic.Homeostatic;
import homeostatic.util.OverlayHelper;

public class WetnessOverlay extends GuiComponent {

    public final static ResourceLocation WETNESS_OVERLAY = new ResourceLocation(Homeostatic.MODID, "textures/gui/wetness.png");

    public WetnessOverlay() {}

    public void render(Minecraft mc, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        player.getCapability(CapabilityRegistry.WETNESS_CAPABILITY).ifPresent(data -> {
            float wetnessPercentage = (float) data.getWetnessLevel() / (float) WetnessInfo.MAX_WETNESS_LEVEL;

            if (wetnessPercentage > 0.0F) {
                OverlayHelper.renderTexture(WETNESS_OVERLAY, scaledWidth, scaledHeight, wetnessPercentage);
            }

        });
    }

}
