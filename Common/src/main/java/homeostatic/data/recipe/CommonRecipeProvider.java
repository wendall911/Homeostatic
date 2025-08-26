package homeostatic.data.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.recipe.ArmorEnhancement;
import homeostatic.common.recipe.HelmetThermometer;
import homeostatic.common.recipe.HomeostaticRecipes;
import homeostatic.common.recipe.PurifiedLeatherFlask;
import homeostatic.common.recipe.RemoveArmorEnhancement;
import net.minecraft.world.item.crafting.Recipe;

import static homeostatic.Homeostatic.loc;

public class CommonRecipeProvider extends RecipeProvider {

    RecipeOutput recipeOutput;
    HolderLookup.Provider registries;

    public CommonRecipeProvider(HolderLookup.Provider registries, RecipeOutput recipeOutput) {
        super(registries, recipeOutput);

        this.recipeOutput = recipeOutput;
        this.registries = registries;
    }

    @Override
    public void buildRecipes() {
        HolderLookup.RegistryLookup<Item> itemRegistry = registries.lookupOrThrow(Registries.ITEM);

        RecipeProviderBase.specialRecipe(
            this.recipeOutput,
            (CustomRecipe.Serializer<?>) HomeostaticRecipes.ARMOR_ENHANCEMENT_SERIALIZER,
            ArmorEnhancement::new
        );
        RecipeProviderBase.specialRecipe(
            this.recipeOutput,
            (CustomRecipe.Serializer<?>) HomeostaticRecipes.PURIFIED_LEATHER_FLASK_SERIALIZER,
            PurifiedLeatherFlask::new
        );
        RecipeProviderBase.specialRecipe(
            this.recipeOutput,
            (CustomRecipe.Serializer<?>) HomeostaticRecipes.HELMET_THERMOMETER_SERIALIZER,
            HelmetThermometer::new
        );
        RecipeProviderBase.specialRecipe(
            this.recipeOutput,
            (CustomRecipe.Serializer<?>) HomeostaticRecipes.REMOVE_ARMOR_ENHANCEMENT_SERIALIZER,
            RemoveArmorEnhancement::new
        );
        RecipeProviderBase.cleanWaterFlaskSmelting(itemRegistry).save(recipeOutput, getKey("furnace_purified_leather_flask"));
        RecipeProviderBase.cleanWaterFlaskCampfire(itemRegistry).save(recipeOutput, getKey("campfire_purified_leather_flask"));
        RecipeProviderBase.cleanWaterFlaskSmoking(itemRegistry).save(recipeOutput, getKey("smoking_purified_leather_flask"));
        RecipeProviderBase.leatherFlask(itemRegistry).save(recipeOutput);
        RecipeProviderBase.waterFilter(itemRegistry).save(recipeOutput);
        RecipeProviderBase.thermometer(itemRegistry).save(recipeOutput);
        RecipeProviderBase.cleanWaterBottleSmelting(itemRegistry).save(recipeOutput, getKey("furnace_purified_water_bottle"));
        RecipeProviderBase.cleanWaterBottleCampfire(itemRegistry).save(recipeOutput, getKey("campfire_purified_water_bottle"));
        RecipeProviderBase.cleanWaterBottleSmoking(itemRegistry).save(recipeOutput, getKey("smoking_purified_water_bottle"));
    }

    private ResourceKey<Recipe<?>> getKey(String id) {
        return ResourceKey.create(Registries.RECIPE, loc(id));
    }

}