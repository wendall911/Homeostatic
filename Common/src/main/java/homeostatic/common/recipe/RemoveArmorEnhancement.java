package homeostatic.common.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RemoveArmorEnhancement extends CustomRecipe {

    public RemoveArmorEnhancement(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingContainer pContainer, Level pLevel) {
        Pair<ItemStack, ItemStack> check = checkContainer(pContainer);
        ItemStack armor = check.getFirst();
        ItemStack removalItem = check.getSecond();

        return removalItem != null && armor != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess registryAccess) {
        Pair<ItemStack, ItemStack> check = checkContainer(pContainer);
        ItemStack armorCopy = check.getFirst().copy();
        ItemStack removalItem = check.getSecond();
        CompoundTag tags = armorCopy.getOrCreateTag();

        if (removalItem.is(Items.SHEARS)) {
            if (tags.contains("insulation")) {
                tags.remove("insulation");
            }
        }
        else if (removalItem.is(Items.LAVA_BUCKET)) {
            if (tags.contains("waterproof")) {
                tags.remove("waterproof");
            }
        }
        else if (removalItem.is(Items.WATER_BUCKET)) {
            if (tags.contains("radiation_protection")) {
                tags.remove("radiation_protection");
            }
        }

        return armorCopy;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return HomeostaticRecipes.REMOVE_ARMOR_ENHANCEMENT_SERIALIZER;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer pContainer) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(pContainer.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack item = pContainer.getItem(i);

            if (item.is(Items.SHEARS)) {
                ItemStack shears = item.copy();
                shears.hurt(1, RandomSource.create(), null);
                nonnulllist.set(i, shears);
            }
            else if (item.is(Items.WATER_BUCKET) || item.is(Items.LAVA_BUCKET)) {
                ItemStack bucket = new ItemStack(Items.BUCKET);
                nonnulllist.set(i, bucket);
            }
        }

        return nonnulllist;
    }

    public Pair<ItemStack, ItemStack> checkContainer(CraftingContainer pContainer) {
        List<ItemStack> ingredients = Lists.newArrayList();
        ItemStack removalItem = null;
        ItemStack armor = null;
        boolean hasInsulation = false;
        boolean hasWaterproof = false;
        boolean hasRadiation = false;

        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack ingredient = pContainer.getItem(i);

            if (ingredient.is(Items.SHEARS) && !hasWaterproof && !hasRadiation) {
                hasInsulation = true;
                removalItem = ingredient;
                ingredients.add(ingredient);
            }
            else if (ingredient.is(Items.LAVA_BUCKET) && !hasInsulation && !hasRadiation) {
                hasWaterproof = true;
                removalItem = ingredient;
                ingredients.add(ingredient);
            }
            else if (ingredient.is(Items.WATER_BUCKET) && !hasWaterproof && !hasInsulation) {
                hasRadiation = true;
                removalItem = ingredient;
                ingredients.add(ingredient);
            }
            else if (ingredient.getItem() instanceof ArmorItem) {
                armor = ingredient;
            }
        }

        if (ingredients.size() != 1) {
            removalItem = null;
        }

        return Pair.of(armor, removalItem);
    }

}