package climatesettings.common.biome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Holder;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import climatesettings.ClimateSettings;
import climatesettings.network.SyncBiomeTypeData;
import climatesettings.platform.Services;

import static climatesettings.ClimateSettings.prefix;

public class BiomeTypeDataManager extends SimpleJsonResourceReloadListener {

    private static final Map<ResourceLocation, BiomeTypeData> BIOME_TYPES = new HashMap<>();

    private static final ResourceLocation MISSING_LOC = prefix(BiomeCategory.Type.MISSING.toString());

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(BiomeTypeData.class, new BiomeTypeData.Serializer()).create();

    public BiomeTypeDataManager() {
        super(GSON, "environment/biome_type_data");
    }

    public static JsonElement parseBiomeData(BiomeTypeData biomeTypeData) {
        return GSON.toJsonTree(biomeTypeData);
    }

    public static BiomeTypeData getBiomeData(ResourceLocation type) {
        return BIOME_TYPES.getOrDefault(type, BIOME_TYPES.get(MISSING_LOC));
    }

    public static BiomeTypeData getDataForBiome(Holder<Biome> biome) {
        ResourceLocation biomeCategory = prefix(BiomeCategoryManager.getBiomeCategory(biome).toString());

        return getBiomeData(biomeCategory);
    }

    public static void update(List<BiomeTypeData> biomeTypeDataList) {
        BIOME_TYPES.clear();

        for (BiomeTypeData biomeTypeData : biomeTypeDataList) {
            BIOME_TYPES.put(biomeTypeData.getLocation(), biomeTypeData);
        }

        ClimateSettings.LOGGER.info("Updated {} biome types.", BIOME_TYPES.size());
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, @NotNull ResourceManager pResourceManager, @NotNull ProfilerFiller pProfiler) {
        BIOME_TYPES.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            try {
                BiomeTypeData biomeTypeData = GSON.fromJson(entry.getValue(), BiomeTypeData.class);

                BIOME_TYPES.put(entry.getKey(), biomeTypeData);
            }
            catch (Exception e) {
                ClimateSettings.LOGGER.error("Couldn't parse biome data {} {}", entry.getKey(), e);
            }
        }

        ClimateSettings.LOGGER.info("Loaded {} biome types.", BIOME_TYPES.size());
    }

    public static void syncWithClient(ServerPlayer player) {
        if (player != null) {
            List<BiomeTypeData> biomeTypeDataList = BIOME_TYPES.values().stream().toList();
            DataResult<Tag> result = Codec.list(BiomeTypeData.CODEC).encodeStart(NbtOps.INSTANCE, biomeTypeDataList);
            Tag data = result.getOrThrow((error) -> {
                throw new IllegalStateException("Failed to encode biome type data for syncing: " + error);
            });

            Services.CLIMATE.syncDataToPlayer(new SyncBiomeTypeData(data), player);
        }
    }

}
