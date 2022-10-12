package homeostatic.data;

import javax.annotation.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import homeostatic.Homeostatic;
import homeostatic.common.TagManager;
import homeostatic.common.fluid.HomeostaticFluids;

public class ModFluidTagsProvider extends FluidTagsProvider {

    public ModFluidTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, Homeostatic.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Homeostatic - Fluid Tags";
    }

    @Override
    protected void addTags() {
        this.tag(TagManager.Fluids.PURIFIED_WATER)
            .add(HomeostaticFluids.PURIFIED_WATER);
    }

}