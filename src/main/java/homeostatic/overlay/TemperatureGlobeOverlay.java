package homeostatic.overlay;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.config.ConfigHandler;
import homeostatic.Homeostatic;
import homeostatic.util.Alignment;
import homeostatic.util.ColorHelper;
import homeostatic.util.FontHelper;
import homeostatic.util.TempHelper;

public class TemperatureGlobeOverlay extends Overlay {

    public final static ResourceLocation SPRITE = Homeostatic.loc("textures/gui/icons.png");
    protected final static int ICON_WIDTH = 18;
    protected final static int ICON_HEIGHT = 14;

    TemperatureGlobeOverlay() {}

    @Override
    public void render(GuiGraphics guiGraphics, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;
        PoseStack matrix = guiGraphics.pose();

        if (player == null) return;

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, SPRITE);

        player.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
            int offsetX = Alignment.getX(ConfigHandler.Client.globePosition(), scaledWidth, ICON_WIDTH,
                    ConfigHandler.Client.globeOffsetX());
            int pY = Alignment.getY(ConfigHandler.Client.globePosition(), scaledHeight, ConfigHandler.Client.globeOffsetY());
            float textScale = 0.5F;
            int textOffsetY = Alignment.getIconTextY(ConfigHandler.Client.globePosition(), scaledHeight,
                    ConfigHandler.Client.globeTextOffsetY(), textScale);
            int pV;
            int localPV;
            int pUOffset = 80; // sprite X offset
            String coreTempFormat = ConfigHandler.Client.showDegreeSymbol() ? "%.1f°" : "%.1f";
            String localTempFormat = ConfigHandler.Client.showDegreeSymbol() ? "%.0f°" : "%.0f";
            String coreTempSmall = String.format(coreTempFormat, TempHelper.convertMcTemp(data.getCoreTemperature(),
                    ConfigHandler.Client.useFahrenheit()));
            String localTemp = String.format(localTempFormat, TempHelper.convertMcTemp(data.getLocalTemperature(),
                    ConfigHandler.Client.useFahrenheit()));

            AtomicBoolean showTemperature = new AtomicBoolean(ConfigHandler.Common.showTemperatureValues());
            int coreOffsetX = Alignment.getIconTextX(ConfigHandler.Client.globePosition(), scaledWidth,
                    mc.font.width(coreTempSmall), ConfigHandler.Client.globeOffsetX(), textScale, ICON_WIDTH);
            int localOffsetX = Alignment.getIconTextX(ConfigHandler.Client.globePosition(), scaledWidth,
                    mc.font.width(localTemp), ConfigHandler.Client.globeOffsetX(), textScale, ICON_WIDTH);
            Tuple<TempHelper.TemperatureRange, Integer> localRangeStep = TempHelper.getLocalTemperatureRangeStep(data.getLocalTemperature());
            Tuple<TempHelper.TemperatureRange, Integer> coreRangeStep = TempHelper.getBodyTemperatureRangeStep(data.getCoreTemperature());
            pV = getTempOffset(coreRangeStep);
            localPV = getTempOffset(localRangeStep);

            guiGraphics.blit(SPRITE, offsetX, pY, pUOffset, pV, ICON_WIDTH, ICON_HEIGHT);
            guiGraphics.blit(SPRITE, offsetX, pY, pUOffset + ICON_WIDTH, localPV, ICON_WIDTH, ICON_HEIGHT);

            if (ConfigHandler.Common.requireThermometer()) {
                player.getCapability(CapabilityRegistry.THERMOMETER_CAPABILITY).ifPresent(thermometer -> {
                    showTemperature.set(thermometer.hasThermometer());
                });
            }

            if (showTemperature.get()) {
                matrix.scale(textScale, textScale, textScale);

                FontHelper.draw(mc, guiGraphics, localTemp, localOffsetX - 1, textOffsetY - 19, ColorHelper.getLocalTemperatureColor(localRangeStep), true);
                FontHelper.draw(mc, guiGraphics, coreTempSmall, coreOffsetX - 1, textOffsetY, ColorHelper.getGlobeTemperatureColor(coreRangeStep), true);
            }

        });
    }

    private int getTempOffset(Tuple<TempHelper.TemperatureRange, Integer> rangeStep) {
        TempHelper.TemperatureRange range = rangeStep.getA();
        int step = rangeStep.getB() / 2;
        int offset;

        if (range == TempHelper.TemperatureRange.COLD) {
            offset = 120 + (step * 15);
        }
        else {
            offset = 120 - (step * 15);
        }

        return offset;
    }

}