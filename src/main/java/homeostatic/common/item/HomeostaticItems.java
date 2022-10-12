package homeostatic.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.IForgeRegistry;

import homeostatic.common.CreativeTabs;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.Homeostatic;

public final class HomeostaticItems {

    public static IForgeRegistry<Item> ITEM_REGISTRY;
    public static Item LEATHER_FLASK;
    public static Item PURIFIED_WATER_BUCKET;
    public static Item WATER_FILTER;


    public static void init(IForgeRegistry<Item> registry) {
        ITEM_REGISTRY = registry;

        LEATHER_FLASK = registerItem(
                "leather_flask",
                new LeatherFlask(new Item.Properties().stacksTo(1).setNoRepair().tab(CreativeTabs.ITEM_TAB_GROUP), 5000));

        PURIFIED_WATER_BUCKET = registerItem(
                "purified_water_bucket",
                new BucketItem(() -> HomeostaticFluids.PURIFIED_WATER, new Item.Properties().stacksTo(1).tab(CreativeTabs.ITEM_TAB_GROUP)));

        WATER_FILTER = registerItem(
                "water_filter",
                new Item(new Item.Properties().tab(CreativeTabs.ITEM_TAB_GROUP)));
    }

    public static Item registerItem(String name, Item item) {
        Item itemConfigured = item.setRegistryName(Homeostatic.loc(name));

        ITEM_REGISTRY.register(itemConfigured);

        return item;
    }

}
