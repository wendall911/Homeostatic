package homeostatic.util;

import net.minecraft.world.level.biome.Biome;

public class BiomeHelper {

    public static Biome.Precipitation getPrecipitation(Biome.ClimateSettings climateSettings) {
        if (!climateSettings.hasPrecipitation()) {
            return Biome.Precipitation.NONE;
        }
        else if(climateSettings.temperature() < 0.15F) {
            return Biome.Precipitation.SNOW;
        }

        return Biome.Precipitation.RAIN;
    }

}
