package homeostatic.util;

import net.minecraft.client.Minecraft;

import homeostaticseasons.api.HomeostaticSeasonsAPI;
import homeostaticseasons.api.Season;

public class HomeostaticSeasonsClientHelper {

    public static String getSeasonName(Minecraft mc) {
        if (mc.level == null) {
            return "UNKNOWN";
        }

        if (!HomeostaticSeasonsAPI.isSeasonalDimension(mc.level.dimension())) {
            return "NONE";
        }

        Season season = HomeostaticSeasonsAPI.getCurrentSeason(mc.level);

        if (season == null) {
            return "NONE";
        }

        return season.name();
    }

}
