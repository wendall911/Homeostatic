package homeostatic.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraftforge.registries.RegistryObject;

import homeostatic.Homeostatic;

public class HomeostaticEffects {

    public static final DeferredRegister<MobEffect> EFFECT_REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Homeostatic.MODID);

    public static final RegistryObject<MobEffect> THIRST = EFFECT_REGISTRY.register(
            "thirst", () -> new Thirst()
    );

    public HomeostaticEffects() {}

}
