package homeostatic.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class LocalPlayerHelper {

    public static Player getLocalPlayer() {
        return Minecraft.getInstance().player;
    }

}
