package homeostatic.event;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import homeostatic.common.particle.HomeostaticParticles;
import homeostatic.common.water.WaterInfo;
import homeostatic.config.ConfigHandler;
import homeostatic.platform.Services;
import homeostatic.util.ClientGameModeHelper;
import homeostatic.util.ParticleHelper;
import homeostatic.util.TempHelper;

public class ClientPlayerEventHandler {

    private static int condensationWait = 0;
    private static int waitTimer = 0;

    public static void drinkWater(Player player, LevelAccessor level, InteractionHand hand) {
        if (hand != InteractionHand.OFF_HAND
                || player.getPose() != Pose.CROUCHING
                || !player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()
                || !ClientGameModeHelper.shouldLoad()) return;

        final HitResult hitresult = player.pick(2.0D, 0.0F, true);
        BlockPos pos = ((BlockHitResult)hitresult).getBlockPos();

        if (hitresult.getType() == HitResult.Type.BLOCK && level.getFluidState(pos).getType() == Fluids.WATER) {
            Services.PLATFORM.getWaterCapabilty(player).ifPresent(data -> {
                if (data.getWaterLevel() < WaterInfo.MAX_WATER_LEVEL) {
                    player.level().playSound(player, pos, SoundEvents.GENERIC_DRINK.value(), SoundSource.PLAYERS, 0.4f, 1.0f);

                    Services.CLIENT_PLATFORM.sendDrinkWaterPacket(player);
                }
            });
        }
    }

    public static void onClientTick(Minecraft minecraft) {
        Player player = minecraft.player;

        if (condensationWait == 0) {
            updateCondensationWait();
        }

        // Check every second
        if (player != null && player.tickCount % 20 == 0) {
            Services.PLATFORM.getTemperatureData(player).ifPresent(data -> {
                waitTimer++;

                if (!player.isUnderWater()
                        && TempHelper.isMixedAirCondensing(data.getLocalTemperature(), data.getRelativeHumidity())) {
                    if (waitTimer > condensationWait) {
                        ParticleHelper.spawnPlayerFaceParticle(
                            minecraft,
                            HomeostaticParticles.CONDENSATION,
                            new Vec3(0.0D, -0.0D, 0.0D),
                            new Vec3(0.0D, 0.0D, Mth.nextDouble(player.getRandom(), 0.0001, 0.0009))
                        );
                        waitTimer = 0;
                        updateCondensationWait();
                    }
                }
            });
        }
    }

    /*
     * Adds a random wait amount between breaths that show condensation.
     */
    private static void updateCondensationWait() {
        condensationWait = ThreadLocalRandom.current().nextInt(ConfigHandler.Client.condensationMin(), ConfigHandler.Client.condensationMax() + 1);
    }

}
