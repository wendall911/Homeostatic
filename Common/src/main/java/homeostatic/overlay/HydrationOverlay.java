package homeostatic.overlay;

import net.minecraft.core.registries.BuiltInRegistries;
import org.jetbrains.annotations.Nullable;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import homeostatic.common.Hydration;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.Homeostatic;
import homeostatic.platform.Services;
import homeostatic.util.WaterHelper;

public class HydrationOverlay extends Overlay {

    public final static ResourceLocation SPRITE = Homeostatic.loc("textures/gui/icons.png");
    private static float unclampedAlpha = 0F;
    private static float alpha = 0F;
    private static byte alphaDirection = 1;
    protected static int tickCount = 0;

    public HydrationOverlay() {}

    @Override
    public void render(GuiGraphics guiGraphics, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        if (player == null) return;

        ItemStack heldItem = player.getMainHandItem();
        Hydration hydration = WaterHelper.getItemHydration(heldItem);

        if (hydration == null) {
            resetAlpha();
            return;
        }

        final Gui gui = mc.gui;

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, WaterHud.SPRITE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        MobEffectInstance effectInstance = mc.player.getEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(HomeostaticEffects.THIRST));

        Services.PLATFORM.getWaterCapabilty(player).ifPresent(data -> {
            final int waterLevel = data.getWaterLevel() + hydration.amount();
            final float waterSaturationLevel = data.getWaterSaturationLevel() + hydration.saturation();

            WaterHelper.drawWaterBar(SPRITE, scaledWidth, scaledHeight, effectInstance, gui, guiGraphics, waterSaturationLevel, waterLevel, tickCount);
        });

        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void onClientTick(Minecraft minecraft) {
        unclampedAlpha += alphaDirection * 0.125f;
        if (unclampedAlpha >= 1.5F) {
            alphaDirection = -1;
        }
        else if (unclampedAlpha <= -0.5F) {
            alphaDirection = 1;
        }

        alpha = Math.max(0F, Math.min(1F, unclampedAlpha)) * Math.min(1F, 0.65F);
        tickCount++;
        tickCount %= 1200;
    }

    public static void resetAlpha() {
        unclampedAlpha = alpha = 0F;
        alphaDirection = 1;
    }

}