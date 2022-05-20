package homeostatic.common;

import java.util.ArrayList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.ServerLevelData;

import net.minecraftforge.fml.ModList;

import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.BiomeConfig;

import homeostatic.Homeostatic;
import homeostatic.mixin.ServerLevelAccessor;
import homeostatic.util.TempHelper;

public final class Temperature {

    /*
     * Returns WBGT
     * See: https://en.wikipedia.org/wiki/Wet-bulb_globe_temperature
     */
    public static float getLocal(ServerPlayer sp, BlockPos pos, Holder<Biome> biome, ServerLevel world) {
        LevelData info = world.getLevelData();
        ArrayList<Holder<Biome>> biomes = new ArrayList<>();
        int chunks = 3;
        float accumulatedDryTemp = 0.0F;
        float accumulatedHumidity = 0.0F;
        double relativeHumidity;
        float dryTemp;
        float wetTemp;
        float blackGlobeTemp;
        float airTemperature;
        float waterTemperature;
        EnvironmentData envData = Environment.getData(world, sp);
        boolean isUnderground = envData.isUnderground();
        boolean isSheltered = envData.isSheltered();
        double envRadiation = envData.getRadiation();
        double waterVolume = envData.getWaterVolume();
        boolean isPartialSubmersion = !sp.isUnderWater() && sp.isInWater() && sp.isInWaterRainOrBubble();
        boolean isSubmerged = sp.isUnderWater() && sp.isInWater() && sp.isInWaterRainOrBubble();
        Holder<Biome> lushBiome = world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getHolderOrThrow(Biomes.LUSH_CAVES);

        /*
         * Since we can literally jump vertically out of the water, check the block under the player to see if they are
         * "swimming" ... mc mechanics are so weird, lol.
         */
        if (!isPartialSubmersion) {
            isPartialSubmersion = sp.getFeetBlockState().getMaterial().equals(Material.WATER);
        }

        // If sheltered, consider local biome UNDERGROUND
        if (isSheltered || isUnderground) {
            biomes.add(lushBiome);
        }
        else {
            biomes.add(biome);
        }

        // Only do biome smoothing if not underground or submerged
        if (!isUnderground || isSubmerged) {
            for (int x = chunks; x < chunks + 1; x++) {
                for (int z = -chunks; z < chunks + 1; z++) {
                    BlockPos chunkPos = pos.offset(x * 16, 0, z * 16);

                    if (world.isLoaded(chunkPos)) {
                        biomes.add(world.getBiome(chunkPos));
                    }
                }
            }
        }

        for (Holder<Biome> chunkBiome : biomes) {
            float seasonOffset = getSeasonOffset(world, BiomeConfig.enablesSeasonalEffects(chunkBiome));

            accumulatedDryTemp += chunkBiome.value().getHeightAdjustedTemperature(pos) * seasonOffset;

            // Only calculate humidity for rain biomes
            if (info.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
                accumulatedHumidity += getBiomeHumidity(world, biome, chunkBiome);
            }
        }

        dryTemp = accumulatedDryTemp / biomes.size();

        relativeHumidity = accumulatedHumidity / biomes.size();
        wetTemp = (float) TempHelper.getHeatIndex(dryTemp, relativeHumidity);
        blackGlobeTemp = (float) getBlackGlobeTemp(world, envRadiation, dryTemp, relativeHumidity, isSheltered, isUnderground);

        if (isSheltered || isUnderground) {
            //If not exposed to solar radiation, we use the simplified formula for temperature calculation.
            airTemperature = (wetTemp * 0.7F) + (blackGlobeTemp * 0.3F);
        }
        else {
            airTemperature = (wetTemp * 0.7F) + (blackGlobeTemp * 0.2F) + (dryTemp * 0.1F);
        }

        if (isSubmerged || isPartialSubmersion) {
            waterTemperature = getWaterTemperature(airTemperature, waterVolume);

            if (isSubmerged) {
                return waterTemperature;
            }

            return (waterTemperature * 0.7F) + (airTemperature * 0.3F);
        }

        return airTemperature;
    }

