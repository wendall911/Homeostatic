package homeostatic.common;

import java.util.Map;
import java.util.function.BiConsumer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import homeostatic.Homeostatic;
import homeostatic.common.item.HomeostaticItems;

import static homeostatic.Homeostatic.loc;

public class FabricCreativeTabs {

    public static final CreativeModeTab HOMEOSTATIC_ITEM_GROUP = FabricItemGroup.builder()
        .icon(() -> new ItemStack(HomeostaticItems.PURIFIED_WATER_BUCKET))
        .title(Component.translatable(Homeostatic.MODID + ".items"))
        .displayItems((features, output) -> {
            for (Map.Entry<ResourceLocation, Item> entry : HomeostaticItems.getAll().entrySet()) {
                Item item = entry.getValue();
                output.accept(new ItemStack(item));
            }
        }).build();

    public static void init(BiConsumer<CreativeModeTab, ResourceLocation> consumer) {
        consumer.accept(HOMEOSTATIC_ITEM_GROUP, loc("items"));
    }

}
