package homeostatic.data;

import homeostatic.Homeostatic;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;

import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, Homeostatic.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Homeostatic - Block Tags";
    }

    @Override
    protected void addTags() {
    }

}
