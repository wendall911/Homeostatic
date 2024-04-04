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

import homeostatic.common.block.BlockRadiation;
import homeostatic.common.block.BlockRadiationManager;
import homeostatic.data.integration.ModIntegration;

public class RadiationBlocksProvider implements DataProvider {

    private final Map<ResourceLocation, BlockRadiation> RADIATION_MAP = new HashMap<>();
    private final PackOutput packOutput;

    public RadiationBlocksProvider(@NotNull final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected void registerRadiationBlocks() {
        add(ModIntegration.mcLoc("soul_campfire"), 8325);
        add(ModIntegration.mcLoc("campfire"), 5550);
        add(ModIntegration.mcLoc("soul_fire"), 1950);
        add(ModIntegration.mcLoc("blast_furnace"), 1800);
        add(ModIntegration.mcLoc("lava"), 1550);
        add(ModIntegration.mcLoc("fire"), 1300);
        add(ModIntegration.mcLoc("furnace"), 1300);
        add(ModIntegration.mcLoc("magma_block"), 1200);
        add(ModIntegration.mcLoc("smoker"), 1100);
        add(ModIntegration.mcLoc("soul_torch"), 525);
        add(ModIntegration.mcLoc("soul_wall_torch"), 525);
        add(ModIntegration.mcLoc("soul_lantern"), 525);
        add(ModIntegration.mcLoc("nether_portal"), 350);
        add(ModIntegration.mcLoc("torch"), 350);
        add(ModIntegration.mcLoc("wall_torch"), 350);
        add(ModIntegration.mcLoc("lantern"), 350);

        // Oh The Biomes You'll Go
        add(ModIntegration.bygLoc("cryptic_campfire"), 7250);
        add(ModIntegration.bygLoc("boric_campfire"), 6250);
        add(ModIntegration.bygLoc("cryptic_magma_block"), 1200);
        add(ModIntegration.bygLoc("boric_lantern"), 400);
        add(ModIntegration.bygLoc("cryptic_lantern"), 470);

        // Create
        add(ModIntegration.createLoc("blaze_burner"), 8325);
        add(ModIntegration.createLoc("lit_blaze_burner"), 8325);

        // Tinkers' Construct
        add(ModIntegration.tconLoc("smeltery_controller"), 5550);
        add(ModIntegration.tconLoc("foundry_controller"), 5550);
        add(ModIntegration.tconLoc("seared_melter"), 2450);
        add(ModIntegration.tconLoc("seared_heater"), 2450);
        add(ModIntegration.tconLoc("scorched_alloyer"), 2450);
    }

    protected void add(ResourceLocation loc, double maxRadiation) {
        RADIATION_MAP.put(loc, new BlockRadiation(loc, maxRadiation));
    }

    @Override
    public String getName() {
        return "Homeostatic - Block Radiation ";
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput cache) throws IllegalStateException {
        List<CompletableFuture<?>> recipeList = new ArrayList<>();

        registerRadiationBlocks();

        for (Map.Entry<ResourceLocation, BlockRadiation> entry : RADIATION_MAP.entrySet()) {
            PackOutput.PathProvider pathProvider = getPath(entry.getKey());

            recipeList.add(DataProvider.saveStable(cache,
                    BlockRadiationManager.parseBlockRadiation(entry.getValue()),
                    pathProvider.json(entry.getKey())));
        }

        return CompletableFuture.allOf(recipeList.toArray(CompletableFuture[]::new));
    }

    private PackOutput.PathProvider getPath(ResourceLocation loc) {
        return this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "environment/block_radiation/");
    }

}
