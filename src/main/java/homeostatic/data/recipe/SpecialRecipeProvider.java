package homeostatic.data.recipe;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;

import homeostatic.common.recipe.HomeostaticRecipes;

public class SpecialRecipeProvider extends RecipeProviderBase {

    public SpecialRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {
        specialRecipe(consumer, (SimpleRecipeSerializer<?>) HomeostaticRecipes.ARMOR_ENHANCEMENT_SERIALIZER);
        specialRecipe(consumer, (SimpleRecipeSerializer<?>) HomeostaticRecipes.PURIFIED_LEATHER_FLASK_SERIALIZER);
        specialRecipe(consumer, (SimpleRecipeSerializer<?>) HomeostaticRecipes.HELMET_THERMOMETER_SERIALIZER);
        specialRecipe(consumer, (SimpleRecipeSerializer<?>) HomeostaticRecipes.REMOVE_ARMOR_ENHANCEMENT_SERIALIZER);
    }

    @Override
    public String getName() {
        return "Homeostatic - Custom Recipes";
    }

}