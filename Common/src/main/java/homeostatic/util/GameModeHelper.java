package homeostatic.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;

public class GameModeHelper {

    public static boolean shouldLoad(ServerPlayer player) {
        return player.gameMode.getGameModeForPlayer() == GameType.SURVIVAL && !VampirismHelper.isVampire(player);
    }

}
