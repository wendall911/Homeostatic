package homeostatic.common.recipe;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.Level;

public class SmokingPurifiedLeatherFlask extends SmokingRecipe implements IWaterContainerCookingRecipe {

    public SmokingPurifiedLeatherFlask(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput recipeInput, HolderLookup.@NotNull Provider pRegistries) {
        return assemble(recipeInput, this.result);
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registryProvider) {
        return getCleanWaterFilledLWaterContainer(this.result);
    }

    @Override
    public boolean matches(@NotNull SingleRecipeInput recipeInput, @NotNull Level level) {
        return matches(recipeInput, 1L);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return HomeostaticRecipes.SMOKING_PURIFIED_LEATHER_FLASK_SERIALIZER;
    }

}
