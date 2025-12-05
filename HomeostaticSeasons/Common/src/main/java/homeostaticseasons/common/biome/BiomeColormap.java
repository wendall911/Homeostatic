package homeostaticseasons.common.biome;

import java.lang.reflect.Type;
import java.util.Locale;

import org.jetbrains.annotations.NotNull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.biome.Biome;

import homeostaticseasons.util.ColorHelper;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.parse;

public record BiomeColormap(ResourceLocation type, int grassColor, float grassSaturation, int foliageColor,
                            float foliageSaturation, int birchColor) {

    public static final Codec<BiomeColormap> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("type").forGetter(BiomeColormap::type),
        Codec.INT.fieldOf("grass_color").forGetter(BiomeColormap::grassColor),
        Codec.FLOAT.fieldOf("grass_saturation").forGetter(BiomeColormap::grassSaturation),
        Codec.INT.fieldOf("foliage_color").forGetter(BiomeColormap::foliageColor),
        Codec.FLOAT.fieldOf("foliage_saturation").forGetter(BiomeColormap::foliageSaturation),
        Codec.INT.fieldOf("birch_color").forGetter(BiomeColormap::birchColor)
    ).apply(instance, BiomeColormap::new));

    public BiomeColormap(ResourceLocation type, int grassColor, int foliageColor, int birchColor) {
        this(type, grassColor, -1, foliageColor, -1, birchColor);
    }

    public int getGrassColor(int originalColor, Holder<Biome> biomeHolder) {
        return getColor(originalColor, this.grassColor, this.grassSaturation, biomeHolder);
    }

    public int getFoliageColor(int originalColor, Holder<Biome> biomeHolder) {
        return getColor(originalColor, this.foliageColor, this.foliageSaturation, biomeHolder);
    }

    public int getBirchColor(int originalColor, Holder<Biome> biomeHolder) {
        return getColor(originalColor, this.birchColor, -1, biomeHolder);
    }

    private int getColor(int originalColor, int newColor, float saturation, Holder<Biome> biomeHolder) {
        int color = newColor == 0xFFFFFF ? originalColor : ColorHelper.blend(originalColor, newColor);
        int mutedColor = color;
        boolean muted = false;

        // TODO Add biome condition tag for muting
        if (muted) {
            mutedColor = ColorHelper.mix(color, originalColor, 0.75F);
        }

        return saturation != -1 ? ColorHelper.saturate(mutedColor, saturation) : mutedColor;
    }

    public static class Serializer implements JsonDeserializer<BiomeColormap>, JsonSerializer<BiomeColormap> {

        @Override
        public BiomeColormap deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "data");

            return new BiomeColormap(
                parse(GsonHelper.getAsString(json, "type")),
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

            json.addProperty("type", biomeColormap.type.toString());
            json.addProperty("grass_color", biomeColormap.grassColor);
            json.addProperty("grass_saturation", biomeColormap.grassSaturation);
            json.addProperty("foliage_color", biomeColormap.foliageColor);
            json.addProperty("foliage_saturation", biomeColormap.foliageSaturation);
            json.addProperty("birch_color", biomeColormap.birchColor);

            return json;
        }

    }

    @Override
    public @NotNull String toString() {
        return "BiomeColormap{" +
            "grassColor=" + grassColor +
            ", grassSaturation=" + grassSaturation +
            ", foliageColor=" + foliageColor +
            ", foliageSaturation=" + foliageSaturation +
            ", birchColor=" + birchColor +
            '}';
    }

    public enum ColormapType {
        NORMAL,
        TEMPERATE;

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ROOT);
        }

    }

}
