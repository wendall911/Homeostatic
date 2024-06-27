package homeostatic.common.recipe;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
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
    public boolean matches(CraftingContainer pContainer, Level pLevel) {
        Pair<ItemStack, ItemStack> check = checkContainer(pContainer);
        ItemStack flask = check.getFirst();
        ItemStack filter = check.getSecond();

        return flask != null && filter != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess registryAccess) {
        Pair<ItemStack, ItemStack> check = checkContainer(pContainer);
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

    public Pair<ItemStack, ItemStack> checkContainer(CraftingContainer pContainer) {
        ItemStack flask = null;
        ItemStack filter = null;

        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack ingredient = pContainer.getItem(i);

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