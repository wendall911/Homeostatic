package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;

import net.minecraftforge.common.data.ExistingFileHelper;

import homeostatic.common.TagManager;
import homeostatic.data.integration.ModIntegration;
import homeostatic.Homeostatic;

public class ModItemTagsProvider extends ItemTagsProvider {

    ModItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ModBlockTagsProvider blockTagsProvider, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), Homeostatic.MODID, helper);
    }

    @Override
    public String getName() {
        return "Homeostatic - Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TagManager.Items.INSULATION)
            .addTag(ItemTags.WOOL)
            .addOptional(ModIntegration.alexLoc("bear_fur"))
            .addOptional(ModIntegration.alexLoc("bison_fur"));

        this.tag(TagManager.Items.WATERPROOF)
            .addOptional(ModIntegration.ieLoc("duroplast"))
            .addTag(ItemTags.CANDLES);

        this.tag(TagManager.Items.RADIATION_PROTECTION)
            .addTag(ItemTags.CRIMSON_STEMS)
            .addTag(ItemTags.WARPED_STEMS)
            .addOptionalTag(ModIntegration.bygLoc("embur_logs"))
            .addOptional(ModIntegration.alexLoc("cockroach_wing"));
    }

}
