package homeostaticseasons.util;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

public class RegistryHelper {

    public static Holder<Biome> getBiomeHolder(Biome biome, Level level) {
        Registry<Biome> biomeRegistry = level.registryAccess().registryOrThrow(Registries.BIOME);

        return biomeRegistry.getResourceKey(biome).flatMap(biomeRegistry::getHolder).orElse(null);
    }

}
