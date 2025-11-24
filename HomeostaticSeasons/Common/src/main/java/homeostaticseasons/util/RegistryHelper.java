package homeostaticseasons.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import technology.roughness.whitenoise.util.ResourceLocationHelper;

public class RegistryHelper extends ResourceLocationHelper {

    public static Holder<Biome> getBiomeHolder(Biome biome, Level level) {
        Registry<Biome> biomeRegistry;

        if (level instanceof ServerLevel) {
            biomeRegistry = level.registryAccess().registryOrThrow(Registries.BIOME);
        }
        else {
            ClientPacketListener connection = Minecraft.getInstance().getConnection();

            if (connection != null) {
                biomeRegistry = connection.registryAccess().registryOrThrow(Registries.BIOME);
            }
            else {
                return null;
            }
        }

        return biomeRegistry.getResourceKey(biome).flatMap(biomeRegistry::getHolder).orElse(null);
    }

}
