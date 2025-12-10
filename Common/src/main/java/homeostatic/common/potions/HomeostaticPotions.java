package homeostatic.common.potions;

import java.util.function.BiConsumer;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import homeostatic.common.effect.HomeostaticEffects;

import static homeostatic.Homeostatic.prefix;

public class HomeostaticPotions {

    public static final Potion FROST_RESISTANCE = new Potion(
        "frost_resistance",
        new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(HomeostaticEffects.FROST_RESISTANCE), 3600)
    );
    public static final Potion LONG_FROST_RESISTANCE = new Potion(
        "long_frost_resistance",
        new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(HomeostaticEffects.FROST_RESISTANCE), 9600)
    );

    public static void init(BiConsumer<Potion, Identifier> consumer) {
        consumer.accept(FROST_RESISTANCE, prefix("frost_resistance"));
        consumer.accept(LONG_FROST_RESISTANCE, prefix("long_frost_resistance"));
    }

}
