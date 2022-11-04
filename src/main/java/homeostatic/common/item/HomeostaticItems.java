package homeostatic.common.item;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegisterEvent;

import homeostatic.common.CreativeTabs;
import homeostatic.common.fluid.HomeostaticFluids;

public final class HomeostaticItems {

    public static RegisterEvent.RegisterHelper<Item> ITEM_REGISTRY;
    public static Item LEATHER_FLASK;
    public static Item PURIFIED_WATER_BUCKET;
    public static Item WATER_FILTER;
    public static Item BOOK;

    public static void init(RegisterEvent.RegisterHelper<Item> registryHelper) {
        ITEM_REGISTRY = registryHelper;

        LEATHER_FLASK = registerItem(
                "leather_flask",
                new LeatherFlask(new Item.Properties().stacksTo(1).setNoRepair().tab(CreativeTabs.ITEM_TAB_GROUP), 5000));

        PURIFIED_WATER_BUCKET = registerItem(
                "purified_water_bucket",
                new BucketItem(HomeostaticFluids.PURIFIED_WATER, new Item.Properties().stacksTo(1).tab(CreativeTabs.ITEM_TAB_GROUP)));

        WATER_FILTER = registerItem(
                "water_filter",
                new Item(new Item.Properties().tab(CreativeTabs.ITEM_TAB_GROUP)));

        BOOK = registerItem(
          "book",
          new HomeostaticBook(new Item.Properties().tab(CreativeTabs.ITEM_TAB_GROUP), "book")
        );
    }

    public static Item registerItem(String name, Item item) {
        ITEM_REGISTRY.register(name, item);

        return item;
    }

}