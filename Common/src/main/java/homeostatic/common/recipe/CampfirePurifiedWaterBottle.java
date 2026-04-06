package homeostatic.common.recipe;

import org.jspecify.annotations.NonNull;

import com.mojang.serialization.MapCodec;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class CampfirePurifiedWaterBottle extends CampfireCookingRecipe implements IWaterBottleCookingRecipe {

    public static final MapCodec<CampfirePurifiedWaterBottle> MAP_CODEC = cookingMapCodec(CampfirePurifiedWaterBottle::new, 100);
    public static final StreamCodec<RegistryFriendlyByteBuf, CampfirePurifiedWaterBottle> STREAM_CODEC = cookingStreamCodec(CampfirePurifiedWaterBottle::new);
    public static final RecipeSerializer<CampfirePurifiedWaterBottle> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final ItemStackTemplate result;

    public CampfirePurifiedWaterBottle(Recipe.CommonInfo commonInfo, AbstractCookingRecipe.CookingBookInfo bookInfo, Ingredient ingredient, ItemStackTemplate result, float experience, int cookingTime) {
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
    public @NonNull RecipeSerializer<CampfireCookingRecipe> getSerializer() {
        return CampfireCookingRecipe.SERIALIZER;
    }

}
