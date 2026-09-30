package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;

import homeostatic.Homeostatic;
import homeostatic.data.recipe.CommonRecipeProvider;

public class HomeostaticRecipeProvider extends FabricRecipeProvider {

    public HomeostaticRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryFuture) {
        super(output, registryFuture);
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(@NonNull Provider registries,
            @NonNull BootstrapContext<Recipe<?>> recipes, @NonNull BootstrapContext<Advancement> advancements) {
        return new CommonRecipeProvider(registries, recipes, advancements);
    }

    @Override
    public @NonNull String getName() {
        return Homeostatic.MOD_NAME + " - Homeostatic Recipes";
    }

}
