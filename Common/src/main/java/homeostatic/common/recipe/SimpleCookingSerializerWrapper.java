package homeostatic.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

public class SimpleCookingSerializerWrapper<T extends AbstractCookingRecipe> extends SimpleCookingSerializer<T> {

    private final Factory<T> factory;
    private final int cookingTime;

    public SimpleCookingSerializerWrapper(Factory<T> factory, int cookingTime) {
        super(null, cookingTime);
        this.factory = factory;
        this.cookingTime = cookingTime;
    }

    @Override
    public T fromJson(ResourceLocation id, JsonObject jsonObject) {
        String group = GsonHelper.getAsString(jsonObject, "group", "");
        JsonElement element = GsonHelper.isArrayNode(jsonObject, "ingredient")
                ? GsonHelper.getAsJsonArray(jsonObject, "ingredient")
                : GsonHelper.getAsJsonObject(jsonObject, "ingredient");
        CookingBookCategory category = CookingBookCategory.CODEC.byName(GsonHelper.getAsString(jsonObject, "category", null), CookingBookCategory.MISC);
        Ingredient ingredient = Ingredient.fromJson(element);

        if (!jsonObject.has("result")) {
            throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        }

        ItemStack result = this.getResult(jsonObject);
        float experience = GsonHelper.getAsFloat(jsonObject, "experience", 0.0F);
        int cookingTime = GsonHelper.getAsInt(jsonObject, "cookingtime", this.cookingTime);

        return this.factory.create(id, group, category, ingredient, result, experience, cookingTime);
    }

    private ItemStack getResult(JsonObject jsonObject) {
        if (jsonObject.get("result").isJsonObject()) {
            return ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
        }

        String rawResult = GsonHelper.getAsString(jsonObject, "result");
        ResourceLocation resultId = new ResourceLocation(rawResult);

        return new ItemStack(BuiltInRegistries.ITEM.getOptional(resultId).orElseThrow(
            () -> new IllegalStateException("Item: " + rawResult + " does not exist")));
    }

    @Override
    public T fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        return this.factory.create(
            id,
            buf.readUtf(),
            buf.readEnum(CookingBookCategory.class),
            Ingredient.fromNetwork(buf),
            buf.readItem(),
            buf.readFloat(),
            buf.readVarInt()
        );
    }

    public interface Factory<T extends AbstractCookingRecipe> {
        T create(
                ResourceLocation id,
                String group,
                CookingBookCategory category,
                Ingredient ingredient,
                ItemStack result,
                float experience,
                int cookingTime
        );
    }
}
