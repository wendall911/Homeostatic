package climatesettings.data;

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
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import climatesettings.ClimateSettings;
import climatesettings.common.biome.BiomeCategory;
import climatesettings.common.biome.BiomeCategoryManager;
import climatesettings.data.integration.ModIntegration;

public class BiomeCategoryProvider implements DataProvider {

    private final Map<Identifier, BiomeCategory> BIOME_CATEGORY_MAP = new HashMap<>();
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
        add(Biomes.STONY_SHORE, BiomeCategory.Type.COLD_BEACH);
        add(Biomes.JUNGLE, BiomeCategory.Type.JUNGLE);
        add(Biomes.SPARSE_JUNGLE, BiomeCategory.Type.JUNGLE);
        add(Biomes.BAMBOO_JUNGLE, BiomeCategory.Type.JUNGLE);
        add(Biomes.BADLANDS, BiomeCategory.Type.MESA);
        add(Biomes.ERODED_BADLANDS, BiomeCategory.Type.MESA);
        add(Biomes.WOODED_BADLANDS, BiomeCategory.Type.MESA);
        add(Biomes.SNOWY_SLOPES, BiomeCategory.Type.ICY);
        add(Biomes.JAGGED_PEAKS, BiomeCategory.Type.MOUNTAIN);
        add(Biomes.FROZEN_PEAKS, BiomeCategory.Type.ICY);
        add(Biomes.MEADOW, BiomeCategory.Type.MOUNTAIN);
        add(Biomes.CHERRY_GROVE, BiomeCategory.Type.FOREST);
        add(Biomes.STONY_PEAKS, BiomeCategory.Type.MOUNTAIN);
        add(Biomes.RIVER, BiomeCategory.Type.RIVER);
        add(Biomes.FROZEN_RIVER, BiomeCategory.Type.FROZEN_RIVER);
        add(Biomes.BEACH, BiomeCategory.Type.BEACH);
        add(Biomes.SNOWY_BEACH, BiomeCategory.Type.COLD_BEACH);
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
        add(ModIntegration.bopLoc("gravel_beach"), BiomeCategory.Type.COLD_BEACH);
        add(ModIntegration.bopLoc("hot_springs"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bopLoc("jacaranda_glade"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bopLoc("overgrown_greens"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bopLoc("wintry_origin_valley"), BiomeCategory.Type.COLD_FOREST);

        // Oh The Biomes We've Gone
        add(ModIntegration.bygLoc("allium_shrubland"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("amaranth_grassland"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("araucaria_savanna"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.bygLoc("aspen_boreal"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("atacama_outback"), BiomeCategory.Type.LUSH_DESERT);
        add(ModIntegration.bygLoc("baobab_savanna"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.bygLoc("basalt_barrera"), BiomeCategory.Type.BEACH);
        add(ModIntegration.bygLoc("bayou"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.bygLoc("black_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("canadian_shield"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("cika_woods"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("coconino_meadow"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("coniferous_forest"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("crag_gardens"), BiomeCategory.Type.JUNGLE);
        add(ModIntegration.bygLoc("crimson_tundra"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("cypress_swamplands"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.bygLoc("dacite_ridges"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("dacite_shore"), BiomeCategory.Type.BOG);
        add(ModIntegration.bygLoc("dead_sea"), BiomeCategory.Type.DEAD_SEA);
        add(ModIntegration.bygLoc("ebony_woods"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("enchanted_tangle"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("eroded_borealis"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("firecracker_chaparral"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.bygLoc("forgotten_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("fragment_jungle"), BiomeCategory.Type.JUNGLE);
        add(ModIntegration.bygLoc("frosted_coniferous_forest"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("frosted_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("howling_peaks"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.bygLoc("ironwood_gour"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.bygLoc("jacaranda_jungle"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.bygLoc("lush_stacks"), BiomeCategory.Type.WARM_OCEAN);
        add(ModIntegration.bygLoc("maple_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.bygLoc("mojave_desert"), BiomeCategory.Type.DESERT);
        add(ModIntegration.bygLoc("orchard"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("overgrowth_woodlands"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("pale_bog"), BiomeCategory.Type.BOG);
        add(ModIntegration.bygLoc("prairie"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("pumpkin_valley"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("rainbow_beach"), BiomeCategory.Type.BEACH);
        add(ModIntegration.bygLoc("red_rock_valley"), BiomeCategory.Type.MESA);
        add(ModIntegration.bygLoc("redwood_thicket"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("rose_fields"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.bygLoc("rugged_badlands"), BiomeCategory.Type.MESA);
        add(ModIntegration.bygLoc("sakura_grove"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("sierra_badlands"), BiomeCategory.Type.MESA);
        add(ModIntegration.bygLoc("shattered_glacier"), BiomeCategory.Type.ICY);
        add(ModIntegration.bygLoc("skyrise_vale"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("temperate_grove"), BiomeCategory.Type.FOREST);
        add(ModIntegration.bygLoc("tropical_rainforest"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.bygLoc("weeping_witch_forest"), BiomeCategory.Type.BOG);
        add(ModIntegration.bygLoc("white_mangrove_marshes"), BiomeCategory.Type.SWAMP);
        add(ModIntegration.bygLoc("windswept_desert"), BiomeCategory.Type.DESERT);
        add(ModIntegration.bygLoc("zelkova_forest"), BiomeCategory.Type.FOREST);

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
        add(ModIntegration.esLoc("warm_shore"), BiomeCategory.Type.WARM_BEACH);

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
        add(ModIntegration.terralithLoc("alpha_islands_winter"), BiomeCategory.Type.COLD_BEACH);
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
        add(ModIntegration.terralithLoc("gravel_beach"), BiomeCategory.Type.COLD_BEACH);
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
        add(ModIntegration.terralithLoc("mirage_isles"), BiomeCategory.Type.WARM_BEACH);
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
        add(ModIntegration.terralithLoc("warm_river"), BiomeCategory.Type.WARM_RIVER);
        add(ModIntegration.terralithLoc("warped_mesa"), BiomeCategory.Type.MESA);
        add(ModIntegration.terralithLoc("white_cliffs"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("white_mesa"), BiomeCategory.Type.MESA);
        add(ModIntegration.terralithLoc("windswept_spires"), BiomeCategory.Type.DESERT);
        add(ModIntegration.terralithLoc("wintry_forest"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.terralithLoc("wintry_lowlands"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.terralithLoc("yellowstone"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("yosemite_cliffs"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.terralithLoc("yosemite_lowlands"), BiomeCategory.Type.FOREST);

        // Regions Unexplored
        add(ModIntegration.regionsLoc("alpha_grove"), BiomeCategory.Type.FOREST);
        add(ModIntegration.regionsLoc("ancient_delta"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.regionsLoc("arid_mountains"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.regionsLoc("ashen_woodland"), BiomeCategory.Type.VOLCANIC);
        add(ModIntegration.regionsLoc("autumnal_maple_forest"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.regionsLoc("bamboo_forest"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.regionsLoc("baobab_savanna"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.regionsLoc("barley_fields"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.regionsLoc("bayou"), BiomeCategory.Type.BOG);
        add(ModIntegration.regionsLoc("bioshroom_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.regionsLoc("blackstone_basin"), BiomeCategory.Type.NETHER);
        add(ModIntegration.regionsLoc("blackwood_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.regionsLoc("boreal_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.regionsLoc("chalk_cliffs"), BiomeCategory.Type.BEACH);
        add(ModIntegration.regionsLoc("clover_plains"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.regionsLoc("cold_boreal_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.regionsLoc("cold_deciduous_forest"), BiomeCategory.Type.COLD_FOREST);
        add(ModIntegration.regionsLoc("cold_river"), BiomeCategory.Type.FROZEN_RIVER);
        add(ModIntegration.regionsLoc("deciduous_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.regionsLoc("dry_bushland"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.regionsLoc("eucalyptus_forest"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.regionsLoc("fen"), BiomeCategory.Type.BOG);
        add(ModIntegration.regionsLoc("flower_fields"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.regionsLoc("frozen_pine_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.regionsLoc("frozen_tundra"), BiomeCategory.Type.ICY);
        add(ModIntegration.regionsLoc("fungal_fen"), BiomeCategory.Type.BOG);
        add(ModIntegration.regionsLoc("glistering_meadow"), BiomeCategory.Type.NETHER);
        add(ModIntegration.regionsLoc("golden_boreal_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.regionsLoc("grassland"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.regionsLoc("grassy_beach"), BiomeCategory.Type.BEACH);
        add(ModIntegration.regionsLoc("gravel_beach"), BiomeCategory.Type.COLD_BEACH);
        add(ModIntegration.regionsLoc("highland_fields"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.regionsLoc("hyacinth_deeps"), BiomeCategory.Type.DEEP_COLD_OCEAN);
        add(ModIntegration.regionsLoc("icy_heights"), BiomeCategory.Type.ICY);
        add(ModIntegration.regionsLoc("infernal_holt"), BiomeCategory.Type.NETHER);
        add(ModIntegration.regionsLoc("joshua_desert"), BiomeCategory.Type.LUSH_DESERT);
        add(ModIntegration.regionsLoc("magnolia_woodland"), BiomeCategory.Type.FOREST);
        add(ModIntegration.regionsLoc("maple_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.regionsLoc("marsh"), BiomeCategory.Type.BOG);
        add(ModIntegration.regionsLoc("mauve_hills"), BiomeCategory.Type.FOREST);
        add(ModIntegration.regionsLoc("mountains"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.regionsLoc("muddy_river"), BiomeCategory.Type.RIVER);
        add(ModIntegration.regionsLoc("mycotoxic_undergrowth"), BiomeCategory.Type.NETHER);
        add(ModIntegration.regionsLoc("old_growth_bayou"), BiomeCategory.Type.BOG);
        add(ModIntegration.regionsLoc("orchard"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.regionsLoc("outback"), BiomeCategory.Type.SAVANNA);
        add(ModIntegration.regionsLoc("pine_slopes"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.regionsLoc("pine_taiga"), BiomeCategory.Type.TAIGA);
        add(ModIntegration.regionsLoc("poppy_fields"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.regionsLoc("prairie"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.regionsLoc("prismachasm"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.regionsLoc("pumpkin_fields"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.regionsLoc("rainforest"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.regionsLoc("redstone_abyss"), BiomeCategory.Type.NETHER);
        add(ModIntegration.regionsLoc("redstone_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.regionsLoc("redwoods"), BiomeCategory.Type.FOREST);
        add(ModIntegration.regionsLoc("rocky_meadow"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.regionsLoc("rocky_reef"), BiomeCategory.Type.LUKEWARM_OCEAN);
        add(ModIntegration.regionsLoc("saguaro_desert"), BiomeCategory.Type.LUSH_DESERT);
        add(ModIntegration.regionsLoc("scorching_caves"), BiomeCategory.Type.UNDERGROUND);
        add(ModIntegration.regionsLoc("shrubland"), BiomeCategory.Type.PLAINS);
        add(ModIntegration.regionsLoc("silver_birch_forest"), BiomeCategory.Type.FOREST);
        add(ModIntegration.regionsLoc("sparse_rainforest"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.regionsLoc("sparse_redwoods"), BiomeCategory.Type.FOREST);
        add(ModIntegration.regionsLoc("spires"), BiomeCategory.Type.ICY);
        add(ModIntegration.regionsLoc("steppe"), BiomeCategory.Type.DRYLAND);
        add(ModIntegration.regionsLoc("temperate_grove"), BiomeCategory.Type.FOREST);
        add(ModIntegration.regionsLoc("towering_cliffs"), BiomeCategory.Type.MOUNTAIN);
        add(ModIntegration.regionsLoc("tropical_river"), BiomeCategory.Type.WARM_RIVER);
        add(ModIntegration.regionsLoc("tropics"), BiomeCategory.Type.RAINFOREST);
        add(ModIntegration.regionsLoc("willow_forest"), BiomeCategory.Type.FOREST);
    }

    protected void add(ResourceKey<Biome> biomeResourceKey, BiomeCategory.Type type) {
        BIOME_CATEGORY_MAP.put(biomeResourceKey.identifier(), new BiomeCategory(biomeResourceKey.identifier(), type.name()));
    }
    
    protected void add(Identifier loc, BiomeCategory.Type type) {
        BIOME_CATEGORY_MAP.put(loc, new BiomeCategory(loc, type.name()));
    }

    @Override
    public @NotNull String getName() {
        return ClimateSettings.MOD_NAME + " - Biome Categories";
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput cache) throws IllegalStateException {
        List<CompletableFuture<?>> recipeList = new ArrayList<>();

        registerBiomeCategories();

        for (Map.Entry<Identifier, BiomeCategory> entry : BIOME_CATEGORY_MAP.entrySet()) {
            PackOutput.PathProvider pathProvider = getPath(entry.getKey());

            recipeList.add(DataProvider.saveStable(cache,
                    BiomeCategoryManager.parseBiomeCategory(entry.getValue()),
                    pathProvider.json(entry.getKey())));
        }

        return CompletableFuture.allOf(recipeList.toArray(CompletableFuture[]::new));
    }

    private PackOutput.PathProvider getPath(Identifier loc) {
        return this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "environment/biome_category/");
    }

}
