package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.Fluid;

import homeostatic.common.TagManager;
import homeostatic.common.fluid.HomeostaticFluids;

public class HomeostaticFluidTagsProvider extends FabricTagsProvider<Fluid> {

    public HomeostaticFluidTagsProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Registries.FLUID, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        this.tag(TagManager.Fluids.PURIFIED_WATER)
            .add(ResourceKey.create(Registries.FLUID, HomeostaticFluids.PURIFIED_WATER_ID));
    }

}