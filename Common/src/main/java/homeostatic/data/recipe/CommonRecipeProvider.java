package homeostatic.data.recipe;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import homeostatic.common.recipe.HomeostaticRecipes;

import static homeostatic.Homeostatic.loc;

public class CommonRecipeProvider extends RecipeProviderBase {

    public CommonRecipeProvider(@NotNull final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {
        specialRecipe(consumer, (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.ARMOR_ENHANCEMENT_SERIALIZER);
        specialRecipe(consumer, (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.PURIFIED_LEATHER_FLASK_SERIALIZER);
        specialRecipe(consumer, (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.HELMET_THERMOMETER_SERIALIZER);
        specialRecipe(consumer, (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.REMOVE_ARMOR_ENHANCEMENT_SERIALIZER);
        cleanWaterFlaskSmelting().save(consumer, loc("furnace_purified_leather_flask"));
        cleanWaterFlaskCampfire().save(consumer, loc("campfire_purified_leather_flask"));
        cleanWaterFlaskSmoking().save(consumer, loc("smoking_purified_leather_flask"));
        leatherFlask().save(consumer);
        waterFilter().save(consumer);
        thermometer().save(consumer);
    }

    @Override
    public String getName() {
        return "Homeostatic - Custom Recipes";
    }

}