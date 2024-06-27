package homeostatic.common.recipe;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.Level;

import homeostatic.platform.Services;

public class SmokingPurifiedLeatherFlask extends SmokingRecipe implements ICookingRecipe {

    public SmokingPurifiedLeatherFlask(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        super(group, category, ingredient, result, experience, cookingTime);
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return getCleanWaterFilledLWaterContainer(this.result);
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        return matches(container, Services.PLATFORM.getFluidCapacity(container.getItem(0)));
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return HomeostaticRecipes.SMOKING_PURIFIED_LEATHER_FLASK_SERIALIZER;
    }

}
