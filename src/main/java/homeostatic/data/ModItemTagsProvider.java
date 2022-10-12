package homeostatic.data;

import homeostatic.data.integration.ModIntegration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;

import net.minecraftforge.common.data.ExistingFileHelper;

import homeostatic.common.TagManager;
import homeostatic.Homeostatic;

public class ModItemTagsProvider extends ItemTagsProvider {

    ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagsProvider, Homeostatic.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Homeostatic - Item Tags";
    }

    @Override
    protected void addTags() {
        this.tag(TagManager.Items.INSULATION)
            .addTag(ItemTags.WOOL)
            .addOptional(ModIntegration.alexLoc("bear_fur"))
            .addOptional(ModIntegration.alexLoc("bison_fur"));

        this.tag(TagManager.Items.WATERPROOF)
            .addOptional(ModIntegration.ieLoc("duroplast"))
            .addTag(ItemTags.CANDLES);

        this.tag(TagManager.Items.RADIATION_PROTECTION)
            .addTag(ItemTags.CRIMSON_STEMS)
            .addOptionalTag(ModIntegration.bygLoc("embur_logs"))
            .addOptional(ModIntegration.alexLoc("cockroach_wing"));
    }

}
