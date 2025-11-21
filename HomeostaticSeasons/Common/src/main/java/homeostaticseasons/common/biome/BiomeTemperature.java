package homeostaticseasons.common.biome;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.ServerLevelData;

import climatesettings.common.biome.BiomeTypeData;
import climatesettings.common.biome.BiomeTypeDataManager;
import climatesettings.common.biome.HomeostaticClimateSettings;

import homeostaticseasons.api.HomeostaticSeasonsAPI;
import homeostaticseasons.api.Season;
import homeostaticseasons.config.ConfigHandler;
import homeostaticseasons.platform.Services;
import homeostaticseasons.util.RegistryHelper;
import homeostaticseasons.util.TemperatureHelper;

import static climatesettings.platform.Services.CLIMATE;

public class BiomeTemperature {

    private static final PerlinSimplexNoise TEMPERATURE_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

    private final Holder<Biome> biomeHolder;
    private final ServerLevel level;
    private final BlockPos blockPos;
    private final BiomeTypeData biomeTypeData;
    private final Season currentSeason;

    public BiomeTemperature(Biome biome, ServerLevel level, BlockPos blockPos) {
        this.biomeHolder = RegistryHelper.getBiomeHolder(biome, level);
        this.level = level;
        this.blockPos = blockPos;
        this.biomeTypeData = BiomeTypeDataManager.getDataForBiome(biomeHolder);
        this.currentSeason = HomeostaticSeasonsAPI.getCurrentSeason(level);
    }

    public BiomeTemperature(Holder<Biome> biomeHolder, ServerLevel level, BlockPos blockPos) {
        this.biomeHolder = biomeHolder;
        this.level = level;
        this.blockPos = blockPos;
        this.biomeTypeData = BiomeTypeDataManager.getDataForBiome(biomeHolder);
        this.currentSeason = HomeostaticSeasonsAPI.getCurrentSeason(level);
    }

    public boolean isWarmEnoughToRain() {
        return getAirTemperature() >= 0.15F;
    }

    public float getAirTemperature() {
        float biomeDryTemp = getHeightAdjustedTemperature();
        double biomeHumidity = getBiomeHumidity();

        biomeDryTemp += getDayNightOffset(biomeHumidity);
        biomeDryTemp = getSeasonAdjustedTemperature(biomeDryTemp);

        float wetTemp = (float) TemperatureHelper.getHeatIndex(biomeDryTemp, biomeHumidity);
        float blackGlobeTemp = (float) getBlackGlobeTemp(biomeDryTemp, biomeHumidity);

        return (wetTemp * 0.7F) + (blackGlobeTemp * 0.2F) + (biomeDryTemp * 0.1F);
    }

    /*
     * TODO: Move to a common utility class shared with Homeostatic.
     */
    private float getHeightAdjustedTemperature() {
        ResourceKey<Level> dimension = level.dimension();
        Biome.Precipitation precipitation = biomeHolder.value().getPrecipitationAt(blockPos);
        float temperature = biomeTypeData.getTemperature(precipitation);

        /*
         * Only calculate in whitelisted dimensions.
         */
        if (ConfigHandler.Common.isValidDimension(dimension)) {
            return temperature;
        }

        if (blockPos.getY() > 80) {
            float noise = (float)(TEMPERATURE_NOISE.getValue((float)blockPos.getX() / 8.0F, ((float)blockPos.getZ() / 8.0F), false) * 8.0D);

            return temperature - (noise + getAdjustedHeight() - 80.0F) * 0.05F / 40.0F;
        }
        else {
            return temperature;
        }
    }

    /*
     * Adjust height based on default max build height of 256.
     * Fixes math to give a corrected height even if max height has been modified.
     * TODO: Move to a common utility class shared with Homeostatic.
     */
    private float getAdjustedHeight() {
        return blockPos.getY() / (this.level.getMaxBuildHeight() / 256.0F);
    }

    /*
     * TODO: Move to a common utility class shared with Homeostatic.
     */
    private float getSeasonAdjustedTemperature(float temperature) {
        ResourceKey<Level> dimension = level.dimension();

        /*
         * Only calculate in whitelisted dimensions.
         */
        if (ConfigHandler.Common.isValidDimension(dimension)) {
            return temperature;
        }

        if (currentSeason != null) {
            int season;
            float lateSummerOffset = biomeTypeData.MC_DEGREE * 5;
            int subSeason = currentSeason.ordinal();
            float variation = biomeTypeData.getSeasonVariation(biomeHolder.value().getPrecipitationAt(blockPos)) / 2.0F;

            if ((subSeason + 9) <= 12) {
                season = subSeason + 9;
            }
            else {
                season = subSeason - 3;
            }

            double temp = getSeasonTemperature(season, variation, temperature);

            if (season == 2) {
                temp += lateSummerOffset;
            }

            return (float) temp;
        }

        return temperature;
    }

