package homeostatic.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class HomeostaticSeasonsHelper {

    public static String getSeasonName(Minecraft mc) {
        //return homeostaticseasons.api.HomeostaticSeasonsAPI.getCurrentSeason(mc.level).toString();
        return "SPRING";
    }

    public static int getCurrentSeason(Level level) {
        //return homeostaticseasons.api.HomeostaticSeasonsAPI.getCurrentSeason(level).ordinal();
        return 0;
    }

}
