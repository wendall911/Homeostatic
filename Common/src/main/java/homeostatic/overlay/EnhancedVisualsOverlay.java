package homeostatic.overlay;

import org.jetbrains.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import homeostatic.Homeostatic;
import homeostatic.common.temperature.TemperatureThreshold;
import homeostatic.platform.Services;
import homeostatic.util.OverlayHelper;

public class EnhancedVisualsOverlay extends Overlay {

    public final static ResourceLocation HYPERTHERMIA_OVERLAY = Homeostatic.prefix("textures/gui/hyperthermia.png");
    private final LastTick lastTick;

    public EnhancedVisualsOverlay() {
         lastTick = new LastTick();
    }

    @Override
    public void render(GuiGraphics guiGraphics, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        if (player == null) return;

        Services.PLATFORM.getTemperatureData(player).ifPresent(data -> {
            if (data.getSkinTemperature() > TemperatureThreshold.SCALDING_WARNING.temperature) {
                float intensity = 1F - ((data.getSkinTemperature() - TemperatureThreshold.SCALDING_WARNING.temperature) * 10);

                if (lastTick.update(player, intensity)) {
                    player.level().addParticle(
                        ParticleTypes.SMOKE,
                        player.getX(),
                        player.getY(1.0D) - 0.5D,
                        player.getZ(),
                        0.0D,
                        9e-6D,
                        0.0D
                    );

                    if (Homeostatic.RANDOM.nextInt(80) == 0) {
                        player.level().addParticle(
                            ParticleTypes.LAVA,
                            player.getX(),
                            player.getY(1.0D) - 0.5D,
                            player.getZ(),
                            0.0D,
                            6e-5D,
                            0.0D
                        );
                    }
                }
            }

            if (data.getCoreTemperature() > TemperatureThreshold.WARNING_HIGH.temperature) {
                float alpha = 0.1F + ((data.getCoreTemperature() - TemperatureThreshold.WARNING_HIGH.temperature) * 10);

                OverlayHelper.renderTexture(guiGraphics, HYPERTHERMIA_OVERLAY, scaledWidth, scaledHeight, alpha);
            }
        });
    }

    private static class LastTick {
        private long lastTick = 0L;

        private boolean update(Player player, float intensity) {

            if (player.tickCount >= lastTick + (20 * intensity)) {
                lastTick = player.tickCount;
                return true;
            }

            return false;
        }
    }

}
