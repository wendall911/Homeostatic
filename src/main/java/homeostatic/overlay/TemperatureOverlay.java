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
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.config.ConfigHandler;
import homeostatic.Homeostatic;
import homeostatic.util.Alignment;
import homeostatic.util.ColorHelper;
import homeostatic.util.FontHelper;
import homeostatic.util.TempHelper;

public class TemperatureOverlay extends Overlay {

    public final static ResourceLocation SPRITE = Homeostatic.loc("textures/gui/icons.png");
    protected final static int ICON_WIDTH = 13;
    protected final static int ICON_HEIGHT = 26;

    public TemperatureOverlay() {}

    @Override
    public void render(GuiGraphics guiGraphics, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;
        PoseStack matrix = guiGraphics.pose();

        if (player == null) return;

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, SPRITE);

        player.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
            int offsetX = Alignment.getX(ConfigHandler.Client.thermometerPosition(), scaledWidth, ICON_WIDTH,
                    ConfigHandler.Client.thermometerOffsetX());
            int pY = Alignment.getY(ConfigHandler.Client.thermometerPosition(), scaledHeight,
                    ConfigHandler.Client.thermometerOffsetY());
            float textScale = 0.5F;
            TempHelper.TemperatureDirection coreTemperatureDirection = TempHelper.getCoreTemperatureDirection(
                    data.getLastSkinTemperature(), data.getCoreTemperature(), data.getSkinTemperature());
            TempHelper.TemperatureDirection skinTemperatureDirection = TempHelper.getSkinTemperatureDirection(
                    data.getLocalTemperature(), data.getSkinTemperature());
            String coreDirection = TemperatureInfo.getDirectionIcon(coreTemperatureDirection);
            String skinDirection = TemperatureInfo.getDirectionIcon(skinTemperatureDirection);
            String coreTempFormat = ConfigHandler.Client.showDegreeSymbol() ? "%.1f°" : "%.1f";
            String localTempFormat = ConfigHandler.Client.showDegreeSymbol() ? "%.0f°" : "%.0f";
            String coreTempSmall = String.format(coreTempFormat, TempHelper.convertMcTemp(data.getCoreTemperature(), ConfigHandler.Client.useFahrenheit()));
            String localTemp = String.format(localTempFormat, TempHelper.convertMcTemp(data.getLocalTemperature(), ConfigHandler.Client.useFahrenheit()));
            int textOffsetX = Alignment.getIconTextX(ConfigHandler.Client.thermometerPosition(), scaledWidth,
                    mc.font.width(coreTempSmall), ConfigHandler.Client.thermometerOffsetX(), textScale, ICON_WIDTH);
            int localTextOffsetX = Alignment.getIconTextX(ConfigHandler.Client.thermometerPosition(), scaledWidth,
                    mc.font.width(localTemp), ConfigHandler.Client.thermometerOffsetX(), textScale, ICON_WIDTH);
            int textOffsetY = Alignment.getIconTextY(ConfigHandler.Client.thermometerPosition(), scaledHeight,
                    ConfigHandler.Client.thermometerTextOffsetY(), textScale);
            int directionOffsetX = Alignment.getIconTextX(ConfigHandler.Client.thermometerPosition(), scaledWidth,
                    mc.font.width(coreDirection), ConfigHandler.Client.thermometerOffsetX(), textScale, ICON_WIDTH);
            int pV = 0;
            int pUOffset = 53;

            Tuple<TempHelper.TemperatureRange, Integer> localRangeStep = TempHelper.getLocalTemperatureRangeStep(data.getLocalTemperature());
            Tuple<TempHelper.TemperatureRange, Integer> coreRangeStep = TempHelper.getBodyTemperatureRangeStep(data.getCoreTemperature());
            Tuple<TempHelper.TemperatureRange, Integer> skinRangeStep =
                    TempHelper.getBodyTemperatureRangeStep(data.getSkinTemperature());
            int lineOffset = getTempLineOffset(coreRangeStep);
            AtomicBoolean showTemperature = new AtomicBoolean(ConfigHandler.Common.showTemperatureValues());

            if (data.getCoreTemperature() > BodyTemperature.WARNING_HIGH) {
                guiGraphics.blit(SPRITE, offsetX, pY, pUOffset, pV + ICON_HEIGHT, ICON_WIDTH, ICON_HEIGHT);
            }
            else if (data.getCoreTemperature() < BodyTemperature.WARNING_LOW) {
                guiGraphics.blit(SPRITE, offsetX, pY, pUOffset, pV + ICON_HEIGHT * 2, ICON_WIDTH, ICON_HEIGHT);
            }
            else {
                guiGraphics.blit(SPRITE, offsetX, pY, pUOffset, pV, ICON_WIDTH, ICON_HEIGHT);
            }
            guiGraphics.blit(SPRITE, offsetX, pY, pUOffset + ICON_WIDTH, pV + lineOffset, ICON_WIDTH, ICON_HEIGHT);

            if (ConfigHandler.Common.requireThermometer()) {
                player.getCapability(CapabilityRegistry.THERMOMETER_CAPABILITY).ifPresent(thermometer -> {
                    showTemperature.set(thermometer.hasThermometer());
                });
            }

            matrix.scale(textScale, textScale, textScale);

            if (ConfigHandler.Client.showThermometerRateChangeSymbols()) {
                FontHelper.draw(mc, guiGraphics, coreDirection, directionOffsetX - 8,
                        textOffsetY - 15, ColorHelper.getLocalTemperatureColor(coreRangeStep), true);
                FontHelper.draw(mc, guiGraphics, skinDirection, directionOffsetX + 8,
                        textOffsetY - 15, ColorHelper.getLocalTemperatureColor(skinRangeStep), true);
            }

            if (showTemperature.get()) {
                FontHelper.draw(mc, guiGraphics, localTemp, localTextOffsetX,
                        textOffsetY - 50, ColorHelper.getLocalTemperatureColor(localRangeStep), true);
                FontHelper.draw(mc, guiGraphics, coreTempSmall, textOffsetX,
                        textOffsetY, ColorHelper.getTemperatureColor(coreRangeStep), true);
            }
        });
    }

    private int getTempLineOffset(Tuple<TempHelper.TemperatureRange, Integer> rangeStep) {
        int offset;
        TempHelper.TemperatureRange range = rangeStep.getA();
        int step = rangeStep.getB() / 2;

        if (range == TempHelper.TemperatureRange.COLD) {
            offset = Math.max(7 - step, 1);
        }
        else {
            offset = Math.min(8 + step, 15);
        }

        return offset;
    }

}