package homeostatic.common.effect;

import java.util.function.BiConsumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import static homeostatic.Homeostatic.loc;

public class HomeostaticEffects {

    public static final MobEffect THIRST = new Thirst();
    public static final MobEffect FROST_RESISTANCE = new FrostResistance();

    public static void init(BiConsumer<MobEffect, ResourceLocation> consumer) {
        consumer.accept(THIRST, loc("thirst"));
        consumer.accept(FROST_RESISTANCE, loc("frost_resistance"));
    }

}
