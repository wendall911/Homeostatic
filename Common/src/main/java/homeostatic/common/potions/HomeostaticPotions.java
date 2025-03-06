package homeostatic.common.potions;

import java.util.function.BiConsumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import homeostatic.common.effect.HomeostaticEffects;

import static homeostatic.Homeostatic.loc;

public class HomeostaticPotions {

    public static final Potion FROST_RESISTANCE = new Potion("frost_resistance", new MobEffectInstance(HomeostaticEffects.FROST_RESISTANCE, 3600));
    public static final Potion LONG_FROST_RESISTANCE = new Potion("long_frost_resistance", new MobEffectInstance(HomeostaticEffects.FROST_RESISTANCE, 9600));

    public static void init(BiConsumer<Potion, ResourceLocation> consumer) {
        consumer.accept(FROST_RESISTANCE, loc("frost_resistance"));
        consumer.accept(LONG_FROST_RESISTANCE, loc("long_frost_resistance"));
    }

}
