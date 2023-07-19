package homeostatic.common.temperature;

import java.util.ArrayList;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.ServerLevelData;

import net.minecraftforge.fml.ModList;

import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.ServerConfig;

import homeostatic.common.biome.BiomeData;
import homeostatic.common.biome.BiomeRegistry;
import homeostatic.mixin.ServerLevelAccessor;
import homeostatic.util.TempHelper;
import homeostatic.util.WetnessHelper;

public class EnvironmentData {

    private boolean isSubmerged;
    private boolean isPartialSubmersion;
    private double relativeHumidity;
    private float airTemperature;
    private float waterTemperature;
    private float localTemperature;
    private double envRadiation;

    /*
     * Returns WBGT
     * See: https://en.wikipedia.org/wiki/Wet-bulb_globe_temperature
     */
    public EnvironmentData(ServerPlayer sp, BlockPos pos, Holder<Biome> biome, ServerLevel world) {
        LevelData info = world.getLevelData();
        ArrayList<Pair<Holder<Biome>, BlockPos>> biomes = new ArrayList<>();
        int chunkRange = 3;
        float accumulatedDryTemp = 0.0F;
        float accumulatedHumidity = 0.0F;
        float moisture = 0.0F;
        float dryTemp;
        float dayNightOffset;
        float wetTemp;
        float blackGlobeTemp;
        EnvironmentInfo envData = Environment.get(world, sp);
        boolean isUnderground = envData.isUnderground();
        boolean isSheltered = envData.isSheltered();
        double waterVolume = envData.getWaterVolume();
        Holder<Biome> lushBiome = world.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(Biomes.LUSH_CAVES);

        this.envRadiation = envData.getRadiation();
        this.isPartialSubmersion = !sp.isUnderWater() && sp.isInWater() && sp.isInWaterRainOrBubble();
        this.isSubmerged = sp.isUnderWater() && sp.isInWater() && sp.isInWaterRainOrBubble();

        if (isSubmerged) {
            moisture = 20.0F;
        }
        else if (isPartialSubmersion) {
            moisture = 10.0F;
        }
        else if (sp.isInWaterRainOrBubble()) {
            moisture = 0.5F;
        }

        if (moisture > 0.0F) {
            WetnessHelper.updateWetnessInfo(sp, moisture, true);
        }

        /*
         * Since we can literally jump vertically out of the water, check the block under the player to see if they are
         * "swimming" ... mc mechanics are so weird, lol.
         */
        if (!this.isPartialSubmersion) {
            this.isPartialSubmersion = sp.getFeetBlockState().getMaterial().equals(Material.WATER);
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

                    if (world.isLoaded(chunkPos)) {
                        biomes.add(Pair.of(world.getBiome(chunkPos), chunkPos));
                    }
                }
            }
        }

        for (Pair<Holder<Biome>, BlockPos> pair : biomes) {
            Holder<Biome> chunkBiome = pair.getFirst();
            BlockPos chunkPos = pair.getSecond();

            float chunkTemp = chunkBiome.value().getHeightAdjustedTemperature(chunkPos);

            accumulatedDryTemp += isUnderground ? chunkTemp : getSeasonAdjustedTemperature(world, chunkBiome, chunkTemp);

            // If weather is enabled
            if (info.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
                double chunkHumidity = getBiomeHumidity(world, chunkBiome);

                accumulatedHumidity += chunkHumidity;
            }
        }

        this.relativeHumidity = accumulatedHumidity / biomes.size();
        dayNightOffset = isUnderground ? 0F : getDayNightOffset(world, biome, this.relativeHumidity);
        dryTemp = (accumulatedDryTemp / biomes.size()) + dayNightOffset;
        wetTemp = (float) TempHelper.getHeatIndex(dryTemp, this.relativeHumidity);
        blackGlobeTemp = (float) getBlackGlobeTemp(world, pos, dryTemp, this.relativeHumidity);

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
    private double getBlackGlobeTemp(ServerLevel world, BlockPos pos, float dryTemp, double relativeHumidity) {
        this.envRadiation += getSunRadiation(world, pos);

        return TempHelper.getBlackGlobe(this.envRadiation, dryTemp, relativeHumidity);
    }

    /*
     * Only calculate humidity for rain and snow biomes
     */
    private static double getBiomeHumidity(ServerLevel world, Holder<Biome> biome) {
        LevelData info = world.getLevelData();
        ServerLevelAccessor serverLevel = (ServerLevelAccessor) world;
        ServerLevelData serverInfo = serverLevel.homeostatic$getServerLevelData();
        double biomeHumidity;
        double maxRH = getMaxBiomeHumidity(biome);
        double minRH = maxRH - 20;

        if (biome.value().getPrecipitation() != Biome.Precipitation.NONE) {
            int nextRain = serverInfo.getClearWeatherTime();

            if (info.isRaining()) {
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
    private static double getSunRadiation(ServerLevel world, BlockPos pos) {
        double radiation = 0.0;
        double sunlight = world.getBrightness(LightLayer.SKY, pos.above()) - world.getSkyDarken();
        float f = world.getSunAngle(1.0F);

        if (sunlight > 0) {
            float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
            f += (f1 - f) * 0.2F;
            sunlight = sunlight * Mth.cos(f);
        }

        radiation += sunlight * 100;

        return Math.max(radiation, 0);
    }

    private static double getMaxBiomeHumidity(Holder<Biome> biome) {
        BiomeData biomeData = BiomeRegistry.getDataForBiome(biome);

        return biomeData.getHumidity(biome);
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

    private static float getDayNightOffset(ServerLevel world, Holder<Biome> biome, double relativeHumidity) {
        BiomeData biomeData =  BiomeRegistry.getDataForBiome(biome);
        long time = (world.getDayTime() % 24000);
        float maxTemp = biomeData.getDayNightOffset(biome.value().getPrecipitation());

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

    private static float getSeasonAdjustedTemperature(ServerLevel world, Holder<Biome> biome, float biomeTemp) {
        if (ModList.get().isLoaded("sereneseasons")) {
            ResourceKey<Level> worldKey = world.dimension();
            boolean seasonEffects = ServerConfig.isDimensionWhitelisted(world.dimension());

            if (seasonEffects && worldKey.location().toString().contains(BuiltinDimensionTypes.OVERWORLD.location().toString())) {
                BiomeData biomeData =  BiomeRegistry.getDataForBiome(biome);
                int season;
                float lateSummerOffset = biomeData.MC_DEGREE * 5;
                int subSeason = SeasonHelper.getSeasonState(world).getSubSeason().ordinal();
                float variation = biomeData.getSeasonVariation(biome.value().getPrecipitation()) / 2.0F;

                if ((subSeason + 9) <= 12) {
                    season = subSeason + 9;
                }
                else {
                    season = subSeason - 3;
                }

                double temp = variation * Math.cos( ((season - 1) * Math.PI) / 6) + biomeTemp;

                if (season == 2) {
                    temp += lateSummerOffset;
                }

                return(float) temp;
            }
        }

        return biomeTemp;
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
                '}';
    }

}
