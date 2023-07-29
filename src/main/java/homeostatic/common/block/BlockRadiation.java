package homeostatic.common.block;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public record BlockRadiation(ResourceLocation loc, double maxRadiation) {

    public double getBlockRadiation(double distance, boolean obscured, int y) {
        double radiation;

        if (distance <= 1) {
            radiation = this.maxRadiation();
        } else {
            radiation = this.maxRadiation() / distance;
        }

        if (y > 0 && y < 5) {
            radiation = radiation * ((4 - y) * 0.25);
        }

        if (obscured) {
            radiation = radiation * 0.9;
        }

        return Math.min(radiation, maxRadiation());
    }

    public double getBlockRadiation(double distance, boolean obscured, double amount, int y) {
        double radiation = 0.0;

        if (amount <= 0) return radiation;

        if (distance <= 1) {
            radiation = this.maxRadiation();
        } else {
            radiation = this.maxRadiation() * amount / distance;
        }

        if (y > 0 && y < 5) {
            radiation = radiation * ((4 - y) * 0.25);
        }

        if (obscured) {
            radiation = radiation * 0.9;
        }

        return Math.min(radiation, this.maxRadiation());
    }

    public static class Serializer implements JsonDeserializer<BlockRadiation>, JsonSerializer<BlockRadiation> {

        @Override
        public BlockRadiation deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "data");

            return new BlockRadiation(new ResourceLocation(json.get("block").getAsString()), json.get("max_radiation").getAsDouble());
        }

        @Override
        public JsonElement serialize(BlockRadiation blockRadiation, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();

            json.addProperty("block", blockRadiation.loc().toString());
            json.addProperty("max_radiation", blockRadiation.maxRadiation());

            return json;
        }

    }

}
