package climatesettings.common.biome;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.parse;

public class BiomeTypeData {

    public static final float FROZEN_OFFSET = -0.31F;
    public static final float SNOW_OFFSET = -0.446F;
    public static final float MC_DEGREE = 0.022289157F;

    public static final Codec<BiomeTypeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("location").forGetter(biomeTypeData -> biomeTypeData.location),
        Codec.FLOAT.fieldOf("temperature").forGetter(biomeTypeData -> biomeTypeData.temperature),
        Codec.DOUBLE.fieldOf("humidity").forGetter(biomeTypeData -> biomeTypeData.humidity),
        Codec.DOUBLE.fieldOf("season_variation").forGetter(biomeTypeData -> biomeTypeData.seasonVariation),
        Codec.DOUBLE.fieldOf("day_night_offset").forGetter(biomeTypeData -> biomeTypeData.dayNightOffset),
        Codec.BOOL.fieldOf("is_frozen").forGetter(biomeTypeData -> biomeTypeData.isFrozen)
    ).apply(instance, BiomeTypeData::new));

    private final ResourceLocation location;
    private final float temperature;
    private double humidity;
    private final double seasonVariation;
    private final double dayNightOffset;
    private final boolean isFrozen;

    public BiomeTypeData(ResourceLocation location, float temperature, double humidity, double seasonVariation, double dayNightOffset, boolean isFrozen) {
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
        this.seasonVariation = seasonVariation;
        this.dayNightOffset = dayNightOffset;
        this.isFrozen = isFrozen;
    }

    public static class Serializer implements JsonDeserializer<BiomeTypeData>, JsonSerializer<BiomeTypeData> {

        @Override
        public BiomeTypeData deserialize(JsonElement jsonElement, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "data");

            return new BiomeTypeData(
                parse(GsonHelper.getAsString(json, "location")),
                GsonHelper.getAsFloat(json, "temperature"),
                GsonHelper.getAsDouble(json, "humidity"),
                GsonHelper.getAsDouble(json, "season_variation"),
                GsonHelper.getAsDouble(json, "day_night_offset"),
                GsonHelper.getAsBoolean(json, "is_frozen")
            );
        }

        @Override
        public JsonElement serialize(BiomeTypeData biomeTypeData, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();

            json.addProperty("location", biomeTypeData.location.toString());
            json.addProperty("temperature", biomeTypeData.temperature);
            json.addProperty("humidity", biomeTypeData.humidity);
            json.addProperty("season_variation", biomeTypeData.seasonVariation);
            json.addProperty("day_night_offset", biomeTypeData.dayNightOffset);
            json.addProperty("is_frozen", biomeTypeData.isFrozen);

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

    public ResourceLocation getLocation() {
        return location;
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
