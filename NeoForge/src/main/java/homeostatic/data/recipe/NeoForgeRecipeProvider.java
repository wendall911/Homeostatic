package homeostatic.data.recipe;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import dev.gigaherz.sewingkit.SewingKitMod;
import dev.gigaherz.sewingkit.api.SewingRecipeBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.Tags;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.integration.ModIntegration;
import homeostatic.Homeostatic;

import static homeostatic.Homeostatic.prefix;

public class NeoForgeRecipeProvider extends RecipeProvider.Runner {

    public NeoForgeRecipeProvider(@NonNull final PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public @NonNull String getName() {
        return Homeostatic.MOD_NAME + " - NeoForge Recipes";
    }

    @Override
    protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider provider, @NonNull RecipeOutput recipeOutput) {
        return new VanillaRecipeProvider(provider, recipeOutput) {

            @Override
            public void buildRecipes() {
                RecipeOutput wrapped = recipeOutput.withConditions(new ModLoadedCondition(ModIntegration.SK_MODID));
                HolderLookup.RegistryLookup<Item> itemRegistry = provider.lookupOrThrow(Registries.ITEM);

                /*
                // TODO Update Sewing Kit when it is available on 1.21.11
                SewingRecipeBuilder.begin(itemRegistry, RecipeCategory.MISC, HomeostaticItems.LEATHER_FLASK)
                    .withTool(SewingKitMod.WOOD_OR_HIGHER)
                    .addMaterial(SewingKitMod.LEATHER_SHEET.get(), 4)
                    .addMaterial(SewingKitMod.LEATHER_STRIP.get(), 2)
                    .addMaterial(Items.STRING, 2)
                    .addMaterial(ItemTags.PLANKS)
                    .addCriterion("has_leather", has(Tags.Items.LEATHERS))
                    .save(wrapped, prefix("leather_flask_via_sewing"));
                 */
            }
        };
    }

}
