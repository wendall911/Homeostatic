package homeostatic.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.Homeostatic;
import homeostatic.util.OverlayHelper;

public class TemperatureOverlay extends GuiComponent {
    public final static ResourceLocation BURNING_OVERLAY = new ResourceLocation(Homeostatic.MODID, "textures/gui/burning.png");
    public final static ResourceLocation HYPERTHERMIA_OVERLAY = new ResourceLocation(Homeostatic.MODID, "textures/gui/hyperthermia.png");

    public TemperatureOverlay() {}

    public void render(Minecraft mc, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        player.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {

            if (data.getSkinTemperature() > BodyTemperature.SCALDING) {
                float alpha = 0.33F + (data.getSkinTemperature() - BodyTemperature.SCALDING);

                OverlayHelper.renderTexture(BURNING_OVERLAY, scaledWidth, scaledHeight, alpha);
            }

            if (data.getCoreTemperature() > BodyTemperature.HIGH) {
                float alpha = 0.33F + (data.getCoreTemperature() - BodyTemperature.HIGH);

                OverlayHelper.renderTexture(HYPERTHERMIA_OVERLAY, scaledWidth, scaledHeight, 0.4F);
            }

        });
    }

}
