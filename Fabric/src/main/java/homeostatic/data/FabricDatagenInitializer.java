package homeostatic.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import homeostatic.data.recipe.CommonRecipeProvider;

public class FabricDatagenInitializer implements DataGeneratorEntrypoint {

    private static FabricTagProvider.BlockTagProvider fabricBlockTagProvider;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        FabricDataGenerator.Pack pack = gen.createPack();
        fabricBlockTagProvider = pack.addProvider(FabricBlockTagProvider::new);

        if (System.getProperty("homeostatic.common_datagen") != null) {
            configureCommonDatagen(pack);
        }
        else {
            configureFabricDatagen(pack);
        }
    }

    public static void configureCommonDatagen(FabricDataGenerator.Pack pack) {
        pack.addProvider((dataOutput, registryFuture) -> new HomeostaticItemTagsProvider(dataOutput, registryFuture, fabricBlockTagProvider.contentsGetter()));
        pack.addProvider(HomeostaticFluidTagsProvider::new);
        pack.addProvider((dataOutput, registryFuture) -> new HomeostaticItemModelProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new RadiationBlocksProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new DrinkingFluidsProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new DrinkableItemsProvider(dataOutput));
        pack.addProvider((dataOutput, registryFuture) -> new BiomeCategoryProvider(dataOutput));
        pack.addProvider(HomeostaticLanguageProvider::new);
        pack.addProvider(CommonRecipeProvider::new);
    }

    public static void configureFabricDatagen(FabricDataGenerator.Pack pack) {
        pack.addProvider(HomeostaticRecipeProvider::new);
    }

}
