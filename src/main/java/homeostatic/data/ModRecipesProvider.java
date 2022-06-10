package homeostatic.data;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import org.jetbrains.annotations.NotNull;

public class ModRecipesProvider extends RecipeProvider {

    public ModRecipesProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    public String getName() {
        return "Homeostatic - Recipes";
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        
    }

}
