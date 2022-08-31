package homeostatic.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.config.ConfigHandler;
import homeostatic.Homeostatic;
import homeostatic.util.ColorHelper;
import homeostatic.util.OverlayHelper;
import homeostatic.util.TempHelper;

public class TemperatureOverlay extends GuiComponent {

    public final static ResourceLocation BURNING_OVERLAY = Homeostatic.loc("textures/gui/burning.png");
    public final static ResourceLocation HYPERTHERMIA_OVERLAY = Homeostatic.loc("textures/gui/hyperthermia.png");
    public final static ResourceLocation ICONS = Homeostatic.loc("textures/gui/icons.png");
    protected final static int ICON_WIDTH = 13;
    protected final static int ICON_HEIGHT = 26;

    public TemperatureOverlay() {}

    public void render(PoseStack matrix, Minecraft mc, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, ICONS);

        player.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
            int offsetX = scaledWidth / 2 + 95;
            int pY = scaledHeight - 27;
            float textScale = 0.5F;
            int textOffsetX = (int) (scaledWidth / textScale) / 2 + 189;
            int textOffsetY = (int) (scaledHeight / textScale) - 15;
            int pV = 0;
            int pU = 0;
            int pUOffset = 53;
            int lineOffset = getTempLineOffset(data.getCoreTemperature());
            TempHelper.TemperatureDirection coreTemperatureDirection = TempHelper.getCoreTemperatureDirection(
                    data.getLastSkinTemperature(), data.getCoreTemperature(), data.getSkinTemperature());
            String coreIcon = TemperatureInfo.getDirectionIcon(coreTemperatureDirection);
            String coreTempSmall = String.format("%.1f°%s", TempHelper.convertMcTemp(data.getCoreTemperature(), ConfigHandler.Client.useFahrenheit()), coreIcon);
            String localTemp = String.format("%.0f°", TempHelper.convertMcTemp(data.getLocalTemperature(), ConfigHandler.Client.useFahrenheit()));

            if (data.getSkinTemperature() > BodyTemperature.SCALDING) {
                float alpha = 0.33F + (data.getSkinTemperature() - BodyTemperature.SCALDING);

                OverlayHelper.renderTexture(BURNING_OVERLAY, scaledWidth, scaledHeight, alpha);
            }

            if (data.getCoreTemperature() > BodyTemperature.HIGH) {
                OverlayHelper.renderTexture(HYPERTHERMIA_OVERLAY, scaledWidth, scaledHeight, 0.4F);
            }
            if (data.getCoreTemperature() > BodyTemperature.WARNING_HIGH) {
                this.blit(matrix, offsetX, pY, pUOffset, pV + 26, ICON_WIDTH, ICON_HEIGHT);
            }
            else if (data.getCoreTemperature() < BodyTemperature.WARNING_LOW) {
                this.blit(matrix, offsetX, pY, pUOffset, pV + 52, ICON_WIDTH, ICON_HEIGHT);
            }
            else {
                this.blit(matrix, offsetX, pY, pUOffset, pV, ICON_WIDTH, ICON_HEIGHT);
            }
            this.blit(matrix, offsetX, pY, pUOffset + 13, pV + lineOffset, ICON_WIDTH, ICON_HEIGHT);

            matrix.scale(textScale, textScale, textScale);
            mc.font.drawShadow(matrix, localTemp, (textOffsetX + 23) - mc.font.width(localTemp), textOffsetY - 50, ColorHelper.getLocalTemperatureColor(data.getLocalTemperature()));
            mc.font.drawShadow(matrix, coreTempSmall, textOffsetX, textOffsetY, ColorHelper.getTemperatureColor(data.getCoreTemperature()));
        });
    }

    private int getTempLineOffset(float coreTemp) {
        int offset;

        if (coreTemp <= BodyTemperature.NORMAL) {
            if (coreTemp <= BodyTemperature.LOW) {
                offset = 1;
            }
            else {
                offset = Math.round((coreTemp - BodyTemperature.LOW) / 0.011462F);
            }
        }
        else {
            if (coreTemp >= BodyTemperature.HIGH) {
                offset = 15;
            }
            else {
                offset = 15 - Math.round((BodyTemperature.HIGH - coreTemp) / 0.020617F);
            }
        }

        return offset;
    }

}
