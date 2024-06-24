package homeostatic.common.biome;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public record ClimateSettings(Holder<Biome> biome, boolean precipitation, float temperature, Biome.TemperatureModifier modifier, float downfall) {

    private static BiomeData biomeData;

    public ClimateSettings {
        biomeData = BiomeRegistry.getDataForBiome(biome);
    }

    public String toString() {
        return "Biome: " + biome.toString() + "\nprecipitation_type=" + getPrecipitationType() + "\ntemperature="
            + temperature + "\ntemperatureModifier=" + modifier + "\ndownfall=" + downfall + "\ndayNightOffset="
            + biomeData.getDayNightOffset(getPrecipitationType()) + "\nhumidity="
            + biomeData.getHumidity(getPrecipitationType()) + "\nbiomeCategory="
            + BiomeCategoryManager.getBiomeCategory(biome);
    }

    public Biome.Precipitation getPrecipitationType() {
        if (!precipitation) {
            return Biome.Precipitation.NONE;
        }
        else {
            return temperature <= 0.15F ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN;
        }
    }

    public static BiomeData getBiomeData() {
        return biomeData;
    }

}
