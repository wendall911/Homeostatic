package homeostatic.common.biome;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.biome.Biome;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeCategoryManager extends SimpleJsonResourceReloadListener {

    private static final Map<ResourceLocation, BiomeCategory> BIOME_CATEGORIES = new HashMap<>();

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(BiomeCategory.class, new BiomeCategory.Serializer()).create();

    public BiomeCategoryManager() {
        super(GSON, "environment/biome_category");
    }

    public static JsonElement parseBiomeCategory(BiomeCategory biomeCategory) {
        return GSON.toJsonTree(biomeCategory);
    }

    public static BiomeCategory.Type getBiomeCategory(Holder<Biome> biome) {
        try {
            Optional<ResourceKey<Biome>> key = biome.unwrapKey();
            BiomeCategory biomeCategory;

            if (key.isPresent()) {
                biomeCategory = BIOME_CATEGORIES.getOrDefault(key.get().location(), BiomeCategory.MISSING);
            }
            else {
                biomeCategory = BiomeCategory.MISSING;
            }

            return BiomeCategory.Type.valueOf(biomeCategory.type());
        } catch (IllegalArgumentException | NoSuchElementException e) {
            Homeostatic.LOGGER.debug("Unable to find biome for: %s", biome.toString());
        }

        return BiomeCategory.Type.MISSING;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        BIOME_CATEGORIES.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            try {
                BiomeCategory biomeCategory = GSON.fromJson(entry.getValue(), BiomeCategory.class);

                BIOME_CATEGORIES.put(biomeCategory.loc(), biomeCategory);
            }
            catch (Exception e) {
                Homeostatic.LOGGER.error("Couldn't parse biome category %s %s", entry.getKey(), e);
            }
        }

        Homeostatic.LOGGER.info("Loaded category for %d biomes.", BIOME_CATEGORIES.size());
    }

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        event.addListener(new BiomeCategoryManager());
    }

}
