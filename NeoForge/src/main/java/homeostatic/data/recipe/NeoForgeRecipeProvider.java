package homeostatic.data.recipe;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import homeostatic.data.integration.ModIntegration;

import static homeostatic.Homeostatic.loc;

public class NeoForgeRecipeProvider extends RecipeProvider {

    public NeoForgeRecipeProvider(@NotNull final PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        RecipeOutput wrapped = recipeOutput.withConditions(new ModLoadedCondition(ModIntegration.PATCHOULI_MODID));

        RecipeProviderBase.book().save(wrapped, loc("book_from_dirt"));
    }

}
