package homeostatic.common.biome;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import biomesoplenty.api.biome.BOPBiomes;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import net.minecraftforge.fml.ModList;

import potionstudios.byg.common.world.biome.BYGBiomes;

import twilightforest.init.BiomeKeys;

import homeostatic.Homeostatic;

public class BiomeRegistry {

    public static final Map<BiomeCategory, BiomeData> BIOMES = new HashMap<>();
    private static final Map<ResourceKey<Biome>, BiomeCategory> BIOME_CATEGORY = new HashMap<>();

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
        BIOME_CATEGORY.put(Biomes.DEEP_FROZEN_OCEAN, BiomeCategory.DEEP_COLD_OCEAN);
        BIOME_CATEGORY.put(Biomes.FROZEN_OCEAN, BiomeCategory.COLD_OCEAN);
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
            BOPBiomes.getAllBiomes().forEach(biomeResourceKey -> {
                switch (biomeResourceKey.location().getPath()) {
                    case "seasonal_forest":
                    case "seasonal_orchard":
                    case "pumpkin_patch":
                    case "boreal_forest":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.COLD_FOREST);
                        break;
                    case "marsh":
                    case "bayou":
                    case "fungal_jungle":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.SWAMP);
                        break;
                    case "rainbow_hills":
                    case "snowy_coniferous_forest":
                    case "snowy_fir_clearing":
                    case "snowy_maple_woods":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.MOUNTAIN);
                        break;
                    case "floodplain":
                    case "rocky_rainforest":
                    case "rainforest":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.RAINFOREST);
                        break;
                    case "wetland":
                    case "grassland":
                    case "clover_patch":
                    case "muskeg":
                    case "shrubland":
                    case "rocky_shrubland":
                    case "cherry_blossom_grove":
                    case "bamboo_grove":
                    case "field":
                    case "forested_field":
                    case "lavender_field":
                    case "lavender_forest":
                    case "orchard":
                    case "pasture":
                    case "prairie":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.PLAINS);
                        break;
                    case "fir_clearing":
                    case "coniferous_forest":
                    case "ominous_woods":
                    case "origin_valley":
                    case "highland_moor":
                    case "highland":
                    case "crag":
                    case "jade_cliffs":
                    case "maple_woods":
                    case "mystic_grove":
                    case "old_growth_woodland":
                    case "redwood_forest":
                    case "woodland":
                    case "auroral_garden":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.FOREST);
                        break;
                    case "dryland":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.DRYLAND);
                        break;
                    case "cold_desert":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.COLD_DESERT);
                        break;
                    case "dune_beach":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.BEACH);
                        break;
                    case "scrubland":
                    case "wooded_scrubland":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.SAVANNA);
                        break;
                    case "bog":
                    case "tundra":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.BOG);
                        break;
                    case "crystalline_chasm":
                    case "erupting_inferno":
                    case "undergrowth":
                    case "visceral_heap":
                    case "withered_abyss":
                    case "wasteland": // is incorrect, but not fixing in this version
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.NETHER);
                        break;
                    case "dead_forest":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.EXTREME_HILLS);
                        break;
                    case "glowing_grotto":
                    case "spider_nest":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.RIVER);
                        break;
                    case "volcano":
                    case "volcanic_plains":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.VOLCANIC);
                        break;
                    case "old_growth_dead_forest":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.TAIGA);
                        break;
                    case "tropics":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.JUNGLE);
                        break;
                    case "lush_desert":
                    case "lush_savanna":
                    case "mediterranean_forest":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.LUSH_DESERT);
                        break;
                    case "wooded_wasteland":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.MESA); // Set as Nether temp
                        break;
                    default:
                        Homeostatic.LOGGER.debug("Missing default category for BOP Biome: %s", biomeResourceKey.location());
                        break;
                }
            });
        }

        // Oh The Biomes You'll Go
        if (ModList.get().isLoaded("byg")) {
            BYGBiomes.BIOMES_BY_TAG.forEach((biomeTagKey, biomeRegistryObject) -> {
                ResourceKey<Biome> biomeResourceKey = biomeRegistryObject.getResourceKey();
                switch (biomeResourceKey.location().getPath()) {
                    case "allium_fields":
                    case "amaranth_fields":
                    case "autumnal_valley":
                    case "coconino_meadow":
                    case "cardinal_tundra":
                    case "prairie":
                    case "rose_fields":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.PLAINS);
                        break;
                    case "araucaria_savanna":
                    case "baobab_savanna":
                    case "firecracker_shrubland":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.SAVANNA);
                        break;
                    case "aspen_forest":
                    case "borealis_grove":
                    case "cherry_blossom_forest":
                    case "cika_woods":
                    case "ebony_woods":
                    case "forgotten_forest":
                    case "grove":
                    case "orchard":
                    case "autumnal_forest":
                    case "temperate_rainforest":
                    case "zelkova_forest":
                    case "redwood_thicket":
                    case "red_oak_forest":
                    case "fragment_forest":
                    case "temperate_grove":
                    case "black_forest":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.FOREST);
                        break;
                    case "atacama_desert":
                    case "windswept_desert":
                    case "mojave_desert":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.DESERT);
                        break;
                    case "bayou":
                    case "white_mangrove_marshes":
                    case "cypress_swamplands":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.SWAMP);
                        break;
                    case "canadian_shield":
                    case "coniferous_forest":
                    case "dacite_ridges":
                    case "maple_taiga":
                    case "autumnal_taiga":
                    case "frosted_taiga":
                    case "frosted_coniferous_forest":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.TAIGA);
                        break;
                    case "crag_gardens":
                    case "guiana_shield":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.JUNGLE);
                        break;
                    case "skyris_vale":
                    case "dacite_shore":
                    case "twilight_meadow":
                    case "weeping_witch_forest":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.BOG);
                        break;
                    case "lush_stacks":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.WARM_OCEAN);
                        break;
                    case "dead_sea":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.VOLCANIC);
                        break;
                    case "howling_peaks":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.MOUNTAIN);
                        break;
                    case "jacaranda_forest":
                    case "tropical_rainforest":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.RAINFOREST);
                        break;
                    case "red_rock_valley":
                    case "sierra_badlands":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.MESA);
                        break;
                    case "shattered_glacier":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.ICY);
                        break;
                    case "rainbow_beach":
                    case "basalt_barrera":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.BEACH);
                        break;
                    case "brimstone_caverns":
                    case "crimson_gardens":
                    case "embur_bog":
                    case "glowstone_gardens":
                    case "magma_wastes":
                    case "subzero_hypogeal":
                    case "sythian_torrids":
                    case "warped_desert":
                    case "wailing_garth":
                    case "arisian_undergrowth":
                    case "weeping_mire":
                    case "quartz_desert":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.NETHER);
                        break;
                    case "ivis_fields":
                    case "nightshade_forest":
                    case "ethereal_islands":
                    case "viscal_isles":
                    case "bulbis_gardens":
                    case "shulkren_forest":
                    case "cryptic_wastes":
                    case "imparius_grove":
                        BIOME_CATEGORY.put(biomeResourceKey, BiomeCategory.THEEND);
                        break;
                    default:
                        Homeostatic.LOGGER.debug("Missing default category for BYG Biome: %s", biomeResourceKey.location());
                        break;
                }
            });
        }
        if (ModList.get().isLoaded("twilightforest")) {
            BIOME_CATEGORY.put(BiomeKeys.FOREST, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.DENSE_FOREST, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.FIREFLY_FOREST, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.CLEARING, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.OAK_SAVANNAH, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.STREAM, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.LAKE, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.MUSHROOM_FOREST, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.DENSE_MUSHROOM_FOREST, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.ENCHANTED_FOREST, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.SPOOKY_FOREST, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.SWAMP, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.FIRE_SWAMP, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.DARK_FOREST, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.DARK_FOREST_CENTER, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.SNOWY_FOREST, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.GLACIER, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.HIGHLANDS, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.THORNLANDS, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.FINAL_PLATEAU, BiomeCategory.RIVER);
            BIOME_CATEGORY.put(BiomeKeys.UNDERGROUND, BiomeCategory.RIVER);
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

}
