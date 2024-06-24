package homeostatic.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.item.HomeostaticItems;

import static homeostatic.Homeostatic.loc;

public class HomeostaticItemModelProvider extends FabricModelProvider {

    public HomeostaticItemModelProvider(FabricDataOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "Homoestatic - Item Models";
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(HomeostaticItems.LEATHER_FLASK, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(HomeostaticItems.PURIFIED_WATER_BUCKET, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(HomeostaticItems.WATER_FILTER, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(HomeostaticItems.BOOK, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(HomeostaticItems.THERMOMETER, ModelTemplates.FLAT_ITEM);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createAirLikeBlock(HomeostaticBlocks.PURIFIED_WATER_FLUID, loc("block/fluid/still_water"));
    }

}
