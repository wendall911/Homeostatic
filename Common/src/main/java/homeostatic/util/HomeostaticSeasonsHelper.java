package homeostatic.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import homeostaticseasons.api.HomeostaticSeasonsAPI;

public class HomeostaticSeasonsHelper {

    public static int getCurrentSeason(Level level) {
        return HomeostaticSeasonsAPI.getCurrentSeason(level).ordinal();
    }

    public static boolean isSeasonDimension(ServerLevel level) {
        return HomeostaticSeasonsAPI.isSeasonalDimension(level.dimension());
    }

}
