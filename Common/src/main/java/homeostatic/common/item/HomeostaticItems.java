package homeostatic.common.item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.Homeostatic;

public final class HomeostaticItems {

    private static final Map<ResourceLocation, Item> ALL = new LinkedHashMap<>();

    public static final Item LEATHER_FLASK = make(
        "leather_flask",
        new LeatherFlask(new Item.Properties())
    );
    public static final Item PURIFIED_WATER_BUCKET = make(
        "purified_water_bucket",
        new BucketItem(HomeostaticFluids.PURIFIED_WATER, new Item.Properties().stacksTo(1))
    );
    public static final Item WATER_FILTER = make(
        "water_filter",
        new Item(new Item.Properties())
    );
    public static final Item BOOK = make(
        "book",
        new HomeostaticBook(new Item.Properties().stacksTo(1), "book")
    );
    public static final Item THERMOMETER = make(
        "thermometer",
        new Item(new Item.Properties())
    );
    public static final Item PURIFIED_WATER_BOTTLE = make(
        "purified_water_bottle",
        new PurifiedWaterBottle(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16))
    );

    public static void init(BiConsumer<Item, ResourceLocation> consumer) {
        for (Map.Entry<ResourceLocation, Item> entry : ALL.entrySet()) {
            consumer.accept(entry.getValue(), entry.getKey());
        }
    }

    public static <T extends Item> T make(String name, T item) {
        ResourceLocation loc = Homeostatic.loc(name);

        ALL.put(loc, item);

        return item;
    }

    public static Map<ResourceLocation, Item> getAll() {
        return ALL;
    }

}
