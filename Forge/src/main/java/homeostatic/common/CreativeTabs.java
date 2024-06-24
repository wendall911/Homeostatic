package homeostatic.common;

import java.util.Map;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.minecraftforge.registries.RegistryObject;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.Homeostatic;

public class CreativeTabs extends HomeostaticModule {

    public static RegistryObject<CreativeModeTab> ALL_ITEMS_TAB;
    public static boolean initialized = false;

    public static void init() {
        if (!initialized) {
            initialized = true;
            ALL_ITEMS_TAB = registerTab("items", Items.WATER_BUCKET);
        }
    }

    private static RegistryObject<CreativeModeTab> registerTab(String name, Item icon) {
        return CREATIVE_MODE_TAB_REGISTRY.register(name, () -> CreativeModeTab.builder().icon(() -> new ItemStack(icon))
            .title(Component.translatable(Homeostatic.MODID + ".items"))
            .displayItems((features, output) -> {
                for (Map.Entry<ResourceLocation, Item> entry : HomeostaticItems.getAll().entrySet()) {
                    Item item = entry.getValue();
                    output.accept(new ItemStack(item));
                }
            }).build());
    }

}
