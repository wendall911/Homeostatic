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
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.ServerLevelData;

import net.minecraftforge.fml.ModList;

import sereneseasons.api.season.SeasonHelper;
import sereneseasons.config.BiomeConfig;

import homeostatic.common.block.BlockRegistry;
import homeostatic.mixin.ServerLevelAccessor;
import homeostatic.util.TempHelper;

public final class Temperature {

    /*
     * Returns WBGT
     */
    public static float getLocal(ServerPlayer sp, BlockPos pos, Holder<Biome> biome, ServerLevel world) {
        LevelData info = world.getLevelData();
        ServerLevelAccessor serverLevel = (ServerLevelAccessor) world;
        ServerLevelData serverInfo = serverLevel.getServerLevelData();
        ArrayList<Holder<Biome>> biomes = new ArrayList<>();
        int chunks = 3;
        float accumulatedDryTemp = 0.0F;
        float accumulatedHumidity = 0.0F;
        double relativeHumidity;
        float dryTemp;
        float wetTemp;
        float blackGlobeTemp;
        boolean isUnderground = isUnderground(pos, world);
        boolean isSheltered = isSheltered(pos, world);

        // If sheltered, consider local biome UNDERGROUND
        if (isSheltered || isUnderground) {
            biomes.add(world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getHolderOrThrow(Biomes.LUSH_CAVES));
        }
        else {
            biomes.add(biome);
        }

        // Only do biome smoothing if not underground
        if (!isUnderground) {
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
            float seasonOffset = getSeasonOffset(chunkBiome, world);

            accumulatedDryTemp += chunkBiome.value().getHeightAdjustedTemperature(pos) * seasonOffset;

            // Only calculate humidity for rain biomes
            if (info.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE)) {
                double biomeHumidity;
                double maxRH = getMaxBiomeHumidity(biome);
                double minRH = maxRH - 20;

                if (chunkBiome.value().getPrecipitation() == Biome.Precipitation.RAIN) {
                    int nextRain = serverInfo.getClearWeatherTime();

                    if (info.isRaining()) {
                        biomeHumidity = maxRH;
                    } else if (nextRain > 0 && nextRain < 12001) {
                        biomeHumidity = minRH + (20 * (1 - (float) nextRain / 12000));
                    } else {
                        biomeHumidity = minRH;
                    }
                }
                else {
                    biomeHumidity = minRH;
                }

                accumulatedHumidity += biomeHumidity;
            }
        }

        dryTemp = accumulatedDryTemp / biomes.size();

        relativeHumidity = accumulatedHumidity / biomes.size();
        wetTemp = (float) TempHelper.getHeatIndex(dryTemp, relativeHumidity);
        blackGlobeTemp = (float) getBlackGlobeTemp(world, sp, dryTemp, relativeHumidity, isSheltered, isUnderground);

        /*
         * If not exposed to solar radiation, we use the simplified formula for temperature calculation.
         * See: https://en.wikipedia.org/wiki/Wet-bulb_globe_temperature
         */
        if (isSheltered || isUnderground) {
            return (wetTemp * 0.7F) + (blackGlobeTemp * 0.3F);
        }
        else {
            return (wetTemp * 0.7F) + (blackGlobeTemp * 0.2F) + (dryTemp * 0.1F);
        }
    }

    /*
     * Calculate current radiation where player is standing.
     */
    private static double getBlackGlobeTemp(ServerLevel world, ServerPlayer sp, float dryTemp, double relativeHumidity, boolean isSheltered, boolean isUnderground) {
        double radiation = 0.0;

        radiation += getSunRadiation(world, isSheltered, isUnderground);
        radiation += BlockRegistry.getBlockRadiation(world, sp);

        return TempHelper.getBlackGlobe(radiation, dryTemp, relativeHumidity);
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
            case PLAINS, UNDERGROUND -> 60.0;
            default -> 40.0;
        };
    }

    /*
     * Simple check to see if player is sheltered.
     */
    private static boolean isSheltered(BlockPos pos, ServerLevel world) {
        boolean isSheltered = !world.canSeeSky(pos.above());
        DimensionType dimensionType = world.dimensionType();

        if (DimensionType.OVERWORLD_LOCATION.equals(dimensionType)) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    if (isSheltered) {
                        isSheltered = !world.canSeeSky(pos.offset(x, 0, z).above());
                    }
                }
            }
        }
        else {
            isSheltered = false;
        }

        return isSheltered;
    }

    /*
     * Simple check to see if player is Underground.
     */
    private static boolean isUnderground(BlockPos pos, ServerLevel world) {
        boolean isUnderground = !world.canSeeSky(pos.above());
        DimensionType dimensionType = world.dimensionType();

        if (DimensionType.OVERWORLD_LOCATION.equals(dimensionType)) {
            for (int x = -12; x <= 12; x++) {
                for (int y = 0; y <= 11; y++) {
                    for (int z = -12; z <= 12; z++) {
                        if (isUnderground) {
                            isUnderground = !world.canSeeSky(pos.offset(x, y, z).above());
                        }
                    }
                }
            }
        }
        else {
            isUnderground = false;
        }

        return isUnderground;
    }

    private static float getSeasonOffset(Holder<Biome> biome, ServerLevel world) {
        float offset = 1.0F;
        DimensionType dimensionType = world.dimensionType();

        if (ModList.get().isLoaded("sereneseasons")) {
            if (BiomeConfig.enablesSeasonalEffects(biome) && !DimensionType.OVERWORLD_LOCATION.equals(dimensionType)) {
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
