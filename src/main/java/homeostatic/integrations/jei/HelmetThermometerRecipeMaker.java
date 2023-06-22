package homeostatic.integrations.jei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import homeostatic.Homeostatic;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.util.RegistryHelper;

public final class HelmetThermometerRecipeMaker {

    public static List<CraftingRecipe> createRecipes() {
        String group = "jei.helmet";
        List<CraftingRecipe> recipes = new ArrayList<>();
        Ingredient thermometer = Ingredient.of(HomeostaticItems.THERMOMETER);

        RegistryHelper.getRegistry(Registries.ITEM).stream()
                .filter(ArmorItem.class::isInstance)
                .filter(armorItem -> ((ArmorItem) armorItem).getEquipmentSlot() == EquipmentSlot.HEAD)
                .forEach(armorItem -> {
                    ItemStack armorStack = new ItemStack(armorItem);
                    Ingredient baseArmorIngredient = Ingredient.of(armorItem.asItem());
                    CompoundTag tag = armorStack.getOrCreateTag();
                    NonNullList<Ingredient> recipeInputs = NonNullList.of(Ingredient.EMPTY, baseArmorIngredient, thermometer);

                    tag.putBoolean("thermometer", true);
                    recipes.add(new ShapelessRecipe(new ResourceLocation(Homeostatic.MODID, group + ".thermometer"), group, CraftingBookCategory.EQUIPMENT, armorStack, recipeInputs));
                });

        return recipes;
    }

}