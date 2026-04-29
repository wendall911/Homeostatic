package homeostatic.util;

import net.minecraft.world.level.Level;

import homeostaticseasons.api.HomeostaticSeasonsAPI;
import homeostaticseasons.api.Season;

public class HomeostaticSeasonsHelper {

    public static int getCurrentSeason(Level level) {
        Season season = HomeostaticSeasonsAPI.getCurrentSeason(level);

        if (season == null) {
            return 0;
        }

        return season.ordinal();
    }

}
