package homeostatic.common.item;

import net.minecraft.world.item.ItemStack;

public class LeatherFlask extends WaterContainerItem {

    public LeatherFlask(Properties properties, int capacity) {
        super(properties, capacity);
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return new ItemStack(HomeostaticItems.LEATHER_FLASK);
    }

}
