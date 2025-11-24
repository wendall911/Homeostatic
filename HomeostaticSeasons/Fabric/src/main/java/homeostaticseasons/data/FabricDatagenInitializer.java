package homeostaticseasons.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FabricDatagenInitializer implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();

        pack.addProvider(HomeostaticSeasonsLanguageProvider::new);
        pack.addProvider((dataOutput, registryFuture) -> new BiomeColormapDataProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new WeatherDataProvider(dataOutput));
        pack.addProvider(HomeostaticSeasonsBlockTagsProvider::new);
    }

}
