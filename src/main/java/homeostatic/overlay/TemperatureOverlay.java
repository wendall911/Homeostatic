package homeostatic.overlay;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import homeostatic.util.Alignment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.config.ConfigHandler;
import homeostatic.Homeostatic;
import homeostatic.util.ColorHelper;
import homeostatic.util.TempHelper;

public class TemperatureOverlay extends Overlay {

    public final static ResourceLocation ICONS = Homeostatic.loc("textures/gui/icons.png");
    protected final static int ICON_WIDTH = 13;
    protected final static int ICON_HEIGHT = 26;

    public TemperatureOverlay() {}

    @Override
    public void render(PoseStack matrix, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        if (player == null) return;

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, ICONS);

        player.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
            int offsetX = Alignment.getX(ConfigHandler.Client.thermometerPosition(), scaledWidth, ICON_WIDTH,
                    ConfigHandler.Client.thermometerOffsetX());
            int pY = Alignment.getY(ConfigHandler.Client.thermometerPosition(), scaledHeight,
                    ConfigHandler.Client.thermometerOffsetY());
            float textScale = 0.5F;
            TempHelper.TemperatureDirection coreTemperatureDirection = TempHelper.getCoreTemperatureDirection(
                    data.getLastSkinTemperature(), data.getCoreTemperature(), data.getSkinTemperature());
            String coreIcon = TemperatureInfo.getDirectionIcon(coreTemperatureDirection);
            String coreTempSmall = String.format("%.1f°%s", TempHelper.convertMcTemp(data.getCoreTemperature(), ConfigHandler.Client.useFahrenheit()), coreIcon);
            String localTemp = String.format("%.0f°", TempHelper.convertMcTemp(data.getLocalTemperature(), ConfigHandler.Client.useFahrenheit()));
            int textOffsetX = Alignment.getIconTextX(ConfigHandler.Client.thermometerPosition(), scaledWidth,
                    mc.font.width(coreTempSmall), ConfigHandler.Client.thermometerOffsetX(), textScale, ICON_WIDTH);
            int textOffsetY = Alignment.getIconTextY(ConfigHandler.Client.thermometerPosition(), scaledHeight,
                    ConfigHandler.Client.thermometerTextOffsetY(), textScale);
            int pV = 0;
            int pUOffset = 53;

            Tuple<TempHelper.TemperatureRange, Integer> localRangeStep = TempHelper.getLocalTemperatureRangeStep(data.getLocalTemperature());
            Tuple<TempHelper.TemperatureRange, Integer> coreRangeStep = TempHelper.getBodyTemperatureRangeStep(data.getCoreTemperature());
            int lineOffset = getTempLineOffset(coreRangeStep);

            if (data.getCoreTemperature() > BodyTemperature.WARNING_HIGH) {
                this.blit(matrix, offsetX, pY, pUOffset, pV + ICON_HEIGHT, ICON_WIDTH, ICON_HEIGHT);
            }
            else if (data.getCoreTemperature() < BodyTemperature.WARNING_LOW) {
                this.blit(matrix, offsetX, pY, pUOffset, pV + ICON_HEIGHT * 2, ICON_WIDTH, ICON_HEIGHT);
            }
            else {
                this.blit(matrix, offsetX, pY, pUOffset, pV, ICON_WIDTH, ICON_HEIGHT);
            }
            this.blit(matrix, offsetX, pY, pUOffset + ICON_WIDTH, pV + lineOffset, ICON_WIDTH, ICON_HEIGHT);

            if (ConfigHandler.Common.showTemperatureValues()) {
                matrix.scale(textScale, textScale, textScale);

                mc.font.drawShadow(matrix, localTemp, (textOffsetX + 23) - mc.font.width(localTemp),
                        textOffsetY - 50, ColorHelper.getLocalTemperatureColor(localRangeStep));
                mc.font.drawShadow(matrix, coreTempSmall, textOffsetX, textOffsetY, ColorHelper.getTemperatureColor(coreRangeStep));
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
