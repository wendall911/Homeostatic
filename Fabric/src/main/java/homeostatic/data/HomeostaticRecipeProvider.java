package homeostatic.data;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.data.recipes.FinishedRecipe;

import homeostatic.Homeostatic;
import homeostatic.data.integration.ModIntegration;
import homeostatic.data.recipe.RecipeProviderBase;

import static homeostatic.Homeostatic.loc;

public class HomeostaticRecipeProvider extends FabricRecipeProvider {

    public HomeostaticRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return Homeostatic.MOD_NAME + " - Fabric Recipies";
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        Consumer<FinishedRecipe> patchouliWrapped = withConditions(consumer, DefaultResourceConditions.allModsLoaded(ModIntegration.PATCHOULI_MODID));

        RecipeProviderBase.book().save(patchouliWrapped, loc("book_from_dirt"));
    }

}