    /*
     * Calculate current radiation where player is standing.
     */
    private static double getBlackGlobeTemp(ServerLevel world, double envRadiation, float dryTemp, double relativeHumidity, boolean isSheltered, boolean isUnderground) {
        double radiation = envRadiation;

        radiation += getSunRadiation(world, isSheltered, isUnderground);

        return TempHelper.getBlackGlobe(radiation, dryTemp, relativeHumidity);
    }

    private static double getBiomeHumidity(ServerLevel world, Holder<Biome> curBiome, Holder<Biome> targetBiome) {
        LevelData info = world.getLevelData();
        ServerLevelAccessor serverLevel = (ServerLevelAccessor) world;
        ServerLevelData serverInfo = serverLevel.getServerLevelData();
        double biomeHumidity;
        double maxRH = getMaxBiomeHumidity(curBiome);
        double minRH = maxRH - 20;

        if (targetBiome.value().getPrecipitation() == Biome.Precipitation.RAIN) {
            int nextRain = serverInfo.getClearWeatherTime();

            if (info.isRaining()) {
                biomeHumidity = maxRH;
            } else if (nextRain > 0 && nextRain <= 12000) {
                biomeHumidity = minRH + (20 * (1 - (float) nextRain / 12000));
            } else {
                biomeHumidity = minRH;
            }
        }
        else {
            biomeHumidity = minRH;
        }

        return biomeHumidity;
    }

    private static double getSunRadiation(ServerLevel world, boolean isSheltered, boolean isUnderground) {
        double radiation = 0.0;

        if (!isSheltered && !isUnderground) {
            long time = (world.getDayTime() % 24000);
            long timeAdj;

            if (time >= 23000) {
                timeAdj = 24000 - time;
            }
            else if (time < 9001) {
                timeAdj = time + 1000;
            }
            else {
                timeAdj = 9000 - time;
            }

            radiation += timeAdj * 0.02;
        }

        return radiation;
    }

    private static double getMaxBiomeHumidity(Holder<Biome> biome) {
        Biome.BiomeCategory biomeCategory = Biome.getBiomeCategory(biome);

        return switch (biomeCategory) {
            case OCEAN, BEACH, MUSHROOM, RIVER -> 70.0;
            case EXTREME_HILLS, MOUNTAIN, FOREST, TAIGA -> 50.0;
            case JUNGLE, SWAMP -> 90.0;
            case PLAINS -> 60.0;
            default -> 40.0;
        };
    }

    private static float getWaterTemperature(float airTemperature, double waterVolume) {
        float baseWaterTemp = 0.663F;
        float waterTemp;
        float parity = 0.997F;

        if (airTemperature >= parity) {
            float increase = 0.1F + ((float) (1 - waterVolume) * 0.35F);
            waterTemp = baseWaterTemp + ((airTemperature - parity) * increase);
        }
        else {
            waterTemp = Math.max(baseWaterTemp - (((parity - airTemperature) * 0.5F)), 0.072F);
        }

        return waterTemp;
    }

    private static float getSeasonOffset(ServerLevel world, boolean seasonEffects) {
        float offset = 1.0F;
        DimensionType dimensionType = world.dimensionType();

        if (ModList.get().isLoaded("sereneseasons")) {
            if (seasonEffects && !DimensionType.OVERWORLD_LOCATION.equals(dimensionType)) {
                switch(SeasonHelper.getSeasonState(world).getSubSeason()) {
                    case MID_SPRING:
                        break;
                    case LATE_SPRING:
                        offset = 1.157F;
                        break;
                    case EARLY_SUMMER:
                        offset = 1.316F;
                        break;
                    case MID_SUMMER:
                        offset = 1.474F;
                        break;
                    case LATE_SUMMER:
                        offset = 1.579F;
                        break;
                    case EARLY_AUTUMN:
                        offset = 1.421F;
                        break;
                    case MID_AUTUMN:
                        offset = 1.21F;
                        break;
                    case LATE_AUTUMN:
                        break;
                    case EARLY_WINTER:
                        offset = 0.789F;
                        break;
                    case MID_WINTER:
                        offset = 0.579F;
                        break;
                    case LATE_WINTER:
                        offset = 0.684F;
                        break;
                    case EARLY_SPRING:
                        offset = 0.789F;
                        break;
                }
            }
        }

        return offset;
    }

}
