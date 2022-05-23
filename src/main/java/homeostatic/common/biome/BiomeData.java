package homeostatic.common.biome;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public class BiomeData {

    public static final float ICY = -0.006F;
    public final float MC_DEGREE = 0.022289157F;
    private final float temperature;
    private double humidity;
    private float seasonVariation;
    private float dayNightOffset;

    BiomeData (float temperature, double humidity, double seasonVariation, double dayNightOffset) {
        this.humidity = humidity;
        this.seasonVariation = (float) seasonVariation * MC_DEGREE;
        this.dayNightOffset = (float) dayNightOffset * MC_DEGREE;
        this.temperature = temperature;
    }

    public float getTemperature(Biome.TemperatureModifier temperatureModifier, Biome.Precipitation precipitation) {

        final float FROZEN_OFFSET = 0.446F;

        if (temperatureModifier == Biome.TemperatureModifier.FROZEN) {
            return ICY;
        }
        if (precipitation == Biome.Precipitation.SNOW) {
            return this.temperature - FROZEN_OFFSET;
        }

        return this.temperature;
    }

    public float getRawTemperature() {
        return this.temperature;
    }

    public double getHumidity(Holder<Biome> biome) {
        if (biome.value().getBaseTemperature() == ICY) {
            humidity = 20.0;
        }
        return this.humidity;
    }

    public double getRawHumidity(Biome biome) {
        return this.humidity;
    }

    public float getSeasonVariation(Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.SNOW) {
            return this.seasonVariation / 2.0F;
        }

        return this.seasonVariation;
    }

    public float getDayNightOffset(Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.SNOW) {
            return this.dayNightOffset / 2.0F;
        }

        return this.dayNightOffset;
    }

}
