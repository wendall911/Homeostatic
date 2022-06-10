package homeostatic.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.Locale;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.config.ConfigHandler;
import homeostatic.util.Alignment;
import homeostatic.util.ColorHelper;
import homeostatic.util.FontHelper;
import homeostatic.util.TempHelper;
import static homeostatic.util.TempHelper.TemperatureDirection;

public class TemperatureInfo {

    public TemperatureInfo() {}

    public void renderText(PoseStack matrix, Minecraft mc, BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        player.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
            TemperatureDirection skinTemperatureDirection = TempHelper.getSkinTemperatureDirection(
                    data.getLocalTemperature(), data.getSkinTemperature());
            TemperatureDirection coreTemperatureDirection = TempHelper.getCoreTemperatureDirection(
                    data.getLastSkinTemperature(), data.getCoreTemperature(), data.getSkinTemperature());
            String skinIcon = getDirectionIcon(skinTemperatureDirection);
            String coreIcon = getDirectionIcon(coreTemperatureDirection);

            String localTemp = String.format(" %.2f", TempHelper.convertMcTemp(data.getLocalTemperature(), ConfigHandler.Client.useFahrenheit()));
            String skinTemp = String.format("s: %.2f%s", TempHelper.convertMcTemp(data.getSkinTemperature(), ConfigHandler.Client.useFahrenheit()), skinIcon);
            String coreTemp = String.format(" c: %.2f%s ", TempHelper.convertMcTemp(data.getCoreTemperature(), ConfigHandler.Client.useFahrenheit()), coreIcon);

            int localTempWidth = mc.font.width(localTemp);
            int skinTempWidth = mc.font.width(skinTemp) + localTempWidth;
            int coreTempWidth = mc.font.width(coreTemp) + skinTempWidth;
            int y = Alignment.getY(scaledHeight, 1, mc.font.lineHeight);

            FontHelper.draw(mc, matrix, localTemp, Alignment.getX(scaledWidth, localTempWidth), y, ColorHelper.getLocalTemperatureColor(data.getLocalTemperature()));
            FontHelper.draw(mc, matrix, skinTemp, Alignment.getX(scaledWidth, skinTempWidth), y, ColorHelper.getTemperatureColor(data.getSkinTemperature()));
            FontHelper.draw(mc, matrix, coreTemp, Alignment.getX(scaledWidth, coreTempWidth), y, ColorHelper.getTemperatureColor(data.getCoreTemperature()));
        });
    }

    private String getDirectionIcon(TemperatureDirection direction) {
        return switch(direction) {
            case COOLING -> "↓";
            case COOLING_RAPIDLY -> "⇊";
            case WARMING -> "↑";
            case WARMING_RAPIDLY -> "⇈";
            case NONE -> "·";
            case COOLING_NORMALLY -> "ˬ";
            case WARMING_NORMALLY -> "ˆ";
        };
    }

}
