package homeostatic.common.biome;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;

public class BiomeData {

    public static final float FROZEN_OFFSET = -0.31F;
    public static final float SNOW_OFFSET = -0.446F;
    public static final float MC_DEGREE = 0.022289157F;

    private final float temperature;
    private double humidity;
    private final double seasonVariation;
    private final double dayNightOffset;
    private final boolean isFrozen;

    public BiomeData(float temperature, double humidity, double seasonVariation, double dayNightOffset, boolean isFrozen) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.seasonVariation = seasonVariation;
        this.dayNightOffset = dayNightOffset;
        this.isFrozen = isFrozen;
    }

    public static class Serializer implements JsonDeserializer<BiomeData>, JsonSerializer<BiomeData> {

        @Override
        public BiomeData deserialize(JsonElement jsonElement, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "data");

            return new BiomeData(
                GsonHelper.getAsFloat(json, "temperature"),
                GsonHelper.getAsDouble(json, "humidity"),
                GsonHelper.getAsDouble(json, "season_variation"),
                GsonHelper.getAsDouble(json, "day_night_offset"),
                GsonHelper.getAsBoolean(json, "is_frozen")
            );
        }

        @Override
        public JsonElement serialize(BiomeData biomeData, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();

            json.addProperty("temperature", biomeData.temperature);
            json.addProperty("humidity", biomeData.humidity);
            json.addProperty("season_variation", biomeData.seasonVariation);
            json.addProperty("day_night_offset", biomeData.dayNightOffset);
            json.addProperty("is_frozen", biomeData.isFrozen);

            return json;
        }

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
        float seasonVariation = (float) this.seasonVariation * MC_DEGREE;

        if (precipitation == Biome.Precipitation.SNOW) {
            return seasonVariation / 2.0F;
        }

        return seasonVariation;
    }

    public float getDayNightOffset(Biome.Precipitation precipitation) {
        float dayNightOffset = (float) this.dayNightOffset * MC_DEGREE;

        if (precipitation == Biome.Precipitation.SNOW) {
            return dayNightOffset / 2.0F;
        }

        return dayNightOffset;
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
