package homeostatic.data.recipe;

import org.jetbrains.annotations.NotNull;

import dev.gigaherz.sewingkit.SewingKitMod;
import dev.gigaherz.sewingkit.api.SewingRecipeBuilder;
import dev.gigaherz.sewingkit.needle.NeedleItem;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.Tags;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.integration.ModIntegration;

import static homeostatic.Homeostatic.loc;

public class NeoForgeRecipeProvider extends RecipeProvider {

    public NeoForgeRecipeProvider(@NotNull final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        RecipeOutput wrapped = recipeOutput.withConditions(new ModLoadedCondition(ModIntegration.PATCHOULI_MODID));

        RecipeProviderBase.book().save(wrapped, loc("book_from_dirt"));

        wrapped = recipeOutput.withConditions(new ModLoadedCondition(ModIntegration.SK_MODID));

        SewingRecipeBuilder.begin(RecipeCategory.MISC, HomeostaticItems.LEATHER_FLASK)
            .withTool(NeedleItem.SEW)
            .addMaterial(SewingKitMod.LEATHER_SHEET.get(), 4)
            .addMaterial(SewingKitMod.LEATHER_STRIP.get(), 2)
            .addMaterial(Items.STRING, 2)
            .addMaterial(ItemTags.PLANKS)
            .addCriterion("has_leather", has(Tags.Items.LEATHER))
            .save(wrapped, loc("leather_flask_via_sewing"));
    }

}
