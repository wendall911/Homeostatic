package homeostatic.data.recipe;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;

import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.data.AdvancedCookingRecipeBuilder;
import homeostatic.data.integration.ConsumerWrapperBuilder;
import homeostatic.data.integration.ModIntegration;
import homeostatic.util.WaterHelper;

import static homeostatic.Homeostatic.loc;

public class ModRecipesProvider extends RecipeProviderBase {

    public ModRecipesProvider(@NotNull final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Homeostatic - Recipes";
    }

    @Override
    protected void registerRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ItemLike leatherFlask = HomeostaticItems.LEATHER_FLASK;
        ItemLike waterFilter = HomeostaticItems.WATER_FILTER;
        ItemStack waterFilledLeatherFlask = WaterHelper.getFilledItem(new ItemStack(leatherFlask), Fluids.WATER, 5000);
        ItemStack cleanWaterFilledLeatherFlask = WaterHelper.getFilledItem(new ItemStack(leatherFlask), HomeostaticFluids.PURIFIED_WATER, 5000);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,leatherFlask)
                .define('S', Items.STRING)
                .define('L', Items.LEATHER)
                .define('P', ItemTags.PLANKS)
                .pattern("SPS")
                .pattern("L L")
                .pattern("LLL")
                .unlockedBy("has_leather", has(Items.LEATHER))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,waterFilter)
                .define('P', Items.PAPER)
                .define('C', Items.CHARCOAL)
                .pattern("P")
                .pattern("C")
                .pattern("P")
                .unlockedBy("has_charcoal", has(Items.CHARCOAL))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HomeostaticItems.THERMOMETER)
                .define('N', Items.IRON_NUGGET)
                .define('D', Items.REDSTONE)
                .define('I', Items.IRON_INGOT)
                .pattern("N")
                .pattern("D")
                .pattern("I")
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer);

        Consumer<FinishedRecipe> wrapped = withCondition(consumer, new ModLoadedCondition(ModIntegration.PATCHOULI_MODID));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, HomeostaticItems.BOOK)
                .requires(Items.DIRT)
                .group("books")
                .unlockedBy("has_dirt", has(Items.DIRT))
                .save(wrapped, loc("book_from_dirt"));

        AdvancedCookingRecipeBuilder.smelting(StrictNBTIngredient.of(waterFilledLeatherFlask), StrictNBTIngredient.of(cleanWaterFilledLeatherFlask), 0.15F, 200)
                .unlockedBy("has_leather_flask", has(leatherFlask))
                .save(consumer, loc("furnace_purified_leather_flask"));

        AdvancedCookingRecipeBuilder.campfireCooking(StrictNBTIngredient.of(waterFilledLeatherFlask), StrictNBTIngredient.of(cleanWaterFilledLeatherFlask), 0.15F, 200)
                .unlockedBy("has_leather_flask", has(leatherFlask))
                .save(consumer, loc("campfire_purified_leather_flask"));

        AdvancedCookingRecipeBuilder.smoking(StrictNBTIngredient.of(waterFilledLeatherFlask), StrictNBTIngredient.of(cleanWaterFilledLeatherFlask), 0.15F, 200)
                .unlockedBy("has_leather_flask", has(leatherFlask))
                .save(consumer, loc("smoking_purified_leather_flask"));
    }

    private static Consumer<FinishedRecipe> withCondition(Consumer<FinishedRecipe> consumer, ICondition... conditions) {
        ConsumerWrapperBuilder builder = ConsumerWrapperBuilder.wrap();

        for (ICondition condition : conditions) {
            builder.addCondition(condition);
        }

        return builder.build(consumer);
    }

}