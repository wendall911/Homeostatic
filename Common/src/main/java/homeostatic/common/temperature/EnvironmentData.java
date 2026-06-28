package homeostatic.common.temperature;

import java.util.ArrayList;

import com.google.common.collect.ImmutableList;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraft.world.level.saveddata.WeatherData;

import climatesettings.common.biome.BiomeTypeData;
import climatesettings.common.biome.BiomeTypeDataManager;
import climatesettings.common.biome.HomeostaticClimateSettings;

import homeostatic.data.integration.ModIntegration;
import homeostatic.platform.Services;
import homeostatic.util.RegistryHelper;
import homeostatic.util.TempHelper;
import homeostatic.util.WetnessHelper;

import static climatesettings.platform.Services.CLIMATE;
import static technology.roughness.whitenoise.platform.Services.WN_PLATFORM;

public class EnvironmentData {

    private boolean isSubmerged;
    private boolean isPartialSubmersion;
    private double relativeHumidity;
    private float airTemperature;
    private float waterTemperature;
    private float localTemperature;
    private double envRadiation;

    private static final PerlinSimplexNoise TEMPERATURE_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(1234L)), ImmutableList.of(0));

    /*
     * Returns WBGT
     * See: https://en.wikipedia.org/wiki/Wet-bulb_globe_temperature
     */
    public EnvironmentData(ServerPlayer sp, BlockPos pos, Holder<Biome> biome, ServerLevel level) {
        ArrayList<Pair<Holder<Biome>, BlockPos>> biomes = new ArrayList<>();
        int chunkRange = 3;
        float accumulatedDryTemp = 0.0F;
        float accumulatedHumidity = 0.0F;
        float moisture = 0.0F;
        float dryTemp;
        float dayNightOffset;
        float wetTemp;
        float blackGlobeTemp;
        EnvironmentInfo envData = Environment.get(level, sp);
        boolean isUnderground = envData.isUnderground();
        boolean isSheltered = envData.isSheltered();
        double waterVolume = envData.getWaterVolume();
        Registry<Biome> biomeRegistry = RegistryHelper.getRegistry(level.getServer(), Registries.BIOME);
        Holder<Biome> lushBiome = biomeRegistry.wrapAsHolder(biomeRegistry.getValueOrThrow(Biomes.LUSH_CAVES));

        this.envRadiation = envData.getRadiation();
        this.isPartialSubmersion = !sp.isUnderWater() && sp.isInWater() && sp.isInWaterOrRain();
        this.isSubmerged = sp.isUnderWater() && sp.isInWater() && sp.isInWaterOrRain();

        if (isSubmerged) {
            moisture = 20.0F;
        }
        else if (isPartialSubmersion) {
            moisture = 10.0F;
        }
        else if (sp.isInWaterOrRain()) {
            moisture = 0.5F;
        }

        if (moisture > 0.0F) {
            WetnessHelper.updateWetnessInfo(sp, moisture, true);
        }

        /*
         * Since we can literally jump vertically out of the water, check the block under the player to see if they are
         * "swimming" ... mc mechanics are so weird, lol.
         */
        if (!this.isPartialSubmersion && !sp.isPassenger()) {
            this.isPartialSubmersion = sp.getInBlockState().is(Blocks.WATER);
        }

        // If sheltered, consider local biome UNDERGROUND
        if (isSheltered || isUnderground) {
            biomes.add(Pair.of(lushBiome, pos));
        }

        // Only do biome smoothing if not underground or player is submerged
        if (!isUnderground || this.isSubmerged) {
            for (int x = -chunkRange; x <= chunkRange; x++) {
                for (int z = -chunkRange; z <= chunkRange; z++) {
                    BlockPos chunkPos = pos.offset(x * 16, 0, z * 16);

                    if (level.isLoaded(chunkPos)) {
                        biomes.add(Pair.of(level.getBiome(chunkPos), chunkPos));
                    }
                }
            }
        }

        for (Pair<Holder<Biome>, BlockPos> pair : biomes) {
            Holder<Biome> chunkBiome = pair.getFirst();
            BlockPos chunkPos = pair.getSecond();

            float chunkTemp = getHeightAdjustedTemperature(level, chunkBiome, chunkPos);

            accumulatedDryTemp += isUnderground ? chunkTemp : getSeasonAdjustedTemperature(level, chunkBiome, chunkTemp, chunkPos);

            // If weather is enabled
            if (level.getGameRules().get(GameRules.ADVANCE_WEATHER)) {
                double chunkHumidity = getBiomeHumidity(level, chunkBiome, chunkPos);

                accumulatedHumidity += (float) chunkHumidity;
            }
        }

        this.relativeHumidity = accumulatedHumidity / biomes.size();
        dayNightOffset = isUnderground ? 0F : getDayNightOffset(level, biome, this.relativeHumidity);
        dryTemp = (accumulatedDryTemp / biomes.size()) + dayNightOffset;
        wetTemp = (float) TempHelper.getHeatIndex(dryTemp, this.relativeHumidity);
        blackGlobeTemp = (float) getBlackGlobeTemp(level, pos, dryTemp, this.relativeHumidity);

        if (isSheltered || isUnderground) {
            //If not exposed to solar radiation, we use the simplified formula for temperature calculation.
            this.airTemperature = (wetTemp * 0.7F) + (blackGlobeTemp * 0.3F);
        }
        else {
            this.airTemperature = (wetTemp * 0.7F) + (blackGlobeTemp * 0.2F) + (dryTemp * 0.1F);
        }

        if (this.isSubmerged || this.isPartialSubmersion) {
            this.waterTemperature = getWaterTemperature(this.airTemperature, waterVolume);

            if (this.isSubmerged) {
                this.localTemperature = this.waterTemperature;
            }
            else {
                this.localTemperature = (this.waterTemperature * 0.7F) + (this.airTemperature * 0.3F);
            }
        }
        else {
            this.localTemperature = this.airTemperature;
        }
        /*
        Homeostatic.LOGGER.warn("[BiomeTemperature] Biome: {}, surrounding biomes: {}, Pos: {}, DryTemp: {}, WetTemp: {}, BlackGlobeTemp: {}, RH: {}, DayNightOffset: {}, AirTemp: {}",
            biome.getRegisteredName(),
            biomes.size(),
            pos,
            dryTemp,
            wetTemp,
            blackGlobeTemp,
            relativeHumidity,
            dayNightOffset,
            this.airTemperature
        );
         */
    }

    public boolean isSubmerged() {
        return isSubmerged;
    }

    public boolean isPartialSubmersion() {
        return isPartialSubmersion;
    }

    public double getRelativeHumidity() {
        return relativeHumidity;
    }

    public float getLocalTemperature() {
        return localTemperature;
    }

    public double getEnvRadiation() {
        return envRadiation;
    }

    /*
     * Calculate current radiation where player is standing.
     */
    private double getBlackGlobeTemp(ServerLevel level, BlockPos pos, float dryTemp, double relativeHumidity) {
        this.envRadiation += getSunRadiation(level, pos);

        return TempHelper.getBlackGlobe(this.envRadiation, dryTemp, relativeHumidity);
    }

    /*
     * Only calculate humidity for rain and snow biomes
     */
    private static double getBiomeHumidity(ServerLevel level, Holder<Biome> biomeHolder, BlockPos pos) {
        Biome biome = biomeHolder.value();
        WeatherData weatherData = level.getWeatherData();
        double biomeHumidity;
        double maxRH = getMaxBiomeHumidity(level, biomeHolder, pos);
        double minRH = maxRH - 20;

        if (biome.hasPrecipitation()) {
            int nextRain = weatherData.getClearWeatherTime();

            if (weatherData.isRaining()) {
                biomeHumidity = maxRH;
            } else if (nextRain > 0 && nextRain <= 12000) {
                biomeHumidity = minRH + (20 * (1 - ((float) nextRain / 12000)));
            } else {
                biomeHumidity = minRH;
            }
        }
        else {
            biomeHumidity = minRH;
        }

        return biomeHumidity;
    }

    /*
     * Based on sun angle ... do mathy things to get radiation
     */
    private static double getSunRadiation(ServerLevel level, BlockPos pos) {
        double radiation = 0.0;
        double sunlight = level.getBrightness(LightLayer.SKY, pos.above()) - level.getSkyDarken();
        float f = getSunAngle(level);

        if (sunlight > 0) {
            float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
            f += (f1 - f) * 0.2F;
            sunlight = sunlight * Mth.cos(f);
        }

        radiation += sunlight * 100;

        double sunRadiation = Math.max(radiation, 0);

        if (sunRadiation > 0) {
            sunRadiation = radiationOffset(sunRadiation, level);
        }

        return sunRadiation;
    }

    /*
     * Seasonally offset radiation/temperature values.
     */
    private static double radiationOffset(double value, ServerLevel level) {
        long time = (level.getDefaultClockTime() % 24000);
        SubSeason subSeason = Services.PLATFORM.getSubSeason(level);

        if (subSeason == null) {
            subSeason = SubSeason.MID_SPRING;
        }

        /*
         * Seasonal offsets for solar radiation throughout the year.
         * MID_SPRING is the highest temp zone;
         */
        switch (subSeason) {
            case EARLY_SPRING -> value *= 0.95F;
            case LATE_SPRING -> value *= 0.87F;
            case EARLY_SUMMER -> value *= 0.945F;
            case MID_SUMMER -> value *= 0.92F;
            case LATE_SUMMER -> value *= 0.888F;
            case EARLY_AUTUMN -> value *= 0.74F;
            case MID_AUTUMN -> value *= 0.716F;
            case LATE_AUTUMN -> value *= 0.56F;
            case EARLY_WINTER -> value *= 0.2F;
            case MID_WINTER -> value *= 0.1F;
            case LATE_WINTER -> value *= 0.3F;
        }

        // If raining, reduce the day/night offset by 90% during day hours (23000 - 9000)
        if (level.getWeatherData().isRaining() && (time > 23000 || time < 9001)) {
            value *= 0.1F;
        }

        return value;
    }

    private static float getSunAngle(ServerLevel level) {
        return timeOfDay(level) * ((float)Math.PI * 2F);
    }

    private static float timeOfDay(ServerLevel level) {
        double d0 = Mth.frac(level.getDefaultClockTime() / 24000.0 - 0.25);
        double d1 = 0.5 - Math.cos(d0 * Math.PI) / 2.0;
        return (float)(d0 * 2.0 + d1) / 3.0F;
    }

    private static double getMaxBiomeHumidity(ServerLevel level, Holder<Biome> biomeHolder, BlockPos pos) {
        BiomeTypeData biomeTypeData = BiomeTypeDataManager.getDataForBiome(biomeHolder);

        return biomeTypeData.getHumidity(biomeHolder.value().getPrecipitationAt(pos, level.getSeaLevel()));
    }

    private static float getWaterTemperature(float airTemperature, double waterVolume) {
        float baseWaterTemp = 0.663F;
        float waterTemp;

        if (airTemperature >= Environment.PARITY) {
            float increase = 0.1F + ((float) (1 - waterVolume) * 0.35F);

            waterTemp = baseWaterTemp + ((airTemperature - Environment.PARITY) * increase);
        }
        else {
            waterTemp = Math.max(baseWaterTemp - (((Environment.PARITY - airTemperature) * 0.5F)), 0.072F);
        }

        return waterTemp;
    }

    private static float getDayNightOffset(ServerLevel level, Holder<Biome> biome, double relativeHumidity) {
        ResourceKey<Level> worldKey = level.dimension();

        /*
         * Only calculate in Overworld.
         */
        if (!worldKey.identifier().toString().contains(BuiltinDimensionTypes.OVERWORLD.identifier().toString())) {
            return 0F;
        }

        BiomeTypeData biomeTypeData = BiomeTypeDataManager.getDataForBiome(biome);
        long time = (level.getDefaultClockTime() % 24000);
        HomeostaticClimateSettings climateSettings = CLIMATE.getClimateSettings(biome);
        float maxTemp = biomeTypeData.getDayNightOffset(climateSettings.getPrecipitationType());

        if (maxTemp == 0F) return maxTemp;

        float increaseTemp = maxTemp / 10000F;
        float decreaseTemp = maxTemp / 14000F;
        float humidityOffset = 1.0F - (float) (relativeHumidity / 100);
        float offset;

        increaseTemp = (float) radiationOffset(increaseTemp, level);

        if (time > 23000) {
            offset = (time - 23000) * increaseTemp;
        }
        else if (time < 9001) {
            offset = (time + 1000) * increaseTemp;
        }
        else {
            offset = maxTemp - ((time - 9000) * decreaseTemp);
        }

        return offset * humidityOffset;
    }

    private static float getHeightAdjustedTemperature(ServerLevel level, Holder<Biome> biomeHolder, BlockPos pos) {
        ResourceKey<Level> worldKey = level.dimension();
        BiomeTypeData biomeTypeData = BiomeTypeDataManager.getDataForBiome(biomeHolder);
        Biome.Precipitation precipitation = biomeHolder.value().getPrecipitationAt(pos, level.getSeaLevel());
        float temperature = biomeTypeData.getTemperature(precipitation);

        /*
         * Only calculate in Overworld.
         */
        if (!worldKey.identifier().toString().contains(BuiltinDimensionTypes.OVERWORLD.identifier().toString())) {
            return temperature;
        }

        /*
         * If not already a snowy biome, add SNOW offset if Primal Winter mod is loaded.
         */
        if (WN_PLATFORM.isModLoaded(ModIntegration.PW_MODID)
                && precipitation != Biome.Precipitation.SNOW) {
            temperature += BiomeTypeData.SNOW_OFFSET;
        }

        if (pos.getY() > 80) {
            float noise = (float)(TEMPERATURE_NOISE.getValue((double)((float)pos.getX() / 8.0F), (double)((float)pos.getZ() / 8.0F), false) * 8.0D);
            return temperature - (noise + getAdjustedHeight(level, (float)pos.getY()) - 80.0F) * 0.05F / 40.0F;
        } else {
            return temperature;
        }
    }

    /*
     * Adjust height based on default max build height of 256.
     * Fixes math to give a corrected height even if max height has been modified.
     */
    private static float getAdjustedHeight(ServerLevel level, float y) {
        return y / (level.getMaxY() / 256.0F);
    }

    private static float getSeasonAdjustedTemperature(ServerLevel level, Holder<Biome> biomeHolder, float biomeTemp, BlockPos pos) {
        ResourceKey<Level> worldKey = level.dimension();

        /*
         * Only calculate season temperatures in Overworld
         */
        if (!worldKey.identifier().toString().contains(BuiltinDimensionTypes.OVERWORLD.identifier().toString())) {
            return biomeTemp;
        }

        BiomeTypeData biomeTypeData = BiomeTypeDataManager.getDataForBiome(biomeHolder);
        SubSeason subSeasonHolder = Services.PLATFORM.getSubSeason(level);

        if (subSeasonHolder != null) {
            int season;
            float lateSummerOffset = biomeTypeData.MC_DEGREE * 5;
            int subSeason = subSeasonHolder.ordinal();
            float variation = biomeTypeData.getSeasonVariation(biomeHolder.value().getPrecipitationAt(pos, level.getSeaLevel())) / 2.0F;

            if ((subSeason + 9) <= 12) {
                season = subSeason + 9;
            }
            else {
                season = subSeason - 3;
            }

            double temp = getSeasonTemperature(season, variation, biomeTemp);

            if (season == 2) {
                temp += lateSummerOffset;
            }

            return (float) temp;
        }
        /*
         * Always set season to winter if Primal Winter mod is loaded.
         *
         * Will always use the full season temperature variation in calculatons.
         *
         * Always will use the full season temperature variation used in RAIN calculations.
         */
        else if (WN_PLATFORM.isModLoaded(ModIntegration.PW_MODID)) {
            int season = 7;
            float variation = biomeTypeData.getSeasonVariation(Biome.Precipitation.RAIN);
            double temp = getSeasonTemperature(season, variation, biomeTemp);

            return (float) temp;
        }

        return biomeTemp;
    }

    private static double getSeasonTemperature(int season, float variation, float biomeTemp) {
        return variation * Math.cos(((season - 1) * Math.PI) / 6) + biomeTemp;
    }

    /*
     * Need to mock what the internal biome method does for precipitation type,
     * this ensures if another mod installed, the default vanilla behavior is preserved.
     */
    public static Precipitation getPrecipitationAt(Biome biome, BlockPos pos, Level level) {
        if (!biome.hasPrecipitation()) {
            return Biome.Precipitation.NONE;
        }
        else {
            return coldEnoughToSnow(biome, pos, level) ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN;
        }
    }

    public static boolean coldEnoughToSnow(Biome biome, BlockPos pos, Level level) {
        return !warmEnoughToRain(biome, pos, level);
    }

    @SuppressWarnings("deprecation")
    public static boolean warmEnoughToRain(Biome biome, BlockPos pos, Level level) {
        return biome.getTemperature(pos, level.getSeaLevel()) >= 0.15F;
    }

    @Override
    public String toString() {
        return "EnvironmentData{" +
                "isSubmerged=" + isSubmerged +
                ", isPartialSubmersion=" + isPartialSubmersion +
                ", relativeHumidity=" + relativeHumidity +
                ", airTemperature=" + airTemperature +
                ", waterTemperature=" + waterTemperature +
                ", localTemperature=" + localTemperature +
                ", envRadiation=" + envRadiation +
                "}";
    }

}
