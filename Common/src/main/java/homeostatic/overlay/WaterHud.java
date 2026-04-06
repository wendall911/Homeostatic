package homeostatic.overlay;

import org.jspecify.annotations.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.Homeostatic;
import homeostatic.platform.Services;
import homeostatic.util.WaterHelper;

public class WaterHud extends Overlay {

    protected static int tickCount = 0;
    public final static Identifier SPRITE = Homeostatic.prefix("textures/gui/icons.png");
    public final static int BAR_WIDTH = 9;
    public final static int BAR_HEIGHT = 9;

    public WaterHud() {}

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        if (player == null) return;

        final Gui gui = mc.gui;

        MobEffectInstance effectInstance = mc.player.getEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(HomeostaticEffects.THIRST));

        Services.PLATFORM.getWaterCapabilty(player).ifPresent(data -> {
            final int waterLevel = data.getWaterLevel();
            final float waterSaturationLevel = data.getWaterSaturationLevel();

            WaterHelper.drawWaterBar(SPRITE, scaledWidth, scaledHeight, effectInstance, gui, guiGraphics, waterSaturationLevel, waterLevel, tickCount);
        });

    }

    public static void onClientTick(Minecraft minecraft) {
        tickCount++;
        tickCount %= 1200;
    }

}
