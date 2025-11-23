package homeostaticseasons.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import homeostaticseasons.HomeostaticSeasons;
import homeostaticseasons.api.Season;
import homeostaticseasons.common.season.WeatherData;
import homeostaticseasons.common.season.WeatherDataManager;

import static homeostaticseasons.HomeostaticSeasons.prefix;
import static net.minecraft.server.level.ServerLevel.RAIN_DELAY;
import static net.minecraft.server.level.ServerLevel.THUNDER_DELAY;

public class WeatherDataProvider implements DataProvider {

    private final Map<ResourceLocation, WeatherData> SEASONS_WEATHER_MAP = new HashMap<>();
    private final PackOutput packOutput;

    public WeatherDataProvider(final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected void registerWeatherData() {
        add(prefix(Season.EARLY_SPRING.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), 65000, THUNDER_DELAY.getMinValue(), THUNDER_DELAY.getMaxValue()));
        add(prefix(Season.MID_SPRING.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), 36000, THUNDER_DELAY.getMinValue(), 96000));
        add(prefix(Season.LATE_SPRING.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), 65000, THUNDER_DELAY.getMinValue(), 84000));
        add(prefix(Season.EARLY_SUMMER.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), RAIN_DELAY.getMaxValue(), THUNDER_DELAY.getMinValue(), 72000));
        add(prefix(Season.MID_SUMMER.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), RAIN_DELAY.getMaxValue(), THUNDER_DELAY.getMinValue(), 64000));
        add(prefix(Season.LATE_SUMMER.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), RAIN_DELAY.getMaxValue(), THUNDER_DELAY.getMinValue(), 96000));
        add(prefix(Season.EARLY_AUTUMN.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), RAIN_DELAY.getMaxValue(), THUNDER_DELAY.getMinValue(), THUNDER_DELAY.getMaxValue()));
        add(prefix(Season.MID_AUTUMN.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), RAIN_DELAY.getMaxValue(), THUNDER_DELAY.getMinValue(), THUNDER_DELAY.getMaxValue()));
        add(prefix(Season.LATE_AUTUMN.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), RAIN_DELAY.getMaxValue(), THUNDER_DELAY.getMinValue(), THUNDER_DELAY.getMaxValue()));
        add(prefix(Season.EARLY_WINTER.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), 90000, -1, -1));
        add(prefix(Season.MID_WINTER.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), 90000, -1, -1));
        add(prefix(Season.LATE_WINTER.getSerializedName()), new WeatherData(RAIN_DELAY.getMinValue(), 90000, -1, -1));
    }

    protected void add(ResourceLocation seasonLoc, WeatherData weatherData) {
        SEASONS_WEATHER_MAP.put(seasonLoc, weatherData);
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        registerWeatherData();

        for (Map.Entry<ResourceLocation, WeatherData> entry : SEASONS_WEATHER_MAP.entrySet()) {
            PackOutput.PathProvider pathProvider = getPath();

            futures.add(DataProvider.saveStable(cache,
                WeatherDataManager.parseWeatherData(entry.getValue()),
                pathProvider.json(entry.getKey())));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public @NotNull String getName() {
        return HomeostaticSeasons.MOD_NAME + " - Weather Data";
    }

    private PackOutput.PathProvider getPath() {
        return this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "seasons/weather/");
    }

}
