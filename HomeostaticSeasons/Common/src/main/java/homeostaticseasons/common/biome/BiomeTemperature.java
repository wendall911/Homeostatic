package homeostaticseasons.common.biome;

import java.util.ArrayList;

import com.google.common.collect.ImmutableList;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.storage.LevelData;

import climatesettings.common.biome.BiomeTypeData;
import climatesettings.common.biome.BiomeTypeDataManager;
import climatesettings.common.biome.HomeostaticClimateSettings;

import homeostaticseasons.api.HomeostaticSeasonsAPI;
import homeostaticseasons.api.Season;
import homeostaticseasons.util.RegistryHelper;
import homeostaticseasons.util.TemperatureHelper;

import static climatesettings.platform.Services.CLIMATE;

public class BiomeTemperature {

    private static final PerlinSimplexNoise TEMPERATURE_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

    private final Holder<Biome> biomeHolder;
    private final Level level;
    private final BlockPos blockPos;
    private final Season currentSeason;
    private float airTemperature;

    public BiomeTemperature(Biome biome, Level level, BlockPos blockPos, float airTemperature) {
        this.biomeHolder = RegistryHelper.getBiomeHolder(biome, level);
        this.level = level;
        this.blockPos = blockPos;
        this.currentSeason = HomeostaticSeasonsAPI.getCurrentSeason(level);
        this.airTemperature = airTemperature;
    }

    public BiomeTemperature(Holder<Biome> biomeHolder, Level level, BlockPos blockPos, float airTemperature) {
        this.biomeHolder = biomeHolder;
        this.level = level;
        this.blockPos = blockPos;
        this.currentSeason = HomeostaticSeasonsAPI.getCurrentSeason(level);
        this.airTemperature = airTemperature;
    }

    public boolean isWarmEnoughToRain() {
        return getAirTemperature() >= 0.15F;
    }

    public float getAirTemperature() {
        if (!Float.isNaN(this.airTemperature)) {
            return this.airTemperature;
        }

        LevelData info = level.getLevelData();
        ArrayList<Pair<Holder<Biome>, BlockPos>> biomes = new ArrayList<>();
        int chunkRange = 3;
        float accumulatedDryTemp = 0.0F;
        float accumulatedHumidity = 0.0F;
        double relativeHumidity;
        float dryTemp;
        float dayNightOffset;
        float wetTemp;
        float blackGlobeTemp;

        // Gather biomes in the surrounding chunks
        for (int x = -chunkRange; x <= chunkRange; x++) {
            for (int z = -chunkRange; z <= chunkRange; z++) {
                BlockPos chunkPos = blockPos.offset(x * 16, 0, z * 16);

                if (chunkPos instanceof MutableBlockPos) {
                    // This is on client, need to ensure chunks are loaded so we can calculate temperature properly
                    level.getChunkSource().getChunk(SectionPos.blockToSectionCoord(blockPos.getX()), SectionPos.blockToSectionCoord(blockPos.getZ()), true);

                    if (!level.isOutsideBuildHeight(blockPos)) {
                        biomes.add(Pair.of(level.getBiome(chunkPos), chunkPos));
                    }
                }
                else if (level.isLoaded(chunkPos)) {
                    biomes.add(Pair.of(level.getBiome(chunkPos), chunkPos));
                }
            }
        }

        for (Pair<Holder<Biome>, BlockPos> pair : biomes) {
            Holder<Biome> chunkBiome = pair.getFirst();
            BlockPos chunkPos = pair.getSecond();

            float chunkTemp = getHeightAdjustedTemperature(level, chunkBiome, chunkPos);

            accumulatedDryTemp += getSeasonAdjustedTemperature(level, chunkBiome, chunkTemp, chunkPos, currentSeason);

            // If weather is enabled
            if (info.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
                double chunkHumidity = getBiomeHumidity(level, chunkBiome, chunkPos);

                accumulatedHumidity += (float) chunkHumidity;
            }
        }

        relativeHumidity = accumulatedHumidity / biomes.size();
        dayNightOffset = getDayNightOffset(level, biomeHolder, relativeHumidity);
        dryTemp = (accumulatedDryTemp / biomes.size()) + dayNightOffset;
        wetTemp = (float) TemperatureHelper.getHeatIndex(dryTemp, relativeHumidity);
        blackGlobeTemp = (float) getBlackGlobeTemp(level, blockPos, dryTemp, relativeHumidity);

        this.airTemperature = (wetTemp * 0.7F) + (blackGlobeTemp * 0.2F) + (dryTemp * 0.1F);

        /*
        HomeostaticSeasons.LOGGER.warn("[BiomeTemperature] Biome: {}, surrounding biomes: {}, Pos: {}, DryTemp: {}, WetTemp: {}, BlackGlobeTemp: {}, RH: {}, DayNightOffset: {}, AirTemp: {}",
            biomeHolder.getRegisteredName(),
            biomes.size(),
            blockPos,
            dryTemp,
            wetTemp,
            blackGlobeTemp,
            relativeHumidity,
            dayNightOffset,
            TemperatureHelper.convertMcTemp(this.airTemperature, true)
        );
         */

        return this.airTemperature;
    }

