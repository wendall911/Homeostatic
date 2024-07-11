package homeostatic.data.integration.sewingkit;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import dev.gigaherz.sewingkit.SewingKitMod;
import dev.gigaherz.sewingkit.api.SewingRecipeBuilder;
import dev.gigaherz.sewingkit.needle.NeedleItem;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.integration.ModIntegration;
import homeostatic.data.recipe.ModRecipesProvider;

import static homeostatic.Homeostatic.loc;

public class SewingKitRecipeProvider extends RecipeProvider {

    public SewingKitRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    public String getName() {
        return "Homeostatic - Sewing Kit Recipes";
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        Consumer<FinishedRecipe> wrapped = ModRecipesProvider.withCondition(consumer, new ModLoadedCondition(ModIntegration.SK_MODID));

        SewingRecipeBuilder.begin(HomeostaticItems.LEATHER_FLASK)
            .withTool(NeedleItem.SEW)
            .addMaterial(SewingKitMod.LEATHER_SHEET.get(), 4)
            .addMaterial(SewingKitMod.LEATHER_STRIP.get(), 2)
            .addMaterial(Items.STRING, 2)
            .addMaterial(ItemTags.PLANKS)
            .addCriterion("has_leather", has(Tags.Items.LEATHER))
            .build(wrapped, loc("leather_flask_via_sewing"));
    }

}
