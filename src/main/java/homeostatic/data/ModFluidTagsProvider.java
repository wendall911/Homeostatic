package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import homeostatic.Homeostatic;
import homeostatic.common.TagManager;
import homeostatic.common.fluid.HomeostaticFluids;

public class ModFluidTagsProvider extends FluidTagsProvider {

    public ModFluidTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper helper) {
        super(packOutput, lookupProvider, Homeostatic.MODID, helper);
    }

    @Override
    public String getName() {
        return "Homeostatic - Fluid Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TagManager.Fluids.PURIFIED_WATER)
            .add(HomeostaticFluids.PURIFIED_WATER);
    }

}