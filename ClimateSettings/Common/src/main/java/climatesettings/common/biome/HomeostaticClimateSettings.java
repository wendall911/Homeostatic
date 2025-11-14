package climatesettings.common.biome;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public record HomeostaticClimateSettings(Holder<Biome> biome, boolean precipitation, float temperature, Biome.TemperatureModifier modifier, float downfall) {

    private static BiomeTypeData biomeTypeData;

    public HomeostaticClimateSettings {
        biomeTypeData = BiomeTypeDataManager.getDataForBiome(biome);
    }

    public String toString() {
        return "Biome: " + biome.toString() + "\nprecipitation_type=" + getPrecipitationType() + "\ntemperature="
            + temperature + "\ntemperatureModifier=" + modifier + "\ndownfall=" + downfall + "\ndayNightOffset="
            + biomeTypeData.getDayNightOffset(getPrecipitationType()) + "\nhumidity="
            + biomeTypeData.getHumidity(getPrecipitationType()) + "\nbiomeCategory="
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

    public static BiomeTypeData getBiomeData() {
        return biomeTypeData;
    }

}
