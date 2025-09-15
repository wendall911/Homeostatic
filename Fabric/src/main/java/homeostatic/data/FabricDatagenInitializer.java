package homeostatic.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FabricDatagenInitializer implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();

        if (System.getProperty("homeostatic.common_datagen") != null) {
            configureCommonDatagen(pack);
        }
        else {
            configureFabricDatagen(pack);
        }
    }

    public static void configureCommonDatagen(FabricDataGenerator.Pack pack) {
        pack.addProvider(HomeostaticItemTagsProvider::new);
        pack.addProvider(HomeostaticFluidTagsProvider::new);
        pack.addProvider((dataOutput, registryFuture) -> new HomeostaticItemModelProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new RadiationBlocksProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new DrinkingFluidsProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new DrinkableItemsProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new BiomeCategoryProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new BiomeTypeDataProvider(dataOutput));
        pack.addProvider(HomeostaticLanguageProvider::new);
        pack.addProvider(HomeostaticRecipeProvider::new);
    }

    public static void configureFabricDatagen(FabricDataGenerator.Pack pack) {
    }

}
