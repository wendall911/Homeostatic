package homeostatic.common.item;

import java.util.LinkedHashMap;
import java.util.Map;

import homeostatic.common.CreativeTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegisterEvent;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.Homeostatic;

public final class HomeostaticItems {

    private static final Map<ResourceLocation, Item> ALL = new LinkedHashMap<>();
    public static RegisterEvent.RegisterHelper<Item> ITEM_REGISTRY;
    public static Item LEATHER_FLASK;
    public static Item PURIFIED_WATER_BUCKET;
    public static Item WATER_FILTER;
    public static Item BOOK;
    public static Item THERMOMETER;

    public static void init(RegisterEvent.RegisterHelper<Item> registryHelper) {
        ITEM_REGISTRY = registryHelper;

        LEATHER_FLASK = registerItem(
                "leather_flask",
                new LeatherFlask(new Item.Properties().stacksTo(1).setNoRepair(), 5000));

        PURIFIED_WATER_BUCKET = registerItem(
                "purified_water_bucket",
                new BucketItem(HomeostaticFluids.PURIFIED_WATER, new Item.Properties().stacksTo(1)));

        WATER_FILTER = registerItem(
                "water_filter",
                new Item(new Item.Properties()));

        BOOK = registerItem(
          "book",
          new HomeostaticBook(new Item.Properties(), "book")
        );

        THERMOMETER = registerItem(
                "thermometer",
                new Item(new Item.Properties()));

        CreativeTabs.init();
    }

    public static Item registerItem(String name, Item item) {
        ResourceLocation loc = Homeostatic.loc(name);

        ITEM_REGISTRY.register(loc, item);
        ALL.put(loc, item);

        return item;
    }

    public static Map<ResourceLocation, Item> getAll() {
        return ALL;
    }

}