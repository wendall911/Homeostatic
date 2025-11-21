package homeostaticseasons.api;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

import homeostaticseasons.common.biome.BiomeTemperature;
import homeostaticseasons.config.ConfigHandler;

public class SeasonWeather {

    public static boolean warmEnoughToRain(Biome biome, BlockPos pos, LevelReader level) {
        if (level instanceof ServerLevel serverLevel && isValid(serverLevel)) {
            return warmEnoughToRain(biome, pos, serverLevel);
        }
        else {
            return biome.warmEnoughToRain(pos);
        }
    }

    public static boolean warmEnoughToRain(Biome biome, BlockPos pos, ServerLevel level) {
        BiomeTemperature biomeTemperature = new BiomeTemperature(biome, level, pos);

        return biomeTemperature.isWarmEnoughToRain();
    }

    public static boolean coldEnoughToSnow(Biome biome, BlockPos pos, ServerLevel level) {
        return !warmEnoughToRain(biome, pos, level);
    }

    public static boolean isRainingAt(ServerLevel level, BlockPos pos) {
        if (!level.isRaining()) {
            return false;
        }
        else if (level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, pos).getY() > pos.getY()) {
            return false;
        }
        else {
            Holder<Biome> biome = level.getBiome(pos);

            if (isValid(level)) {
                BiomeTemperature biomeTemperature = new BiomeTemperature(biome, level, pos);

                return biomeTemperature.getPrecipitationType()
                    == Biome.Precipitation.RAIN && biomeTemperature.isWarmEnoughToRain();
            }
            else {
                return biome.value().getPrecipitationAt(pos) == Biome.Precipitation.RAIN;
            }
        }
    }

    public static Biome.Precipitation getPrecipitationType(Biome biome, BlockPos pos, ServerLevel level) {
        if (!isValid(level)) {
            return biome.getPrecipitationAt(pos);
        }

        BiomeTemperature biomeTemperature = new BiomeTemperature(biome, level, pos);
        Biome.Precipitation biomePrecipitationOverride = biomeTemperature.getPrecipitationType();

        if (biomePrecipitationOverride == Biome.Precipitation.NONE) {
            return biomePrecipitationOverride;
        }
        else {
            boolean coldEnoughToSnow = coldEnoughToSnow(biome, pos, level);

            return coldEnoughToSnow ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN;
        }
    }

    public static boolean isValid(ServerLevel level) {
        return ConfigHandler.Common.isValidDimension(level.dimension()) && ConfigHandler.Common.seasonalWeather();
    }

}
