package homeostatic.integrations;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.recipe.RecipeProviderBase;
import homeostatic.platform.Services;

import static homeostatic.Homeostatic.loc;

public final class CampfireRecipeMaker {

    public static final String GROUP = ".campfire.purified_water";

    public static List<CampfireCookingRecipe> createFlaskRecipes(String plugin) {
        String group = plugin + GROUP;
        List<CampfireCookingRecipe> recipes = new ArrayList<>();
        ItemStack filledWaterLeatherFlask = new ItemStack(HomeostaticItems.LEATHER_FLASK);
        ItemStack filledPurifiedWaterLeatherFlask = new ItemStack(HomeostaticItems.LEATHER_FLASK);

        Services.PLATFORM.fillFluid(filledWaterLeatherFlask, Fluids.WATER, Services.PLATFORM.getFluidCapacity(filledWaterLeatherFlask));
        Services.PLATFORM.fillFluid(filledPurifiedWaterLeatherFlask, HomeostaticFluids.PURIFIED_WATER, Services.PLATFORM.getFluidCapacity(filledPurifiedWaterLeatherFlask));

        recipes.add(new CampfireCookingRecipe(loc(group + ".flask"), group, CookingBookCategory.MISC, Ingredient.of(filledWaterLeatherFlask), filledPurifiedWaterLeatherFlask, 0.15F, 200));

        return recipes;
    }

    public static List<CampfireCookingRecipe> createWaterBottleRecipes(String plugin) {
        String group = plugin + GROUP;
        List<CampfireCookingRecipe> recipes = new ArrayList<>();
        ItemStack result = new ItemStack(HomeostaticItems.PURIFIED_WATER_BOTTLE);

        recipes.add(new CampfireCookingRecipe(loc(group + ".water_bottle"), group, CookingBookCategory.MISC, Ingredient.of(RecipeProviderBase.waterBottle), result, 0.05F, 100));

        return recipes;
    }

}
