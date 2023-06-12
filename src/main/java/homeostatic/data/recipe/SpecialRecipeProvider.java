package homeostatic.data.recipe;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import homeostatic.common.recipe.HomeostaticRecipes;

public class SpecialRecipeProvider extends RecipeProviderBase {

    public SpecialRecipeProvider(@NotNull final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {
        specialRecipe(consumer, (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.ARMOR_ENHANCEMENT_SERIALIZER);
        specialRecipe(consumer, (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.PURIFIED_LEATHER_FLASK_SERIALIZER);
        specialRecipe(consumer, (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.HELMET_THERMOMETER_SERIALIZER);
        specialRecipe(consumer, (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.REMOVE_ARMOR_ENHANCEMENT_SERIALIZER);
    }

    @Override
    public String getName() {
        return "Homeostatic - Custom Recipes";
    }

}