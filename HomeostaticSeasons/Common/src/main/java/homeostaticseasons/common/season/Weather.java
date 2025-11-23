package homeostaticseasons.common.season;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;

import homeostaticseasons.api.Season;
import homeostaticseasons.api.SeasonWeather;

public class Weather {

    public static void updateWeather(ServerLevel level, Season season) {
        if (!SeasonWeather.isValid(level)) {
            return;
        }

        WeatherData weatherData = WeatherDataManager.getWeatherData(season);
        ServerLevelData serverLevelData = (ServerLevelData) level.getLevelData();

        if (weatherData != null) {
            if (weatherData.canRain()) {
                if (!serverLevelData.isRaining() && serverLevelData.getRainTime() > weatherData.maxRainTime()) {
                    serverLevelData.setRainTime(
                        level.random.nextInt(weatherData.maxRainTime() - weatherData.minRainTime()) + weatherData.minRainTime());
                }
            }
            else if (serverLevelData.isRaining()) {
                serverLevelData.setRaining(false);
            }

            if (weatherData.canThunder()) {
                if (!serverLevelData.isThundering() && serverLevelData.getThunderTime() > weatherData.maxThunderTime()) {
                    serverLevelData.setThunderTime(
                        level.random.nextInt(weatherData.maxThunderTime() - weatherData.minThunderTime()) + weatherData.minThunderTime());
                }
            }
            else if (serverLevelData.isThundering()) {
                serverLevelData.setThundering(false);
            }
        }
    }

}
