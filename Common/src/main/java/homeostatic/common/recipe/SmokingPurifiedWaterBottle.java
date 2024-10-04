package homeostatic.common.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SmokingPurifiedWaterBottle extends SmokingRecipe implements IWaterBottleCookingRecipe {

    public SmokingPurifiedWaterBottle(ResourceLocation id, String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(id, group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container container, @NotNull RegistryAccess registryAccess) {
        return assemble();
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        return matches(container);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return HomeostaticRecipes.SMOKING_PURIFIED_WATER_BOTTLE_SERIALIZER;
    }

}
