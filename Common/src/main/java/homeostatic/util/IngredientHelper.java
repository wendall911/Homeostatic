package homeostatic.util;

import java.util.stream.StreamSupport;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientHelper {

    public static Ingredient fromTag(TagKey<Item> tag) {
        HolderSet<Item> itemHolderSet = HolderSet.direct(
            StreamSupport.stream(BuiltInRegistries.ITEM.getTagOrEmpty(tag).spliterator(), false).toList()
        );

        return Ingredient.of(itemHolderSet);
    }

}
