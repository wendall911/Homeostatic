package homeostatic.util;

import net.minecraft.world.level.Level;

import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.ServerConfig;

public class SereneSeasonsHelper {

    public static int getSeasonDuration(Level level) {
        return SeasonHelper.getSeasonState(level).getSeasonDuration();
    }

    public static boolean isSeasonDimension(Level level) {
        return ServerConfig.isDimensionWhitelisted(level.dimension());
    }

}
