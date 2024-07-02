package homeostatic.common.effect;

import org.jetbrains.annotations.NotNull;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import homeostatic.util.WaterHelper;

public class Thirst extends MobEffect {

    public Thirst() {
        super(MobEffectCategory.HARMFUL, 0x80c71f);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayer) {
            WaterHelper.updateWaterInfo((ServerPlayer) entity, 0.005F * (float)(amplifier + 1));
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

}
