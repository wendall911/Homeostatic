package homeostatic.data;

import homeostatic.data.integration.ModIntegration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;

import net.minecraft.world.item.Items;
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
            .addTag(ItemTags.WARPED_STEMS)
            .addOptionalTag(ModIntegration.bygLoc("embur_logs"))
            .addOptional(ModIntegration.alexLoc("cockroach_wing"));

        this.tag(TagManager.Items.INSULATED_ARMOR)
            .addOptional(ModIntegration.scubaLoc("scuba_helmet"))
            .addOptional(ModIntegration.scubaLoc("scuba_chestplate"))
            .addOptional(ModIntegration.scubaLoc("scuba_leggings"))
            .addOptional(ModIntegration.scubaLoc("scuba_boots"));

        this.tag(TagManager.Items.WATERPROOF_ARMOR)
            .addOptional(ModIntegration.scubaLoc("scuba_helmet"))
            .addOptional(ModIntegration.scubaLoc("scuba_chestplate"))
            .addOptional(ModIntegration.scubaLoc("scuba_leggings"))
            .addOptional(ModIntegration.scubaLoc("scuba_boots"));

        this.tag(TagManager.Items.RADIATION_PROTECTED_ARMOR)
            .add(Items.NETHERITE_HELMET)
            .add(Items.NETHERITE_CHESTPLATE)
            .add(Items.NETHERITE_LEGGINGS)
            .add(Items.NETHERITE_BOOTS);
    }

}
