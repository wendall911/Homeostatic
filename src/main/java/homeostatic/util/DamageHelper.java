package homeostatic.util;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.damagesource.DamageType;

public class DamageHelper {

    public static Holder<DamageType> getHolder(MinecraftServer server, ResourceKey<DamageType> damageType) {
        return RegistryHelper.getRegistry(server, Registries.DAMAGE_TYPE).getHolderOrThrow(damageType);
    }

}
