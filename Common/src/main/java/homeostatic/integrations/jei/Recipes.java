package homeostatic.integrations.jei;

import java.util.List;

import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;

public final class Recipes {
    private final RecipeMap syncedRecipes;

    public Recipes(RecipeMap syncedRecipes) {
        this.syncedRecipes = syncedRecipes;
    }

    public List<RecipeHolder<CraftingRecipe>> getCraftingRecipes() {
        return getHandledRecipes(syncedRecipes, RecipeType.CRAFTING);
    }

    public List<RecipeHolder<SmeltingRecipe>> getSmeltingRecipes() {
        return getHandledRecipes(syncedRecipes, RecipeType.SMELTING);
    }

    public List<RecipeHolder<SmokingRecipe>> getSmokingRecipes() {
        return getHandledRecipes(syncedRecipes, RecipeType.SMOKING);
    }

    public List<RecipeHolder<CampfireCookingRecipe>> getCampfireCookingRecipes() {
        return getHandledRecipes(syncedRecipes, RecipeType.CAMPFIRE_COOKING);
    }

    public static <C extends RecipeInput, T extends Recipe<C>> List<RecipeHolder<T>> getHandledRecipes(
            RecipeMap syncedRecipes,
            RecipeType<T> recipeType
    ) {
        return syncedRecipes.byType(recipeType).stream().toList();
    }

}
