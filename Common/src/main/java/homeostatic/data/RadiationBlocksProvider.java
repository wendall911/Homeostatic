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
        add(ModIntegration.mcNamespaceLoc("soul_campfire"), 8325);
        add(ModIntegration.mcNamespaceLoc("campfire"), 5550);
        add(ModIntegration.mcNamespaceLoc("soul_fire"), 1950);
        add(ModIntegration.mcNamespaceLoc("blast_furnace"), 1800);
        add(ModIntegration.mcNamespaceLoc("lava"), 1550);
        add(ModIntegration.mcNamespaceLoc("fire"), 1300);
        add(ModIntegration.mcNamespaceLoc("furnace"), 1300);
        add(ModIntegration.mcNamespaceLoc("magma_block"), 1200);
        add(ModIntegration.mcNamespaceLoc("smoker"), 1100);
        add(ModIntegration.mcNamespaceLoc("soul_torch"), 525);
        add(ModIntegration.mcNamespaceLoc("soul_wall_torch"), 525);
        add(ModIntegration.mcNamespaceLoc("soul_lantern"), 525);
        add(ModIntegration.mcNamespaceLoc("nether_portal"), 350);
        add(ModIntegration.mcNamespaceLoc("torch"), 350);
        add(ModIntegration.mcNamespaceLoc("wall_torch"), 350);
        add(ModIntegration.mcNamespaceLoc("lantern"), 350);

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
