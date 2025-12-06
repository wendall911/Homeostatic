package climatesettings.common.biome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.minecraft.core.Holder;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.biome.Biome;

import climatesettings.ClimateSettings;
import climatesettings.network.SyncBiomeCategoryData;
import climatesettings.platform.Services;

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
            ClimateSettings.LOGGER.debug("Unable to find biome for: {}", biome.toString());
        }

        return BiomeCategory.Type.MISSING;
    }

    public static void update(List<BiomeCategory> biomeCategories) {
        BIOME_CATEGORIES.clear();

        for (BiomeCategory biomeCategory : biomeCategories) {
            BIOME_CATEGORIES.put(biomeCategory.loc(), biomeCategory);
        }

        ClimateSettings.LOGGER.info("Updated category for {} biomes.", BIOME_CATEGORIES.size());
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
                ClimateSettings.LOGGER.error("Couldn't parse biome category {} {}", entry.getKey(), e);
            }
        }

        ClimateSettings.LOGGER.info("Loaded category for {} biomes.", BIOME_CATEGORIES.size());
    }

    public static void syncWithClient(ServerPlayer player) {
        if (player != null) {
            List<BiomeCategory> biomeCategories = BIOME_CATEGORIES.values().stream().toList();
            DataResult<Tag> result = Codec.list(BiomeCategory.CODEC).encodeStart(net.minecraft.nbt.NbtOps.INSTANCE, biomeCategories);
            Tag data = result.getOrThrow((error) -> {
                throw new IllegalStateException("Failed to encode biome categories for syncing to client: " + error);
            });

            Services.CLIMATE.syncDataToPlayer(new SyncBiomeCategoryData(data), player);
        }
    }

}
