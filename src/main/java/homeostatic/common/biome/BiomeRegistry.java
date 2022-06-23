package homeostatic.common.biome;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class BiomeRegistry {

    public static final Map<BiomeCategory, BiomeData> BIOMES = new HashMap<>();

    private static final BiomeData DEEP_COLD_OCEAN = new BiomeData(0.373F, 20.0F, 20F, 5F);
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
    private static final BiomeData SWAMP = new BiomeData(0.663F, 90.0F, 40F, 12F);
    public static final BiomeData UNDERGROUND = new BiomeData(0.663F, 40.0F, 40F, 12F);
    private static final BiomeData MUSHROOM = new BiomeData(0.685F, 70.0F, 40F, 12F);
    private static final BiomeData WARM_OCEAN = new BiomeData(0.730F, 70.0F, 40F, 10F);
    private static final BiomeData PLAINS = new BiomeData(0.774F, 60.0F, 40F, 15F);
    private static final BiomeData JUNGLE = new BiomeData(0.997F, 90.0F, 40F, 15F);
    private static final BiomeData SAVANNA = new BiomeData(1.108F, 30.0F, 40F, 15F);
    private static final BiomeData MESA = new BiomeData(1.309F, 20.0F, 40F, 15F);
    private static final BiomeData DESERT = new BiomeData(1.354F, 20.0F, 40F, 20F);
    private static final BiomeData NONE = new BiomeData(0.15F, 40.0F, 40F, 0F);
    private static final BiomeData THEEND = new BiomeData(0.551F, 40.0F, 40F, 0F);
    private static final BiomeData NETHER = new BiomeData(1.666F, 20.0F, 40F, 0F);

    static {
        BIOMES.put(BiomeCategory.DEEP_COLD_OCEAN, DEEP_COLD_OCEAN);
        BIOMES.put(BiomeCategory.COLD_OCEAN, COLD_OCEAN);
        BIOMES.put(BiomeCategory.ICY, ICY);
        BIOMES.put(BiomeCategory.OCEAN, OCEAN);
        BIOMES.put(BiomeCategory.WARM_OCEAN, WARM_OCEAN);
        BIOMES.put(BiomeCategory.LUKEWARM_OCEAN, LUKEWARM_OCEAN);
        BIOMES.put(BiomeCategory.DEEP_LUKEWARM_OCEAN, DEEP_LUKEWARM_OCEAN);
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
        BIOMES.put(BiomeCategory.JUNGLE, JUNGLE);
        BIOMES.put(BiomeCategory.SAVANNA, SAVANNA);
        BIOMES.put(BiomeCategory.MESA, MESA);
        BIOMES.put(BiomeCategory.DESERT, DESERT);
        BIOMES.put(BiomeCategory.NONE, NONE);
        BIOMES.put(BiomeCategory.THEEND, THEEND);
        BIOMES.put(BiomeCategory.NETHER, NETHER);
    }

    public static enum BiomeCategory {
        ICY,
        OCEAN,
        WARM_OCEAN,
        LUKEWARM_OCEAN,
        DEEP_LUKEWARM_OCEAN,
        COLD_OCEAN,
        DEEP_COLD_OCEAN,
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
        JUNGLE,
        SAVANNA,
        MESA,
        DESERT,
        NONE,
        THEEND,
        NETHER,
        MISSING
    }

    public static BiomeData getDataForBiome(Holder<Biome> biome) {
        BiomeCategory biomeCategory = BiomeCategory.MISSING;

        if (biome.is(Biomes.THE_VOID)) {
            biomeCategory = BiomeCategory.NONE;
        }
        else if (biome.is(Biomes.PLAINS) || biome.is(Biomes.SUNFLOWER_PLAINS)) {
            biomeCategory = BiomeCategory.PLAINS;
        }
        else if (biome.is(Biomes.SNOWY_PLAINS) || biome.is(Biomes.ICE_SPIKES)) {
            biomeCategory = BiomeCategory.ICY;
        }
        else if (biome.is(Biomes.DESERT)) {
            biomeCategory = BiomeCategory.DESERT;
        }
        else if (biome.is(Biomes.SWAMP) || biome.is(Biomes.MANGROVE_SWAMP)) {
            biomeCategory = BiomeCategory.SWAMP;
        }
        else if (biome.is(Biomes.GROVE)
                || biome.is(Biomes.BIRCH_FOREST)
                || biome.is(Biomes.OLD_GROWTH_BIRCH_FOREST)
                || biome.is(Biomes.DARK_FOREST)
                || biome.is(Biomes.FOREST)
                || biome.is(Biomes.FLOWER_FOREST)) {
            biomeCategory = BiomeCategory.FOREST;
        }
        else if (biome.is(Biomes.OLD_GROWTH_PINE_TAIGA)
                || biome.is(Biomes.TAIGA)
                || biome.is(Biomes.SNOWY_TAIGA)
                || biome.is(Biomes.OLD_GROWTH_SPRUCE_TAIGA)) {
            biomeCategory = BiomeCategory.TAIGA;
        }
        else if (biome.is(Biomes.SAVANNA)
                || biome.is(Biomes.SAVANNA_PLATEAU)
                || biome.is(Biomes.WINDSWEPT_SAVANNA)) {
            biomeCategory = BiomeCategory.SAVANNA;
        }
        else if (biome.is(Biomes.WINDSWEPT_HILLS)
                || biome.is(Biomes.WINDSWEPT_GRAVELLY_HILLS)
                || biome.is(Biomes.WINDSWEPT_FOREST)
                || biome.is(Biomes.STONY_SHORE)) {
            biomeCategory = BiomeCategory.EXTREME_HILLS;
        }
        else if (biome.is(Biomes.JUNGLE)
                || biome.is(Biomes.SPARSE_JUNGLE)
                || biome.is(Biomes.BAMBOO_JUNGLE)) {
            biomeCategory = BiomeCategory.JUNGLE;
        }
        else if (biome.is(Biomes.BADLANDS)
                || biome.is(Biomes.ERODED_BADLANDS)
                || biome.is(Biomes.WOODED_BADLANDS)) {
            biomeCategory = BiomeCategory.MESA;
        }
        else if (biome.is(Biomes.SNOWY_SLOPES)
                || biome.is(Biomes.JAGGED_PEAKS)
                || biome.is(Biomes.FROZEN_PEAKS)
                || biome.is(Biomes.MEADOW)
                || biome.is(Biomes.STONY_PEAKS)) {
            biomeCategory = BiomeCategory.MOUNTAIN;
        }
        else if (biome.is(Biomes.RIVER) || biome.is(Biomes.FROZEN_RIVER)) {
            biomeCategory = BiomeCategory.RIVER;
        }
        else if (biome.is(Biomes.BEACH) || biome.is(Biomes.SNOWY_BEACH)) {
            biomeCategory = BiomeCategory.BEACH;
        }
        else if (biome.is(Biomes.WARM_OCEAN)) {
            biomeCategory = BiomeCategory.WARM_OCEAN;
        }
        else if (biome.is(Biomes.LUKEWARM_OCEAN)) {
            biomeCategory = BiomeCategory.LUKEWARM_OCEAN;
        }
        else if (biome.is(Biomes.DEEP_LUKEWARM_OCEAN)) {
            biomeCategory = BiomeCategory.DEEP_LUKEWARM_OCEAN;
        }
        else if (biome.is(Biomes.DEEP_FROZEN_OCEAN)
                || biome.is(Biomes.FROZEN_OCEAN)
                || biome.is(Biomes.OCEAN)
                || biome.is(Biomes.DEEP_OCEAN)) {
            biomeCategory = BiomeCategory.OCEAN;
        }
        else if (biome.is(Biomes.COLD_OCEAN)) {
            biomeCategory = BiomeCategory.COLD_OCEAN;
        }
        else if (biome.is(Biomes.DEEP_COLD_OCEAN)) {
            biomeCategory = BiomeCategory.DEEP_COLD_OCEAN;
        }
        else if (biome.is(Biomes.MUSHROOM_FIELDS)) {
            biomeCategory = BiomeCategory.MUSHROOM;
        }
        else if (biome.is(Biomes.DRIPSTONE_CAVES)
                || biome.is(Biomes.DEEP_DARK)
                || biome.is(Biomes.LUSH_CAVES)) {
            biomeCategory = BiomeCategory.UNDERGROUND;
        }
        else if (biome.is(Biomes.NETHER_WASTES)
                || biome.is(Biomes.WARPED_FOREST)
                || biome.is(Biomes.CRIMSON_FOREST)
                || biome.is(Biomes.SOUL_SAND_VALLEY)
                || biome.is(Biomes.BASALT_DELTAS)) {
            biomeCategory = BiomeCategory.NETHER;
        }
        else if (biome.is(Biomes.THE_END)
                || biome.is(Biomes.END_HIGHLANDS)
                || biome.is(Biomes.END_MIDLANDS)
                || biome.is(Biomes.SMALL_END_ISLANDS)
                || biome.is(Biomes.END_BARRENS)) {
            biomeCategory = BiomeCategory.THEEND;
        }

        if (biomeCategory == BiomeCategory.MISSING) {
            // Set some defaults for MISSING
            return new BiomeData(biome.get().getBaseTemperature(), 40.0F, 40F, 10F);
        }
        else {
            return BiomeRegistry.BIOMES.get(biomeCategory);
        }
    }

    public static BiomeCategory getBiomeCategory(BiomeGenerationSettings generationSettings, float temperature, MobSpawnSettings mobSpawnSettings, Biome.TemperatureModifier temperatureModifier, float downfall, Biome.Precipitation precipitation) {
        BiomeCategory biomeCategory = BiomeCategory.MISSING;

        if (temperature == -0.2F) {
            // GROVE
            biomeCategory = BiomeCategory.FOREST;
        }
        else if (temperature == -0.3F) {
            // SNOWY_SLOPES
            biomeCategory = BiomeCategory.MOUNTAIN;
        }
        else if (temperature == -0.5F) {
            // SNOWY_TAIGA
            biomeCategory = BiomeCategory.TAIGA;
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
        else if (temperature == 0.25F) {
            // TAIGA OLD_GROWTH_SPRUCE_TAIGA
            biomeCategory = BiomeCategory.TAIGA;
        }
        else if (temperature == 0.2F) {
            // WINDSWEPT_HILLS WINDSWEPT_GRAVELLY_HILLS WINDSWEPT_FOREST STONY_SHORE
            biomeCategory = BiomeCategory.EXTREME_HILLS;
        }
        else if (temperature == 0.3F) {
            // OLD_GROWTH_PINE_TAIGA
            biomeCategory = BiomeCategory.TAIGA;
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
            else if (downfall == 0.8F) {
                // MEADOW
                biomeCategory = BiomeCategory.MOUNTAIN;
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
                // RIVER
                biomeCategory = BiomeCategory.RIVER;
            }
            else if (precipitation == Biome.Precipitation.NONE) {
                // THEEND
                biomeCategory = BiomeCategory.THEEND;
            }
        }
        else if (temperature == 0.6F) {
            // BIRCH_FOREST OLD_GROWTH_BIRCH_FOREST
            biomeCategory = BiomeCategory.FOREST;
        }
        else if (temperature == 0.7F) {
            if (generationSettings.hasFeature(AquaticPlacements.SEAGRASS_SWAMP.get())) {
                // SWAMP
                biomeCategory = BiomeCategory.SWAMP;
            }
            else {
                // DARK_FOREST FOREST FLOWER_FOREST
                biomeCategory = BiomeCategory.FOREST;
            }
        }
        else if (temperature == 0.8F) {
            if (mobSpawnSettings.getEntityTypes().contains(EntityType.TURTLE)) {
                // BEACH
                biomeCategory = BiomeCategory.BEACH;
            }
            else if (generationSettings.hasFeature(AquaticPlacements.SEAGRASS_SWAMP.get())) {
                // MANGROVE_SWAMP
                biomeCategory = BiomeCategory.SWAMP;
            }
            else if (generationSettings.hasFeature(CavePlacements.LARGE_DRIPSTONE.get())
                    || generationSettings.hasFeature(CavePlacements.SCULK_VEIN.get())) {
                // DRIPSTONE_CAVES DEEP_DARK
                biomeCategory = BiomeCategory.UNDERGROUND;
            }
            else {
                // PLAINS SUNFLOWER_PLAINS
                biomeCategory = BiomeCategory.PLAINS;
            }
        }
        else if (temperature == 0.9F) {
            // MUSHROOM_FIELDS
            biomeCategory = BiomeCategory.MUSHROOM;
        }
        else if (temperature == 0.95F) {
            // JUNGLE SPARSE_JUNGLE BAMBOO_JUNGLE
            biomeCategory = BiomeCategory.JUNGLE;
        }
        else if (temperature == 1.0F) {
            // STONY_PEAKS
            biomeCategory = BiomeCategory.MOUNTAIN;
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
            else {
                // BADLANDS ERODED_BADLANDS WOODED_BADLANDS
                biomeCategory = BiomeCategory.MESA;
            }
        }

        return biomeCategory;
    }

}
