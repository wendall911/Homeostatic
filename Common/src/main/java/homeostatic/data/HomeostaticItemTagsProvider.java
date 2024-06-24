package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import homeostatic.common.TagManager;
import homeostatic.data.integration.ModIntegration;

public class HomeostaticItemTagsProvider extends ItemTagsProvider {

    HomeostaticItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagsProvider) {
        super(packOutput, lookupProvider, blockTagsProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TagManager.Items.INSULATION)
            .addOptionalTag(ItemTags.WOOL.location())
            .addOptional(ModIntegration.alexLoc("bear_fur"))
            .addOptional(ModIntegration.alexLoc("bison_fur"));

        this.tag(TagManager.Items.WATERPROOF)
            .addOptional(ModIntegration.ieLoc("duroplast"))
            .addOptionalTag(ItemTags.CANDLES.location());

        this.tag(TagManager.Items.RADIATION_PROTECTION)
            .addOptionalTag(ItemTags.CRIMSON_STEMS.location())
            .addOptionalTag(ItemTags.WARPED_STEMS.location())
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
