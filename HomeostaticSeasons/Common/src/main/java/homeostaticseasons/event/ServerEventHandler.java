package homeostaticseasons.event;

import net.minecraft.server.level.ServerLevel;

import homeostaticseasons.api.HomeostaticSeasonsAPI;
import homeostaticseasons.api.Season;
import homeostaticseasons.api.SeasonWeather;
import homeostaticseasons.common.season.Weather;

public class ServerEventHandler {

    static Season lastSeason = null;

    /*
     * Check every second to see if the season has changed, and update the weather accordingly.
     */
    public static void onLevelTick(ServerLevel level) {
        if (SeasonWeather.isValid(level) && level.getGameTime() % 20 == 0) {
            Season currentSeason = HomeostaticSeasonsAPI.getCurrentSeason(level);

            if (lastSeason != currentSeason) {
                lastSeason = currentSeason;
                Weather.updateWeather(level, currentSeason);
            }
        }
    }

}
