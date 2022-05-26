package homeostatic.common.biome;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.level.biome.Biome;

public class BiomeRegistry {

    public static final Map<Biome.BiomeCategory, BiomeData> BIOMES = new HashMap<>();
    private static final BiomeData ICY = new BiomeData(BiomeData.ICY, 20.0F, 20F, 5F);
    private static final BiomeData TAIGA = new BiomeData(0.507F, 50.0F, 40F, 10F);
    private static final BiomeData OCEAN = new BiomeData(0.551F, 70.0F, 40F, 10F);
    private static final BiomeData RIVER = new BiomeData(0.551F, 70.0F, 40F, 10F);
    private static final BiomeData EXTREME_HILLS = new BiomeData(0.618F, 50.0F, 40F, 10F);
    private static final BiomeData MOUNTAIN = new BiomeData(0.618F, 50.0F, 40F, 10F);
    private static final BiomeData BEACH = new BiomeData(0.663F, 70.0F, 40F, 10F);
    private static final BiomeData FOREST = new BiomeData(0.663F, 50.0F, 40F, 12F);
    private static final BiomeData SWAMP = new BiomeData(0.663F, 90.0F, 40F, 12F);
    public static final BiomeData UNDERGROUND = new BiomeData(0.663F, 40.0F, 40F, 12F);
    private static final BiomeData MUSHROOM = new BiomeData(0.685F, 70.0F, 40F, 12F);
    private static final BiomeData PLAINS = new BiomeData(0.774F, 60.0F, 40F, 15F);
    private static final BiomeData JUNGLE = new BiomeData(0.997F, 90.0F, 40F, 15F);
    private static final BiomeData SAVANNA = new BiomeData(1.108F, 30.0F, 40F, 15F);
    private static final BiomeData MESA = new BiomeData(1.309F, 20.0F, 40F, 15F);
    private static final BiomeData DESERT = new BiomeData(1.354F, 20.0F, 40F, 20F);
    private static final BiomeData NONE = new BiomeData(1.666F, 40.0F, 40F, 0F);
    private static final BiomeData THEEND = new BiomeData(0.15F, 40.0F, 40F, 0F);
    private static final BiomeData NETHER = new BiomeData(0.551F, 20.0F, 40F, 0F);

    static {
        BIOMES.put(Biome.BiomeCategory.ICY, ICY);
        BIOMES.put(Biome.BiomeCategory.OCEAN, OCEAN);
        BIOMES.put(Biome.BiomeCategory.RIVER, RIVER);
        BIOMES.put(Biome.BiomeCategory.TAIGA, TAIGA);
        BIOMES.put(Biome.BiomeCategory.EXTREME_HILLS, EXTREME_HILLS);
        BIOMES.put(Biome.BiomeCategory.MOUNTAIN, MOUNTAIN);
        BIOMES.put(Biome.BiomeCategory.BEACH, BEACH);
        BIOMES.put(Biome.BiomeCategory.FOREST, FOREST);
        BIOMES.put(Biome.BiomeCategory.SWAMP, SWAMP);
        BIOMES.put(Biome.BiomeCategory.UNDERGROUND, UNDERGROUND);
        BIOMES.put(Biome.BiomeCategory.MUSHROOM, MUSHROOM);
        BIOMES.put(Biome.BiomeCategory.PLAINS, PLAINS);
        BIOMES.put(Biome.BiomeCategory.JUNGLE, JUNGLE);
        BIOMES.put(Biome.BiomeCategory.SAVANNA, SAVANNA);
        BIOMES.put(Biome.BiomeCategory.MESA, MESA);
        BIOMES.put(Biome.BiomeCategory.DESERT, DESERT);
        BIOMES.put(Biome.BiomeCategory.NONE, NONE);
        BIOMES.put(Biome.BiomeCategory.THEEND, THEEND);
        BIOMES.put(Biome.BiomeCategory.NETHER, NETHER);
    }
}
