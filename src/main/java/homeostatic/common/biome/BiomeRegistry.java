package homeostatic.common.biome;

import biomesoplenty.api.biome.BOPBiomes;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;

import net.minecraftforge.fml.ModList;

import potionstudios.byg.common.world.biome.BYGBiomes;

import homeostatic.Homeostatic;

public class BiomeRegistry {

    public static final Map<BiomeCategory, BiomeData> BIOMES = new HashMap<>();
    private static final Map<ResourceKey<Biome>, BiomeCategory> BIOME_CATEGORY = new HashMap<>();

    private static final BiomeData BOG = new BiomeData(0.351F, 60.0F, 40F, 10F);
    private static final BiomeData DEEP_COLD_OCEAN = new BiomeData(0.373F, 20.0F, 20F, 5F);
    private static final BiomeData COLD_FOREST = new BiomeData(0.373F, 60.0F, 40F, 12F);
    private static final BiomeData COLD_DESERT = new BiomeData(0.395F, 20.0F, 40F, 20F);
    private static final BiomeData COLD_OCEAN = new BiomeData(0.440F, 20.0F, 20F, 5F);
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
        BIOMES.put(BiomeCategory.DEEP_COLD_OCEAN, DEEP_COLD_OCEAN);
        BIOMES.put(BiomeCategory.COLD_DESERT, COLD_DESERT);
        BIOMES.put(BiomeCategory.COLD_FOREST, COLD_FOREST);
        BIOMES.put(BiomeCategory.COLD_OCEAN, COLD_OCEAN);
        BIOMES.put(BiomeCategory.ICY, ICY);
        BIOMES.put(BiomeCategory.OCEAN, OCEAN);
        BIOMES.put(BiomeCategory.WARM_OCEAN, WARM_OCEAN);
        BIOMES.put(BiomeCategory.LUKEWARM_OCEAN, LUKEWARM_OCEAN);
        BIOMES.put(BiomeCategory.DEEP_LUKEWARM_OCEAN, DEEP_LUKEWARM_OCEAN);
        BIOMES.put(BiomeCategory.BOG, BOG);
        BIOMES.put(BiomeCategory.RIVER, RIVER);
        BIOMES.put(BiomeCategory.TAIGA, TAIGA);
        BIOMES.put(BiomeCategory.EXTREME_HILLS, EXTREME_HILLS);
        BIOMES.put(BiomeCategory.MOUNTAIN, MOUNTAIN);
        BIOMES.put(BiomeCategory.BEACH, BEACH);
        BIOMES.put(BiomeCategory.FOREST, FOREST);
        BIOMES.put(BiomeCategory.SWAMP, SWAMP);
        BIOMES.put(BiomeCategory.UNDERGROUND, UNDERGROUND);
        BIOMES.put(BiomeCategory.MUSHROOM, MUSHROOM);
        BIOMES.put(BiomeCategory.PLAINS, PLAINS);
        BIOMES.put(BiomeCategory.LUSH_DESERT, LUSH_DESERT);
        BIOMES.put(BiomeCategory.DRYLAND, DRYLAND);
        BIOMES.put(BiomeCategory.RAINFOREST, RAINFOREST);
        BIOMES.put(BiomeCategory.JUNGLE, JUNGLE);
        BIOMES.put(BiomeCategory.VOLCANIC, VOLCANIC);
        BIOMES.put(BiomeCategory.SAVANNA, SAVANNA);
        BIOMES.put(BiomeCategory.MESA, MESA);
        BIOMES.put(BiomeCategory.DESERT, DESERT);
        BIOMES.put(BiomeCategory.NONE, NONE);
        BIOMES.put(BiomeCategory.THEEND, THEEND);
        BIOMES.put(BiomeCategory.NETHER, NETHER);
    }

    public enum BiomeCategory {
        ICY,
        OCEAN,
        WARM_OCEAN,
        LUKEWARM_OCEAN,
        DEEP_LUKEWARM_OCEAN,
        COLD_OCEAN,
        DEEP_COLD_OCEAN,
        COLD_DESERT,
        COLD_FOREST,
        BOG,
        RIVER,
        TAIGA,
        EXTREME_HILLS,
        MOUNTAIN,
        BEACH,
        FOREST,
        SWAMP,
        UNDERGROUND,
        MUSHROOM,
        PLAINS,
        LUSH_DESERT,
        DRYLAND,
        RAINFOREST,
        JUNGLE,
        VOLCANIC,
        SAVANNA,
        MESA,
        DESERT,
        NONE,
        THEEND,
        NETHER,
        MISSING
    }

    public static BiomeData getDataForBiome(Holder<Biome> biome) {
        BiomeCategory biomeCategory = getBiomeCategory(biome);

        if (biomeCategory == BiomeCategory.MISSING) {
            // Set some defaults for MISSING
            return new BiomeData(biome.get().getBaseTemperature(), 40.0F, 40F, 10F);
        }
        else {
            return BiomeRegistry.BIOMES.get(biomeCategory);
        }
    }

    public static void init() {
        BIOME_CATEGORY.put(Biomes.THE_VOID, BiomeCategory.NONE);
        BIOME_CATEGORY.put(Biomes.PLAINS, BiomeCategory.PLAINS);
        BIOME_CATEGORY.put(Biomes.SUNFLOWER_PLAINS, BiomeCategory.PLAINS);
        BIOME_CATEGORY.put(Biomes.SNOWY_PLAINS, BiomeCategory.ICY);
        BIOME_CATEGORY.put(Biomes.ICE_SPIKES, BiomeCategory.ICY);
        BIOME_CATEGORY.put(Biomes.DESERT, BiomeCategory.DESERT);
        BIOME_CATEGORY.put(Biomes.SWAMP, BiomeCategory.SWAMP);
        BIOME_CATEGORY.put(Biomes.MANGROVE_SWAMP, BiomeCategory.SWAMP);
        BIOME_CATEGORY.put(Biomes.GROVE, BiomeCategory.FOREST);
        BIOME_CATEGORY.put(Biomes.BIRCH_FOREST, BiomeCategory.FOREST);
        BIOME_CATEGORY.put(Biomes.OLD_GROWTH_BIRCH_FOREST, BiomeCategory.FOREST);
        BIOME_CATEGORY.put(Biomes.DARK_FOREST, BiomeCategory.FOREST);
        BIOME_CATEGORY.put(Biomes.FOREST, BiomeCategory.FOREST);
        BIOME_CATEGORY.put(Biomes.FLOWER_FOREST, BiomeCategory.FOREST);
        BIOME_CATEGORY.put(Biomes.OLD_GROWTH_PINE_TAIGA, BiomeCategory.TAIGA);
        BIOME_CATEGORY.put(Biomes.TAIGA, BiomeCategory.TAIGA);
        BIOME_CATEGORY.put(Biomes.SNOWY_TAIGA, BiomeCategory.TAIGA);
        BIOME_CATEGORY.put(Biomes.OLD_GROWTH_SPRUCE_TAIGA, BiomeCategory.TAIGA);
        BIOME_CATEGORY.put(Biomes.SAVANNA, BiomeCategory.SAVANNA);
        BIOME_CATEGORY.put(Biomes.SAVANNA_PLATEAU, BiomeCategory.SAVANNA);
        BIOME_CATEGORY.put(Biomes.WINDSWEPT_SAVANNA, BiomeCategory.SAVANNA);
        BIOME_CATEGORY.put(Biomes.WINDSWEPT_HILLS, BiomeCategory.EXTREME_HILLS);
        BIOME_CATEGORY.put(Biomes.WINDSWEPT_GRAVELLY_HILLS, BiomeCategory.EXTREME_HILLS);
        BIOME_CATEGORY.put(Biomes.WINDSWEPT_FOREST, BiomeCategory.EXTREME_HILLS);
        BIOME_CATEGORY.put(Biomes.STONY_SHORE, BiomeCategory.EXTREME_HILLS);
        BIOME_CATEGORY.put(Biomes.JUNGLE, BiomeCategory.JUNGLE);
        BIOME_CATEGORY.put(Biomes.SPARSE_JUNGLE, BiomeCategory.JUNGLE);
        BIOME_CATEGORY.put(Biomes.BAMBOO_JUNGLE, BiomeCategory.JUNGLE);
        BIOME_CATEGORY.put(Biomes.BADLANDS, BiomeCategory.MESA);
        BIOME_CATEGORY.put(Biomes.ERODED_BADLANDS, BiomeCategory.MESA);
        BIOME_CATEGORY.put(Biomes.WOODED_BADLANDS, BiomeCategory.MESA);
        BIOME_CATEGORY.put(Biomes.SNOWY_SLOPES, BiomeCategory.MOUNTAIN);
        BIOME_CATEGORY.put(Biomes.JAGGED_PEAKS, BiomeCategory.MOUNTAIN);
        BIOME_CATEGORY.put(Biomes.FROZEN_PEAKS, BiomeCategory.MOUNTAIN);
        BIOME_CATEGORY.put(Biomes.MEADOW, BiomeCategory.MOUNTAIN);
        BIOME_CATEGORY.put(Biomes.STONY_PEAKS, BiomeCategory.MOUNTAIN);
        BIOME_CATEGORY.put(Biomes.RIVER, BiomeCategory.RIVER);
        BIOME_CATEGORY.put(Biomes.FROZEN_RIVER, BiomeCategory.RIVER);
        BIOME_CATEGORY.put(Biomes.BEACH, BiomeCategory.BEACH);
        BIOME_CATEGORY.put(Biomes.SNOWY_BEACH, BiomeCategory.BEACH);
        BIOME_CATEGORY.put(Biomes.WARM_OCEAN, BiomeCategory.WARM_OCEAN);
        BIOME_CATEGORY.put(Biomes.LUKEWARM_OCEAN, BiomeCategory.LUKEWARM_OCEAN);
        BIOME_CATEGORY.put(Biomes.DEEP_LUKEWARM_OCEAN, BiomeCategory.DEEP_LUKEWARM_OCEAN);
        BIOME_CATEGORY.put(Biomes.DEEP_FROZEN_OCEAN, BiomeCategory.OCEAN);
        BIOME_CATEGORY.put(Biomes.FROZEN_OCEAN, BiomeCategory.OCEAN);
        BIOME_CATEGORY.put(Biomes.OCEAN, BiomeCategory.OCEAN);
        BIOME_CATEGORY.put(Biomes.DEEP_OCEAN, BiomeCategory.OCEAN);
        BIOME_CATEGORY.put(Biomes.COLD_OCEAN, BiomeCategory.COLD_OCEAN);
        BIOME_CATEGORY.put(Biomes.DEEP_COLD_OCEAN, BiomeCategory.DEEP_COLD_OCEAN);
        BIOME_CATEGORY.put(Biomes.MUSHROOM_FIELDS, BiomeCategory.MUSHROOM);
        BIOME_CATEGORY.put(Biomes.DRIPSTONE_CAVES, BiomeCategory.UNDERGROUND);
        BIOME_CATEGORY.put(Biomes.DEEP_DARK, BiomeCategory.UNDERGROUND);
        BIOME_CATEGORY.put(Biomes.LUSH_CAVES, BiomeCategory.UNDERGROUND);
        BIOME_CATEGORY.put(Biomes.NETHER_WASTES, BiomeCategory.NETHER);
        BIOME_CATEGORY.put(Biomes.WARPED_FOREST, BiomeCategory.NETHER);
        BIOME_CATEGORY.put(Biomes.CRIMSON_FOREST, BiomeCategory.NETHER);
        BIOME_CATEGORY.put(Biomes.SOUL_SAND_VALLEY, BiomeCategory.NETHER);
        BIOME_CATEGORY.put(Biomes.BASALT_DELTAS, BiomeCategory.NETHER);
        BIOME_CATEGORY.put(Biomes.THE_END, BiomeCategory.THEEND);
        BIOME_CATEGORY.put(Biomes.END_HIGHLANDS, BiomeCategory.THEEND);
        BIOME_CATEGORY.put(Biomes.END_MIDLANDS, BiomeCategory.THEEND);
        BIOME_CATEGORY.put(Biomes.SMALL_END_ISLANDS, BiomeCategory.THEEND);
        BIOME_CATEGORY.put(Biomes.END_BARRENS, BiomeCategory.THEEND);

        // BOP
        if (ModList.get().isLoaded("biomesoplenty")) {
            BIOME_CATEGORY.put(BOPBiomes.SEASONAL_FOREST, BiomeCategory.COLD_FOREST);
            BIOME_CATEGORY.put(BOPBiomes.PUMPKIN_PATCH, BiomeCategory.COLD_FOREST);
            BIOME_CATEGORY.put(BOPBiomes.BOREAL_FOREST, BiomeCategory.COLD_FOREST);
            BIOME_CATEGORY.put(BOPBiomes.MARSH, BiomeCategory.SWAMP);
            BIOME_CATEGORY.put(BOPBiomes.BAYOU, BiomeCategory.SWAMP);
            BIOME_CATEGORY.put(BOPBiomes.FUNGAL_JUNGLE, BiomeCategory.SWAMP);
            BIOME_CATEGORY.put(BOPBiomes.RAINBOW_HILLS, BiomeCategory.MOUNTAIN);
            BIOME_CATEGORY.put(BOPBiomes.SNOWY_CONIFEROUS_FOREST, BiomeCategory.MOUNTAIN);
            BIOME_CATEGORY.put(BOPBiomes.SNOWY_FIR_CLEARING, BiomeCategory.MOUNTAIN);
            BIOME_CATEGORY.put(BOPBiomes.SNOWY_MAPLE_WOODS, BiomeCategory.MOUNTAIN);
            BIOME_CATEGORY.put(BOPBiomes.FLOODPLAIN, BiomeCategory.RAINFOREST);
            BIOME_CATEGORY.put(BOPBiomes.ROCKY_RAINFOREST, BiomeCategory.RAINFOREST);
            BIOME_CATEGORY.put(BOPBiomes.RAINFOREST, BiomeCategory.RAINFOREST);
            BIOME_CATEGORY.put(BOPBiomes.WETLAND, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.GRASSLAND, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.CLOVER_PATCH, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.MUSKEG, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.SHRUBLAND, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.ROCKY_SHRUBLAND, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.CHERRY_BLOSSOM_GROVE, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.BAMBOO_GROVE, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.FIELD, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.FORESTED_FIELD, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.LAVENDER_FIELD, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.LAVENDER_FOREST, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.ORCHARD, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.PASTURE, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.PRAIRIE, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BOPBiomes.FIR_CLEARING, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.CONIFEROUS_FOREST, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.OMINOUS_WOODS, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.ORIGIN_VALLEY, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.HIGHLAND_MOOR, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.HIGHLAND, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.CRAG, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.JADE_CLIFFS, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.MAPLE_WOODS, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.MYSTIC_GROVE, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.OLD_GROWTH_WOODLAND, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.REDWOOD_FOREST, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.WOODLAND, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BOPBiomes.DRYLAND, BiomeCategory.DRYLAND);
            BIOME_CATEGORY.put(BOPBiomes.COLD_DESERT, BiomeCategory.COLD_DESERT);
            BIOME_CATEGORY.put(BOPBiomes.DUNE_BEACH, BiomeCategory.BEACH);
            BIOME_CATEGORY.put(BOPBiomes.SCRUBLAND, BiomeCategory.SAVANNA);
            BIOME_CATEGORY.put(BOPBiomes.WOODED_SCRUBLAND, BiomeCategory.SAVANNA);
            BIOME_CATEGORY.put(BOPBiomes.BOG, BiomeCategory.BOG);
            BIOME_CATEGORY.put(BOPBiomes.TUNDRA, BiomeCategory.BOG);
            BIOME_CATEGORY.put(BOPBiomes.CRYSTALLINE_CHASM, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BOPBiomes.ERUPTING_INFERNO, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BOPBiomes.UNDERGROWTH, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BOPBiomes.VISCERAL_HEAP, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BOPBiomes.WITHERED_ABYSS, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BOPBiomes.DEAD_FOREST, BiomeCategory.EXTREME_HILLS);
            BIOME_CATEGORY.put(BOPBiomes.GLOWING_GROTTO, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BOPBiomes.SPIDER_NEST, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BOPBiomes.VOLCANO, BiomeCategory.VOLCANIC);
            BIOME_CATEGORY.put(BOPBiomes.VOLCANIC_PLAINS, BiomeCategory.VOLCANIC);
            BIOME_CATEGORY.put(BOPBiomes.OLD_GROWTH_DEAD_FOREST, BiomeCategory.TAIGA);
            BIOME_CATEGORY.put(BOPBiomes.TROPICS, BiomeCategory.JUNGLE);
            BIOME_CATEGORY.put(BOPBiomes.LUSH_DESERT, BiomeCategory.LUSH_DESERT);
            BIOME_CATEGORY.put(BOPBiomes.LUSH_SAVANNA, BiomeCategory.LUSH_DESERT);
            BIOME_CATEGORY.put(BOPBiomes.MEDITERRANEAN_FOREST, BiomeCategory.LUSH_DESERT);
            BIOME_CATEGORY.put(BOPBiomes.WASTELAND, BiomeCategory.MESA); // Set as Nether temp
            BIOME_CATEGORY.put(BOPBiomes.WOODED_WASTELAND, BiomeCategory.MESA); // Set as Nether temp
        }

        // Oh The Biomes You'll Go
        if (ModList.get().isLoaded("byg")) {
            BIOME_CATEGORY.put(BYGBiomes.ALLIUM_FIELDS, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BYGBiomes.AMARANTH_FIELDS, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BYGBiomes.ARAUCARIA_SAVANNA, BiomeCategory.SAVANNA);
            BIOME_CATEGORY.put(BYGBiomes.ASPEN_FOREST, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.ATACAMA_DESERT, BiomeCategory.DESERT);
            BIOME_CATEGORY.put(BYGBiomes.AUTUMNAL_VALLEY, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BYGBiomes.BAOBAB_SAVANNA, BiomeCategory.SAVANNA);
            BIOME_CATEGORY.put(BYGBiomes.BAYOU, BiomeCategory.SWAMP);
            BIOME_CATEGORY.put(BYGBiomes.BLACK_FOREST, BiomeCategory.TAIGA);
            BIOME_CATEGORY.put(BYGBiomes.BOREALIS_GROVE, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.CANADIAN_SHIELD, BiomeCategory.TAIGA);
            BIOME_CATEGORY.put(BYGBiomes.CHERRY_BLOSSOM_FOREST, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.CIKA_WOODS, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.CONIFEROUS_FOREST, BiomeCategory.TAIGA);
            BIOME_CATEGORY.put(BYGBiomes.CRAG_GARDENS, BiomeCategory.JUNGLE);
            BIOME_CATEGORY.put(BYGBiomes.CYPRESS_SWAMPLANDS, BiomeCategory.BOG);
            BIOME_CATEGORY.put(BYGBiomes.LUSH_STACKS, BiomeCategory.WARM_OCEAN);
            BIOME_CATEGORY.put(BYGBiomes.DEAD_SEA, BiomeCategory.VOLCANIC);
            BIOME_CATEGORY.put(BYGBiomes.DACITE_RIDGES, BiomeCategory.TAIGA);
            BIOME_CATEGORY.put(BYGBiomes.WINDSWEPT_DESERT, BiomeCategory.DESERT);
            BIOME_CATEGORY.put(BYGBiomes.EBONY_WOODS, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.FORGOTTEN_FOREST, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.GROVE, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.GUIANA_SHIELD, BiomeCategory.JUNGLE);
            BIOME_CATEGORY.put(BYGBiomes.HOWLING_PEAKS, BiomeCategory.MOUNTAIN);
            BIOME_CATEGORY.put(BYGBiomes.JACARANDA_FOREST, BiomeCategory.RAINFOREST);
            BIOME_CATEGORY.put(BYGBiomes.MAPLE_TAIGA, BiomeCategory.TAIGA);
            BIOME_CATEGORY.put(BYGBiomes.COCONINO_MEADOW, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BYGBiomes.MOJAVE_DESERT, BiomeCategory.DESERT);
            BIOME_CATEGORY.put(BYGBiomes.CARDINAL_TUNDRA, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BYGBiomes.ORCHARD, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.PRAIRIE, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BYGBiomes.RED_OAK_FOREST, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.RED_ROCK_VALLEY, BiomeCategory.MESA);
            BIOME_CATEGORY.put(BYGBiomes.ROSE_FIELDS, BiomeCategory.PLAINS);
            BIOME_CATEGORY.put(BYGBiomes.AUTUMNAL_FOREST, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.AUTUMNAL_TAIGA, BiomeCategory.TAIGA);
            BIOME_CATEGORY.put(BYGBiomes.SHATTERED_GLACIER, BiomeCategory.ICY);
            BIOME_CATEGORY.put(BYGBiomes.FIRECRACKER_SHRUBLAND, BiomeCategory.SAVANNA);
            BIOME_CATEGORY.put(BYGBiomes.SIERRA_BADLANDS, BiomeCategory.MESA);
            BIOME_CATEGORY.put(BYGBiomes.SKYRIS_VALE, BiomeCategory.BOG);
            BIOME_CATEGORY.put(BYGBiomes.REDWOOD_THICKET, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.FROSTED_TAIGA, BiomeCategory.TAIGA);
            BIOME_CATEGORY.put(BYGBiomes.FROSTED_CONIFEROUS_FOREST, BiomeCategory.TAIGA);
            BIOME_CATEGORY.put(BYGBiomes.FRAGMENT_FOREST, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.TROPICAL_RAINFOREST, BiomeCategory.RAINFOREST);
            BIOME_CATEGORY.put(BYGBiomes.TWILIGHT_MEADOW, BiomeCategory.BOG);
            BIOME_CATEGORY.put(BYGBiomes.WEEPING_WITCH_FOREST, BiomeCategory.BOG);
            BIOME_CATEGORY.put(BYGBiomes.WHITE_MANGROVE_MARSHES, BiomeCategory.SWAMP);
            BIOME_CATEGORY.put(BYGBiomes.TEMPERATE_RAINFOREST, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.ZELKOVA_FOREST, BiomeCategory.FOREST);
            BIOME_CATEGORY.put(BYGBiomes.RAINBOW_BEACH, BiomeCategory.BEACH);
            BIOME_CATEGORY.put(BYGBiomes.BASALT_BARRERA, BiomeCategory.BEACH);
            BIOME_CATEGORY.put(BYGBiomes.DACITE_SHORE, BiomeCategory.BOG);
            BIOME_CATEGORY.put(BYGBiomes.BRIMSTONE_CAVERNS, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.CRIMSON_GARDENS, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.EMBUR_BOG, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.GLOWSTONE_GARDENS, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.MAGMA_WASTES, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.SUBZERO_HYPOGEAL, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.SYTHIAN_TORRIDS, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.WARPED_DESERT, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.WAILING_GARTH, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.ARISIAN_UNDERGROWTH, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.WEEPING_MIRE, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.QUARTZ_DESERT, BiomeCategory.NETHER);
            BIOME_CATEGORY.put(BYGBiomes.IVIS_FIELDS, BiomeCategory.THEEND);
            BIOME_CATEGORY.put(BYGBiomes.NIGHTSHADE_FOREST, BiomeCategory.THEEND);
            BIOME_CATEGORY.put(BYGBiomes.ETHEREAL_ISLANDS, BiomeCategory.THEEND);
            BIOME_CATEGORY.put(BYGBiomes.VISCAL_ISLES, BiomeCategory.THEEND);
            BIOME_CATEGORY.put(BYGBiomes.BULBIS_GARDENS, BiomeCategory.THEEND);
            BIOME_CATEGORY.put(BYGBiomes.SHULKREN_FOREST, BiomeCategory.THEEND);
            BIOME_CATEGORY.put(BYGBiomes.CRYPTIC_WASTES, BiomeCategory.THEEND);
            BIOME_CATEGORY.put(BYGBiomes.IMPARIUS_GROVE, BiomeCategory.THEEND);
        }
    }

    public static BiomeCategory getBiomeCategory(Holder<Biome> biome) {
        try {
            Optional<ResourceKey<Biome>> key = biome.unwrapKey();

            if (key.isPresent()) {
                return BIOME_CATEGORY.getOrDefault(key.get(), BiomeCategory.MISSING);
            }
        } catch (NoSuchElementException e) {
            Homeostatic.LOGGER.debug("Unable to find biome for: %s", biome.toString());
        }

        return BiomeCategory.MISSING;
    }

    public static BiomeCategory getBiomeCategory(BiomeGenerationSettings generationSettings, float temperature, MobSpawnSettings mobSpawnSettings, Biome.TemperatureModifier temperatureModifier, float downfall, Biome.Precipitation precipitation, BiomeSpecialEffects specialEffects) {
        BiomeCategory biomeCategory = BiomeCategory.MISSING;

        if (temperature == -0.2F) {
            // GROVE
            biomeCategory = BiomeCategory.FOREST;
        }
        else if (temperature == -0.25F) {
            // MUSKEG
            if (downfall == 0.6F && precipitation == Biome.Precipitation.SNOW) {
                biomeCategory = BiomeCategory.PLAINS;
            } else {
                // RAINBOW_HILLS SNOWY_CONIFEROUS_FOREST SNOWY_FIR_CLEARING SNOWY_MAPLE_WOODS
                biomeCategory = BiomeCategory.MOUNTAIN;
            }
        }
        else if (temperature == -0.3F) {
            // SNOWY_SLOPES
            biomeCategory = BiomeCategory.MOUNTAIN;
        }
        else if (temperature == -0.5F) {
            if (downfall == 0.9F) {
                // SHATTERED_GLACIER
                biomeCategory = BiomeCategory.ICY;
            }
            else {
                // SNOWY_TAIGA FROSTED_TAIGA FROSTED_CONIFEROUS_FOREST
                biomeCategory = BiomeCategory.TAIGA;
            }
        }
        else if (temperature == -0.7F) {
            // JAGGED_PEAKS FROZEN_PEAKS
            biomeCategory = BiomeCategory.MOUNTAIN;
        }
        else if (temperature == 0.05F) {
            // SNOWY_BEACH
            biomeCategory = BiomeCategory.BEACH;
        }
        else if (temperature == 0.0F) {
            if (generationSettings.hasFeature(MiscOverworldPlacements.ICE_SPIKE.get())
                    || generationSettings.hasFeature(VegetationPlacements.TREES_SNOWY.get())) {
                // ICE_SPIKES SNOWY_PLAINS
                biomeCategory = BiomeCategory.ICY;
            }
            else if (temperatureModifier == Biome.TemperatureModifier.FROZEN) {
                // DEEP_FROZEN_OCEAN
                biomeCategory = BiomeCategory.OCEAN;
            }
            else {
                // FROZEN_RIVER
                biomeCategory = BiomeCategory.RIVER;
            }
        }
        else if (temperature == 0.15F) {
            if (downfall == 0.5F) {
                // HOWLING_PEAKS
                biomeCategory = BiomeCategory.MOUNTAIN;
            }
        }
        else if (temperature == 0.2F) {
            if (downfall == 0.5F) {
                // BOG TUNDRA
                biomeCategory = BiomeCategory.BOG;
            }
            else {
                // WINDSWEPT_HILLS WINDSWEPT_GRAVELLY_HILLS WINDSWEPT_FOREST STONY_SHORE DEAD_FOREST
                biomeCategory = BiomeCategory.EXTREME_HILLS;
            }
        }
        else if (temperature == 0.25F) {
            if (generationSettings.hasFeature(VegetationPlacements.PATCH_GRASS_TAIGA_2.get())
                    || generationSettings.hasFeature(VegetationPlacements.PATCH_GRASS_TAIGA.get())) {
                // TAIGA BLACK_FOREST MAPLE_TAIGA CANADIAN_SHIELD CONIFEROUS_FOREST DACITE_RIDGES AUTUMNAL_TAIGA
                biomeCategory = BiomeCategory.TAIGA;
            }
            else if (generationSettings.hasFeature(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA.get())) {
                // OLD_GROWTH_SPRUCE_TAIGA
                biomeCategory = BiomeCategory.TAIGA;
            }
            else if (mobSpawnSettings.getEntityTypes().contains(EntityType.PARROT)) {
                // FRAGMENT_FOREST
                biomeCategory = BiomeCategory.FOREST;
            }
            else if (specialEffects.getGrassColorOverride().isPresent() && specialEffects.getGrassColorOverride().get() == 9470285) {
                // ZELKOVA_FOREST
                biomeCategory = BiomeCategory.FOREST;
            }
            else if (downfall == 0.5F || downfall == 0.9F) {
                // MAPLE_WOODS AUTUMNAL_FOREST BOREALIS_GROVE
                biomeCategory = BiomeCategory.FOREST;
            }
            else if (downfall == 0.0F) {
                // COLD_DESERT
                biomeCategory = BiomeCategory.COLD_DESERT;
            }
            else if (downfall == 0.8F) {
                // CYPRESS_SWAMPLANDS SKYRIS_VALE TWILIGHT_MEADOW WEEPING_WITCH_FOREST DACITE_SHORE
                biomeCategory = BiomeCategory.BOG;

            }
        }
        else if (temperature == 0.3F) {
            // OLD_GROWTH_PINE_TAIGA OLD_GROWTH_DEAD_FOREST
            biomeCategory = BiomeCategory.TAIGA;
        }
        else if (temperature == 0.35F) {
            if (downfall == 0.4F) {
                // CARDINAL_TUNDRA
                biomeCategory = BiomeCategory.PLAINS;
            }
            else if (downfall == 0.5F) {
                // CIKA_WOODS
                biomeCategory = BiomeCategory.FOREST;
            }
            else if (downfall == 0.8F) {
                // AUTUMNAL_VALLEY
                biomeCategory = BiomeCategory.PLAINS;
            }
        }
        else if (temperature == 0.4F) {
            if (downfall == 0.7F) {
                // FIELD FORESTED_FIELD
                biomeCategory = BiomeCategory.PLAINS;
            }
            else {
                // SEASONAL_FOREST PUMPKIN_PATCH BOREAL_FOREST
                biomeCategory = BiomeCategory.COLD_FOREST;
            }
        }
        else if (temperature == 0.45F) {
            // CONIFEROUS_FOREST FIR_CLEARING
            biomeCategory = BiomeCategory.FOREST;
        }
        else if (temperature == 0.5F) {
            if (temperatureModifier == Biome.TemperatureModifier.FROZEN
                    || generationSettings.hasFeature(AquaticPlacements.SEAGRASS_NORMAL.get())
                    || generationSettings.hasFeature(AquaticPlacements.SEAGRASS_DEEP.get())) {
                // FROZEN_OCEAN OCEAN DEEP_OCEAN
                biomeCategory = BiomeCategory.OCEAN;
            }
            else if (generationSettings.hasFeature(AquaticPlacements.SEA_PICKLE.get())) {
                // WARM_OCEAN
                biomeCategory = BiomeCategory.WARM_OCEAN;
            }
            else if (generationSettings.hasFeature(AquaticPlacements.SEAGRASS_WARM.get())) {
                // LUKEWARM_OCEAN
                biomeCategory = BiomeCategory.LUKEWARM_OCEAN;
            }
            else if (generationSettings.hasFeature(AquaticPlacements.SEAGRASS_DEEP_WARM.get())) {
                // DEEP_LUKEWARM_OCEAN
                biomeCategory = BiomeCategory.DEEP_LUKEWARM_OCEAN;
            }
            else if (generationSettings.hasFeature(AquaticPlacements.SEAGRASS_COLD.get())) {
                // COLD_OCEAN
                biomeCategory = BiomeCategory.COLD_OCEAN;
            }
            else if (generationSettings.hasFeature(AquaticPlacements.SEAGRASS_DEEP_COLD.get())) {
                // DEEP_COLD_OCEAN
                biomeCategory = BiomeCategory.DEEP_COLD_OCEAN;
            }
            else if (generationSettings.hasFeature(MiscOverworldPlacements.VOID_START_PLATFORM.get())) {
                // THE_VOID
                biomeCategory = BiomeCategory.NONE;
            }
            else if (generationSettings.hasFeature(CavePlacements.CAVE_VINES.get())) {
                // LUSH_CAVES
                biomeCategory = BiomeCategory.UNDERGROUND;
            }
            else if (precipitation == Biome.Precipitation.RAIN) {
                if (downfall == 0.8F) {
                    // MEADOW
                    biomeCategory = BiomeCategory.MOUNTAIN;
                }
                else if (downfall == 0.5F) {
                    // RIVER SPIDER_NEST
                    biomeCategory = BiomeCategory.RIVER;
                }
            }
            else if (precipitation == Biome.Precipitation.NONE) {
                // THEEND
                biomeCategory = BiomeCategory.THEEND;
            }
        }
        else if (temperature == 0.6F) {
            if (downfall == 0.7F) {
                // WETLAND GRASSLAND CLOVER_PATCH
                biomeCategory = BiomeCategory.PLAINS;
            }
            else if (downfall == 0.6F) {
                // OMINOUS_WOODS ORIGIN_VALLEY HIGHLAND_MOOR HIGHLAND CRAG
                // BIRCH_FOREST OLD_GROWTH_BIRCH_FOREST
                biomeCategory = BiomeCategory.FOREST;
            }
            else if (downfall == 0.05F) {
                // SHRUBLAND ROCKY_SHRUBLAND
                biomeCategory = BiomeCategory.PLAINS;
            }
            else if (downfall == 0.9F) {
                // CHERRY_BLOSSOM_GROVE BAMBOO_GROVE
                biomeCategory = BiomeCategory.PLAINS;
            }
        }
        else if (temperature == 0.65F) {
            if (downfall == 0.7F) {
                // MARSH
                biomeCategory = BiomeCategory.SWAMP;
            }
        }
        else if (temperature == 0.7F) {
            if (generationSettings.hasFeature(AquaticPlacements.SEAGRASS_SWAMP.get())) {
                // SWAMP
                biomeCategory = BiomeCategory.SWAMP;
            }
            else if (precipitation == Biome.Precipitation.RAIN) {
                if (downfall == 0.4F) {
                    // DUNE_BEACH
                    biomeCategory = BiomeCategory.BEACH;
                }
                else if (downfall == 0.6F) {
                    // ASPEN_FOREST
                    biomeCategory = BiomeCategory.FOREST;
                }
                else if (downfall == 0.8F) {
                    // DARK_FOREST FOREST FLOWER_FOREST MYSTIC_GROVE CHERRY_BLOSSOM_FOREST
                    biomeCategory = BiomeCategory.FOREST;
                }
            }
        }
        else if (temperature == 0.75F) {
            if (downfall == 0.8F) {
                // GROVE
                biomeCategory = BiomeCategory.FOREST;
            }
        }
        else if (temperature == 0.8F) {
            if (mobSpawnSettings.getEntityTypes().contains(EntityType.TURTLE)) {
                // BEACH
                biomeCategory = BiomeCategory.BEACH;
            }
            else if (generationSettings.hasFeature(AquaticPlacements.SEAGRASS_SWAMP.get())) {
                // MANGROVE_SWAMP BAYOU WHITE_MANGROVE_MARSHES
                biomeCategory = BiomeCategory.SWAMP;
            }
            else if (generationSettings.hasFeature(CavePlacements.LARGE_DRIPSTONE.get())
                    || generationSettings.hasFeature(CavePlacements.SCULK_VEIN.get())) {
                // DRIPSTONE_CAVES DEEP_DARK
                biomeCategory = BiomeCategory.UNDERGROUND;
            }
            else if (generationSettings.hasFeature(VegetationPlacements.PATCH_SUNFLOWER.get())){
                // PLAINS SUNFLOWER_PLAINS
                biomeCategory = BiomeCategory.PLAINS;
            }
            else if (specialEffects.getGrassColorOverride().isPresent() && specialEffects.getGrassColorOverride().get() == 6530407) {
                // COCONINO_MEADOW
                biomeCategory = BiomeCategory.PLAINS;
            }
            else if (precipitation == Biome.Precipitation.RAIN) {
                if (downfall == 0.2F) {
                    // PRAIRIE
                    biomeCategory = BiomeCategory.PLAINS;
                }
                else if (downfall == 0.275F) {
                    // MEDITERRANEAN_FOREST
                    biomeCategory = BiomeCategory.LUSH_DESERT;
                }
                else if (downfall == 0.4F || downfall == 0.3F) {
                    // ORCHARD PASTURE PRAIRIE ALLIUM_FIELDS AMARANTH_FIELDS ROSE_FIELDS
                    biomeCategory = BiomeCategory.PLAINS;
                }
                else if (downfall == 0.5F || downfall == 0.6F || downfall == 0.8F || downfall == 0.85F || downfall == 0.9F) {
                    // OLD_GROWTH_WOODLAND REDWOOD_FOREST WOODLAND JADE_CLIFFS WOODLAND EBONY_WOODS FORGOTTEN_FOREST ORCHARD RED_OAK_FOREST TEMPERATE_RAINFOREST
                    biomeCategory = BiomeCategory.FOREST;
                }
                else if (downfall == 0.7F) {
                    // LAVENDER_FIELDS LAVENDER_FOREST
                    biomeCategory = BiomeCategory.PLAINS;
                }
            }
            else if (precipitation == Biome.Precipitation.NONE) {
                if (downfall == 0.4F || downfall == 0.8F) {
                    // IVIS_FIELDS NIGHTSHADE_FOREST ETHEREAL_ISLANDS VISCAL_ISLES BULBIS_GARDENS SHULKREN_FOREST CRYPTIC_WASTES IMPARIUS_GROVE
                    biomeCategory = BiomeCategory.THEEND;
                }
            }
        }
        else if (temperature == 0.85F) {
            if (downfall == 0.75F) {
                // RAINBOW_BEACH BASALT_BARRERA
                biomeCategory = BiomeCategory.BEACH;
            }
            else {
                // DRYLAND
                biomeCategory = BiomeCategory.DRYLAND;
            }
        }
        else if (temperature == 0.9F) {
            if (generationSettings.hasFeature(VegetationPlacements.MUSHROOM_ISLAND_VEGETATION.get())) {
                // MUSHROOM_FIELDS
                biomeCategory = BiomeCategory.MUSHROOM;
            }
            if (generationSettings.hasFeature(VegetationPlacements.PATCH_GRASS_FOREST.get())) {
                // REDWOOD_THICKET
                biomeCategory = BiomeCategory.FOREST;
            }
            else if (precipitation == Biome.Precipitation.RAIN) {
                if (downfall == 0.5F) {
                    // LUSH_DESERT LUSH_SAVANNA
                    biomeCategory = BiomeCategory.LUSH_DESERT;
                }
                else if (downfall == 0.9F) {
                    // FUNGAL_JUNGLE
                    biomeCategory = BiomeCategory.SWAMP;
                }
            }
        }
        else if (temperature == 0.95F) {
            if (generationSettings.hasFeature(VegetationPlacements.PATCH_GRASS_JUNGLE.get())) {
                if (specialEffects.getGrassColorOverride().isPresent() && specialEffects.getGrassColorOverride().get() == 10145074) {
                    // TROPICAL_RAINFOREST JACARANDA_FOREST
                    biomeCategory = BiomeCategory.RAINFOREST;
                }
                else {
                    // JUNGLE SPARSE_JUNGLE BAMBOO_JUNGLE GUIANA_SHIELD
                    biomeCategory = BiomeCategory.JUNGLE;
                }
            }
            else if (downfall == 0.9F) {
                // BAYOU
                biomeCategory = BiomeCategory.SWAMP;
            }
            else if (downfall == 1.0F) {
                // TROPICS
                biomeCategory = BiomeCategory.JUNGLE;
            }
            else if (downfall == 0.3F) {
                // VOLCANO VOLCANIC_PLAINS
                biomeCategory = BiomeCategory.VOLCANIC;
            }
        }
        else if (temperature == 1.0F) {
            if (generationSettings.hasFeature(AquaticPlacements.SEAGRASS_WARM.get())) {
                // LUSH_STACKS
                biomeCategory = BiomeCategory.WARM_OCEAN;
            }
            else if (downfall == 0.3F) {
                // STONY_PEAKS
                biomeCategory = BiomeCategory.MOUNTAIN;
            }
            else if (downfall == 0.8F) {
                // CRAG_GARDENS
                biomeCategory = BiomeCategory.JUNGLE;
            }
        }
        else if (temperature == 1.1F) {
            // SCRUBLAND WOODED_SCRUBLAND
            biomeCategory = BiomeCategory.SAVANNA;
        }
        else if (temperature == 1.2F) {
            if (downfall == 0.0F) {
                // ARAUCARIA_SAVANNA BAOBAB_SAVANNA
                biomeCategory = BiomeCategory.SAVANNA;
            }
            else if (downfall == 0.1F) {
                // RED_ROCK_VALLEY SIERRA_BADLANDS
                biomeCategory = BiomeCategory.MESA;
            }
            else {
                // FLOODPLAIN RAINFOREST ROCKY_RAINFOREST
                biomeCategory = BiomeCategory.RAINFOREST;
            }
        }
        else if (temperature == 2.0F) {
            if (generationSettings.hasFeature(VegetationPlacements.TREES_WINDSWEPT_SAVANNA.get())
                    || generationSettings.hasFeature(VegetationPlacements.PATCH_TALL_GRASS.get())) {
                // SAVANNA SAVANNA_PLATEAU WINDSWEPT_SAVANNA
                biomeCategory = BiomeCategory.SAVANNA;
            }
            else if (generationSettings.hasFeature(MiscOverworldPlacements.DESERT_WELL.get())) {
                // DESERT
                biomeCategory = BiomeCategory.DESERT;
            }
            else if (generationSettings.hasFeature(NetherPlacements.PATCH_FIRE.get())) {
                // NETHER
                biomeCategory = BiomeCategory.NETHER;
            }
            else if (generationSettings.hasFeature(VegetationPlacements.PATCH_GRASS_BADLANDS.get())) {
                // BADLANDS ERODED_BADLANDS WOODED_BADLANDS
                biomeCategory = BiomeCategory.MESA;
            }
            else if (generationSettings.hasFeature(AquaticPlacements.SEAGRASS_NORMAL.get())) {
                // DEAD_SEA
                biomeCategory = BiomeCategory.VOLCANIC;
            }
            else if (specialEffects.getSkyColor() == 12815488) {
                // WINDSWEPT_DESERT ATACAMA_DESERT
                biomeCategory = BiomeCategory.DESERT;
            }
            else if (generationSettings.hasFeature(VegetationPlacements.PATCH_SUGAR_CANE_DESERT.get())) {
                // MOJAVE_DESERT
                biomeCategory = BiomeCategory.DESERT;
            }
            else if (downfall == 0.4F) {
                // FIRECRACKER_SHRUBLAND
                biomeCategory = BiomeCategory.SAVANNA;
            }
            else {
                // CRYSTALLINE_CHASM ERUPTING_INFERNO UNDERGROWTH VISCERAL_HEAP WITHERED_ABYSS (WASTELAND WOODED_WASTELAND -- should be MESA)
                // BRIMSTONE_CAVERNS CRIMSON_GARDENS EMBUR_BOG GLOWSTONE_GARDENS MAGMA_WASTES SUBZERO_HYPOGEAL SYTHIAN_TORRIDS WARPED_DESERT WAILING_GARTH ARISIAN_UNDERGROWTH WEEPING_MIRE QUARTZ_DESERT
                biomeCategory = BiomeCategory.NETHER;
            }
        }

        return biomeCategory;
    }

}
