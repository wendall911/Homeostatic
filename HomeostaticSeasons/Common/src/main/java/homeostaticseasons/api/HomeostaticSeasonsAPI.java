package homeostaticseasons.api;

import java.time.Duration;
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
            return null;
        }
    }

    public static Season getNextSeason(Level level, Season currentSeason) {
        if (ConfigHandler.Common.isValidDimension(level.dimension())) {
            return currentSeason.next();
        }

        return null;
    }

    public static long getSeasonTime(Level level, Season season) {
        if (ConfigHandler.Common.seasonChangeMethod() == SeasonChangeMethod.CONFIGURED
                && ConfigHandler.Common.isValidDimension(level.dimension())) {
            return ConfigHandler.Common.getSeasonTime(season);
        }

        return -1L;
    }

    public static long getTimeUntilNextSeason(Level level) {
        if (ConfigHandler.Common.isValidDimension(level.dimension())) {
            if (ConfigHandler.Common.seasonChangeMethod() == SeasonChangeMethod.REALTIME) {
                return getRealtimeUntilNextSeason();
            }
            else if (ConfigHandler.Common.seasonChangeMethod() == SeasonChangeMethod.CONFIGURED) {
                return ConfigHandler.Common.getTimeUntilNextSeason(level.getGameTime());
            }
        }

        return 0L;
    }

    public static long getTimeUntilSeason(Level level, Season season) {
        if (ConfigHandler.Common.seasonChangeMethod() == SeasonChangeMethod.CONFIGURED
                && ConfigHandler.Common.isValidDimension(level.dimension())) {
            return ConfigHandler.Common.getTimeUntilSeason(level.getGameTime(), season);
        }

        return -1L;
    }

    private static long getRealtimeUntilNextSeason() {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime nextSeasonDate;
        Season cuurrentSeason = getRealtimeSeason();

        if (ConfigHandler.Common.hemisphere() == Hemisphere.NORTHERN) {
            nextSeasonDate = switch (cuurrentSeason) {
                case EARLY_SPRING -> LocalDateTime.of(date.getYear(), 4, 20, 0, 0);
                case MID_SPRING -> LocalDateTime.of(date.getYear(), 5, 21, 0, 0);
                case LATE_SPRING -> LocalDateTime.of(date.getYear(), 6, 21, 0, 0);
                case EARLY_SUMMER -> LocalDateTime.of(date.getYear(), 7, 23, 0, 0);
                case MID_SUMMER -> LocalDateTime.of(date.getYear(), 8, 23, 0, 0);
                case LATE_SUMMER -> LocalDateTime.of(date.getYear(), 9, 23, 0, 0);
                case EARLY_AUTUMN -> LocalDateTime.of(date.getYear(), 10, 23, 0, 0);
                case MID_AUTUMN -> LocalDateTime.of(date.getYear(), 11, 22, 0, 0);
                case LATE_AUTUMN -> LocalDateTime.of(date.getYear(), 12, 21, 0, 0);
                case EARLY_WINTER -> LocalDateTime.of(date.getYear() + 1, 1, 20, 0, 0);
                case MID_WINTER -> LocalDateTime.of(date.getYear() + 1, 2, 19, 0, 0);
                case LATE_WINTER -> LocalDateTime.of(date.getYear(), 3, 21, 0, 0);
            };
        }
        else {
            nextSeasonDate = switch (cuurrentSeason) {
                case EARLY_SPRING -> LocalDateTime.of(date.getYear(), 10, 23, 0, 0);
                case MID_SPRING -> LocalDateTime.of(date.getYear(), 11, 22, 0, 0);
                case LATE_SPRING -> LocalDateTime.of(date.getYear(), 12, 21, 0, 0);
                case EARLY_SUMMER -> LocalDateTime.of(date.getYear() + 1, 1, 20, 0, 0);
                case MID_SUMMER -> LocalDateTime.of(date.getYear() + 1, 2, 19, 0, 0);
                case LATE_SUMMER -> LocalDateTime.of(date.getYear(), 3, 21, 0, 0);
                case EARLY_AUTUMN -> LocalDateTime.of(date.getYear(), 4, 20, 0, 0);
                case MID_AUTUMN -> LocalDateTime.of(date.getYear(), 5, 21, 0, 0);
                case LATE_AUTUMN -> LocalDateTime.of(date.getYear(), 6, 21, 0, 0);
                case EARLY_WINTER -> LocalDateTime.of(date.getYear(), 7, 23, 0, 0);
                case MID_WINTER -> LocalDateTime.of(date.getYear(), 8, 23, 0, 0);
                case LATE_WINTER -> LocalDateTime.of(date.getYear(), 9, 23, 0, 0);
            };
        }

        Duration duration = Duration.between(date, nextSeasonDate);

        return duration.toDays() * 24000L;
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
