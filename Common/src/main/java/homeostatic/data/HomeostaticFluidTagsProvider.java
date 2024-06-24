package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;

import homeostatic.common.TagManager;
import homeostatic.common.fluid.HomeostaticFluids;

public class HomeostaticFluidTagsProvider extends FluidTagsProvider {

    public HomeostaticFluidTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TagManager.Fluids.PURIFIED_WATER)
            .add(HomeostaticFluids.PURIFIED_WATER);
    }

}