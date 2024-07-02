package homeostatic.common.item;

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

import homeostatic.common.Hydration;

public record DrinkableItem(ResourceLocation loc, int amount, float saturation, int potency, int duration, float chance) {

    public static Hydration getHydration(DrinkableItem item) {
        return new Hydration(item.amount(), item.saturation(), item.potency(), item.duration(), item.chance());
    }

    public static class Serializer implements JsonDeserializer<DrinkableItem>, JsonSerializer<DrinkableItem> {

        @Override
        public DrinkableItem deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "data");

            return new DrinkableItem(
                    ResourceLocation.parse(json.get("type").getAsString()),
                    json.get("amount").getAsInt(),
                    json.get("saturation").getAsFloat(),
                    json.get("effect_potency").getAsInt(),
                    json.get("effect_duration").getAsInt(),
                    json.get("effect_chance").getAsFloat()
            );
        }

        @Override
        public JsonElement serialize(DrinkableItem drinkableItem, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();

            json.addProperty("type", drinkableItem.loc().toString());
            json.addProperty("amount", drinkableItem.amount());
            json.addProperty("saturation", drinkableItem.saturation());
            json.addProperty("effect_potency", drinkableItem.potency());
            json.addProperty("effect_duration", drinkableItem.duration());
            json.addProperty("effect_chance", drinkableItem.chance());

            return json;
        }
    }

}
