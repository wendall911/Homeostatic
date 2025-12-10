package homeostatic.common.fluid;

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

import net.minecraft.resources.Identifier;
import net.minecraft.util.GsonHelper;

import homeostatic.common.Hydration;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.parse;

public record DrinkingFluid(Identifier loc, int amount, float saturation, int potency, int duration, float chance) {

    public static final Codec<DrinkingFluid> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Identifier.CODEC.fieldOf("fluid").forGetter(DrinkingFluid::loc),
        Codec.INT.fieldOf("amount").forGetter(DrinkingFluid::amount),
        Codec.FLOAT.fieldOf("saturation").forGetter(DrinkingFluid::saturation),
        Codec.INT.fieldOf("effect_potency").forGetter(DrinkingFluid::potency),
        Codec.INT.fieldOf("effect_duration").forGetter(DrinkingFluid::duration),
        Codec.FLOAT.fieldOf("effect_chance").forGetter(DrinkingFluid::chance)
    ).apply(instance, DrinkingFluid::new));

    public static Hydration getHydration(DrinkingFluid fluid) {
        return new Hydration(fluid.amount(), fluid.saturation(), fluid.potency(), fluid.duration(), fluid.chance());
    }

    public static class Serializer implements JsonDeserializer<DrinkingFluid>, JsonSerializer<DrinkingFluid> {

        @Override
        public DrinkingFluid deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "data");

            return new DrinkingFluid(
                parse(json.get("fluid").getAsString()),
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

}
