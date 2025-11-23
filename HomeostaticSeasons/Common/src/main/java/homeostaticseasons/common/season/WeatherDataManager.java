package homeostaticseasons.common.season;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import homeostaticseasons.HomeostaticSeasons;
import homeostaticseasons.api.Season;

public class WeatherDataManager extends SimpleJsonResourceReloadListener {

    private static final Map<Season, WeatherData> WEATHER_DATA = new HashMap<>();

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(WeatherData.class, new WeatherData.Serializer()).create();

    public WeatherDataManager() {
        super(GSON, "seasons/weather");
    }

    public static WeatherData getWeatherData(Season season) {
        return WEATHER_DATA.getOrDefault(season, null);
    }

    public static JsonElement parseWeatherData(WeatherData weatherData) {
        return GSON.toJsonTree(weatherData);
    }

    @Override
    protected void apply(@NotNull Map<ResourceLocation, JsonElement> pObject, @NotNull ResourceManager resourceManager,
                         @NotNull ProfilerFiller profilerFiller) {
        WEATHER_DATA.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            try {
                WeatherData weatherData = GSON.fromJson(entry.getValue(), WeatherData.class);

                WEATHER_DATA.put(Season.valueOf(entry.getKey().getPath().toUpperCase(Locale.ROOT)), weatherData);
            }
            catch (Exception e) {
                HomeostaticSeasons.LOGGER.error("Failed to load weather data: {}", entry.getKey(), e);
            }
        }

        HomeostaticSeasons.LOGGER.info("Loaded {} weather data entries", WEATHER_DATA.size());
        HomeostaticSeasons.LOGGER.info("Loaded {} weather data", WEATHER_DATA);
    }

}
