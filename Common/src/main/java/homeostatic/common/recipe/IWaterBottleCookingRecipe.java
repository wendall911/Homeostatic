package homeostatic.common.recipe;

import homeostatic.common.item.HomeostaticItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public interface IWaterBottleCookingRecipe {

    default ItemStack assemble(ItemStack result) {
        return new ItemStack(HomeostaticItems.PURIFIED_WATER_BOTTLE);
    }

    default boolean matches(SingleRecipeInput recipeInput) {
        ItemStack stack = recipeInput.getItem(0);
        PotionContents potioncontents = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

        return potioncontents.is(Potions.WATER);
    }

}
