package homeostatic.data.recipe;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import homeostatic.common.recipe.ArmorEnhancement;
import homeostatic.common.recipe.HelmetThermometer;
import homeostatic.common.recipe.PurifiedLeatherFlask;
import homeostatic.common.recipe.RemoveArmorEnhancement;

public class SpecialRecipeProvider extends RecipeProviderBase {

    public SpecialRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {
        specialRecipe(consumer, ArmorEnhancement.ARMOR_ENHANCEMENT_SERIALIZER);
        specialRecipe(consumer, PurifiedLeatherFlask.PURIFIED_LEATHER_FLASK_SERIALIZER);
        specialRecipe(consumer, HelmetThermometer.HELMET_THERMOMETER_SERIALIZER);
        specialRecipe(consumer, RemoveArmorEnhancement.REMOVE_ARMOR_ENHANCEMENT_SERIALIZER);
    }

    @Override
    public String getName() {
        return "Homeostatic - Custom Recipes";
    }

}
