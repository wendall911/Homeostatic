package homeostatic.common.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;

import homeostatic.common.fluid.FluidInfo;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.WaterContainerItem;
import homeostatic.platform.Services;

public interface ICookingRecipe {

    default ItemStack assemble(Container container, ItemStack result) {
        ItemStack filledFlask = result.copy();

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack ingredient = container.getItem(i);

            if (ingredient.getItem() instanceof WaterContainerItem) {
                FluidInfo fluidInfo = Services.PLATFORM.getFluidInfo(ingredient).get();
                int amount = (int) fluidInfo.amount();

                Services.PLATFORM.fillFluid(filledFlask, HomeostaticFluids.PURIFIED_WATER, amount);
            }
        }

        return filledFlask;
    }

    default boolean matches(Container container, Long minimumFluid) {
        if (container.getItem(0).getItem() instanceof WaterContainerItem) {
            FluidInfo fluidInfo = Services.PLATFORM.getFluidInfo(container.getItem(0)).get();

            return fluidInfo.amount() >= minimumFluid && fluidInfo.fluid() == Fluids.WATER;
        }

        return false;
    }

    default ItemStack getCleanWaterFilledLWaterContainer(ItemStack original) {
        ItemStack filledStack = original.copy();

        Services.PLATFORM.fillFluid(filledStack, HomeostaticFluids.PURIFIED_WATER, Services.PLATFORM.getFluidCapacity(filledStack));

        return filledStack;
    }

}
