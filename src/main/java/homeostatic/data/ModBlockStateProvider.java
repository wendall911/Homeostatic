package homeostatic.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.Homeostatic;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Homeostatic.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Homeostatic - Block State and Models";
    }

    @Override
    protected void registerStatesAndModels() {
        String fullName = HomeostaticBlocks.PURIFIED_WATER_FLUID.getName().getString();
        String name = fullName.substring(fullName.lastIndexOf('.') + 1);

        ModelFile modelFile = models()
                .withExistingParent(name, modLoc("block/purified_water_fluid"));
        BlockModelBuilder purifiedWater = models()
                .withExistingParent(name, modLoc("block/purified_water_fluid"))
                .texture("particle", modLoc("block/fluid/still_water"));

        getVariantBuilder(HomeostaticBlocks.PURIFIED_WATER_FLUID)
                .forAllStates(state -> ConfiguredModel.builder().modelFile(modelFile).build());
    }
}
