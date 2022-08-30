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

public class RadiationBlocksProvider implements DataProvider {

    private final Map<ResourceLocation, BlockRadiation> RADIATION_MAP = new HashMap<>();
    private final DataGenerator dataGenerator;
    private final String modid;

    public RadiationBlocksProvider(DataGenerator dataGenerator, String modid) {
        this.dataGenerator = dataGenerator;
        this.modid = modid;
    }

    protected void registerRadiationBlocks() {
        add(new ResourceLocation("minecraft", "soul_campfire"), 8325);
        add(new ResourceLocation("minecraft", "campfire"), 5550);
        add(new ResourceLocation("minecraft", "soul_fire"), 1950);
        add(new ResourceLocation("minecraft", "blast_furnace"), 1800);
        add(new ResourceLocation("minecraft", "lava"), 1550);
        add(new ResourceLocation("minecraft", "fire"), 1300);
        add(new ResourceLocation("minecraft", "furnace"), 1300);
        add(new ResourceLocation("minecraft", "magma_block"), 1200);
        add(new ResourceLocation("minecraft", "smoker"), 1100);
        add(new ResourceLocation("minecraft", "soul_torch"), 525);
        add(new ResourceLocation("minecraft", "soul_wall_torch"), 525);
        add(new ResourceLocation("minecraft", "soul_lantern"), 525);
        add(new ResourceLocation("minecraft", "nether_portal"), 350);
        add(new ResourceLocation("minecraft", "torch"), 350);
        add(new ResourceLocation("minecraft", "wall_torch"), 350);
        add(new ResourceLocation("minecraft", "lantern"), 350);

        // Oh The Biomes You'll Go
        add(new ResourceLocation("byg", "cryptic_campfire"), 7250);
        add(new ResourceLocation("byg", "boric_campfire"), 6250);
        add(new ResourceLocation("byg", "cryptic_magma_block"), 1200);
        add(new ResourceLocation("byg", "boric_lantern"), 400);
        add(new ResourceLocation("byg", "cryptic_lantern"), 470);
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
