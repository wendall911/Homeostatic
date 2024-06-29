package homeostatic.common.item;

import org.jetbrains.annotations.NotNull;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;

import net.minecraft.world.item.ItemStack;

import homeostatic.Homeostatic;
import homeostatic.util.WaterHelper;

public class FluidHandlerItem extends FluidHandlerItemStack {

    public FluidHandlerItem(@NotNull ItemStack container, int capacity) {
        super(container, capacity);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluidStack) {
        // Allow recipes to register any liquid. Will default to hydration registry if not running datagen
        return Homeostatic.DATA_GEN || WaterHelper.getFluidHydration(fluidStack.getFluid()) != null;
    }

}