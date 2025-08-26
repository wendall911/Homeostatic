package homeostatic.overlay;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import homeostatic.Homeostatic;
import homeostatic.common.temperature.TemperatureThreshold;
import homeostatic.platform.Services;
import homeostatic.util.OverlayHelper;

public class EnhancedVisualsOverlay extends Overlay {

    public final static ResourceLocation BURNING_OVERLAY = Homeostatic.loc("textures/gui/burning.png");
    public final static ResourceLocation HYPERTHERMIA_OVERLAY = Homeostatic.loc("textures/gui/hyperthermia.png");

    public EnhancedVisualsOverlay() {}

    @Override
    public void render(GuiGraphics guiGraphics, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        if (player == null) return;

        Services.PLATFORM.getTemperatureData(player).ifPresent(data -> {
            if (data.getSkinTemperature() > TemperatureThreshold.SCALDING_WARNING.temperature) {
                float alpha = 0.1F + ((data.getSkinTemperature() - TemperatureThreshold.SCALDING_WARNING.temperature) * 10);

                OverlayHelper.renderTexture(guiGraphics, BURNING_OVERLAY, scaledWidth, scaledHeight, alpha);
            }

            if (data.getCoreTemperature() > TemperatureThreshold.WARNING_HIGH.temperature) {
                float alpha = 0.1F + ((data.getCoreTemperature() - TemperatureThreshold.WARNING_HIGH.temperature) * 10);

                OverlayHelper.renderTexture(guiGraphics, HYPERTHERMIA_OVERLAY, scaledWidth, scaledHeight, alpha);
            }
        });
    }

}
