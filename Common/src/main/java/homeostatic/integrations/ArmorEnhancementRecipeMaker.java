package homeostatic.integrations;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import homeostatic.common.component.HomeostaticComponents;
import homeostatic.common.TagManager;
import homeostatic.util.IngredientHelper;
import homeostatic.util.RegistryHelper;

import static homeostatic.Homeostatic.prefix;

public final class ArmorEnhancementRecipeMaker {

    public static List<Pair<ItemStack, RecipeHolder<CraftingRecipe>>> createRecipes(String plugin) {
        String group = plugin + ".armor.enhancement";
        List<Pair<ItemStack, RecipeHolder<CraftingRecipe>>> recipes = new ArrayList<>();

        Ingredient wool = IngredientHelper.fromTag(TagManager.Items.INSULATION);
        Ingredient waterproof = IngredientHelper.fromTag(TagManager.Items.WATERPROOF);
        Ingredient radiation = IngredientHelper.fromTag(TagManager.Items.RADIATION_PROTECTION);

        RegistryHelper.getRegistry(Registries.ITEM).stream()
                .filter(item -> item.components().get(DataComponents.EQUIPPABLE) != null)
                .forEach(armorItem -> {
                    Ingredient baseArmorIngredient = Ingredient.of(armorItem.asItem());
                    ItemStack woolArmorStack = new ItemStack(armorItem);
                    ItemStack waterproofArmorStack = new ItemStack(armorItem);
                    ItemStack radiationArmorStack = new ItemStack(armorItem);
                    CompoundTag woolArmorStackTag = woolArmorStack.getOrDefault(HomeostaticComponents.ARMOR, CustomData.EMPTY).copyTag();
                    CompoundTag waterproofArmorStackTag = waterproofArmorStack.getOrDefault(HomeostaticComponents.ARMOR, CustomData.EMPTY).copyTag();
                    CompoundTag radiationArmorStackTag = radiationArmorStack.getOrDefault(HomeostaticComponents.ARMOR, CustomData.EMPTY).copyTag();
                    NonNullList<Ingredient> insulatedInputs = NonNullList.of(null, baseArmorIngredient, wool, wool, wool);
                    NonNullList<Ingredient> waterproofInputs = NonNullList.of(null, baseArmorIngredient, waterproof, waterproof, waterproof);
                    NonNullList<Ingredient> radiationInputs = NonNullList.of(null, baseArmorIngredient, radiation, radiation, radiation);

                    woolArmorStackTag.putBoolean("insulation", true);
                    woolArmorStack.set(HomeostaticComponents.ARMOR, CustomData.of(woolArmorStackTag));
                    recipes.add(Pair.of(woolArmorStack, new RecipeHolder<>(
                        ResourceKey.create(Registries.RECIPE, prefix(group + ".insulated")),
                        new ShapelessRecipe(
                            RecipeBuilder.createCraftingCommonInfo(true),
                            RecipeBuilder.createCraftingBookInfo(RecipeCategory.MISC, group),
                            new ItemStackTemplate(woolArmorStack.getItem(), 1),
                            insulatedInputs
                        )
                    )));

                    waterproofArmorStackTag.putBoolean("waterproof", true);
                    waterproofArmorStack.set(HomeostaticComponents.ARMOR, CustomData.of(waterproofArmorStackTag));
                    recipes.add(Pair.of(waterproofArmorStack, new RecipeHolder<>(
                        ResourceKey.create(Registries.RECIPE, prefix(group + ".waterproof")),
                        new ShapelessRecipe(
                            RecipeBuilder.createCraftingCommonInfo(true),
                            RecipeBuilder.createCraftingBookInfo(RecipeCategory.MISC, group),
                            new ItemStackTemplate(waterproofArmorStack.getItem(), 1),
                            waterproofInputs
                        )
                    )));

                    radiationArmorStackTag.putBoolean("radiation_protection", true);
                    radiationArmorStack.set(HomeostaticComponents.ARMOR, CustomData.of(radiationArmorStackTag));
                    recipes.add(Pair.of(radiationArmorStack, new RecipeHolder<>(
                        ResourceKey.create(Registries.RECIPE, prefix(group + ".radiation_resistance")),
                        new ShapelessRecipe(
                            RecipeBuilder.createCraftingCommonInfo(true),
                            RecipeBuilder.createCraftingBookInfo(RecipeCategory.MISC, group),
                            new ItemStackTemplate(radiationArmorStack.getItem(), 1),
                            radiationInputs
                        )
                    )));
                });

        return recipes;
    }

}
