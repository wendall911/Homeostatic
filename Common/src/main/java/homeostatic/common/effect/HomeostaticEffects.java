package homeostatic.common.effect;

import java.util.function.BiConsumer;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

import static homeostatic.Homeostatic.prefix;

public class HomeostaticEffects {

    public static final MobEffect THIRST = new Thirst();
    public static final Identifier THIRST_ID = prefix("thirst");
    public static final MobEffect FROST_RESISTANCE = new FrostResistance();
    public static final Identifier FROST_RESISTANCE_ID = prefix("frost_resistance");

    public static void init(BiConsumer<MobEffect, Identifier> consumer) {
        consumer.accept(THIRST, THIRST_ID);
        consumer.accept(FROST_RESISTANCE, FROST_RESISTANCE_ID);
    }

}
