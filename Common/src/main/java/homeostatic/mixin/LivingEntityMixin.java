package homeostatic.mixin;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import homeostatic.common.effect.HomeostaticEffects;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "canFreeze", at = @At("HEAD"), cancellable = true)
    private void homeostatic$canFreeze(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (self.isSpectator() || self.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(HomeostaticEffects.FROST_RESISTANCE))) {
            cir.setReturnValue(false);
        }
        else {
            self.getType().builtInRegistryHolder().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES);
        }
    }

}
