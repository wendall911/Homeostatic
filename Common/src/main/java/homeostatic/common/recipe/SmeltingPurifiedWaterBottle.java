package homeostatic.common.recipe;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class SmeltingPurifiedWaterBottle extends SmeltingRecipe implements IWaterBottleCookingRecipe {

    public SmeltingPurifiedWaterBottle(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SingleRecipeInput recipeInput, HolderLookup.@NotNull Provider pRegistries) {
        return assemble(this.result);
    }

    @Override
    public boolean matches(@NotNull SingleRecipeInput recipeInput) {
        return matches(recipeInput);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return HomeostaticRecipes.SMELTING_PURIFIED_WATER_BOTTLE_SERIALIZER;
    }

}
