package homeostatic.data.recipe;

import dev.gigaherz.sewingkit.SewingKitMod;
import dev.gigaherz.sewingkit.api.SewingRecipeBuilder;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;

import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.Tags;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.integration.ModIntegration;

import static homeostatic.Homeostatic.prefix;

public class NeoForgeRecipeProvider extends RecipeProvider {

    public NeoForgeRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
    }

    @Override
    public void buildRecipes() {
        RecipeOutput wrapped = this.output.withConditions(new ModLoadedCondition(ModIntegration.SK_MODID));

        /* Waiting on latest version
        SewingRecipeBuilder.begin(items, RecipeCategory.MISC, HomeostaticItems.LEATHER_FLASK)
            .withTool(SewingKitMod.WOOD_OR_HIGHER_NEEDLE)
            .addMaterial(SewingKitMod.LEATHER_SHEET.get(), 4)
            .addMaterial(SewingKitMod.LEATHER_STRIP.get(), 2)
            .addMaterial(Items.STRING, 2)
            .addMaterial(ItemTags.PLANKS)
            .addCriterion("has_leather", has(Tags.Items.LEATHERS))
            .save(wrapped, prefix("leather_flask_via_sewing"));
         */
    }

}
