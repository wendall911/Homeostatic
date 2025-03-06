package homeostatic.common.effect;

import java.util.function.BiConsumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import static homeostatic.Homeostatic.loc;

public class HomeostaticEffects {

    public static final MobEffect THIRST = new Thirst();
    public static final ResourceLocation THIRST_ID = loc("thirst");
    public static final MobEffect FROST_RESISTANCE = new FrostResistance();
    public static final ResourceLocation FROST_RESISTANCE_ID = loc("frost_resistance");

    public static void init(BiConsumer<MobEffect, ResourceLocation> consumer) {
        consumer.accept(THIRST, THIRST_ID);
        consumer.accept(FROST_RESISTANCE, FROST_RESISTANCE_ID);
    }

}
