package homeostatic.util;

import net.minecraft.world.level.Level;

import sereneseasons.api.season.SeasonHelper;

import static sereneseasons.init.ModConfig.seasons;

import homeostatic.common.temperature.SubSeason;

// TODO Re-enable when sereneseasons is updated to 26.1

public class SereneSeasonsFabricHelper {

    public static int getSeasonDuration(Level level) {
        //return SeasonHelper.getSeasonState(level).getSeasonDuration();
        return 0;
    }

    public static boolean isSeasonDimension(Level level) {
        //return seasons.isDimensionWhitelisted(level.dimension());
        return false;
    }

    public static SubSeason getSubSeason(Level level) {
        //return SubSeason.values()[SeasonHelper.getSeasonState(level).getSubSeason().ordinal()];
        return SubSeason.EARLY_SPRING;
    }

}