    /*
     * TODO: Move to a common utility class shared with Homeostatic.
     */
    private static double getSeasonTemperature(int season, float variation, float biomeTemp) {
        return variation * Math.cos(((season - 1) * Math.PI) / 6) + biomeTemp;
    }

    /*
     * Based on sun angle ... do mathy things to get radiation
     * TODO: Move to a common utility class shared with Homeostatic.
     */
    private double getSunRadiation() {
        double radiation = 0.0;
        double sunlight = level.getBrightness(LightLayer.SKY, blockPos.above()) - level.getSkyDarken();
        float f = level.getSunAngle(1.0F);

        if (sunlight > 0) {
            float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
            f += (f1 - f) * 0.2F;
            sunlight = sunlight * Mth.cos(f);
        }

        radiation += sunlight * 100;

        return Math.max(radiation, 0);
    }

    /*
     * Calculate current radiation at current biome position
     */
    private double getBlackGlobeTemp(float dryTemp, double relativeHumidity) {
        return TemperatureHelper.getBlackGlobe(getSunRadiation(), dryTemp, relativeHumidity);
    }

    /*
     * Only calculate humidity for rain and snow biomes
     */
    private double getBiomeHumidity() {
        LevelData info = level.getLevelData();
        Biome biome = biomeHolder.value();
        ServerLevelData serverInfo = Services.PLATFORM.getServerLevelData(level);
        double biomeHumidity;
        double maxRH = getMaxBiomeHumidity();
        double minRH = maxRH - 20;

        if (biome.hasPrecipitation()) {
            int nextRain = serverInfo.getClearWeatherTime();

            if (info.isRaining()) {
                biomeHumidity = maxRH;
            }
            else if (nextRain > 0 && nextRain <= 12000) {
                biomeHumidity = minRH + (20 * (1 - ((float) nextRain / 12000)));
            }
            else {
                biomeHumidity = minRH;
            }
        }
        else {
            biomeHumidity = minRH;
        }

        return biomeHumidity;
    }

    private double getMaxBiomeHumidity() {
        return biomeTypeData.getHumidity(biomeHolder.value().getPrecipitationAt(blockPos));
    }

    private float getDayNightOffset(double relativeHumidity) {
        ResourceKey<Level> dimension = level.dimension();

        /*
         * Only calculate in whitelisted dimensions.
         */
        if (ConfigHandler.Common.isValidDimension(dimension)) {
            return 0F;
        }

        long time = (level.getDayTime() % 24000);
        HomeostaticClimateSettings climateSettings = CLIMATE.getClimateSettings(biomeHolder);
        float maxTemp = biomeTypeData.getDayNightOffset(climateSettings.getPrecipitationType());

        if (maxTemp == 0F) return maxTemp;

        float increaseTemp = maxTemp / 10000F;
        float decreaseTemp = maxTemp / 14000F;
        float humidityOffset = 1.0F - (float) (relativeHumidity / 100);
        float offset;

        if (time > 23000) {
            offset = (24001 - time) * increaseTemp;
        } else if (time < 9001) {
            offset = (time + 1000) * increaseTemp;
        } else {
            offset = maxTemp - ((time - 9000) * decreaseTemp);
        }

        return offset * humidityOffset;
    }

    /*
     * Get the precipitation type for the biome
     * This is used to get colormaps for seasonal changes.
     * Should probably move to a common utility class shared with colormap.
     */
    public Biome.Precipitation getPrecipitationType() {
        BiomeColormap.ColormapType colormapType = BiomeColormapManager.getColormapType(biomeHolder);

        if (colormapType == BiomeColormap.ColormapType.TEMPERATE) {
            if (currentSeason.isWetSeason()) {
                return Biome.Precipitation.RAIN;
            }
            else {
                return Biome.Precipitation.NONE;
            }
        }
        else {
            return biomeHolder.value().getPrecipitationAt(blockPos);
        }
    }

}
