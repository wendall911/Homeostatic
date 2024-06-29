package homeostatic.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import homeostatic.Homeostatic;
import homeostatic.common.fluid.FluidInfo;
import homeostatic.platform.Services;

public class ItemStackFluidHelper {

    public static FluidInfo getFluidInfo(ItemStack stack) {
        return new FluidInfo(getFluid(stack), getAmount(stack));
    }

    public static Fluid getFluid(ItemStack stack)  {
        CompoundTag tag = stack.getOrCreateTagElement(Homeostatic.MODID);

        if (!tag.contains(Services.PLATFORM.fluidStackTag())) {
            setFluid(stack, Fluids.EMPTY);
        }

        String fluidName = tag.getString(Services.PLATFORM.fluidStackTag());

        return BuiltInRegistries.FLUID.get(new ResourceLocation(fluidName));
    }

    public static void setFluid(ItemStack stack, Fluid fluid) {
        stack.getOrCreateTagElement(Homeostatic.MODID).putString(
            Services.PLATFORM.fluidStackTag(),
            Services.PLATFORM.getFluidResourceLocation(fluid).toString()
        );
    }

    public static void setAmount(ItemStack stack, Long amount) {
        CompoundTag tag = stack.getOrCreateTagElement(Homeostatic.MODID);

        tag.putLong("Amount", amount);
    }

    public static Long getAmount(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTagElement(Homeostatic.MODID);

        if (!tag.contains("Amount")) {
            setAmount(stack, 0L);
        }

        return tag.getLong("Amount");
    }

    public static void drainFluid(ItemStack stack, Long amount) {
        Long currentAmount = getAmount(stack);

        setAmount(stack, Math.max(0L, currentAmount - amount));
        updateDamage(stack);
    }

    public static void fillFluid(ItemStack stack, Fluid fluid, long amount) {
        setFluid(stack, fluid);
        setAmount(stack, amount);
        updateDamage(stack);
    }

    public static void updateDamage(ItemStack stack) {
        if (stack.isDamageableItem()) {
            FluidInfo fluidInfo = getFluidInfo(stack);

            if (stack.isDamageableItem()) {
                stack.setDamageValue(Math.min(stack.getMaxDamage(), stack.getMaxDamage() - (int) fluidInfo.amount()));
            }
        }
    }

}
