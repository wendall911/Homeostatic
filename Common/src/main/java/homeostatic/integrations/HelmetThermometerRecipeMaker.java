package homeostatic.integrations;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import homeostatic.common.component.HomeostaticComponents;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.util.RegistryHelper;

import static homeostatic.Homeostatic.loc;

public final class HelmetThermometerRecipeMaker {

    public static List<RecipeHolder<CraftingRecipe>> createRecipes(String plugin) {
        String group = plugin + ".helmet";
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();
        Ingredient thermometer = Ingredient.of(HomeostaticItems.THERMOMETER);

        RegistryHelper.getRegistry(Registries.ITEM).stream()
                .filter(ArmorItem.class::isInstance)
                .filter(armorItem -> ((ArmorItem) armorItem).getEquipmentSlot() == EquipmentSlot.HEAD)
                .forEach(armorItem -> {
                    ItemStack armorStack = new ItemStack(armorItem);
                    Ingredient baseArmorIngredient = Ingredient.of(armorItem.asItem());
                    CompoundTag tag = armorStack.getOrDefault(HomeostaticComponents.ARMOR, CustomData.EMPTY).copyTag();
                    NonNullList<Ingredient> recipeInputs = NonNullList.of(Ingredient.EMPTY, baseArmorIngredient, thermometer);

                    tag.putBoolean("thermometer", true);
                    armorStack.set(HomeostaticComponents.ARMOR, CustomData.of(tag));
                    recipes.add(new RecipeHolder<>(
                        loc(group + ".thermometer"),
                        new ShapelessRecipe(group, CraftingBookCategory.EQUIPMENT, armorStack, recipeInputs)
                    ));
                });

        return recipes;
    }

}