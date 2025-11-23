package homeostaticseasons.common.season;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.GsonHelper;

public record WeatherData(int minRainTime, int maxRainTime, int minThunderTime, int maxThunderTime) {

    public static class Serializer implements JsonDeserializer<WeatherData>, JsonSerializer<WeatherData> {

        @Override
        public WeatherData deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "data");

            return new WeatherData(
                GsonHelper.getAsInt(json, "min_rain_time"),
                GsonHelper.getAsInt(json, "max_rain_time"),
                GsonHelper.getAsInt(json, "min_thunder_time"),
                GsonHelper.getAsInt(json, "max_thunder_time")
            );
        }

        @Override
        public JsonElement serialize(WeatherData weatherData, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();

            json.addProperty("min_rain_time", weatherData.minRainTime);
            json.addProperty("max_rain_time", weatherData.maxRainTime);
            json.addProperty("min_thunder_time", weatherData.minThunderTime);
            json.addProperty("max_thunder_time", weatherData.maxThunderTime);

            return json;
        }

    }

    public boolean canRain() {
        return this.minRainTime != -1 && this.maxRainTime != -1;
    }

    public boolean canThunder() {
        return this.minThunderTime != -1 && this.maxThunderTime != -1;
    }

}
