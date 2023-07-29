package homeostatic.common.biome;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

public class BiomeRegistry {

    public static final Map<BiomeCategory.Type, BiomeData> BIOMES = new HashMap<>();

    private static final BiomeData BOG = new BiomeData(0.351F, 60.0F, 40F, 10F);
    private static final BiomeData COLD_OCEAN = new BiomeData(0.373F, 20.0F, 20F, 5F);
    private static final BiomeData COLD_FOREST = new BiomeData(0.373F, 60.0F, 40F, 12F);
    private static final BiomeData COLD_DESERT = new BiomeData(0.395F, 20.0F, 40F, 20F);
    private static final BiomeData DEEP_COLD_OCEAN = new BiomeData(0.440F, 20.0F, 20F, 5F);
    private static final BiomeData ICY = new BiomeData(0.507F, 20.0F, 20F, 5F);
    private static final BiomeData TAIGA = new BiomeData(0.507F, 50.0F, 40F, 10F);
    private static final BiomeData OCEAN = new BiomeData(0.551F, 70.0F, 40F, 10F);
    private static final BiomeData RIVER = new BiomeData(0.551F, 70.0F, 40F, 10F);
    private static final BiomeData DEEP_LUKEWARM_OCEAN = new BiomeData(0.596F, 70.0F, 40F, 10F);
    private static final BiomeData EXTREME_HILLS = new BiomeData(0.618F, 50.0F, 40F, 10F);
    private static final BiomeData MOUNTAIN = new BiomeData(0.618F, 50.0F, 40F, 10F);
    private static final BiomeData LUKEWARM_OCEAN = new BiomeData(0.640F, 70.0F, 40F, 10F);
    private static final BiomeData BEACH = new BiomeData(0.663F, 70.0F, 40F, 10F);
    private static final BiomeData FOREST = new BiomeData(0.663F, 50.0F, 40F, 12F);
    public static final BiomeData UNDERGROUND = new BiomeData(0.663F, 40.0F, 40F, 12F);
    private static final BiomeData SWAMP = new BiomeData(0.685F, 90.0F, 40F, 12F);
    private static final BiomeData MUSHROOM = new BiomeData(0.685F, 70.0F, 40F, 12F);
    private static final BiomeData WARM_OCEAN = new BiomeData(0.730F, 70.0F, 40F, 10F);
    private static final BiomeData PLAINS = new BiomeData(0.774F, 60.0F, 40F, 15F);
    private static final BiomeData LUSH_DESERT = new BiomeData(0.886F, 60.0F, 40F, 15F);
    private static final BiomeData DRYLAND = new BiomeData(0.886F, 35.0F, 40F, 15F);
    private static final BiomeData RAINFOREST = new BiomeData(0.886F, 95.0F, 40F, 15F);
    private static final BiomeData JUNGLE = new BiomeData(0.997F, 90.0F, 40F, 15F);
    private static final BiomeData VOLCANIC = new BiomeData(1.04F, 35.0F, 40F, 15F);
    private static final BiomeData SAVANNA = new BiomeData(1.108F, 30.0F, 40F, 15F);
    private static final BiomeData MESA = new BiomeData(1.309F, 20.0F, 40F, 15F);
    private static final BiomeData DESERT = new BiomeData(1.354F, 20.0F, 40F, 20F);
    private static final BiomeData NONE = new BiomeData(0.15F, 40.0F, 40F, 0F);
    private static final BiomeData THEEND = new BiomeData(0.551F, 40.0F, 40F, 0F);
    private static final BiomeData NETHER = new BiomeData(1.666F, 20.0F, 40F, 0F);

    static {
        BIOMES.put(BiomeCategory.Type.DEEP_COLD_OCEAN, DEEP_COLD_OCEAN);
        BIOMES.put(BiomeCategory.Type.COLD_DESERT, COLD_DESERT);
        BIOMES.put(BiomeCategory.Type.COLD_FOREST, COLD_FOREST);
        BIOMES.put(BiomeCategory.Type.COLD_OCEAN, COLD_OCEAN);
        BIOMES.put(BiomeCategory.Type.ICY, ICY);
        BIOMES.put(BiomeCategory.Type.OCEAN, OCEAN);
        BIOMES.put(BiomeCategory.Type.WARM_OCEAN, WARM_OCEAN);
        BIOMES.put(BiomeCategory.Type.LUKEWARM_OCEAN, LUKEWARM_OCEAN);
        BIOMES.put(BiomeCategory.Type.DEEP_LUKEWARM_OCEAN, DEEP_LUKEWARM_OCEAN);
        BIOMES.put(BiomeCategory.Type.BOG, BOG);
        BIOMES.put(BiomeCategory.Type.RIVER, RIVER);
        BIOMES.put(BiomeCategory.Type.TAIGA, TAIGA);
        BIOMES.put(BiomeCategory.Type.EXTREME_HILLS, EXTREME_HILLS);
        BIOMES.put(BiomeCategory.Type.MOUNTAIN, MOUNTAIN);
        BIOMES.put(BiomeCategory.Type.BEACH, BEACH);
        BIOMES.put(BiomeCategory.Type.FOREST, FOREST);
        BIOMES.put(BiomeCategory.Type.SWAMP, SWAMP);
        BIOMES.put(BiomeCategory.Type.UNDERGROUND, UNDERGROUND);
        BIOMES.put(BiomeCategory.Type.MUSHROOM, MUSHROOM);
        BIOMES.put(BiomeCategory.Type.PLAINS, PLAINS);
        BIOMES.put(BiomeCategory.Type.LUSH_DESERT, LUSH_DESERT);
        BIOMES.put(BiomeCategory.Type.DRYLAND, DRYLAND);
        BIOMES.put(BiomeCategory.Type.RAINFOREST, RAINFOREST);
        BIOMES.put(BiomeCategory.Type.JUNGLE, JUNGLE);
        BIOMES.put(BiomeCategory.Type.VOLCANIC, VOLCANIC);
        BIOMES.put(BiomeCategory.Type.SAVANNA, SAVANNA);
        BIOMES.put(BiomeCategory.Type.MESA, MESA);
        BIOMES.put(BiomeCategory.Type.DESERT, DESERT);
        BIOMES.put(BiomeCategory.Type.NONE, NONE);
        BIOMES.put(BiomeCategory.Type.THEEND, THEEND);
        BIOMES.put(BiomeCategory.Type.NETHER, NETHER);
    }

    public static BiomeData getDataForBiome(Holder<Biome> biome) {
        BiomeCategory.Type biomeCategory = BiomeCategoryManager.getBiomeCategory(biome);

        if (biomeCategory == BiomeCategory.Type.MISSING) {
            // Set some defaults for MISSING
            return new BiomeData(biome.get().getBaseTemperature(), 40.0F, 40F, 10F);
        }
        else {
            return BiomeRegistry.BIOMES.get(biomeCategory);
        }
    }

    public static void init() {
    }

}
