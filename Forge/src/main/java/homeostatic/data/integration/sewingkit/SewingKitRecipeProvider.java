package homeostatic.data.integration.sewingkit;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import dev.gigaherz.sewingkit.SewingKitMod;
import dev.gigaherz.sewingkit.api.SewingRecipeBuilder;
import dev.gigaherz.sewingkit.needle.NeedleItem;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.integration.ModIntegration;
import homeostatic.data.recipe.ForgeRecipeProvider;
import homeostatic.data.recipe.RecipeProviderBase;

import static homeostatic.Homeostatic.loc;

public class SewingKitRecipeProvider extends RecipeProviderBase {

    public SewingKitRecipeProvider(@NotNull final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Homeostatic - Sewing Kit Recipes";
    }

    @Override
    protected void registerRecipes(Consumer<FinishedRecipe> consumer) {
        Consumer<FinishedRecipe> wrapped = ForgeRecipeProvider.withCondition(consumer, new ModLoadedCondition(ModIntegration.SK_MODID));

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
