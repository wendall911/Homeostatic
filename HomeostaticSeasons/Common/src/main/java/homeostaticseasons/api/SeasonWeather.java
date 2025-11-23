package homeostaticseasons.api;

import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

import homeostaticseasons.common.biome.BiomeTemperature;
import homeostaticseasons.config.ConfigHandler;

public class SeasonWeather {

    private static final ThreadLocal<Long2FloatLinkedOpenHashMap> temperatureCache = ThreadLocal.withInitial(() ->  Util.make(() -> {
        Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = new Long2FloatLinkedOpenHashMap(1024, 0.25F) {
            protected void rehash(int newSize) {}
        };
        long2floatlinkedopenhashmap.defaultReturnValue(Float.NaN);

        return long2floatlinkedopenhashmap;
    }));

    public static boolean warmEnoughToRain(Biome biome, BlockPos pos, LevelReader levelReader) {
        if (levelReader instanceof Level level && isValid(level)) {
            return warmEnoughToRain(biome, pos, level);
        }
        else {
            return biome.warmEnoughToRain(pos);
        }
    }

    public static boolean warmEnoughToRain(Biome biome, BlockPos pos, Level level) {
        BiomeTemperature biomeTemperature = getBiomeTemperature(biome, level, pos);

        return biomeTemperature.isWarmEnoughToRain();
    }

    public static boolean isRainingAt(Level level, BlockPos pos) {
        if (!level.isRaining()) {
            return false;
        }
        else if (level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY()) {
            return false;
        }
        else {
            Holder<Biome> biome = level.getBiome(pos);

            if (isValid(level)) {
                BiomeTemperature biomeTemperature = getBiomeTemperature(biome.value(), level, pos);

                return biomeTemperature.getPrecipitationType()
                    == Biome.Precipitation.RAIN && biomeTemperature.isWarmEnoughToRain();
            }
            else {
                return BiomeTemperature.getPrecipitationAt(biome.value(), pos) == Biome.Precipitation.RAIN;
            }
        }
    }

    public static Biome.Precipitation getPrecipitationType(Biome biome, BlockPos pos, ServerLevel level) {
        if (!isValid(level)) {
            return BiomeTemperature.getPrecipitationAt(biome, pos);
        }

        BiomeTemperature biomeTemperature = getBiomeTemperature(biome, level, pos);

        return biomeTemperature.getPrecipitationType();
    }

    public static Biome.Precipitation getPrecipitationType(BlockPos pos, Level level) {
        if (!isValid(level)) {
            return null;
        }

        Holder<Biome> biomeHolder = level.getBiome(pos);

        BiomeTemperature biomeTemperature = getBiomeTemperature(biomeHolder.value(), level, pos);

        return biomeTemperature.getPrecipitationType();
    }

    public static boolean isValid(Level level) {
        return ConfigHandler.Common.isValidDimension(level.dimension()) && ConfigHandler.Common.seasonalWeather();
    }

    public static BiomeTemperature getBiomeTemperature(Biome biome, Level level, BlockPos pos) {
        long i = pos.asLong();
        Long2FloatLinkedOpenHashMap long2floatlinkedopenhashmap = temperatureCache.get();
        float f = long2floatlinkedopenhashmap.get(i);

        if (!Float.isNaN(f)) {
            return new BiomeTemperature(biome, level, pos, f);
        }
        else {
            BiomeTemperature biomeTemperature = new BiomeTemperature(biome, level, pos, Float.NaN);
            float f1 = biomeTemperature.getAirTemperature();
            if (long2floatlinkedopenhashmap.size() == 1024) {
                long2floatlinkedopenhashmap.removeFirstFloat();
            }

            long2floatlinkedopenhashmap.put(i, f1);

            return biomeTemperature;
        }
    }

}
