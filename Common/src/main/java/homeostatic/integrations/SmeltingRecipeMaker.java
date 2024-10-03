package homeostatic.integrations;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.material.Fluids;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.recipe.RecipeProviderBase;
import homeostatic.platform.Services;

import static homeostatic.Homeostatic.loc;

public class SmeltingRecipeMaker {

    public static final String GROUP = ".smelting.purified_water";

    public static List<RecipeHolder<SmeltingRecipe>> createFlaskRecipes(String plugin) {
        String group = plugin + GROUP;
        List<RecipeHolder<SmeltingRecipe>> recipes = new ArrayList<>();
        ItemStack filledWaterLeatherFlask = new ItemStack(HomeostaticItems.LEATHER_FLASK);
        ItemStack filledPurifiedWaterLeatherFlask = new ItemStack(HomeostaticItems.LEATHER_FLASK);

        Services.PLATFORM.fillFluid(filledWaterLeatherFlask, Fluids.WATER, Services.PLATFORM.getFluidCapacity(filledWaterLeatherFlask));
        Services.PLATFORM.fillFluid(filledPurifiedWaterLeatherFlask, HomeostaticFluids.PURIFIED_WATER, Services.PLATFORM.getFluidCapacity(filledPurifiedWaterLeatherFlask));

        recipes.add(new RecipeHolder<>(
            loc(group + ".flask"),
            new SmeltingRecipe(group, CookingBookCategory.MISC, Ingredient.of(filledWaterLeatherFlask), filledPurifiedWaterLeatherFlask, 0.15F, 150)
        ));

        return recipes;
    }

    public static List<RecipeHolder<SmeltingRecipe>> createWaterBottleRecipes(String plugin) {
        String group = plugin + GROUP;
        List<RecipeHolder<SmeltingRecipe>> recipes = new ArrayList<>();
        ItemStack result = new ItemStack(HomeostaticItems.PURIFIED_WATER_BOTTLE);

        recipes.add(new RecipeHolder<>(
            loc(group + ".water_bottle"),
            new SmeltingRecipe(group, CookingBookCategory.MISC, Ingredient.of(RecipeProviderBase.waterBottle), result, 0.05F, 75)
        ));

        return recipes;
    }

}
