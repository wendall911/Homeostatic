package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.common.data.ExistingFileHelper;

import homeostatic.Homeostatic;

public class ModBlockTagsProvider extends IntrinsicHolderTagsProvider<Block> {

    public ModBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
        super(packOutput, Registries.BLOCK, lookupProvider, (block) -> block.builtInRegistryHolder().key(), Homeostatic.MODID, helper);
    }

    @Override
    public String getName() {
        return "Homeostatic - Block Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
    }

}
