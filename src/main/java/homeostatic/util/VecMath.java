package homeostatic.util;

import org.joml.Vector3d;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class VecMath {

    public static double getDistance(ServerPlayer sp, Vector3d vPos) {
        double x = Math.max(0, Math.abs(sp.getX() - vPos.x) - sp.getBbWidth() / 2);
        double y = Math.max(0, Math.abs((sp.getY() + sp.getBbHeight() / 2) - vPos.y) - sp.getBbHeight() / 2);
        double z = Math.max(0, Math.abs(sp.getZ() - vPos.z) - sp.getBbWidth() / 2);

        return Math.sqrt(x * x + y * y + z * z);
    }

    public static boolean isBlockObscured(ServerPlayer sp, Vec3 blockVec) {
        Vec3 playerVec = new Vec3(sp.getX(), sp.getEyeY(), sp.getZ());
        ClipContext clipContext = new ClipContext(playerVec, blockVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, sp);

        return sp.serverLevel().clip(clipContext).getType() != HitResult.Type.MISS;
    }

}
