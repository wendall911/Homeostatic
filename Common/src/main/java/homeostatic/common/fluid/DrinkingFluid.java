package homeostatic.common.fluid;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import homeostatic.common.Hydration;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.mcLoc;

public record DrinkingFluid(ResourceLocation loc, int amount, float saturation, int potency, int duration, float chance) {

    public static Hydration getHydration(DrinkingFluid fluid) {
        return new Hydration(fluid.amount(), fluid.saturation(), fluid.potency(), fluid.duration(), fluid.chance());
    }

    public static class Serializer implements JsonDeserializer<DrinkingFluid>, JsonSerializer<DrinkingFluid> {

        @Override
        public DrinkingFluid deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "data");

            return new DrinkingFluid(
                mcLoc(json.get("fluid").getAsString()),
                json.get("amount").getAsInt(),
                json.get("saturation").getAsFloat(),
                json.get("effect_potency").getAsInt(),
                json.get("effect_duration").getAsInt(),
                json.get("effect_chance").getAsFloat()
            );
        }

        @Override
        public JsonElement serialize(DrinkingFluid drinkingFluid, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();

            json.addProperty("fluid", drinkingFluid.loc().toString());
            json.addProperty("amount", drinkingFluid.amount());
            json.addProperty("saturation", drinkingFluid.saturation());
            json.addProperty("effect_potency", drinkingFluid.potency());
            json.addProperty("effect_duration", drinkingFluid.duration());
            json.addProperty("effect_chance", drinkingFluid.chance());

            return json;
        }

    }

    public static CompoundTag serialize(DrinkingFluid drinkingFluid) {
        CompoundTag tag = new CompoundTag();

        tag.putString("loc", drinkingFluid.loc().toString());
        tag.putInt("amount", drinkingFluid.amount());
        tag.putFloat("saturation", drinkingFluid.saturation());
        tag.putInt("potency", drinkingFluid.potency());
        tag.putInt("duration", drinkingFluid.duration());
        tag.putFloat("chance", drinkingFluid.chance());

        return tag;
    }

    public static DrinkingFluid deserialize(CompoundTag tag) {
        return new DrinkingFluid(
            mcLoc(tag.getString("loc")),
            tag.getInt("amount"),
            tag.getFloat("saturation"),
            tag.getInt("potency"),
            tag.getInt("duration"),
            tag.getFloat("chance")
        );
    }

}
