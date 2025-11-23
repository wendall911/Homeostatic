package homeostaticseasons.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import homeostaticseasons.api.HomeostaticSeasonsAPI;
import homeostaticseasons.api.Season;

public class ClientEventHandler {

    static Season lastSeason = null;

    public static void onClientTick(Minecraft minecraft) {
        Player player = minecraft.player;

        // Check every second
        if (player != null && player.tickCount % 20 == 0) {
            Season currentSeason = HomeostaticSeasonsAPI.getCurrentSeason(player.level());

            if (currentSeason != null && lastSeason != currentSeason) {
                lastSeason = currentSeason;
                minecraft.levelRenderer.allChanged();
            }
        }
    }

}
