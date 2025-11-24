package homeostaticseasons.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import homeostaticseasons.common.TagManager;
import homeostaticseasons.util.RegistryHelper;

public class HomeostaticSeasonsBlockTagsProvider extends IntrinsicHolderTagsProvider<Block> {

    @SuppressWarnings("deprecation")
    public HomeostaticSeasonsBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Registries.BLOCK, lookupProvider, (block) -> block.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        getOrCreateRawBuilder(TagManager.Blocks.REPLACEABLE_BY_SNOW)
            .addOptionalTag(BlockTags.SAPLINGS.location())
            .addOptionalTag(BlockTags.FLOWERS.location())
            .addOptionalTag(BlockTags.TALL_FLOWERS.location())
            .addElement(RegistryHelper.getBlockId(Blocks.SHORT_GRASS))
            .addElement(RegistryHelper.getBlockId(Blocks.TALL_GRASS))
            .addElement(RegistryHelper.getBlockId(Blocks.FERN))
            .addElement(RegistryHelper.getBlockId(Blocks.LARGE_FERN))
            .addElement(RegistryHelper.getBlockId(Blocks.DEAD_BUSH))
            .addElement(RegistryHelper.getBlockId(Blocks.GLOW_LICHEN))
            .addOptionalTag(TagManager.Blocks.FLOWERS.location())
            .addOptionalTag(TagManager.Blocks.FS_REPLACEABLE_BY_SNOW.location());
    }

}
