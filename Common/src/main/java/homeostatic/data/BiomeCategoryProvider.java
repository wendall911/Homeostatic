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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import homeostatic.common.biome.BiomeCategory;
import homeostatic.common.biome.BiomeCategoryManager;
import homeostatic.data.integration.ModIntegration;

public class BiomeCategoryProvider implements DataProvider {

    private final Map<ResourceLocation, BiomeCategory> BIOME_CATEGORY_MAP = new HashMap<>();
    private final PackOutput packOutput;

    public BiomeCategoryProvider(@NotNull final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected void registerBiomeCategories() {
        add(Biomes.THE_VOID, BiomeCategory.Type.NONE);
        add(Biomes.PLAINS, BiomeCategory.Type.PLAINS);
        add(Biomes.SUNFLOWER_PLAINS, BiomeCategory.Type.PLAINS);
        add(Biomes.SNOWY_PLAINS, BiomeCategory.Type.ICY);
        add(Biomes.ICE_SPIKES, BiomeCategory.Type.ICY);
        add(Biomes.DESERT, BiomeCategory.Type.DESERT);
        add(Biomes.SWAMP, BiomeCategory.Type.SWAMP);
        add(Biomes.MANGROVE_SWAMP, BiomeCategory.Type.SWAMP);
        add(Biomes.GROVE, BiomeCategory.Type.FOREST);
        add(Biomes.BIRCH_FOREST, BiomeCategory.Type.FOREST);
        add(Biomes.OLD_GROWTH_BIRCH_FOREST, BiomeCategory.Type.FOREST);
        add(Biomes.DARK_FOREST, BiomeCategory.Type.FOREST);
        add(Biomes.FOREST, BiomeCategory.Type.FOREST);
        add(Biomes.FLOWER_FOREST, BiomeCategory.Type.FOREST);
        add(Biomes.OLD_GROWTH_PINE_TAIGA, BiomeCategory.Type.TAIGA);
        add(Biomes.TAIGA, BiomeCategory.Type.TAIGA);
        add(Biomes.SNOWY_TAIGA, BiomeCategory.Type.TAIGA);
        add(Biomes.OLD_GROWTH_SPRUCE_TAIGA, BiomeCategory.Type.TAIGA);
        add(Biomes.SAVANNA, BiomeCategory.Type.SAVANNA);
        add(Biomes.SAVANNA_PLATEAU, BiomeCategory.Type.SAVANNA);
        add(Biomes.WINDSWEPT_SAVANNA, BiomeCategory.Type.SAVANNA);
        add(Biomes.WINDSWEPT_HILLS, BiomeCategory.Type.EXTREME_HILLS);
        add(Biomes.WINDSWEPT_GRAVELLY_HILLS, BiomeCategory.Type.EXTREME_HILLS);
        add(Biomes.WINDSWEPT_FOREST, BiomeCategory.Type.EXTREME_HILLS);
        add(Biomes.STONY_SHORE, BiomeCategory.Type.BEACH);
        add(Biomes.JUNGLE, BiomeCategory.Type.JUNGLE);
        add(Biomes.SPARSE_JUNGLE, BiomeCategory.Type.JUNGLE);
        add(Biomes.BAMBOO_JUNGLE, BiomeCategory.Type.JUNGLE);
        add(Biomes.BADLANDS, BiomeCategory.Type.MESA);
        add(Biomes.ERODED_BADLANDS, BiomeCategory.Type.MESA);
        add(Biomes.WOODED_BADLANDS, BiomeCategory.Type.MESA);
        add(Biomes.SNOWY_SLOPES, BiomeCategory.Type.MOUNTAIN);
        add(Biomes.JAGGED_PEAKS, BiomeCategory.Type.MOUNTAIN);
        add(Biomes.FROZEN_PEAKS, BiomeCategory.Type.MOUNTAIN);
        add(Biomes.MEADOW, BiomeCategory.Type.MOUNTAIN);
        add(Biomes.CHERRY_GROVE, BiomeCategory.Type.FOREST);
        add(Biomes.STONY_PEAKS, BiomeCategory.Type.MOUNTAIN);
        add(Biomes.RIVER, BiomeCategory.Type.RIVER);
        add(Biomes.FROZEN_RIVER, BiomeCategory.Type.RIVER);
        add(Biomes.BEACH, BiomeCategory.Type.BEACH);
        add(Biomes.SNOWY_BEACH, BiomeCategory.Type.BEACH);
        add(Biomes.WARM_OCEAN, BiomeCategory.Type.WARM_OCEAN);
        add(Biomes.LUKEWARM_OCEAN, BiomeCategory.Type.LUKEWARM_OCEAN);
        add(Biomes.DEEP_LUKEWARM_OCEAN, BiomeCategory.Type.DEEP_LUKEWARM_OCEAN);
        add(Biomes.DEEP_FROZEN_OCEAN, BiomeCategory.Type.OCEAN);
        add(Biomes.FROZEN_OCEAN, BiomeCategory.Type.COLD_OCEAN);
        add(Biomes.OCEAN, BiomeCategory.Type.OCEAN);
        add(Biomes.DEEP_OCEAN, BiomeCategory.Type.OCEAN);
        add(Biomes.COLD_OCEAN, BiomeCategory.Type.COLD_OCEAN);
        add(Biomes.DEEP_COLD_OCEAN, BiomeCategory.Type.DEEP_COLD_OCEAN);
        add(Biomes.MUSHROOM_FIELDS, BiomeCategory.Type.MUSHROOM);
        add(Biomes.DRIPSTONE_CAVES, BiomeCategory.Type.UNDERGROUND);
        add(Biomes.DEEP_DARK, BiomeCategory.Type.UNDERGROUND);
        add(Biomes.LUSH_CAVES, BiomeCategory.Type.UNDERGROUND);
        add(Biomes.NETHER_WASTES, BiomeCategory.Type.NETHER);
        add(Biomes.WARPED_FOREST, BiomeCategory.Type.NETHER);
        add(Biomes.CRIMSON_FOREST, BiomeCategory.Type.NETHER);
        add(Biomes.SOUL_SAND_VALLEY, BiomeCategory.Type.NETHER);
        add(Biomes.BASALT_DELTAS, BiomeCategory.Type.NETHER);
        add(Biomes.THE_END, BiomeCategory.Type.THEEND);
        add(Biomes.END_HIGHLANDS, BiomeCategory.Type.THEEND);
        add(Biomes.END_MIDLANDS, BiomeCategory.Type.THEEND);
        add(Biomes.SMALL_END_ISLANDS, BiomeCategory.Type.THEEND);
        add(Biomes.END_BARRENS, BiomeCategory.Type.THEEND);

        // BOP
        add(ModIntegration.bopLoc("seasonal_forest"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.bopLoc("seasonal_orchard"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.bopLoc("pumpkin_patch"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.bopLoc("boreal_forest"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.bopLoc("marsh"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.bopLoc("bayou"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.bopLoc("fungal_jungle"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.bopLoc("rainbow_hills"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.bopLoc("snowy_coniferous_forest"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.bopLoc("snowy_fir_clearing"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.bopLoc("snowy_maple_woods"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.bopLoc("floodplain"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.bopLoc("rocky_rainforest"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.bopLoc("rainforest"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.bopLoc("wetland"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.bopLoc("grassland"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("clover_patch"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("muskeg"), BiomeCategory.Type.ICY);
        add(ModIntegration.bopLoc("shrubland"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("rocky_shrubland"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("cherry_blossom_grove"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("bamboo_grove"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("field"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("forested_field"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("lavender_field"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("lavender_forest"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("orchard"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("pasture"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("prairie"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("fir_clearing"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("coniferous_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("ominous_woods"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("origin_valley"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("moor"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("highland"), BiomeCategory.Type.EXTREME_HILLS);
        add(ModIntegration.bopLoc("crag"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.bopLoc("jade_cliffs"), BiomeCategory.Type.EXTREME_HILLS);
        add(ModIntegration.bopLoc("maple_woods"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("mystic_grove"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("old_growth_woodland"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("redwood_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("woodland"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("auroral_garden"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("snowblossom_grove"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("dryland"), BiomeCategory.Type.DRYLAND);
        add(ModIntegration.bopLoc("cold_desert"), BiomeCategory.Type.COLD_DESERT);
        add(ModIntegration.bopLoc("dune_beach"), BiomeCategory.Type.BEACH);
        add(ModIntegration.bopLoc("scrubland"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.bopLoc("wooded_scrubland"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.bopLoc("wasteland"), BiomeCategory.Type.DESERT);
        add(ModIntegration.bopLoc("bog"), BiomeCategory.Type.BOG);
        add(ModIntegration.bopLoc("tundra"), BiomeCategory.Type.BOG);
        add(ModIntegration.bopLoc("crystalline_chasm"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bopLoc("erupting_inferno"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bopLoc("undergrowth"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bopLoc("visceral_heap"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bopLoc("withered_abyss"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bopLoc("dead_forest"), BiomeCategory.Type.EXTREME_HILLS);
        add(ModIntegration.bopLoc("glowing_grotto"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.bopLoc("spider_nest"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.bopLoc("volcano"), BiomeCategory.Type.VOLCANIC);
        add(ModIntegration.bopLoc("volcanic_plains"), BiomeCategory.Type.VOLCANIC);
        add(ModIntegration.bopLoc("old_growth_dead_forest"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bopLoc("tropics"), BiomeCategory.Type.JUNGLE);
        add(ModIntegration.bopLoc("lush_desert"), BiomeCategory.Type.LUSH_DESERT);
        add(ModIntegration.bopLoc("lush_savanna"), BiomeCategory.Type.LUSH_DESERT);
        add(ModIntegration.bopLoc("mediterranean_forest"), BiomeCategory.Type.LUSH_DESERT);
        add(ModIntegration.bopLoc("wasteland_steppe"), BiomeCategory.Type.MESA);
        add(ModIntegration.bopLoc("aspen_glade"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("end_corruption"), BiomeCategory.Type.THEEND);
        add(ModIntegration.bopLoc("end_reef"), BiomeCategory.Type.THEEND);
        add(ModIntegration.bopLoc("end_wilds"), BiomeCategory.Type.THEEND);
        add(ModIntegration.bopLoc("gravel_beach"), BiomeCategory.Type.BEACH);
        add(ModIntegration.bopLoc("hot_springs"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bopLoc("jacaranda_glade"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("overgrown_greens"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("wintry_origin_valley"), BiomeCategory.Type.COLD_FOREST);

        // Oh The Biomes You'll Go
        add(ModIntegration.bygLoc("allium_fields"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("amaranth_fields"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("autumnal_valley"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("coconino_meadow"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("cardinal_tundra"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("prairie"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("rose_fields"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("araucaria_savanna"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.bygLoc("baobab_savanna"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.bygLoc("firecracker_shrubland"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.bygLoc("aspen_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("borealis_grove"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("cherry_blossom_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("cika_woods"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("ebony_woods"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("forgotten_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("grove"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("orchard"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("autumnal_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("temperate_rainforest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("zelkova_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("redwood_thicket"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("red_oak_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("fragment_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("temperate_grove"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("black_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("atacama_desert"), BiomeCategory.Type.DESERT);
        add(ModIntegration.bygLoc("windswept_desert"), BiomeCategory.Type.DESERT);
        add(ModIntegration.bygLoc("mojave_desert"), BiomeCategory.Type.DESERT);
        add(ModIntegration.bygLoc("bayou"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.bygLoc("white_mangrove_marshes"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.bygLoc("cypress_swamplands"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.bygLoc("canadian_shield"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("coniferous_forest"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("dacite_ridges"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("maple_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("autumnal_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("frosted_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("frosted_coniferous_forest"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("crag_gardens"), BiomeCategory.Type.JUNGLE);
        add(ModIntegration.bygLoc("guiana_shield"), BiomeCategory.Type.JUNGLE);
        add(ModIntegration.bygLoc("skyris_vale"), BiomeCategory.Type.BOG);
        add(ModIntegration.bygLoc("dacite_shore"), BiomeCategory.Type.BOG);
        add(ModIntegration.bygLoc("twilight_meadow"), BiomeCategory.Type.BOG);
        add(ModIntegration.bygLoc("weeping_witch_forest"), BiomeCategory.Type.BOG);
        add(ModIntegration.bygLoc("lush_stacks"), BiomeCategory.Type.WARM_OCEAN);
        add(ModIntegration.bygLoc("dead_sea"), BiomeCategory.Type.DEAD_SEA);
        add(ModIntegration.bygLoc("howling_peaks"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.bygLoc("jacaranda_forest"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.bygLoc("tropical_rainforest"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.bygLoc("red_rock_valley"), BiomeCategory.Type.MESA);
        add(ModIntegration.bygLoc("sierra_badlands"), BiomeCategory.Type.MESA);
        add(ModIntegration.bygLoc("shattered_glacier"), BiomeCategory.Type.ICY);
        add(ModIntegration.bygLoc("rainbow_beach"), BiomeCategory.Type.BEACH);
        add(ModIntegration.bygLoc("basalt_barrera"), BiomeCategory.Type.BEACH);
        add(ModIntegration.bygLoc("brimstone_caverns"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("crimson_gardens"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("embur_bog"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("glowstone_gardens"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("magma_wastes"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("subzero_hypogeal"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("sythian_torrids"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("warped_desert"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("wailing_garth"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("arisian_undergrowth"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("weeping_mire"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("quartz_desert"), BiomeCategory.Type.NETHER);
        add(ModIntegration.bygLoc("ivis_fields"), BiomeCategory.Type.THEEND);
        add(ModIntegration.bygLoc("nightshade_forest"), BiomeCategory.Type.THEEND);
        add(ModIntegration.bygLoc("ethereal_islands"), BiomeCategory.Type.THEEND);
        add(ModIntegration.bygLoc("viscal_isles"), BiomeCategory.Type.THEEND);
        add(ModIntegration.bygLoc("bulbis_gardens"), BiomeCategory.Type.THEEND);
        add(ModIntegration.bygLoc("shulkren_forest"), BiomeCategory.Type.THEEND);
        add(ModIntegration.bygLoc("cryptic_wastes"), BiomeCategory.Type.THEEND);
        add(ModIntegration.bygLoc("imparius_grove"), BiomeCategory.Type.THEEND);

        // Twilight Forest
        add(ModIntegration.tfLoc("forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.tfLoc("dense_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.tfLoc("firefly_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.tfLoc("clearing"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.tfLoc("oak_savannah"), BiomeCategory.Type.FOREST);
        add(ModIntegration.tfLoc("stream"), BiomeCategory.Type.RIVER);
        add(ModIntegration.tfLoc("lake"), BiomeCategory.Type.RIVER);
        add(ModIntegration.tfLoc("mushroom_forest"), BiomeCategory.Type.MUSHROOM);
        add(ModIntegration.tfLoc("dense_mushroom_forest"), BiomeCategory.Type.MUSHROOM);
        add(ModIntegration.tfLoc("enchanted_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.tfLoc("spooky_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.tfLoc("swamp"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.tfLoc("fire_swamp"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.tfLoc("dark_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.tfLoc("dark_forest_center"), BiomeCategory.Type.FOREST);
        add(ModIntegration.tfLoc("snowy_forest"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.tfLoc("glacier"), BiomeCategory.Type.ICY);
        add(ModIntegration.tfLoc("highlands"), BiomeCategory.Type.DRYLAND);
        add(ModIntegration.tfLoc("thornlands"), BiomeCategory.Type.DRYLAND);
        add(ModIntegration.tfLoc("final_plateau"), BiomeCategory.Type.DRYLAND);
        add(ModIntegration.tfLoc("underground"), BiomeCategory.Type.UNDERGROUND);

        // Ars Nouveau
        add(ModIntegration.arsLoc("archwood_forest"), BiomeCategory.Type.FOREST);

        // Ars Elemental
        add(ModIntegration.arseLoc("blazing_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.arseLoc("cascading_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.arseLoc("flourishing_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.arseLoc("flashing_forest"), BiomeCategory.Type.FOREST);

        // Eternal Starlight
        add(ModIntegration.esLoc("crystallized_desert"), BiomeCategory.Type.DESERT);
        add(ModIntegration.esLoc("dark_swamp"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.esLoc("ether_river"), BiomeCategory.Type.RIVER);
        add(ModIntegration.esLoc("scarlet_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.esLoc("shimmer_river"), BiomeCategory.Type.RIVER);
        add(ModIntegration.esLoc("starlight_dense_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.esLoc("starlight_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.esLoc("starlight_permafrost_forest"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.esLoc("starlit_sea"), BiomeCategory.Type.WARM_OCEAN);
        add(ModIntegration.esLoc("the_abyss"), BiomeCategory.Type.DEEP_COLD_OCEAN);
        add(ModIntegration.esLoc("torreya_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.esLoc("warm_shore"), BiomeCategory.Type.BEACH);

        // The Undergarden
        add(ModIntegration.ugLoc("ancient_sea"), BiomeCategory.Type.WARM_OCEAN);
        add(ModIntegration.ugLoc("barren_abyss"), BiomeCategory.Type.DRYLAND);
        add(ModIntegration.ugLoc("blood_mushroom_bog"), BiomeCategory.Type.BOG);
        add(ModIntegration.ugLoc("dead_sea"), BiomeCategory.Type.DEAD_SEA);
        add(ModIntegration.ugLoc("dense_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.ugLoc("forgotten_field"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.ugLoc("frostfields"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.ugLoc("frosty_smogstem_forest"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.ugLoc("gronglegrowth"), BiomeCategory.Type.FOREST);
        add(ModIntegration.ugLoc("icy_sea"), BiomeCategory.Type.COLD_OCEAN);
        add(ModIntegration.ugLoc("indigo_mushroom_bog"), BiomeCategory.Type.BOG);
        add(ModIntegration.ugLoc("ink_mushroom_bog"), BiomeCategory.Type.BOG);
        add(ModIntegration.ugLoc("smog_spires"), BiomeCategory.Type.DRYLAND);
        add(ModIntegration.ugLoc("smogstem_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.ugLoc("veil_mushroom_bog"), BiomeCategory.Type.BOG);
        add(ModIntegration.ugLoc("wigglewood_forest"), BiomeCategory.Type.FOREST);

        // Terralith
        add(ModIntegration.terralithLoc("alpha_islands"), BiomeCategory.Type.BEACH);
        add(ModIntegration.terralithLoc("alpha_islands_winter"), BiomeCategory.Type.BEACH);
        add(ModIntegration.terralithLoc("alpine_grove"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("alpine_highlands"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("amethyst_canyon"), BiomeCategory.Type.JUNGLE);
        add(ModIntegration.terralithLoc("amethyst_rainforest"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.terralithLoc("ancient_sands"), BiomeCategory.Type.DESERT);
        add(ModIntegration.terralithLoc("arid_highlands"), BiomeCategory.Type.DRYLAND);
        add(ModIntegration.terralithLoc("ashen_savanna"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("basalt_cliffs"), BiomeCategory.Type.DRYLAND);
        add(ModIntegration.terralithLoc("birch_taiga"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("blooming_plateau"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.terralithLoc("blooming_valley"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("brushland"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.terralithLoc("bryce_canyon"), BiomeCategory.Type.MESA);
        add(ModIntegration.terralithLoc("caldera"), BiomeCategory.Type.VOLCANIC);
        add(ModIntegration.terralithLoc("cave/andesite_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.terralithLoc("cave/deep_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.terralithLoc("cave/diorite_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.terralithLoc("cave/frostfire_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.terralithLoc("cave/fungal_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.terralithLoc("cave/granite_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.terralithLoc("cave/infested_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.terralithLoc("cave/mantle_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.terralithLoc("cave/thermal_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.terralithLoc("cave/tuff_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.terralithLoc("cave/underground_jungle"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.terralithLoc("cloud_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("cold_shrubland"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.terralithLoc("desert_canyon"), BiomeCategory.Type.DESERT);
        add(ModIntegration.terralithLoc("desert_oasis"), BiomeCategory.Type.DESERT);
        add(ModIntegration.terralithLoc("desert_spires"), BiomeCategory.Type.MESA);
        add(ModIntegration.terralithLoc("emerald_peaks"), BiomeCategory.Type.EXTREME_HILLS);
        add(ModIntegration.terralithLoc("forested_highlands"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("fractured_savanna"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.terralithLoc("frozen_cliffs"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.terralithLoc("glacial_chasm"), BiomeCategory.Type.ICY);
        add(ModIntegration.terralithLoc("granite_cliffs"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.terralithLoc("gravel_beach"), BiomeCategory.Type.BEACH);
        add(ModIntegration.terralithLoc("gravel_desert"), BiomeCategory.Type.DESERT);
        add(ModIntegration.terralithLoc("haze_mountain"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("highlands"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("hot_shrubland"), BiomeCategory.Type.DRYLAND);
        add(ModIntegration.terralithLoc("ice_marsh"), BiomeCategory.Type.BOG);
        add(ModIntegration.terralithLoc("jungle_mountains"), BiomeCategory.Type.JUNGLE);
        add(ModIntegration.terralithLoc("lavender_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("lavender_valley"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("lush_desert"), BiomeCategory.Type.LUSH_DESERT);
        add(ModIntegration.terralithLoc("lush_valley"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.terralithLoc("mirage_isles"), BiomeCategory.Type.BEACH);
        add(ModIntegration.terralithLoc("moonlight_grove"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("moonlight_valley"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("orchid_swamp"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.terralithLoc("painted_mountains"), BiomeCategory.Type.EXTREME_HILLS);
        add(ModIntegration.terralithLoc("red_oasis"), BiomeCategory.Type.DESERT);
        add(ModIntegration.terralithLoc("rocky_jungle"), BiomeCategory.Type.JUNGLE);
        add(ModIntegration.terralithLoc("rocky_mountains"), BiomeCategory.Type.EXTREME_HILLS);
        add(ModIntegration.terralithLoc("rocky_shrubland"), BiomeCategory.Type.LUSH_DESERT);
        add(ModIntegration.terralithLoc("sakura_grove"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("sakura_valley"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("sandstone_valley"), BiomeCategory.Type.DESERT);
        add(ModIntegration.terralithLoc("savanna_badlands"), BiomeCategory.Type.MESA);
        add(ModIntegration.terralithLoc("savanna_slopes"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("scarlet_mountains"), BiomeCategory.Type.EXTREME_HILLS);
        add(ModIntegration.terralithLoc("shield"), BiomeCategory.Type.EXTREME_HILLS);
        add(ModIntegration.terralithLoc("shield_clearing"), BiomeCategory.Type.EXTREME_HILLS);
        add(ModIntegration.terralithLoc("shrubland"), BiomeCategory.Type.LUSH_DESERT);
        add(ModIntegration.terralithLoc("siberian_grove"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("siberian_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.terralithLoc("skylands_autumn"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("skylands_spring"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("skylands_summer"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("skylands_winter"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("snowy_badlands"), BiomeCategory.Type.COLD_DESERT);
        add(ModIntegration.terralithLoc("snowy_cherry_grove"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.terralithLoc("snowy_maple_forest"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("snowy_shield"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("steppe"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("stony_spires"), BiomeCategory.Type.EXTREME_HILLS);
        add(ModIntegration.terralithLoc("temperate_highlands"), BiomeCategory.Type.FOREST);
        add(ModIntegration.terralithLoc("tropical_jungle"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.terralithLoc("valley_clearing"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.terralithLoc("volcanic_crater"), BiomeCategory.Type.VOLCANIC);
        add(ModIntegration.terralithLoc("volcanic_peaks"), BiomeCategory.Type.EXTREME_HILLS);
        add(ModIntegration.terralithLoc("warm_river"), BiomeCategory.Type.RIVER);
        add(ModIntegration.terralithLoc("warped_mesa"), BiomeCategory.Type.MESA);
        add(ModIntegration.terralithLoc("white_cliffs"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("white_mesa"), BiomeCategory.Type.MESA);
        add(ModIntegration.terralithLoc("windswept_spires"), BiomeCategory.Type.DESERT);
        add(ModIntegration.terralithLoc("wintry_forest"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.terralithLoc("wintry_lowlands"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.terralithLoc("yellowstone"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("yosemite_cliffs"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("yosemite_lowlands"), BiomeCategory.Type.FOREST);
    }
    
    protected void add (ResourceKey<Biome> biomeResourceKey, BiomeCategory.Type type) {
        BIOME_CATEGORY_MAP.put(biomeResourceKey.location(), new BiomeCategory(biomeResourceKey.location(), type.name()));
    }
    
    protected void add(ResourceLocation loc, BiomeCategory.Type type) {
        BIOME_CATEGORY_MAP.put(loc, new BiomeCategory(loc, type.name()));
    }

    @Override
    public String getName() {
        return "Homeostatic - Biome Categories";
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput cache) throws IllegalStateException {
        List<CompletableFuture<?>> recipeList = new ArrayList<>();

        registerBiomeCategories();

        for (Map.Entry<ResourceLocation, BiomeCategory> entry : BIOME_CATEGORY_MAP.entrySet()) {
            PackOutput.PathProvider pathProvider = getPath(entry.getKey());

            recipeList.add(DataProvider.saveStable(cache,
                    BiomeCategoryManager.parseBiomeCategory(entry.getValue()),
                    pathProvider.json(entry.getKey())));
        }

        return CompletableFuture.allOf(recipeList.toArray(CompletableFuture[]::new));
    }

    private PackOutput.PathProvider getPath(ResourceLocation loc) {
        return this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "environment/biome_category/");
    }

}
