package homeostatic.common.biome;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import biomesoplenty.api.biome.BOPBiomes;
import biomesoplenty.common.worldgen.placement.BOPVegetationPlacements;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.minecraftforge.fml.ModList;

import potionstudios.byg.common.world.biome.BYGBiomes;
import potionstudios.byg.common.world.feature.BYGPlacedFeatures;

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
                    case "wasteland":
                    case "wasteland_steppe":
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
            BYGBiomes.BIOMES_BY_TAG.forEach((biomeTagKey, biomeResourceKey) -> {
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
    }

    public static boolean hasFeature(BiomeGenerationSettings biomeGenerationSettings, ResourceKey<PlacedFeature> placedFeatureResourceKey) {
        return biomeGenerationSettings.features().stream().flatMap(HolderSet::stream).map(Holder::unwrapKey).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet()).contains(placedFeatureResourceKey);
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

    public static BiomeCategory getBiomeCategory(BiomeGenerationSettings generationSettings, float temperature, Biome.TemperatureModifier temperatureModifier, float downfall, Biome.Precipitation precipitation, BiomeSpecialEffects specialEffects) {
        BiomeCategory biomeCategory = BiomeCategory.MISSING;

        if (temperature == -0.2F) {
            // GROVE
            biomeCategory = BiomeCategory.FOREST;
        }
        else if (temperature == -0.25F) {
            if (ModList.get().isLoaded("biomesoplenty") && downfall == 0.5F
                    && precipitation == Biome.Precipitation.SNOW
                    && hasFeature(generationSettings, BOPVegetationPlacements.TREES_AURORAL_GARDEN)) {
                // AURORAL_GARDEN
                biomeCategory = BiomeCategory.FOREST;
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
            if (ModList.get().isLoaded("byg")
                    && hasFeature(generationSettings, BYGPlacedFeatures.LARGE_HOWLING_PEAKS_BOULDERS)) {
                // HOWLING_PEAKS
                biomeCategory = BiomeCategory.MOUNTAIN;
            }
            else if (downfall == 1.0F) {
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
            // MUSKEG
            if (downfall == 0.6F && precipitation == Biome.Precipitation.SNOW) {
                biomeCategory = BiomeCategory.PLAINS;
            }
            else if (hasFeature(generationSettings, MiscOverworldPlacements.ICE_SPIKE)
                    || hasFeature(generationSettings, VegetationPlacements.TREES_SNOWY)) {
                // ICE_SPIKES SNOWY_PLAINS
                biomeCategory = BiomeCategory.ICY;
            }
            else if (temperatureModifier == Biome.TemperatureModifier.FROZEN) {
                // FROZEN_OCEAN
                biomeCategory = BiomeCategory.COLD_OCEAN;
            }
            else {
                // FROZEN_RIVER
                biomeCategory = BiomeCategory.RIVER;
            }
        }
        else if (temperature == 0.145F) {
            // CARDINAL_TUNDRA
            biomeCategory = BiomeCategory.PLAINS;
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
            if (ModList.get().isLoaded("byg")
                    && hasFeature(generationSettings, BYGPlacedFeatures.ZELKOVA_TREES)) {
                // ZELKOVA_FOREST
                biomeCategory = BiomeCategory.FOREST;
            }
            else if (hasFeature(generationSettings, VegetationPlacements.PATCH_GRASS_TAIGA_2)
                    || hasFeature(generationSettings, VegetationPlacements.PATCH_GRASS_TAIGA)) {
                // TAIGA MAPLE_TAIGA CANADIAN_SHIELD CONIFEROUS_FOREST DACITE_RIDGES AUTUMNAL_TAIGA
                biomeCategory = BiomeCategory.TAIGA;
            }
            else if (hasFeature(generationSettings, VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA)) {
                // OLD_GROWTH_SPRUCE_TAIGA
                biomeCategory = BiomeCategory.TAIGA;
            }
            else if (ModList.get().isLoaded("byg")
                    && hasFeature(generationSettings, BYGPlacedFeatures.FRAGMENT_FOREST_TREES)) {
                // FRAGMENT_FOREST
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
                // SKYRIS_VALE TWILIGHT_MEADOW WEEPING_WITCH_FOREST DACITE_SHORE
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
                // SEASONAL_FOREST PUMPKIN_PATCH SEASONAL_ORCHARD
                biomeCategory = BiomeCategory.COLD_FOREST;
            }
        }
        else if (temperature == 0.45F) {
            // CONIFEROUS_FOREST FIR_CLEARING BLACK_FOREST
            biomeCategory = BiomeCategory.FOREST;
        }
        else if (temperature == 0.5F) {
            if (temperatureModifier == Biome.TemperatureModifier.FROZEN) {
                biomeCategory = BiomeCategory.DEEP_COLD_OCEAN;
            }
            else if (hasFeature(generationSettings, AquaticPlacements.SEAGRASS_NORMAL)
                    || hasFeature(generationSettings, AquaticPlacements.SEAGRASS_DEEP)) {
                // DEEP_OCEAN OCEAN
                biomeCategory = BiomeCategory.OCEAN;
            }
            else if (hasFeature(generationSettings, AquaticPlacements.SEA_PICKLE)) {
                // WARM_OCEAN
                biomeCategory = BiomeCategory.WARM_OCEAN;
            }
            else if (hasFeature(generationSettings, AquaticPlacements.SEAGRASS_WARM)) {
                // LUKEWARM_OCEAN
                biomeCategory = BiomeCategory.LUKEWARM_OCEAN;
            }
            else if (hasFeature(generationSettings, AquaticPlacements.SEAGRASS_DEEP_WARM)) {
                // DEEP_LUKEWARM_OCEAN
                biomeCategory = BiomeCategory.DEEP_LUKEWARM_OCEAN;
            }
            else if (hasFeature(generationSettings, AquaticPlacements.SEAGRASS_COLD)) {
                // COLD_OCEAN
                biomeCategory = BiomeCategory.COLD_OCEAN;
            }
            else if (hasFeature(generationSettings, AquaticPlacements.SEAGRASS_DEEP_COLD)) {
                // DEEP_COLD_OCEAN
                biomeCategory = BiomeCategory.DEEP_COLD_OCEAN;
            }
            else if (hasFeature(generationSettings, MiscOverworldPlacements.VOID_START_PLATFORM)) {
                // THE_VOID
                biomeCategory = BiomeCategory.NONE;
            }
            else if (hasFeature(generationSettings, CavePlacements.CAVE_VINES)) {
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
                else if (downfall == 0.6F) {
                    // ASPEN_FOREST
                    biomeCategory = BiomeCategory.FOREST;
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
            if (hasFeature(generationSettings, AquaticPlacements.SEAGRASS_SWAMP)) {
                // SWAMP
                biomeCategory = BiomeCategory.SWAMP;
            }
            else if (precipitation == Biome.Precipitation.RAIN) {
                if (downfall == 0.4F) {
                    // DUNE_BEACH
                    biomeCategory = BiomeCategory.BEACH;
                }
                else if (downfall == 0.8F) {
                    // DARK_FOREST FOREST FLOWER_FOREST MYSTIC_GROVE CHERRY_BLOSSOM_FOREST
                    biomeCategory = BiomeCategory.FOREST;
                }
            }
        }
        else if (temperature == 0.75F) {
            if (downfall == 0.8F) {
                // GROVE TEMPERATE_GROVE CYPRESS_SWAMPLANDS
                biomeCategory = BiomeCategory.FOREST;
            }
        }
        else if (temperature == 0.8F) {
            if (ModList.get().isLoaded("byg")
                    && (hasFeature(generationSettings, BYGPlacedFeatures.ALLIUM_FIELD_FLOWERS)
                        || hasFeature(generationSettings, BYGPlacedFeatures.AMARANTH_FIELD_FLOWERS)
                        || hasFeature(generationSettings, BYGPlacedFeatures.ROSE_FIELD_FLOWERS))) {
                // ALLIUM_FIELDS AMARANTH_FIELDS ROSE_FIELDS
                biomeCategory = BiomeCategory.PLAINS;
            }
            else if (hasFeature(generationSettings, AquaticPlacements.SEAGRASS_SWAMP)) {
                // MANGROVE_SWAMP BAYOU WHITE_MANGROVE_MARSHES
                biomeCategory = BiomeCategory.SWAMP;
            }
            else if (hasFeature(generationSettings, CavePlacements.LARGE_DRIPSTONE)
                    || hasFeature(generationSettings, CavePlacements.SCULK_VEIN)) {
                // DRIPSTONE_CAVES DEEP_DARK
                biomeCategory = BiomeCategory.UNDERGROUND;
            }
            else if (hasFeature(generationSettings, VegetationPlacements.PATCH_SUNFLOWER)){
                // SUNFLOWER_PLAINS
                biomeCategory = BiomeCategory.PLAINS;
            }
            else if (specialEffects.getGrassColorOverride().isPresent() && specialEffects.getGrassColorOverride().get() == 6530407) {
                // COCONINO_MEADOW
                biomeCategory = BiomeCategory.PLAINS;
            }
            else if (ModList.get().isLoaded("biomesoplenty")
                    && hasFeature(generationSettings, BOPVegetationPlacements.TREES_ORCHARD)) {
                // ORCHARD
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
                else if (downfall == 0.3F) {
                    // PASTURE
                    biomeCategory = BiomeCategory.PLAINS;
                }
                else if (downfall == 0.4F && !hasFeature(generationSettings, VegetationPlacements.PATCH_TALL_GRASS_2)) {
                    // BEACH
                    biomeCategory = BiomeCategory.BEACH;
                }
                else if (downfall == 0.4F) {
                    // PLAINS
                    biomeCategory = BiomeCategory.PLAINS;
                }
                else if (downfall == 0.5F || downfall == 0.6F || downfall == 0.8F || downfall == 0.85F || downfall == 0.9F) {
                    // OLD_GROWTH_WOODLAND REDWOOD_FOREST WOODLAND JADE_CLIFFS WOODLAND EBONY_WOODS FORGOTTEN_FOREST RED_OAK_FOREST TEMPERATE_RAINFOREST
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
            if (hasFeature(generationSettings, VegetationPlacements.MUSHROOM_ISLAND_VEGETATION)) {
                // MUSHROOM_FIELDS
                biomeCategory = BiomeCategory.MUSHROOM;
            }
            if (hasFeature(generationSettings, VegetationPlacements.PATCH_GRASS_FOREST)) {
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
            if (hasFeature(generationSettings, VegetationPlacements.PATCH_GRASS_JUNGLE)) {
                if (specialEffects.getGrassColorOverride().isPresent() && specialEffects.getGrassColorOverride().get() == 10145074) {
                    // TROPICAL_RAINFOREST
                    biomeCategory = BiomeCategory.RAINFOREST;
                }
                else {
                    // JUNGLE SPARSE_JUNGLE BAMBOO_JUNGLE GUIANA_SHIELD
                    biomeCategory = BiomeCategory.JUNGLE;
                }
            }
            else if (hasFeature(generationSettings, VegetationPlacements.PATCH_GRASS_SAVANNA)) {
                // JACARANDA_FOREST
                biomeCategory = BiomeCategory.RAINFOREST;
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
            if (hasFeature(generationSettings, AquaticPlacements.SEAGRASS_WARM)) {
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
            // SCRUBLAND
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
            if (hasFeature(generationSettings, VegetationPlacements.TREES_WINDSWEPT_SAVANNA)
                    || hasFeature(generationSettings, VegetationPlacements.PATCH_TALL_GRASS)) {
                // SAVANNA SAVANNA_PLATEAU WINDSWEPT_SAVANNA
                biomeCategory = BiomeCategory.SAVANNA;
            }
            else if (hasFeature(generationSettings, MiscOverworldPlacements.DESERT_WELL)) {
                // DESERT
                biomeCategory = BiomeCategory.DESERT;
            }
            else if (hasFeature(generationSettings, NetherPlacements.PATCH_FIRE)) {
                // NETHER
                biomeCategory = BiomeCategory.NETHER;
            }
            else if (hasFeature(generationSettings, VegetationPlacements.PATCH_GRASS_BADLANDS)) {
                // BADLANDS ERODED_BADLANDS WOODED_BADLANDS
                biomeCategory = BiomeCategory.MESA;
            }
            else if (hasFeature(generationSettings, AquaticPlacements.SEAGRASS_NORMAL)) {
                // DEAD_SEA
                biomeCategory = BiomeCategory.VOLCANIC;
            }
            else if (specialEffects.getSkyColor() == 12815488) {
                // WINDSWEPT_DESERT ATACAMA_DESERT
                biomeCategory = BiomeCategory.DESERT;
            }
            else if (hasFeature(generationSettings, VegetationPlacements.PATCH_SUGAR_CANE_DESERT)) {
                // MOJAVE_DESERT
                biomeCategory = BiomeCategory.DESERT;
            }
            else if (downfall == 0.4F) {
                // FIRECRACKER_SHRUBLAND
                biomeCategory = BiomeCategory.SAVANNA;
            }
            else if (ModList.get().isLoaded("biomesoplenty")
                    && hasFeature(generationSettings, BOPVegetationPlacements.PATCH_DEAD_GRASS)
                    || hasFeature(generationSettings, BOPVegetationPlacements.TREES_WASTELAND)) {
                biomeCategory = BiomeCategory.MESA;
            }
            else {
                // CRYSTALLINE_CHASM ERUPTING_INFERNO UNDERGROWTH VISCERAL_HEAP WITHERED_ABYSS
                // BRIMSTONE_CAVERNS CRIMSON_GARDENS EMBUR_BOG GLOWSTONE_GARDENS MAGMA_WASTES SUBZERO_HYPOGEAL SYTHIAN_TORRIDS WARPED_DESERT WAILING_GARTH ARISIAN_UNDERGROWTH WEEPING_MIRE QUARTZ_DESERT
                biomeCategory = BiomeCategory.NETHER;
            }
        }

        return biomeCategory;
    }

}
