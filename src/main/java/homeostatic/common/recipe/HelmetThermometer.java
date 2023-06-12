package homeostatic.common.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import homeostatic.common.item.HomeostaticItems;

public class HelmetThermometer extends CustomRecipe {

    public HelmetThermometer(ResourceLocation resourceLocation, CraftingBookCategory category) {
        super(resourceLocation, category);
    }

    @Override
    public boolean matches(CraftingContainer pContainer, Level pLevel) {
        ItemStack armor = checkContainer(pContainer);

        return armor != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer) {
        ItemStack armorCopy = checkContainer(pContainer).copy();
        CompoundTag tags = armorCopy.getOrCreateTag();

        tags.putBoolean("thermometer", true);

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

    public ItemStack checkContainer(CraftingContainer pContainer) {
        List<ItemStack> ingredients = Lists.newArrayList();
        ItemStack armor = null;

        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack ingredient = pContainer.getItem(i);

            if (ingredient.is(HomeostaticItems.THERMOMETER)) {
                ingredients.add(ingredient);
            }
            else if (ingredient.getItem() instanceof ArmorItem armorItem && armorItem.getSlot() == EquipmentSlot.HEAD) {
                armor = ingredient;
            }
        }

        if (ingredients.size() == 1 && armor != null) {
            return armor;
        }

        return null;
    }

}