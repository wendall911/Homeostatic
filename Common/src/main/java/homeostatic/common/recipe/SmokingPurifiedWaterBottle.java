package homeostatic.common.recipe;

import org.jspecify.annotations.NonNull;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmokingRecipe;

public class SmokingPurifiedWaterBottle extends SmokingRecipe implements IWaterBottleCookingRecipe {

    public static final MapCodec<SmokingPurifiedWaterBottle> MAP_CODEC = cookingMapCodec(SmokingPurifiedWaterBottle::new, 100);
    public static final StreamCodec<RegistryFriendlyByteBuf, SmokingPurifiedWaterBottle> STREAM_CODEC = cookingStreamCodec(SmokingPurifiedWaterBottle::new);
    public static final RecipeSerializer<SmokingPurifiedWaterBottle> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final ItemStackTemplate result;

    public SmokingPurifiedWaterBottle(Recipe.CommonInfo commonInfo, AbstractCookingRecipe.CookingBookInfo bookInfo, Ingredient ingredient, ItemStackTemplate result, float experience, int cookingTime) {
        this.result = result;

        super(commonInfo, bookInfo, ingredient, result, experience, cookingTime);
    }

    @Override
    public @NonNull ItemStack assemble(@NonNull SingleRecipeInput recipeInput) {
        return assemble(result.create());
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NonNull RecipeSerializer<SmokingRecipe> getSerializer() {
        return SmokingRecipe.SERIALIZER;
    }

}
