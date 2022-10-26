package homeostatic.overlay;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.config.ConfigHandler;
import homeostatic.Homeostatic;
import homeostatic.util.ColorHelper;
import homeostatic.util.TempHelper;

public class TemperatureCenterOverlay extends Overlay {

    public final static ResourceLocation ICONS = Homeostatic.loc("textures/gui/icons.png");
    protected final static int ICON_WIDTH = 18;
    protected final static int ICON_HEIGHT = 14;

    TemperatureCenterOverlay() {}

    @Override
    public void render(PoseStack matrix, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        if (player == null) return;

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, ICONS);

        player.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
            int offsetX = scaledWidth / 2 - 9; // In middle based on half of icon size
            int pY = scaledHeight - 44; // Subtract Y offset
            float textScale = 0.5F;
            int textOffsetY = (int) (scaledHeight / textScale) - 78;
            int pV = getTempOffset(data.getCoreTemperature());
            int pUOffset = 80; // sprite X offset

            TempHelper.TemperatureDirection coreTemperatureDirection = TempHelper.getCoreTemperatureDirection(
                    data.getLastSkinTemperature(), data.getCoreTemperature(), data.getSkinTemperature());
            String coreIcon = TemperatureInfo.getDirectionIcon(coreTemperatureDirection);
            String coreTempSmall = String.format("%.1f°%s", TempHelper.convertMcTemp(data.getCoreTemperature(), ConfigHandler.Client.useFahrenheit()), coreIcon);
            String localTemp = String.format("%.0f°", TempHelper.convertMcTemp(data.getLocalTemperature(), ConfigHandler.Client.useFahrenheit()));

            int coreOffsetX = (int) ((scaledWidth - (mc.font.width(coreTempSmall) / 2)) / textScale) / 2;
            int localOffsetX = (int) ((scaledWidth - (mc.font.width(localTemp) / 2)) / textScale) / 2;

            this.blit(matrix, offsetX, pY, pUOffset, pV, ICON_WIDTH, ICON_HEIGHT);

            matrix.scale(textScale, textScale, textScale);

            mc.font.drawShadow(matrix, localTemp, localOffsetX, textOffsetY - 19, ColorHelper.getLocalTemperatureColor(data.getLocalTemperature()));
            mc.font.drawShadow(matrix, coreTempSmall, coreOffsetX, textOffsetY, ColorHelper.getGlobeTemperatureColor(data.getLocalTemperature()));
        });
    }

    private int getTempOffset(float coreTemp) {
        int offset;

        if (coreTemp <= BodyTemperature.NORMAL) {
            if (coreTemp <= BodyTemperature.LOW) {
                offset = 240;
            }
            else {
                offset = 120 + (Math.round((BodyTemperature.NORMAL - coreTemp) / 0.010030121F) * 15);
            }
        }
        else {
            if (coreTemp >= BodyTemperature.HIGH) {
                offset = 0;
            }
            else {
                offset = 120 - (Math.round((coreTemp - BodyTemperature.NORMAL) / 0.02061747F) * 15);
            }
        }

        return offset;
    }

}