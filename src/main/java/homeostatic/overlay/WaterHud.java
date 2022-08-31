package homeostatic.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Homeostatic.MODID)
public class WaterHud extends GuiComponent {

    protected static int tickCount = 0;
    public final static ResourceLocation WATER_BAR = Homeostatic.loc("textures/gui/icons.png");
    protected final static int BAR_WIDTH = 9;
    protected final static int BAR_HEIGHT = 9;

    public WaterHud() {}

    public void render(PoseStack matrix, Minecraft mc, int scaledWidth, int scaledHeight) {
        final Player player = mc.player;

        if (player == null) return;

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, WATER_BAR);
        MobEffectInstance effectInstance = mc.player.getEffect(HomeostaticEffects.THIRST.get());

        if (hasAirBar(player) || isRidingHighHealth(player)) {
            matrix.translate(0,-9,0);
        }

        player.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
            final int waterLevel = data.getWaterLevel();
            final float waterSaturationLevel = data.getWaterSaturationLevel();
            int offsetX = scaledWidth / 2 + 91;
            int offsetY = scaledHeight - 50;
            int pY = offsetY;
            int pV = 0;
            int pU = 0;
            int pUOffset = 0;

            if (effectInstance != null) {
                pU += 18;
                pUOffset += 9;
            }

            for (int i = 0; i < 10; ++i) {
                int pX = offsetX - i * 8 - 9;
                this.blit(matrix, pX, pY, pUOffset + 36, pV, BAR_WIDTH, BAR_HEIGHT);

                if (waterSaturationLevel <= 0.0F && tickCount % (waterLevel * 3 + 1) == 0) {
                    pY = offsetY + (Homeostatic.RANDOM.nextInt(3) - 1);
                }

                if (i * 2 + 1 < waterLevel) {
                    this.blit(matrix, pX, pY, pU, pV, BAR_WIDTH, BAR_HEIGHT);
                }
                
                if (i * 2 + 1 == waterLevel) {
                    this.blit(matrix, pX, pY, pU + 9, pV, BAR_WIDTH, BAR_HEIGHT);
                }

                if (i * 2 + 1 < waterSaturationLevel) {
                    this.blit(matrix, pX, pY - 1, pU, pV + 9, 9, 9);
                    this.blit(matrix, pX, pY + 1, pU + 9, pV + 9, 9, 9);
                }

                if (i * 2 + 1 == waterSaturationLevel) {
                    this.blit(matrix, pX, pY, pU, pV + 9, 9, 9);
                }
            }

        });

    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        tickCount++;
        tickCount %= 1200;
    }

    public static boolean hasAirBar(Player player) {
        int maxAirSupply = player.getMaxAirSupply();
        int airSupply = Math.min(player.getAirSupply(), maxAirSupply);

        return player.isEyeInFluid(FluidTags.WATER) || airSupply < maxAirSupply;
    }

    public static boolean isRidingHighHealth(Player player) {
        final boolean isMounted = player.getVehicle() instanceof LivingEntity;

        if (isMounted) {
            return ((LivingEntity) player.getVehicle()).getMaxHealth() >= 22.0F;
        }

        return false;
    }

}
