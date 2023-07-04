package homeostatic.overlay;

import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.Homeostatic;
import homeostatic.util.WaterHelper;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Homeostatic.MODID)
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

        player.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
            final int waterLevel = data.getWaterLevel();
            final float waterSaturationLevel = data.getWaterSaturationLevel();

            WaterHelper.drawWaterBar(SPRITE, scaledWidth, scaledHeight, effectInstance, gui, guiGraphics, waterSaturationLevel, waterLevel, tickCount);
        });

    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        tickCount++;
        tickCount %= 1200;
    }

}