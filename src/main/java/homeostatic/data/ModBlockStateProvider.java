package homeostatic.data;

import homeostatic.common.block.HomeostaticBlocks;
import net.minecraft.data.DataGenerator;

import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import homeostatic.Homeostatic;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Homeostatic.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Homeostatic - Block State and Models";
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile modelFile = models()
                .withExistingParent(HomeostaticBlocks.PURIFIED_WATER_FLUID.getRegistryName().toString(), modLoc("block/purified_water_fluid"));
        BlockModelBuilder purifiedWater = models()
                .withExistingParent(HomeostaticBlocks.PURIFIED_WATER_FLUID.getRegistryName().toString(), modLoc("block/purified_water_fluid"))
                .texture("particle", modLoc("block/fluid/still_water"));

        getVariantBuilder(HomeostaticBlocks.PURIFIED_WATER_FLUID)
                .forAllStates(state -> ConfiguredModel.builder().modelFile(modelFile).build());
    }
}
