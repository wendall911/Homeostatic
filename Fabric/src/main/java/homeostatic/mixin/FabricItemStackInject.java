package homeostatic.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import homeostatic.common.item.IItemStackFluid;

@Mixin(ItemStack.class)
public abstract class FabricItemStackInject {

    @Shadow public abstract Item getItem();

    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;)V", at = @At(value = "TAIL"))
    public void homeostatic$init(ItemLike itemLike, CallbackInfo ci) {
        if (this.getItem() instanceof IItemStackFluid) {
            ((IItemStackFluid) this.getItem()).initializeFluidValues((ItemStack) (Object) this);
        }
    }

}
