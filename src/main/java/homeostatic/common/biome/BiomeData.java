package homeostatic.common.biome;

import net.minecraft.world.level.biome.Biome;

public class BiomeData {

    public static final float FROZEN_OFFSET = -0.31F;
    public static final float SNOW_OFFSET = -0.446F;
    public static final float MC_DEGREE = 0.022289157F;

    private final float temperature;
    private double humidity;
    private final float seasonVariation;
    private final float dayNightOffset;
    private final boolean isFrozen;

    BiomeData (float temperature, double humidity, double seasonVariation, double dayNightOffset, boolean isFrozen) {
        this.humidity = humidity;
        this.seasonVariation = (float) seasonVariation * MC_DEGREE;
        this.dayNightOffset = (float) dayNightOffset * MC_DEGREE;
        this.temperature = temperature;
        this.isFrozen = isFrozen;
    }

    public float getTemperature(Biome.Precipitation precipitation) {
        float temperature = this.temperature;

        if (this.isFrozen) {
            temperature += FROZEN_OFFSET;
        }

        if (precipitation == Biome.Precipitation.SNOW) {
            temperature += SNOW_OFFSET;
        }

        return temperature;
    }

    public double getHumidity(Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.SNOW) {
            this.humidity = 20.0;
        }
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

    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public String toString() {
        return "BiomeData{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", seasonVariation=" + seasonVariation +
                ", dayNightOffset=" + dayNightOffset +
                '}';
    }

}
