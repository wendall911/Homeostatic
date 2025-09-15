package homeostatic.common.biome;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import homeostatic.Homeostatic;

public class BiomeTypeDataManager extends SimpleJsonResourceReloadListener {

    private static final Map<ResourceLocation, BiomeData> BIOME_TYPES = new HashMap<>();

    private static final ResourceLocation MISSING_LOC = Homeostatic.prefix(BiomeCategory.Type.MISSING.toString());

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(BiomeData.class, new BiomeData.Serializer()).create();

    public BiomeTypeDataManager() {
        super(GSON, "environment/biome_type_data");
    }

    public static JsonElement parseBiomeData(BiomeData biomeData) {
        return GSON.toJsonTree(biomeData);
    }

    public static BiomeData getBiomeData(ResourceLocation type) {
        return BIOME_TYPES.getOrDefault(type, BIOME_TYPES.get(MISSING_LOC));
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, @NotNull ResourceManager pResourceManager, @NotNull ProfilerFiller pProfiler) {
        BIOME_TYPES.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            try {
                BiomeData biomeData = GSON.fromJson(entry.getValue(), BiomeData.class);

                BIOME_TYPES.put(entry.getKey(), biomeData);
            }
            catch (Exception e) {
                Homeostatic.LOGGER.error("Couldn't parse biome data {} {}", entry.getKey(), e);
            }
        }

        Homeostatic.LOGGER.info("Loaded {} biome types.", BIOME_TYPES.size());
    }

}
