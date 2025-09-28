package homeostatic.common.damagesource;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import static homeostatic.Homeostatic.loc;

public class HomeostaticDamageTypes {

    public static final List<ResourceKey<DamageType>> ALL = new ArrayList<>();
    public static final ResourceKey<DamageType> HYPERTHERMIA = register("hyperthermia");
    public static final ResourceKey<DamageType> SCALDING = register("scalding");
    public static final ResourceKey<DamageType> DEHYDRATION = register("dehydration");

    public static void init() {}

    public static void bootstrap(BootstapContext<DamageType> context) {
        ALL.forEach((key) -> register(context, key));
    }

    private static void register(BootstapContext<DamageType> context, ResourceKey<DamageType> key) {
        context.register(key, new DamageType( key.location().getPath(), 0.0F));
    }

    private static ResourceKey<DamageType> register(String name) {
        ResourceKey<DamageType> key = ResourceKey.create(Registries.DAMAGE_TYPE, loc(name));
        ALL.add(key);

        return key;
    }

}
