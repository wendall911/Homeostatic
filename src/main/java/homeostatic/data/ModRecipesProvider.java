package homeostatic.data;

import java.util.function.Consumer;

import homeostatic.util.WaterHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;

import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import org.jetbrains.annotations.NotNull;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.Homeostatic;

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
        ItemLike leatherFlask = HomeostaticItems.LEATHER_FLASK;
        ItemLike waterFilter = HomeostaticItems.WATER_FILTER;
        ItemStack waterFilledLeatherFlask = WaterHelper.getFilledItem(new ItemStack(leatherFlask), Fluids.WATER, 5000);
        ItemStack cleanWaterFilledLeatherFlask = WaterHelper.getFilledItem(new ItemStack(leatherFlask), HomeostaticFluids.PURIFIED_WATER, 5000);

        ShapedRecipeBuilder.shaped(leatherFlask)
                .define('S', Items.STRING)
                .define('L', Items.LEATHER)
                .define('P', ItemTags.PLANKS)
                .pattern("SPS")
                .pattern("L L")
                .pattern("LLL")
                .unlockedBy("has_leather", has(Items.LEATHER))
                .save(consumer);

        ShapedRecipeBuilder.shaped(waterFilter)
                .define('P', Items.PAPER)
                .define('C', Items.CHARCOAL)
                .pattern("P")
                .pattern("C")
                .pattern("P")
                .unlockedBy("has_charcoal", has(Items.CHARCOAL))
                .save(consumer);

        AdvancedCookingRecipeBuilder.smelting(StrictNBTIngredient.of(waterFilledLeatherFlask), StrictNBTIngredient.of(cleanWaterFilledLeatherFlask), 0.15F, 200)
                .unlockedBy("has_leather_flask", has(leatherFlask))
                .save(consumer, Homeostatic.loc("furnace_purified_leather_flask"));

        AdvancedCookingRecipeBuilder.campfireCooking(StrictNBTIngredient.of(waterFilledLeatherFlask), StrictNBTIngredient.of(cleanWaterFilledLeatherFlask), 0.15F, 200)
                .unlockedBy("has_leather_flask", has(leatherFlask))
                .save(consumer, Homeostatic.loc("campfire_purified_leather_flask"));

        AdvancedCookingRecipeBuilder.smoking(StrictNBTIngredient.of(waterFilledLeatherFlask), StrictNBTIngredient.of(cleanWaterFilledLeatherFlask), 0.15F, 200)
                .unlockedBy("has_leather_flask", has(leatherFlask))
                .save(consumer, Homeostatic.loc("smoking_purified_leather_flask"));
    }

}
