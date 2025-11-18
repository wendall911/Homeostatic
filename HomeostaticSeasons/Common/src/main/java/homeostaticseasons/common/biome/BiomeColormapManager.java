package homeostaticseasons.common.biome;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import homeostaticseasons.HomeostaticSeasons;

public class BiomeColormapManager extends SimpleJsonResourceReloadListener {

    private static final Map<ResourceLocation, BiomeColormap> BIOME_COLORMAPS = new java.util.HashMap<>();

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(BiomeColormap.class, new BiomeColormap.Serializer()).create();

    public BiomeColormapManager() {
        super(GSON, "biome/colormaps");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        BIOME_COLORMAPS.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            try {
                BiomeColormap biomeColormap = GSON.fromJson(entry.getValue(), BiomeColormap.class);

                BIOME_COLORMAPS.put(entry.getKey(), biomeColormap);
            }
            catch (Exception e) {
                // Log error if necessary
                HomeostaticSeasons.LOGGER.error("Couldn't parse biome colormap data {} {}", entry.getKey(), e);
            }
        }

        HomeostaticSeasons.LOGGER.info("Loaded {} biome colormap entries.", BIOME_COLORMAPS.size());
    }

}
