package homeostatic.common.biome;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public record BiomeCategory(ResourceLocation loc, String type) {

    public static final BiomeCategory MISSING = new BiomeCategory(ResourceLocation.withDefaultNamespace("missing"), "MISSING");

    public static class Serializer implements JsonDeserializer<BiomeCategory>, JsonSerializer<BiomeCategory> {

        @Override
        public BiomeCategory deserialize(JsonElement jsonElement, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "data");

            return new BiomeCategory(ResourceLocation.parse(json.get("biome").getAsString()), json.get("category").getAsString());
        }

        @Override
        public JsonElement serialize(BiomeCategory biomeCategory, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();

            json.addProperty("biome", biomeCategory.loc().toString());
            json.addProperty("category", biomeCategory.type());

            return json;
        }

    }

    public enum Type {
        ICY,
        OCEAN,
        WARM_OCEAN,
        LUKEWARM_OCEAN,
        DEEP_LUKEWARM_OCEAN,
        COLD_OCEAN,
        FROZEN_OCEAN,
        DEEP_COLD_OCEAN,
        COLD_DESERT,
        COLD_FOREST,
        BOG,
        RIVER,
        TAIGA,
        EXTREME_HILLS,
        MOUNTAIN,
        BEACH,
        FOREST,
        SWAMP,
        UNDERGROUND,
        MUSHROOM,
        PLAINS,
        LUSH_DESERT,
        DRYLAND,
        RAINFOREST,
        JUNGLE,
        VOLCANIC,
        DEAD_SEA,
        SAVANNA,
        MESA,
        DESERT,
        NONE,
        THEEND,
        NETHER,
        MISSING
    }

}
