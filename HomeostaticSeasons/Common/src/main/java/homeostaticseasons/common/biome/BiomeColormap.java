package homeostaticseasons.common.biome;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.util.GsonHelper;

public class BiomeColormap {

    private final int grassColor;
    private final float grassSaturation;
    private final int foliageColor;
    private final float foliageSaturation;
    private final int birchColor;

    public BiomeColormap(int grassColor, float grassSaturation, int foliageColor, float foliageSaturation, int birchColor) {
        this.grassColor = grassColor;
        this.grassSaturation = grassSaturation;
        this.foliageColor = foliageColor;
        this.foliageSaturation = foliageSaturation;
        this.birchColor = birchColor;
    }

    public static class Serializer implements JsonDeserializer<BiomeColormap>, JsonSerializer<BiomeColormap> {

        @Override
        public BiomeColormap deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "data");

            return new BiomeColormap(
                GsonHelper.getAsInt(json, "grass_color"),
                GsonHelper.getAsFloat(json, "grass_saturation"),
                GsonHelper.getAsInt(json, "foliage_color"),
                GsonHelper.getAsFloat(json, "foliage_saturation"),
                GsonHelper.getAsInt(json, "birch_color")
            );
        }

        @Override
        public JsonElement serialize(BiomeColormap biomeColormap, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();

            json.addProperty("grass_color", biomeColormap.grassColor);
            json.addProperty("grass_saturation", biomeColormap.grassSaturation);
            json.addProperty("foliage_color", biomeColormap.foliageColor);
            json.addProperty("foliage_saturation", biomeColormap.foliageSaturation);
            json.addProperty("birch_color", biomeColormap.birchColor);

            return json;
        }

    }

    @Override
    public String toString() {
        return "BiomeColormap{" +
                "grassColor=" + grassColor +
                ", grassSaturation=" + grassSaturation +
                ", foliageColor=" + foliageColor +
                ", foliageSaturation=" + foliageSaturation +
                ", birchColor=" + birchColor +
                '}';
    }

}
