package homeostatic.overlay;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.lwjgl.opengl.GL11;

import homeostatic.Homeostatic;
import homeostatic.common.Hydration;
import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.util.WaterHelper;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Homeostatic.MODID)
public class HydrationOverlay extends Overlay {

    private static float unclampedAlpha = 0F;
    private static float alpha = 0F;
    private static byte alphaDirection = 1;
    protected static int tickCount = 0;

    public HydrationOverlay() {}

    @Override
    public void render(PoseStack matrix, Minecraft mc, @Nullable BlockPos pos, int scaledWidth, int scaledHeight) {
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
        RenderSystem.setShaderTexture(0, WaterHud.WATER_BAR);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        MobEffectInstance effectInstance = mc.player.getEffect(HomeostaticEffects.THIRST.get());

        player.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
            final int waterLevel = data.getWaterLevel() + hydration.amount();
            final float waterSaturationLevel = data.getWaterSaturationLevel() + hydration.saturation();

            WaterHelper.drawWaterBar(scaledWidth, scaledHeight, effectInstance, gui, matrix, waterSaturationLevel, waterLevel, tickCount);
        });

        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

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