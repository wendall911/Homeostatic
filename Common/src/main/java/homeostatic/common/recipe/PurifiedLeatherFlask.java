package homeostatic.common.recipe;

import org.jetbrains.annotations.NotNull;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import homeostatic.common.fluid.FluidInfo;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.item.LeatherFlask;
import homeostatic.platform.Services;

public class PurifiedLeatherFlask extends CustomRecipe {

    public PurifiedLeatherFlask(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(@NotNull CraftingInput craftingInput, @NotNull Level level) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingInput);
        ItemStack flask = check.getFirst();
        ItemStack filter = check.getSecond();

        return flask != null && filter != null;
    }

    @Override
    public ItemStack assemble(@NotNull CraftingInput craftingInput, HolderLookup.@NotNull Provider provider) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingInput);
        ItemStack flaskCopy = check.getFirst().copy();
        FluidInfo fluidInfo = Services.PLATFORM.getFluidInfo(flaskCopy).get();
        int amount = (int) fluidInfo.amount();

        Services.PLATFORM.drainFluid(flaskCopy, amount);

        return Services.PLATFORM.fillFluid(flaskCopy, HomeostaticFluids.PURIFIED_WATER, amount);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HomeostaticRecipes.PURIFIED_LEATHER_FLASK_SERIALIZER;
    }

    public Pair<ItemStack, ItemStack> checkContainer(CraftingInput craftingInput) {
        ItemStack flask = null;
        ItemStack filter = null;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack ingredient = craftingInput.getItem(i);

            if (ingredient.is(HomeostaticItems.WATER_FILTER)) {
                filter = ingredient;
            }
            else if (ingredient.getItem() instanceof LeatherFlask) {
                FluidInfo fluidInfo = Services.PLATFORM.getFluidInfo(ingredient).get();

                if (fluidInfo.amount() > 0) {
                    flask = ingredient;
                }
            }
        }

        return Pair.of(flask, filter);
    }

    public static class Type implements RecipeType<PurifiedLeatherFlask> {

        public static final Type INSTANCE = new Type();

        private Type() {}

    }

}