package homeostatic.common;

import java.util.function.Supplier;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.util.Lazy;

import homeostatic.Homeostatic;
import homeostatic.common.item.HomeostaticItems;

public class CreativeTabs {
/*
    public static final CreativeTabBase ITEM_TAB_GROUP = new CreativeTabBase(Homeostatic.MODID + ".items", () -> new ItemStack(HomeostaticItems.PURIFIED_WATER_BUCKET));

    public static class CreativeTabBase extends CreativeModeTab {

        private final Lazy<ItemStack> iconStack;

        public CreativeTabBase(String label, Supplier<ItemStack> iconStack) {
            super(label);
            this.iconStack = Lazy.of(iconStack);
        }

        @Override
        public ItemStack makeIcon() {
            return iconStack.get();
        }

    }
 */
}
