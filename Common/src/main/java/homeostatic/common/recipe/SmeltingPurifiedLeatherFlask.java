package homeostatic.common.recipe;

import org.jspecify.annotations.NonNull;

import com.mojang.serialization.MapCodec;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;

public class SmeltingPurifiedLeatherFlask extends SmeltingRecipe implements IWaterContainerCookingRecipe {

    public static final MapCodec<SmeltingRecipe> MAP_CODEC = cookingMapCodec(SmeltingPurifiedLeatherFlask::new, 100);
    public static final StreamCodec<RegistryFriendlyByteBuf, SmeltingRecipe> STREAM_CODEC = cookingStreamCodec(SmeltingPurifiedLeatherFlask::new);
    public static final RecipeSerializer<SmeltingRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final ItemStackTemplate result;

    public SmeltingPurifiedLeatherFlask(Recipe.CommonInfo commonInfo, AbstractCookingRecipe.CookingBookInfo bookInfo, Ingredient ingredient, ItemStackTemplate result, float experience, int cookingTime) {
        this.result = result;

        super(commonInfo, bookInfo, ingredient, result, experience, cookingTime);
    }

    @Override
    protected @NonNull ItemStackTemplate result() {
        return getCleanWaterFilledWaterContainer(result.item().value());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput recipeInput, ItemStack result) {
        return IWaterContainerCookingRecipe.super.assemble(recipeInput, getCleanWaterFilledLWaterContainer(result));
    }

    @Override
    public boolean matches(@NonNull SingleRecipeInput recipeInput, @NonNull Level level) {
        return matches(recipeInput, 1L);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NonNull RecipeSerializer<SmeltingRecipe> getSerializer() {
        return SERIALIZER;
    }

}
