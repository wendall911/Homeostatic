package climatesettings.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import climatesettings.ClimateSettings;
import climatesettings.common.biome.BiomeCategory;
import climatesettings.common.biome.BiomeTypeData;
import climatesettings.common.biome.BiomeTypeDataManager;

import static climatesettings.ClimateSettings.prefix;

public class BiomeTypeDataProvider implements DataProvider {

    private final Map<Identifier, BiomeTypeData> BIOME_TYPES_MAP = new HashMap<>();
    private final PackOutput packOutput;

    public BiomeTypeDataProvider(@NonNull final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected void registerBiomeTypeData() {
        add(prefix(BiomeCategory.Type.BOG.toString()), 0.351F, 60.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.FROZEN_OCEAN.toString()), 0.373F, 20.0F, 20F, 5F, true);
        add(prefix(BiomeCategory.Type.COLD_OCEAN.toString()), 0.373F, 20.0F, 20F, 5F, false);
        add(prefix(BiomeCategory.Type.COLD_FOREST.toString()), 0.373F, 60.0F, 40F, 12F, false);
        add(prefix(BiomeCategory.Type.FROZEN_RIVER.toString()), 0.385F, 70.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.COLD_DESERT.toString()), 0.395F, 20.0F, 40F, 20F, false);
        add(prefix(BiomeCategory.Type.DEEP_COLD_OCEAN.toString()), 0.440F, 20.0F, 20F, 5F, false);
        add(prefix(BiomeCategory.Type.COLD_BEACH.toString()), 0.503F, 70.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.ICY.toString()), 0.507F, 20.0F, 20F, 5F, false);
        add(prefix(BiomeCategory.Type.TAIGA.toString()), 0.507F, 50.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.OCEAN.toString()), 0.551F, 70.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.RIVER.toString()), 0.551F, 70.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.DEEP_LUKEWARM_OCEAN.toString()), 0.596F, 70.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.EXTREME_HILLS.toString()), 0.618F, 50.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.MOUNTAIN.toString()), 0.618F, 50.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.LUKEWARM_OCEAN.toString()), 0.640F, 70.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.WARM_RIVER.toString()), 0.651F, 70.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.BEACH.toString()), 0.663F, 70.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.FOREST.toString()), 0.663F, 50.0F, 40F, 12F, false);
        add(prefix(BiomeCategory.Type.UNDERGROUND.toString()), 0.663F, 40.0F, 40F, 12F, false);
        add(prefix(BiomeCategory.Type.SWAMP.toString()), 0.685F, 90.0F, 40F, 12F, false);
        add(prefix(BiomeCategory.Type.MUSHROOM.toString()), 0.685F, 70.0F, 40F, 12F, false);
        add(prefix(BiomeCategory.Type.WARM_OCEAN.toString()), 0.730F, 70.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.WARM_BEACH.toString()), 0.763F, 70.0F, 40F, 10F, false);
        add(prefix(BiomeCategory.Type.PLAINS.toString()), 0.774F, 60.0F, 40F, 15F, false);
        add(prefix(BiomeCategory.Type.MISSING.toString()), 0.774F, 40.0F, 40F, 15F, false);
        add(prefix(BiomeCategory.Type.LUSH_DESERT.toString()), 0.886F, 60.0F, 40F, 15F, false);
        add(prefix(BiomeCategory.Type.DRYLAND.toString()), 0.886F, 35.0F, 40F, 15F, false);
        add(prefix(BiomeCategory.Type.RAINFOREST.toString()), 0.886F, 95.0F, 40F, 15F, false);
        add(prefix(BiomeCategory.Type.JUNGLE.toString()), 0.997F, 90.0F, 40F, 15F, false);
        add(prefix(BiomeCategory.Type.VOLCANIC.toString()), 1.04F, 35.0F, 40F, 15F, false);
        add(prefix(BiomeCategory.Type.DEAD_SEA.toString()), 1.04F, 35.0F, 40F, 15F, false);
        add(prefix(BiomeCategory.Type.SAVANNA.toString()), 1.108F, 30.0F, 40F, 15F, false);
        add(prefix(BiomeCategory.Type.MESA.toString()), 1.309F, 20.0F, 40F, 15F, false);
        add(prefix(BiomeCategory.Type.DESERT.toString()), 1.354F, 20.0F, 40F, 20F, false);
        add(prefix(BiomeCategory.Type.NONE.toString()), 0.15F, 40.0F, 40F, 0F, false);
        add(prefix(BiomeCategory.Type.THEEND.toString()), 0.551F, 40.0F, 40F, 0F, false);
        add(prefix(BiomeCategory.Type.NETHER.toString()), 1.666F, 20.0F, 40F, 0F, false);
    }

    protected void add(Identifier loc, float temperature, double humidity, double seasonVariation, double dayNightOffset, boolean isFrozen) {
        BiomeTypeData biomeTypeData = new BiomeTypeData(loc, temperature, humidity, seasonVariation, dayNightOffset, isFrozen);

        BIOME_TYPES_MAP.put(loc, biomeTypeData);
    }

    @Override
    public @NonNull String getName() {
        return ClimateSettings.MOD_NAME + " - Biome Type Data";
    }

    @Override
    @NonNull
    public CompletableFuture<?> run(@NonNull CachedOutput cache) throws IllegalStateException {
        List<CompletableFuture<?>> recipeList = new ArrayList<>();

        registerBiomeTypeData();

        for (Map.Entry<Identifier, BiomeTypeData> entry : BIOME_TYPES_MAP.entrySet()) {
            PackOutput.PathProvider pathProvider = getPath(entry.getKey());

            recipeList.add(DataProvider.saveStable(cache,
                BiomeTypeDataManager.parseBiomeData(entry.getValue()),
                pathProvider.json(entry.getKey())));
        }

        return CompletableFuture.allOf(recipeList.toArray(CompletableFuture[]::new));
    }

    private PackOutput.PathProvider getPath(Identifier loc) {
        return this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "environment/biome_type_data/");
    }

}
