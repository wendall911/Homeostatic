package homeostatic.event;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import homeostatic.common.water.WaterInfo;
import homeostatic.platform.Services;

public class ClientPlayerEventHandler {

    public static void drinkWater(Player player, LevelAccessor level, InteractionHand hand) {
        if (hand != InteractionHand.OFF_HAND
                || player.getPose() != Pose.CROUCHING
                || !player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) return;

        final HitResult hitresult = player.pick(2.0D, 0.0F, true);
        BlockPos pos = ((BlockHitResult)hitresult).getBlockPos();

        if (hitresult.getType() == HitResult.Type.BLOCK && level.getFluidState(pos).getType() == Fluids.WATER) {
            Services.PLATFORM.getWaterCapabilty(player).ifPresent(data -> {
                if (data.getWaterLevel() < WaterInfo.MAX_WATER_LEVEL) {
                    player.level().playSound(player, pos, SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 0.4f, 1.0f);

                    Services.CLIENT_PLATFORM.sendDrinkWaterPacket();
                }
            });
        }
    }

}
