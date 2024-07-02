package homeostatic.common.recipe;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import homeostatic.common.component.HomeostaticComponents;
import homeostatic.common.item.HomeostaticItems;

public class HelmetThermometer extends CustomRecipe {

    public HelmetThermometer(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(@NotNull CraftingInput craftingInput, @NotNull Level level) {
        ItemStack armor = checkContainer(craftingInput);

        return armor != null;
    }

    @Override
    public ItemStack assemble(@NotNull CraftingInput craftingInput, HolderLookup.@NotNull Provider provider) {
        ItemStack armorCopy = checkContainer(craftingInput).copy();
        CompoundTag tags = armorCopy.getOrDefault(HomeostaticComponents.ARMOR, CustomData.EMPTY).copyTag();

        tags.putBoolean("thermometer", true);
        armorCopy.set(HomeostaticComponents.ARMOR, CustomData.of(tags));

        return armorCopy;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HomeostaticRecipes.HELMET_THERMOMETER_SERIALIZER;
    }

    public ItemStack checkContainer(CraftingInput craftingInput) {
        List<ItemStack> ingredients = Lists.newArrayList();
        ItemStack armor = null;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack ingredient = craftingInput.getItem(i);

            if (ingredient.is(HomeostaticItems.THERMOMETER)) {
                ingredients.add(ingredient);
            }
            else if (ingredient.getItem() instanceof ArmorItem armorItem && armorItem.getEquipmentSlot() == EquipmentSlot.HEAD) {
                armor = ingredient;
            }
        }

        if (ingredients.size() == 1 && armor != null) {
            return armor;
        }

        return null;
    }

}