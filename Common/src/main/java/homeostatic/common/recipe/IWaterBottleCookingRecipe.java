package homeostatic.common.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.alchemy.PotionUtils;

import homeostatic.common.item.HomeostaticItems;

public interface IWaterBottleCookingRecipe {

    default ItemStack assemble() {
        return new ItemStack(HomeostaticItems.PURIFIED_WATER_BOTTLE);
    }

    default boolean matches(Container container) {
        ItemStack stack = container.getItem(0);
        Potion potion = PotionUtils.getPotion(stack);

        return potion.equals(Potions.WATER);
    }

}
