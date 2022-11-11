package homeostatic.overlay;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.config.ConfigHandler;
import homeostatic.Homeostatic;
import homeostatic.util.Alignment;
import homeostatic.util.ColorHelper;
import homeostatic.util.TempHelper;

public class TemperatureGlobeOverlay extends Overlay {

    public final static ResourceLocation ICONS = Homeostatic.loc("textures/gui/icons.png");
    protected final static int ICON_WIDTH = 18;
    protected final static int ICON_HEIGHT = 14;

    TemperatureGlobeOverlay() {}

    @Override
    public void render(PoseStack matrix, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        if (player == null) return;

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, ICONS);

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

            int coreOffsetX = Alignment.getIconTextX(ConfigHandler.Client.globePosition(), scaledWidth,
                    mc.font.width(coreTempSmall), ConfigHandler.Client.globeOffsetX(), textScale, ICON_WIDTH);
            int localOffsetX = Alignment.getIconTextX(ConfigHandler.Client.globePosition(), scaledWidth,
                    mc.font.width(localTemp), ConfigHandler.Client.globeOffsetX(), textScale, ICON_WIDTH);
            Tuple<TempHelper.TemperatureRange, Integer> localRangeStep = TempHelper.getLocalTemperatureRangeStep(data.getLocalTemperature());
            Tuple<TempHelper.TemperatureRange, Integer> coreRangeStep = TempHelper.getBodyTemperatureRangeStep(data.getCoreTemperature());
            pV = getTempOffset(coreRangeStep);
            localPV = getTempOffset(localRangeStep);

            this.blit(matrix, offsetX, pY, pUOffset, pV, ICON_WIDTH, ICON_HEIGHT);
            this.blit(matrix, offsetX, pY, pUOffset + ICON_WIDTH, localPV, ICON_WIDTH, ICON_HEIGHT);

            if (ConfigHandler.Common.showTemperatureValues()) {
                matrix.scale(textScale, textScale, textScale);

                mc.font.drawShadow(matrix, localTemp, localOffsetX - 1, textOffsetY - 19, ColorHelper.getLocalTemperatureColor(localRangeStep));
                mc.font.drawShadow(matrix, coreTempSmall, coreOffsetX - 1, textOffsetY, ColorHelper.getGlobeTemperatureColor(coreRangeStep));
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