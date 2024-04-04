package homeostatic.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

import homeostatic.Homeostatic;
import homeostatic.common.block.BlockRadiation;
import homeostatic.common.block.BlockRadiationManager;
import homeostatic.data.integration.ModIntegration;

public class RadiationBlocksProvider implements DataProvider {

    private final Map<ResourceLocation, BlockRadiation> RADIATION_MAP = new HashMap<>();
    private final DataGenerator dataGenerator;
    private final String modid;

    public RadiationBlocksProvider(DataGenerator dataGenerator, String modid) {
        this.dataGenerator = dataGenerator;
        this.modid = modid;
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
    public void run(CachedOutput pOutput) throws IOException {
        registerRadiationBlocks();

        Path output = dataGenerator.getOutputFolder();

        for (Map.Entry<ResourceLocation, BlockRadiation> entry : RADIATION_MAP.entrySet()) {
            Path blockRadiationPath = getPath(output, entry.getKey());

            try {
                DataProvider.saveStable(pOutput, BlockRadiationManager.parseBlockRadiation(entry.getValue()), blockRadiationPath);
            }
            catch (IOException e) {
                Homeostatic.LOGGER.error("Couldn't save homeostatic block_radiation %s %s", blockRadiationPath, e);
            }
        }
    }

    private static Path getPath(Path output, ResourceLocation loc) {
        return output.resolve("data/" + loc.getNamespace() + "/environment/block_radiation/" + loc.getPath() + ".json");
    }

}
