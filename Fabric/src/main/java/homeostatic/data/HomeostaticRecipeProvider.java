package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;

import homeostatic.Homeostatic;
import homeostatic.data.integration.ModIntegration;
import homeostatic.data.recipe.CommonRecipeProvider;
import homeostatic.data.recipe.RecipeProviderBase;

public class HomeostaticRecipeProvider extends FabricRecipeProvider {

    public HomeostaticRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryFuture) {
        super(output, registryFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput recipeOutput) {
        RecipeOutput patchouliWrapped = withConditions(recipeOutput, ResourceConditions.allModsLoaded(ModIntegration.PATCHOULI_MODID));
        HolderLookup.RegistryLookup<Item> itemRegistry = registryLookup.lookupOrThrow(Registries.ITEM);

        RecipeProviderBase.book(itemRegistry).save(patchouliWrapped, "book_from_dirt");

        return new CommonRecipeProvider(registryLookup, recipeOutput);
    }

    @Override
    public String getName() {
        return Homeostatic.MOD_NAME + " - Fabric Recipies";
    }

}
