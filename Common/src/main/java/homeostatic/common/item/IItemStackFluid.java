package homeostatic.common.item;

import net.minecraft.world.item.ItemStack;

import homeostatic.util.ItemStackFluidHelper;

public interface IItemStackFluid {

    default void initializeFluidValues(ItemStack stack) {
        ItemStackFluidHelper.getFluidInfo(stack);
    }

}
