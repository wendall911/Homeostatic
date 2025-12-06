package homeostatic.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

import homeostaticseasons.api.HomeostaticSeasonsAPI;

public class HomeostaticSeasonsHelper {

    public static String getSeasonName(Minecraft mc) {
        if (mc.level == null) {
            return "UNKNOWN";
        }

        return HomeostaticSeasonsAPI.getCurrentSeason(mc.level).toString();
    }

    public static int getCurrentSeason(Level level) {
        return HomeostaticSeasonsAPI.getCurrentSeason(level).ordinal();
    }

}
