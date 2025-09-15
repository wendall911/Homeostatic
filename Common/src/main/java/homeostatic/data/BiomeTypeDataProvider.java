package homeostatic.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import homeostatic.common.biome.BiomeCategory;
import homeostatic.common.biome.BiomeData;
import homeostatic.common.biome.BiomeTypeDataManager;

import static homeostatic.Homeostatic.prefix;

public class BiomeTypeDataProvider implements DataProvider {

    private final Map<ResourceLocation, BiomeData> BIOME_TYPES_MAP = new HashMap<>();
    private final PackOutput packOutput;

    public BiomeTypeDataProvider(@NotNull final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected void registerBiomeTypeData() {
        add(prefix(BiomeCategory.Type.BOG.toString()), new BiomeData(0.351F, 60.0F, 40F, 10F, false));
        add(prefix(BiomeCategory.Type.FROZEN_OCEAN.toString()), new BiomeData(0.373F, 20.0F, 20F, 5F, true));
        add(prefix(BiomeCategory.Type.COLD_OCEAN.toString()), new BiomeData(0.373F, 20.0F, 20F, 5F, false));
        add(prefix(BiomeCategory.Type.COLD_FOREST.toString()), new BiomeData(0.373F, 60.0F, 40F, 12F, false));
        add(prefix(BiomeCategory.Type.COLD_DESERT.toString()), new BiomeData(0.395F, 20.0F, 40F, 20F, false));
        add(prefix(BiomeCategory.Type.DEEP_COLD_OCEAN.toString()), new BiomeData(0.440F, 20.0F, 20F, 5F, false));
        add(prefix(BiomeCategory.Type.ICY.toString()), new BiomeData(0.507F, 20.0F, 20F, 5F, false));
        add(prefix(BiomeCategory.Type.TAIGA.toString()), new BiomeData(0.507F, 50.0F, 40F, 10F, false));
        add(prefix(BiomeCategory.Type.OCEAN.toString()), new BiomeData(0.551F, 70.0F, 40F, 10F, false));
        add(prefix(BiomeCategory.Type.RIVER.toString()), new BiomeData(0.551F, 70.0F, 40F, 10F, false));
        add(prefix(BiomeCategory.Type.DEEP_LUKEWARM_OCEAN.toString()), new BiomeData(0.596F, 70.0F, 40F, 10F, false));
        add(prefix(BiomeCategory.Type.EXTREME_HILLS.toString()), new BiomeData(0.618F, 50.0F, 40F, 10F, false));
        add(prefix(BiomeCategory.Type.MOUNTAIN.toString()), new BiomeData(0.618F, 50.0F, 40F, 10F, false));
        add(prefix(BiomeCategory.Type.LUKEWARM_OCEAN.toString()), new BiomeData(0.640F, 70.0F, 40F, 10F, false));
        add(prefix(BiomeCategory.Type.BEACH.toString()), new BiomeData(0.663F, 70.0F, 40F, 10F, false));
        add(prefix(BiomeCategory.Type.FOREST.toString()), new BiomeData(0.663F, 50.0F, 40F, 12F, false));
        add(prefix(BiomeCategory.Type.UNDERGROUND.toString()), new BiomeData(0.663F, 40.0F, 40F, 12F, false));
        add(prefix(BiomeCategory.Type.SWAMP.toString()), new BiomeData(0.685F, 90.0F, 40F, 12F, false));
        add(prefix(BiomeCategory.Type.MUSHROOM.toString()), new BiomeData(0.685F, 70.0F, 40F, 12F, false));
        add(prefix(BiomeCategory.Type.WARM_OCEAN.toString()), new BiomeData(0.730F, 70.0F, 40F, 10F, false));
        add(prefix(BiomeCategory.Type.PLAINS.toString()), new BiomeData(0.774F, 60.0F, 40F, 15F, false));
        add(prefix(BiomeCategory.Type.MISSING.toString()), new BiomeData(0.774F, 40.0F, 40F, 15F, false));
        add(prefix(BiomeCategory.Type.LUSH_DESERT.toString()), new BiomeData(0.886F, 60.0F, 40F, 15F, false));
        add(prefix(BiomeCategory.Type.DRYLAND.toString()), new BiomeData(0.886F, 35.0F, 40F, 15F, false));
        add(prefix(BiomeCategory.Type.RAINFOREST.toString()), new BiomeData(0.886F, 95.0F, 40F, 15F, false));
        add(prefix(BiomeCategory.Type.JUNGLE.toString()), new BiomeData(0.997F, 90.0F, 40F, 15F, false));
        add(prefix(BiomeCategory.Type.VOLCANIC.toString()), new BiomeData(1.04F, 35.0F, 40F, 15F, false));
        add(prefix(BiomeCategory.Type.DEAD_SEA.toString()), new BiomeData(1.04F, 35.0F, 40F, 15F, false));
        add(prefix(BiomeCategory.Type.SAVANNA.toString()), new BiomeData(1.108F, 30.0F, 40F, 15F, false));
        add(prefix(BiomeCategory.Type.MESA.toString()), new BiomeData(1.309F, 20.0F, 40F, 15F, false));
        add(prefix(BiomeCategory.Type.DESERT.toString()), new BiomeData(1.354F, 20.0F, 40F, 20F, false));
        add(prefix(BiomeCategory.Type.NONE.toString()), new BiomeData(0.15F, 40.0F, 40F, 0F, false));
        add(prefix(BiomeCategory.Type.THEEND.toString()), new BiomeData(0.551F, 40.0F, 40F, 0F, false));
        add(prefix(BiomeCategory.Type.NETHER.toString()), new BiomeData(1.666F, 20.0F, 40F, 0F, false));
    }

    protected void add(ResourceLocation loc, BiomeData biomeData) {
        BIOME_TYPES_MAP.put(loc, biomeData);
    }

    @Override
    public @NotNull String getName() {
        return "Homeostatic - Biome Type Data";
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput cache) throws IllegalStateException {
        List<CompletableFuture<?>> recipeList = new ArrayList<>();

        registerBiomeTypeData();

        for (Map.Entry<ResourceLocation, BiomeData> entry : BIOME_TYPES_MAP.entrySet()) {
            PackOutput.PathProvider pathProvider = getPath(entry.getKey());

            recipeList.add(DataProvider.saveStable(cache,
                BiomeTypeDataManager.parseBiomeData(entry.getValue()),
                pathProvider.json(entry.getKey())));
        }

        return CompletableFuture.allOf(recipeList.toArray(CompletableFuture[]::new));
    }

    private PackOutput.PathProvider getPath(ResourceLocation loc) {
        return this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "environment/biome_type_data/");
    }

}
