package homeostatic.data.recipe;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;

import homeostatic.data.integration.ConsumerWrapperBuilder;
import homeostatic.data.integration.ModIntegration;

import static homeostatic.Homeostatic.loc;

public class ForgeRecipeProvider extends RecipeProviderBase {

    public ForgeRecipeProvider(@NotNull final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Homeostatic - Recipes";
    }

    @Override
    protected void registerRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        Consumer<FinishedRecipe> wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.PATCHOULI_MODID));

        book().save(wrapped, loc("book_from_dirt"));
    }

    private static Consumer<FinishedRecipe> withCondition(Consumer<FinishedRecipe> consumer, ICondition... conditions) {
        ConsumerWrapperBuilder builder = ConsumerWrapperBuilder.wrap();

        for (ICondition condition : conditions) {
            builder.addCondition(condition);
        }

        return builder.build(consumer);
    }

}