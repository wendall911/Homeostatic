package homeostatic.overlay;

import org.jetbrains.annotations.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.Homeostatic;
import homeostatic.platform.Services;
import homeostatic.util.WaterHelper;

public class WaterHud extends Overlay {

    protected static int tickCount = 0;
    public final static ResourceLocation SPRITE = Homeostatic.loc("textures/gui/icons.png");
    public final static int BAR_WIDTH = 9;
    public final static int BAR_HEIGHT = 9;

    public WaterHud() {}

    @Override
    public void render(GuiGraphics guiGraphics, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        if (player == null) return;

        final Gui gui = mc.gui;

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, SPRITE);
        MobEffectInstance effectInstance = mc.player.getEffect(HomeostaticEffects.THIRST);

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