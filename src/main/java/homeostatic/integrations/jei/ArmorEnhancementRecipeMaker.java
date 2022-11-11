package homeostatic.integrations.jei;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import homeostatic.common.TagManager;
import homeostatic.Homeostatic;

public final class ArmorEnhancementRecipeMaker {

    public static List<CraftingRecipe> createRecipes() {
        String group = "jei.armor.enhancement";
        List<CraftingRecipe> recipes = new ArrayList<>();
        Ingredient wool = Ingredient.of(TagManager.Items.INSULATION);
        Ingredient waterproof = Ingredient.of(TagManager.Items.WATERPROOF);
        Ingredient radiation = Ingredient.of(TagManager.Items.RADIATION_PROTECTION);

        Registry.ITEM.stream()
                .filter(ArmorItem.class::isInstance)
                .forEach(armorItem -> {
                    Ingredient baseArmorIngredient = Ingredient.of(armorItem.asItem());
                    ItemStack woolArmorStack = new ItemStack(armorItem);
                    ItemStack waterproofArmorStack = new ItemStack(armorItem);
                    ItemStack radiationArmorStack = new ItemStack(armorItem);
                    CompoundTag woolArmorStackTag = woolArmorStack.getTag();
                    CompoundTag waterproofArmorStackTag = waterproofArmorStack.getTag();
                    CompoundTag radiationArmorStackTag = radiationArmorStack.getTag();
                    NonNullList<Ingredient> insulatedInputs = NonNullList.of(Ingredient.EMPTY, baseArmorIngredient, wool, wool, wool);
                    NonNullList<Ingredient> waterproofInputs = NonNullList.of(Ingredient.EMPTY, baseArmorIngredient, waterproof, waterproof, waterproof);
                    NonNullList<Ingredient> radiationInputs = NonNullList.of(Ingredient.EMPTY, baseArmorIngredient, radiation, radiation, radiation);

                    if (woolArmorStackTag != null) {
                        woolArmorStackTag.putBoolean("insulation", true);
                        recipes.add(new ShapelessRecipe(new ResourceLocation(Homeostatic.MODID, group + ".insulated"), group, woolArmorStack, insulatedInputs));
                    }

                    if (waterproofArmorStackTag != null) {
                        waterproofArmorStackTag.putBoolean("waterproof", true);
                        recipes.add(new ShapelessRecipe(new ResourceLocation(Homeostatic.MODID, group + ".waterproof"), group, waterproofArmorStack, waterproofInputs));

                    }

                    if (radiationArmorStackTag != null) {
                        radiationArmorStackTag.putBoolean("radiation_protection", true);
                        recipes.add(new ShapelessRecipe(new ResourceLocation(Homeostatic.MODID, group + ".radiation_resistance"), group, radiationArmorStack, radiationInputs));
                    }
                });

        return recipes;
    }

}