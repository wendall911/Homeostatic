package homeostatic.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class ParticleHelper {

    public static void spawnPlayerFaceParticle(Minecraft minecraft, ParticleOptions options, Vec3 offset, Vec3 speed) {
        float partialTick = minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        Player player = minecraft.player;

        if (player != null) {
            offset = offset.add(0.0D, -0.1D, 0.3D);
            offset = offset.xRot(-player.getViewXRot(partialTick) * Mth.DEG_TO_RAD);
            offset = offset.yRot(-player.getViewYRot(partialTick) * Mth.DEG_TO_RAD);
            offset = offset.add(player.getX(), player.getEyeY(), player.getZ());
            speed = speed.xRot(-player.getViewXRot(partialTick) * Mth.DEG_TO_RAD);
            speed = speed.yRot(-player.getViewYRot(partialTick) * Mth.DEG_TO_RAD);

            player.level().addParticle(options, offset.x(), offset.y(), offset.z(), speed.x(), speed.y(), speed.z());
        }
    }

}
