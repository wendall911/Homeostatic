package homeostatic.common.effect;

import net.minecraft.world.effect.MobEffect;

import net.minecraftforge.registries.RegisterEvent;


public class HomeostaticEffects {

    public static final MobEffect THIRST = new Thirst();

    public static void init(RegisterEvent.RegisterHelper<MobEffect> registerHelper) {
        registerHelper.register("thirst", THIRST);
    }

}
