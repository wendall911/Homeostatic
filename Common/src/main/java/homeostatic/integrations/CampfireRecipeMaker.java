package homeostatic.integrations;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.material.Fluids;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.recipe.CommonRecipeProvider;
import homeostatic.platform.Services;

import static homeostatic.Homeostatic.prefix;

public final class CampfireRecipeMaker {

    public static final String GROUP = ".campfire.purified_water";

    public static List<RecipeHolder<CampfireCookingRecipe>> createFlaskRecipes(String plugin) {
        String group = plugin + GROUP;
        List<RecipeHolder<CampfireCookingRecipe>> recipes = new ArrayList<>();
        ItemStack filledWaterLeatherFlask = new ItemStack(HomeostaticItems.LEATHER_FLASK);
        ItemStack filledPurifiedWaterLeatherFlask = new ItemStack(HomeostaticItems.LEATHER_FLASK);

        Services.PLATFORM.fillFluid(filledWaterLeatherFlask, Fluids.WATER, Services.PLATFORM.getFluidCapacity(filledWaterLeatherFlask));
        Services.PLATFORM.fillFluid(filledPurifiedWaterLeatherFlask, HomeostaticFluids.PURIFIED_WATER, Services.PLATFORM.getFluidCapacity(filledPurifiedWaterLeatherFlask));

        recipes.add(new RecipeHolder<>(
            ResourceKey.create(Registries.RECIPE, prefix(group + ".flask")),
            new CampfireCookingRecipe(
                RecipeBuilder.createCraftingCommonInfo(true),
                new AbstractCookingRecipe.CookingBookInfo(CookingBookCategory.MISC, group),
                Ingredient.of(filledWaterLeatherFlask.getItem()),
                new ItemStackTemplate(filledPurifiedWaterLeatherFlask.getItem(), 1),
                0.15F,
                200
            )
        ));

        return recipes;
    }

    public static List<RecipeHolder<CampfireCookingRecipe>> createWaterBottleRecipes(String plugin) {
        String group = plugin + GROUP;
        List<RecipeHolder<CampfireCookingRecipe>> recipes = new ArrayList<>();
        ItemStack result = new ItemStack(HomeostaticItems.PURIFIED_WATER_BOTTLE);
        ItemStack waterBottle = PotionContents.createItemStack(Items.POTION, Potions.WATER);

        recipes.add(new RecipeHolder<>(
            ResourceKey.create(Registries.RECIPE, prefix(group + ".water_bottle")),
            new CampfireCookingRecipe(
                RecipeBuilder.createCraftingCommonInfo(true),
                new AbstractCookingRecipe.CookingBookInfo(CookingBookCategory.MISC, group),
                Ingredient.of(waterBottle.getItem()),
                new ItemStackTemplate(result.getItem(), 1),
                0.05F,
                100
            )
        ));

        return recipes;
    }

}
