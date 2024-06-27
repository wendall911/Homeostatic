package homeostatic.mixin;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import homeostatic.common.item.FluidHandlerItem;
import homeostatic.common.item.WaterContainerItem;

@Mixin(WaterContainerItem.class)
public class FluidHandlerCapablityMixin extends Item {

    @Shadow
    int capacity;

    public FluidHandlerCapablityMixin(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable CompoundTag tag) {
        return new FluidHandlerItem(stack, capacity);
    }

}
