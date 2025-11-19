package homeostaticseasons.common.biome;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.biome.Biome;

import climatesettings.common.biome.BiomeCategory;
import climatesettings.common.biome.BiomeCategoryManager;

import homeostaticseasons.HomeostaticSeasons;
import homeostaticseasons.api.Season;
import homeostaticseasons.common.biome.BiomeColormap.ColormapType;

import static climatesettings.ClimateSettings.prefix;

public class BiomeColormapManager extends SimpleJsonResourceReloadListener {

    private static final Map<ResourceLocation, BiomeColormap> COLORMAPS = new java.util.HashMap<>();
    private  static final Map<ResourceLocation, ResourceLocation> BIOMETYPE_TO_COLORMAP = new java.util.HashMap<>();

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(BiomeColormap.class, new BiomeColormap.Serializer()).create();

    public BiomeColormapManager() {
        super(GSON, "biome/colormaps");

        for (BiomeCategory.Type type : BiomeCategory.Type.values()) {
            switch (type) {
                case BOG, COLD_DESERT, DESERT, DRYLAND, LUSH_DESERT, MESA, MUSHROOM, RAINFOREST, SAVANNA, SWAMP, VOLCANIC, WARM_OCEAN -> {
                    for (Season season : Season.values()) {
                        BIOMETYPE_TO_COLORMAP.put(prefix(type.toString()), getSerializedName(ColormapType.TEMPERATE, season));
                    }
                }
                default -> {
                    for (Season season : Season.values()) {
                        BIOMETYPE_TO_COLORMAP.put(prefix(type.toString()), getSerializedName(ColormapType.NORMAL, season));
                    }
                }
            }
        }
    }

    private static ResourceLocation getSerializedName(BiomeColormap.ColormapType type, Season season) {
        return prefix(season.getSerializedName() + "_" + type.toString());
    }

    public static JsonElement parseBiomeColormapData(BiomeColormap biomeColormap) {
        return GSON.toJsonTree(biomeColormap);
    }

    public static BiomeColormap getBiomeColormap(ResourceLocation type) {
        return COLORMAPS.get(type);
    }

    public static BiomeColormap getColormapForBiome(Holder<Biome> biome) {
        ResourceLocation biomeCategory = prefix(BiomeCategoryManager.getBiomeCategory(biome).toString());
        ResourceLocation biomeTypeColormap = BIOMETYPE_TO_COLORMAP.get(biomeCategory);

        return getBiomeColormap(biomeTypeColormap);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        COLORMAPS.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            try {
                BiomeColormap biomeColormap = GSON.fromJson(entry.getValue(), BiomeColormap.class);

                COLORMAPS.put(entry.getKey(), biomeColormap);
            }
            catch (Exception e) {
                // Log error if necessary
                HomeostaticSeasons.LOGGER.error("Couldn't parse biome colormap data {} {}", entry.getKey(), e);
            }
        }

        HomeostaticSeasons.LOGGER.info("Loaded {} biome colormap entries.", COLORMAPS.size());
    }

}
