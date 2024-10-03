package homeostatic.data.recipe;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import homeostatic.common.recipe.ArmorEnhancement;
import homeostatic.common.recipe.HelmetThermometer;
import homeostatic.common.recipe.HomeostaticRecipes;
import homeostatic.common.recipe.PurifiedLeatherFlask;
import homeostatic.common.recipe.RemoveArmorEnhancement;

import static homeostatic.Homeostatic.loc;

public class CommonRecipeProvider extends RecipeProvider {

    public CommonRecipeProvider(@NotNull final PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryFuture) {
        super(packOutput, registryFuture);
    }

    @Override
    public void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        RecipeProviderBase.specialRecipe(
            recipeOutput,
            (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.ARMOR_ENHANCEMENT_SERIALIZER,
            ArmorEnhancement::new
        );
        RecipeProviderBase.specialRecipe(
            recipeOutput,
            (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.PURIFIED_LEATHER_FLASK_SERIALIZER,
            PurifiedLeatherFlask::new
        );
        RecipeProviderBase.specialRecipe(
            recipeOutput,
            (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.HELMET_THERMOMETER_SERIALIZER,
            HelmetThermometer::new
        );
        RecipeProviderBase.specialRecipe(
            recipeOutput,
            (SimpleCraftingRecipeSerializer<?>) HomeostaticRecipes.REMOVE_ARMOR_ENHANCEMENT_SERIALIZER,
            RemoveArmorEnhancement::new
        );
        RecipeProviderBase.cleanWaterFlaskSmelting().save(recipeOutput, loc("furnace_purified_leather_flask"));
        RecipeProviderBase.cleanWaterFlaskCampfire().save(recipeOutput, loc("campfire_purified_leather_flask"));
        RecipeProviderBase.cleanWaterFlaskSmoking().save(recipeOutput, loc("smoking_purified_leather_flask"));
        RecipeProviderBase.leatherFlask().save(recipeOutput);
        RecipeProviderBase.waterFilter().save(recipeOutput);
        RecipeProviderBase.thermometer().save(recipeOutput);
        RecipeProviderBase.cleanWaterBottleSmelting().save(recipeOutput, loc("furnace_purified_water_bottle"));
        RecipeProviderBase.cleanWaterBottleCampfire().save(recipeOutput, loc("campfire_purified_water_bottle"));
        RecipeProviderBase.cleanWaterBottleSmoking().save(recipeOutput, loc("smoking_purified_water_bottle"));
    }

}