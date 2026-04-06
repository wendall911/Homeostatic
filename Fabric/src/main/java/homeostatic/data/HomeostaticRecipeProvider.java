package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import homeostatic.Homeostatic;
import homeostatic.data.recipe.CommonRecipeProvider;

public class HomeostaticRecipeProvider extends FabricRecipeProvider {

    public HomeostaticRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryFuture) {
        super(output, registryFuture);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput recipeOutput) {
        return new CommonRecipeProvider(registryLookup, recipeOutput);
    }

    @Override
    public @NonNull String getName() {
        return Homeostatic.MOD_NAME + " - Fabric Recipies";
    }

}