    private static float getHeightAdjustedTemperature(Level level, Holder<Biome> biomeHolder, BlockPos blockPos) {
        Biome.Precipitation precipitation = getPrecipitationAt(biomeHolder.value(), blockPos);
        BiomeTypeData biomeTypeData = BiomeTypeDataManager.getDataForBiome(biomeHolder);
        float temperature = biomeTypeData.getTemperature(precipitation);

        if (blockPos.getY() > 80) {
            float noise = (float)(TEMPERATURE_NOISE.getValue((float)blockPos.getX() / 8.0F, ((float)blockPos.getZ() / 8.0F), false) * 8.0D);

            return temperature - (noise + getAdjustedHeight(level, blockPos.getY()) - 80.0F) * 0.05F / 40.0F;
        }
        else {
            return temperature;
        }
    }

    /*
     * Adjust height based on default max build height of 256.
     * Fixes math to give a corrected height even if max height has been modified.
     */
    private static float getAdjustedHeight(Level level, float y) {
        return y / (level.getMaxBuildHeight() / 256.0F);
    }

    private static float getSeasonAdjustedTemperature(Level level, Holder<Biome> biomeHolder,
            float temperature, BlockPos blockPos, Season currentSeason) {
        BiomeTypeData biomeTypeData = BiomeTypeDataManager.getDataForBiome(biomeHolder);

        if (currentSeason != null) {
            int season;
            float lateSummerOffset = BiomeTypeData.MC_DEGREE * 5;
            int subSeason = currentSeason.ordinal();
            float variation = biomeTypeData.getSeasonVariation(getPrecipitationAt(biomeHolder.value(), blockPos)) / 2.0F;

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

    private static double getSeasonTemperature(int season, float variation, float biomeTemp) {
        return variation * Math.cos(((season - 1) * Math.PI) / 6) + biomeTemp;
    }

    /*
     * Based on sun angle ... do mathy things to get radiation
     */
    private double getSunRadiation(Level level, BlockPos blockPos) {
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
    private double getBlackGlobeTemp(Level level, BlockPos blockPos, float dryTemp, double relativeHumidity) {
        return TemperatureHelper.getBlackGlobe(getSunRadiation(level, blockPos), dryTemp, relativeHumidity);
    }

    /*
     * Only calculate humidity for rain and snow biomes
     * This differs from Homeostatic which calculates time to next rain event,
     * but here we use the rainLevel as an approximation if not raining.
     */
    private static double getBiomeHumidity(Level level, Holder<Biome> biomeHolder, BlockPos blockPos) {
        Biome biome = biomeHolder.value();
        double biomeHumidity;
        double maxRH = getMaxBiomeHumidity(biomeHolder, blockPos);
        double minRH = maxRH - 20;

        if (biome.hasPrecipitation()) {
            float rainLevel = level.getRainLevel(1.0F);

            if (level.isRaining()) {
                biomeHumidity = maxRH;
            }
            else {
                biomeHumidity = minRH + (20 * (0.2 - rainLevel));
            }
        }
        else {
            biomeHumidity = minRH;
        }

        return biomeHumidity;
    }

    private static double getMaxBiomeHumidity(Holder<Biome> biomeHolder, BlockPos blockPos) {
        BiomeTypeData biomeTypeData = BiomeTypeDataManager.getDataForBiome(biomeHolder);

        return biomeTypeData.getHumidity(getPrecipitationAt(biomeHolder.value(), blockPos));
    }

    private static float getDayNightOffset(Level level, Holder<Biome> biomeHolder, double relativeHumidity) {
        long time = (level.getDayTime() % 24000);
        HomeostaticClimateSettings climateSettings = CLIMATE.getClimateSettings(biomeHolder);
        BiomeTypeData biomeTypeData = BiomeTypeDataManager.getDataForBiome(biomeHolder);
        float maxTemp = biomeTypeData.getDayNightOffset(climateSettings.getPrecipitationType());

        if (maxTemp == 0F) {
            return maxTemp;
        }

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
                return isWarmEnoughToRain() ? Biome.Precipitation.RAIN : Biome.Precipitation.SNOW;
            }
            else {
                return Biome.Precipitation.NONE;
            }
        }
        else {
            return isWarmEnoughToRain() ? Biome.Precipitation.RAIN : Biome.Precipitation.SNOW;
        }
    }

    /*
     * Need to mock what the internal biome method does for precipitation type,
     * as this is used to override on the client.
     */
    public static Precipitation getPrecipitationAt(Biome biome, BlockPos pos) {
        if (!biome.hasPrecipitation()) {
            return Biome.Precipitation.NONE;
        }
        else {
            return coldEnoughToSnow(biome, pos) ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN;
        }
    }

    public static boolean coldEnoughToSnow(Biome biome, BlockPos pos) {
        return !warmEnoughToRain(biome, pos);
    }

    public static boolean warmEnoughToRain(Biome biome, BlockPos pos) {
        return biome.getTemperature(pos) >= 0.15F;
    }

    @Override
    public String toString() {
        return "BiomeTemperature{biome=" + biomeHolder.getRegisteredName() +
               ", pos=" + blockPos +
               ", season=" + currentSeason +
               ", airTemperature=" + airTemperature +
               '}';
    }

}
