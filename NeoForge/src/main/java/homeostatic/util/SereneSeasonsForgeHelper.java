package homeostatic.util;

import homeostatic.common.temperature.SubSeason;
import net.minecraft.world.level.Level;

import sereneseasons.api.season.SeasonHelper;

import static sereneseasons.init.ModConfig.seasons;

public class SereneSeasonsForgeHelper {

    public static int getSeasonDuration(Level level) {
        return SeasonHelper.getSeasonState(level).getSeasonDuration();
    }

    public static boolean isSeasonDimension(Level level) {
        return seasons.isDimensionWhitelisted(level.dimension());
    }

    public static SubSeason getSubSeason(Level level) {
        return SubSeason.values()[SeasonHelper.getSeasonState(level).getSubSeason().ordinal()];
    }

}
