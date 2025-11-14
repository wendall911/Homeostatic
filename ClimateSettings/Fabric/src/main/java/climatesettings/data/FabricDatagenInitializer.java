package climatesettings.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator.Pack;

public class FabricDatagenInitializer implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();

        if (System.getProperty("climatesettings.common_datagen") != null) {
            configureCommonDatagen(pack);
        }
    }

    private void configureCommonDatagen(Pack pack) {
        pack.addProvider((dataOutput, registryFuture) -> new BiomeCategoryProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new BiomeTypeDataProvider(dataOutput));
    }

}
