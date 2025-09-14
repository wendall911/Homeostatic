package homeostatic.common.item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.Homeostatic;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumables;

public final class HomeostaticItems {

    private static final Map<ResourceLocation, Item> ALL = new LinkedHashMap<>();

    public static final Item LEATHER_FLASK = make(
        "leather_flask",
        new LeatherFlask(
            new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, Homeostatic.prefix("leather_flask")))
                .stacksTo(1)
                .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
        )
    );
    public static final Item PURIFIED_WATER_BUCKET = make(
        "purified_water_bucket",
        new BucketItem(
            HomeostaticFluids.PURIFIED_WATER,
            new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, Homeostatic.prefix("purified_water_bucket")))
                .stacksTo(1)
        )
    );
    public static final Item WATER_FILTER = make(
        "water_filter",
        new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Homeostatic.prefix("water_filter"))))
    );
    public static final Item BOOK = make(
        "book",
        new HomeostaticBook(
            new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, Homeostatic.prefix("book")))
                .stacksTo(1),
            "book"
        )
    );
    public static final Item THERMOMETER = make(
        "thermometer",
        new Item(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Homeostatic.prefix("thermometer"))))
    );
    public static final Item PURIFIED_WATER_BOTTLE = make(
        "purified_water_bottle",
        new PurifiedWaterBottle(
            new Item.Properties()
                .setId(ResourceKey.create(Registries.ITEM, Homeostatic.prefix("purified_water_bottle")))
                .craftRemainder(Items.GLASS_BOTTLE)
                .stacksTo(16)
                .component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
                .usingConvertsTo(Items.GLASS_BOTTLE)
        )
    );

    public static void init(BiConsumer<Item, ResourceLocation> consumer) {
        for (Map.Entry<ResourceLocation, Item> entry : ALL.entrySet()) {
            consumer.accept(entry.getValue(), entry.getKey());
        }
    }

    public static <T extends Item> T make(String name, T item) {
        ResourceLocation loc = Homeostatic.prefix(name);

        ALL.put(loc, item);

        return item;
    }

    public static Map<ResourceLocation, Item> getAll() {
        return ALL;
    }

}
