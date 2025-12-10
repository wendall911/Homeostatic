package climatesettings.event;

import java.util.Map;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import climatesettings.ClimateSettings;
import climatesettings.common.biome.BiomeCategory;
import climatesettings.common.biome.BiomeCategoryManager;
import climatesettings.common.biome.BiomeTypeData;
import climatesettings.common.biome.BiomeTypeDataManager;

import static climatesettings.ClimateSettings.prefix;

public class ServerEventListener {


    @SubscribeEvent
    public static void onResourceReload(AddServerReloadListenersEvent event) {
        event.addListener(prefix("biome_category"), new BiomeCategoryManager());
        event.addListener(prefix("biome_type_data"), new BiomeTypeDataManager());
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void serverStart(final ServerStartedEvent event) {
        Registry<Biome> biomeRegistry = getRegistry(event.getServer(), Registries.BIOME);

        for (Map.Entry<ResourceKey<Biome>, Biome> entry : biomeRegistry.entrySet()) {
            ResourceKey<Biome> biomeResourceKey = entry.getKey();
            Identifier biomeName = biomeResourceKey.identifier();
            Holder<Biome> biomeHolder = biomeRegistry.wrapAsHolder(biomeRegistry.getValueOrThrow(biomeResourceKey));
            BiomeCategory.Type biomeCategory = BiomeCategoryManager.getBiomeCategory(biomeHolder);
            BiomeTypeData biomeTypeData = BiomeTypeDataManager.getDataForBiome(biomeHolder);
            Biome biome = biomeHolder.value();
            Biome.Precipitation precipitation = getPrecipitation(biome);
            String temperatureModifier = biomeTypeData.isFrozen() ? "FROZEN" : "NONE";
            float dayNightOffset = biomeTypeData.getDayNightOffset(precipitation);
            double humidity = biomeTypeData.getHumidity(precipitation);

            if (!biomeName.toString().equals("terrablender:deferred_placeholder")) {
                if (biomeCategory == BiomeCategory.Type.MISSING) {
                    ClimateSettings.LOGGER.warn("Missing biome in registry, will set to neutral temperature for: {}", biomeName);
                }

                ClimateSettings.LOGGER.debug("Biome: " + biomeName
                    + "\nprecipitation_type=" + precipitation
                    + "\ntemperature=" + biomeTypeData.getTemperature(precipitation)
                    + "\ntemperatureModifier=" + temperatureModifier
                    + "\ndownfall=" + biome.getModifiedClimateSettings().downfall()
                    + "\ndayNightOffset=" + dayNightOffset
                    + "\nhumidity=" + humidity
                    + "\nbiomeCategory=" + biomeCategory);
            }
        }
    }

    /*
     * Mock for debugging purposes. Will not be 100% accurate, but should help map to older versions.
     */
    private static Biome.Precipitation getPrecipitation(Biome biome) {
        if (!biome.hasPrecipitation()) {
            return Biome.Precipitation.NONE;
        }
        else {
            return biome.getBaseTemperature() <= 0.15F ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN;
        }
    }

    public static <T> Registry<T> getRegistry(MinecraftServer server, ResourceKey<Registry<T>> resourceKey) {
        return server.registryAccess().lookupOrThrow(resourceKey);
    }

}
