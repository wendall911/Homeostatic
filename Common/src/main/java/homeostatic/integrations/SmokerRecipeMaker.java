package homeostatic.integrations;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.material.Fluids;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.recipe.RecipeProviderBase;
import homeostatic.platform.Services;

import static homeostatic.Homeostatic.loc;

public class SmokerRecipeMaker {

    public static final String GROUP = ".smoking.purified_water";

    public static List<SmokingRecipe> createFlaskRecipes(String plugin) {
        String group = plugin + GROUP;
        List<SmokingRecipe> recipes = new ArrayList<>();
        ItemStack filledWaterLeatherFlask = new ItemStack(HomeostaticItems.LEATHER_FLASK);
        ItemStack filledPurifiedWaterLeatherFlask = new ItemStack(HomeostaticItems.LEATHER_FLASK);

        Services.PLATFORM.fillFluid(filledWaterLeatherFlask, Fluids.WATER, Services.PLATFORM.getFluidCapacity(filledWaterLeatherFlask));
        Services.PLATFORM.fillFluid(filledPurifiedWaterLeatherFlask, HomeostaticFluids.PURIFIED_WATER, Services.PLATFORM.getFluidCapacity(filledPurifiedWaterLeatherFlask));

        recipes.add(new SmokingRecipe(loc(group + ".flask"), group, CookingBookCategory.MISC, Ingredient.of(filledWaterLeatherFlask), filledPurifiedWaterLeatherFlask, 0.15F, 100));

        return recipes;
    }

    public static List<SmokingRecipe> createWaterBottleRecipes(String plugin) {
        String group = plugin + GROUP;
        List<SmokingRecipe> recipes = new ArrayList<>();
        ItemStack result = new ItemStack(HomeostaticItems.PURIFIED_WATER_BOTTLE);

        recipes.add(new SmokingRecipe(loc(group + ".water_bottle"), group, CookingBookCategory.MISC, Ingredient.of(RecipeProviderBase.waterBottle), result, 0.05F, 50));

        return recipes;
    }

}
