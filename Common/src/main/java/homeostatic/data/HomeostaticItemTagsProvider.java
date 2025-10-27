package homeostatic.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.TagManager;
import homeostatic.data.integration.ModIntegration;

public class HomeostaticItemTagsProvider extends IntrinsicHolderTagsProvider<Item> {

    HomeostaticItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Registries.ITEM, lookupProvider, (item) -> item.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateRawBuilder(TagManager.Items.INSULATION)
            .addOptionalTag(ItemTags.WOOL.location())
            .addOptionalElement(ModIntegration.alexLoc("bear_fur"))
            .addOptionalElement(ModIntegration.alexLoc("bison_fur"));

        getOrCreateRawBuilder(TagManager.Items.WATERPROOF)
            .addOptionalElement(ModIntegration.ieLoc("duroplast"))
            .addOptionalTag(ItemTags.CANDLES.location());

        getOrCreateRawBuilder(TagManager.Items.RADIATION_PROTECTION)
            .addOptionalTag(ItemTags.CRIMSON_STEMS.location())
            .addOptionalTag(ItemTags.WARPED_STEMS.location())
            .addOptionalTag(ModIntegration.bygLoc("embur_logs"))
            .addOptionalElement(ModIntegration.alexLoc("cockroach_wing"));

        getOrCreateRawBuilder(TagManager.Items.INSULATED_ARMOR)
            .addOptionalElement(ModIntegration.scubaLoc("scuba_helmet"))
            .addOptionalElement(ModIntegration.scubaLoc("scuba_chestplate"))
            .addOptionalElement(ModIntegration.scubaLoc("scuba_leggings"))
            .addOptionalElement(ModIntegration.scubaLoc("scuba_boots"))
            .addTag(TagManager.Items.SEWINGKIT_WEARABLE.location())
            .addOptionalElement(ModIntegration.ldMeadowLoc("fur_helmet"))
            .addOptionalElement(ModIntegration.ldMeadowLoc("fur_chestplate"))
            .addOptionalElement(ModIntegration.ldMeadowLoc("fur_leggings"))
            .addOptionalElement(ModIntegration.ldMeadowLoc("fur_boots"));

        getOrCreateRawBuilder(TagManager.Items.WATERPROOF_ARMOR)
            .addOptionalElement(ModIntegration.scubaLoc("scuba_helmet"))
            .addOptionalElement(ModIntegration.scubaLoc("scuba_chestplate"))
            .addOptionalElement(ModIntegration.scubaLoc("scuba_leggings"))
            .addOptionalElement(ModIntegration.scubaLoc("scuba_boots"));

        this.tag(TagManager.Items.RADIATION_PROTECTED_ARMOR)
            .add(Items.NETHERITE_HELMET)
            .add(Items.NETHERITE_CHESTPLATE)
            .add(Items.NETHERITE_LEGGINGS)
            .add(Items.NETHERITE_BOOTS);

        getOrCreateRawBuilder(TagManager.Items.SEWINGKIT_WEARABLE)
            .addOptionalElement(ModIntegration.skLoc("wool_hat"))
            .addOptionalElement(ModIntegration.skLoc("wool_shirt"))
            .addOptionalElement(ModIntegration.skLoc("wool_pants"))
            .addOptionalElement(ModIntegration.skLoc("wool_shoes"));

        this.tag(ItemTags.BOOKSHELF_BOOKS).add(HomeostaticItems.BOOK);
    }

}
