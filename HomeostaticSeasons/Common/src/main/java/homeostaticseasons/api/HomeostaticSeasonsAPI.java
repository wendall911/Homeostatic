package homeostaticseasons.api;

import java.time.LocalDateTime;

import net.minecraft.world.level.Level;

import homeostaticseasons.config.ConfigHandler;

public class HomeostaticSeasonsAPI {

    public static Season getCurrentSeason(Level level) {
        if (ConfigHandler.Common.isValidDimension(level.dimension())) {
            if (ConfigHandler.Common.seasonChangeMethod() == SeasonChangeMethod.REALTIME) {
                return getRealtimeSeason();
            }
            else if (ConfigHandler.Common.seasonChangeMethod() == SeasonChangeMethod.FIXED) {
                return ConfigHandler.Common.fixedSeason();
            }
            else {
                return ConfigHandler.Common.getSeasonFromGameTime(level.getGameTime());
            }
        }
        else {
            return Season.EARLY_SPRING;
        }
    }

    public static Season getNextSeason(Level level, Season currentSeason) {
        if (ConfigHandler.Common.isValidDimension(level.dimension())) {
            return currentSeason.next();
        }

        return Season.EARLY_SPRING;
    }

    public static long getTimeUntilNextSeason(Level level) {
        if (ConfigHandler.Common.seasonChangeMethod() == SeasonChangeMethod.CONFIGURED
                && ConfigHandler.Common.isValidDimension(level.dimension())) {
            return ConfigHandler.Common.getTimeUntilNextSeason(level.getGameTime());
        }

        return Long.MAX_VALUE;
    }

    public static long getTimeUntilSeason(Level level, Season season) {
        if (ConfigHandler.Common.seasonChangeMethod() == SeasonChangeMethod.CONFIGURED
                && ConfigHandler.Common.isValidDimension(level.dimension())) {
            return ConfigHandler.Common.getTimeUntilSeason(level.getGameTime(), season);
        }

        return Long.MAX_VALUE;
    }

    private static Season getRealtimeSeason() {
        LocalDateTime date = LocalDateTime.now();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        if (ConfigHandler.Common.hemisphere() == Hemisphere.NORTHERN) {
            /*
             * Northern Hemisphere Seasons
             * Early Spring: March 21 - April 19
             * Mid Spring: April 20 - May 20
             * Late Spring: May 21 - June 20
             * Early Summer: June 21 - July 22
             * Mid Summer: July 23 - August 22
             * Late Summer: August 23 - September 22
             * Early Autumn: September 23 - October 22
             * Mid Autumn: October 23 - November 21
             * Late Autumn: November 22 - December 20
             * Early Winter: December 21 - January 19
             * Mid Winter: January 20 - February 18
             * Late Winter: February 19 - March 20
             */
            if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
                return Season.EARLY_SPRING;
            }
            else if (month == 4 || (month == 5 && day <= 20)) {
                return Season.MID_SPRING;
            }
            else if (month == 5 || (month == 6 && day <= 20)) {
                return Season.LATE_SPRING;
            }
            else if (month == 6 || (month == 7 && day <= 22)) {
                return Season.EARLY_SUMMER;
            }
            else if (month == 7 || (month == 8 && day <= 22)) {
                return Season.MID_SUMMER;
            }
            else if (month == 8 || (month == 9 && day <= 22)) {
                return Season.LATE_SUMMER;
            }
            else if (month == 9 || (month == 10 && day <= 22)) {
                return Season.EARLY_AUTUMN;
            }
            else if (month == 10 || (month == 11 && day <= 21)) {
                return Season.MID_AUTUMN;
            }
            else if (month == 11 || (month == 12 && day <= 20)) {
                return Season.LATE_AUTUMN;
            }
            else if (month == 12 || (month == 1 && day <= 19)) {
                return Season.EARLY_WINTER;
            }
            else if (month == 1 || (month == 2 && day <= 18)) {
                return Season.MID_WINTER;
            }
            else {
                return Season.LATE_WINTER;
            }
        }
        else {
            /*
             * Southern Hemisphere Seasons
             * Early Spring: September 23 - October 22
             * Mid Spring: October 23 - November 21
             * Late Spring: November 22 - December 20
             * Early Summer: December 21 - January 19
             * Mid Summer: January 20 - February 18
             * Late Summer: February 19 - March 20
             * Early Autumn: March 21 - April 19
             * Mid Autumn: April 20 - May 20
             * Late Autumn: May 21 - June 20
             * Early Winter: June 21 - July 22
             * Mid Winter: July 23 - August 22
             * Late Winter: August 23 - September 22
             */
            if ((month == 9 && day >= 23) || (month == 10 && day <= 22)) {
                return Season.EARLY_SPRING;
            }
            else if (month == 10 || (month == 11 && day <= 21)) {
                return Season.MID_SPRING;
            }
            else if (month == 11 || (month == 12 && day <= 20)) {
                return Season.LATE_SPRING;
            }
            else if (month == 12 || (month == 1 && day <= 19)) {
                return Season.EARLY_SUMMER;
            }
            else if (month == 1 || (month == 2 && day <= 18)) {
                return Season.MID_SUMMER;
            }
            else if (month == 2 || (month == 3 && day <= 20)) {
                return Season.LATE_SUMMER;
            }
            else if (month == 3 || (month == 4 && day <= 19)) {
                return Season.EARLY_AUTUMN;
            }
            else if (month == 4 || (month == 5 && day <= 20)) {
                return Season.MID_AUTUMN;
            }
            else if (month == 5 || (month == 6 && day <= 20)) {
                return Season.LATE_AUTUMN;
            }
            else if (month == 6 || (month == 7 && day <= 22)) {
                return Season.EARLY_WINTER;
            }
            else if (month == 7 || (month == 8 && day <= 22)) {
                return Season.MID_WINTER;
            }
            else {
                return Season.LATE_WINTER;
            }
        }
    }

}
