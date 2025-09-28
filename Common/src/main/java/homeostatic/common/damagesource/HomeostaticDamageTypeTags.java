package homeostatic.common.damagesource;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class HomeostaticDamageTypeTags {

    public static final TagKey<DamageType> HYPERTHERMIA = register(HomeostaticDamageTypes.HYPERTHERMIA);
    public static final TagKey<DamageType> SCALDING = register(HomeostaticDamageTypes.SCALDING);
    public static final TagKey<DamageType> DEHYDRATION = register(HomeostaticDamageTypes.DEHYDRATION);

    private static TagKey<DamageType> register(ResourceKey<DamageType> key) {
        return TagKey.create(Registries.DAMAGE_TYPE, key.location());
    }

}
