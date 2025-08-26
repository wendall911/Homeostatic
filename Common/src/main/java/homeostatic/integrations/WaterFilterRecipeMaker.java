package homeostatic.integrations;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.platform.Services;
import homeostatic.util.WaterHelper;

import static homeostatic.Homeostatic.loc;

public class WaterFilterRecipeMaker {

    public static List<Pair<ItemStack, RecipeHolder<CraftingRecipe>>> getFilterCraftingRecipes(String plugin) {
        List<Pair<ItemStack, RecipeHolder<CraftingRecipe>>> recipes = new ArrayList<>();
        Ingredient ingredient = Ingredient.of(HomeostaticItems.WATER_FILTER);
        ItemStack leatherFlaskBase = new ItemStack(HomeostaticItems.LEATHER_FLASK);
        ItemStack leatherFlask = WaterHelper.getFilledItem(
            leatherFlaskBase,
            HomeostaticFluids.PURIFIED_WATER,
            (int) Services.PLATFORM.getFluidCapacity(leatherFlaskBase)
        );
        String group = plugin + ".flask.filter";

        Ingredient baseFlaskIngredient = Ingredient.of(leatherFlaskBase.getItem());
        NonNullList<Ingredient> recipeInputs = NonNullList.of(null, baseFlaskIngredient, ingredient);

        recipes.add(Pair.of(leatherFlask, new RecipeHolder<>(
            ResourceKey.create(Registries.RECIPE, loc(group + ".purified_leather_flask")),
            new ShapelessRecipe(group, CraftingBookCategory.MISC, leatherFlask, recipeInputs)
        )));

        return recipes;
    }

}
