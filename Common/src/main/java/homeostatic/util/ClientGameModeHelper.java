package homeostatic.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientGameModeHelper {

    public static boolean shouldLoad() {
        Player player = Minecraft.getInstance().player;

        if (player == null) {
            return false;
        } else if (player.isCreative()) {
            return false;
        }
        else if (player.isSpectator()) {
            return false;
        }
        else return !VampirismHelper.isVampire(player);
    }

}
