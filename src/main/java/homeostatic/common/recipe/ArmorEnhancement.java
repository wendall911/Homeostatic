package homeostatic.common.recipe;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import com.mojang.datafixers.util.Pair;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import net.minecraftforge.registries.ForgeRegistryEntry;

import homeostatic.common.TagManager;
import homeostatic.Homeostatic;

public class ArmorEnhancement extends CustomRecipe {

    public static final RecipeSerializer<ArmorEnhancement> ARMOR_ENHANCEMENT_SERIALIZER = new ArmorEnhancement.Serializer();

    public static void init() {
        ARMOR_ENHANCEMENT_SERIALIZER.setRegistryName(new ResourceLocation(Homeostatic.MODID + ":armor_enhancement"));
    }

    public ArmorEnhancement(ResourceLocation loc) {
        super(loc);
    }

    @Override
    public boolean matches(CraftingContainer pContainer, Level pLevel) {
        Pair<ItemStack, TagKey> check = checkContainer(pContainer);
        ItemStack armor = check.getFirst();
        TagKey tagKey = check.getSecond();

        return tagKey != null && armor != null;
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer) {
        Pair<ItemStack, TagKey> check = checkContainer(pContainer);
        ItemStack armorCopy = check.getFirst().copy();
        TagKey tagKey = check.getSecond();
        CompoundTag tags = armorCopy.getTag();

        if (tagKey == TagManager.Items.INSULATION) {
            if (!tags.contains("insulation")) {
                tags.putBoolean("insulation", true);
            }
        }
        else if (tagKey == TagManager.Items.WATERPROOF) {
            if (!tags.contains("waterproof")) {
                tags.putBoolean("waterproof", true);
            }
        }
        else if (tagKey == TagManager.Items.RADIATION_PROTECTION) {
            if (!tags.contains("radiation_protection")) {
                tags.putBoolean("radiation_protection", true);
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
        return ARMOR_ENHANCEMENT_SERIALIZER;
    }

    public Pair<ItemStack, TagKey> checkContainer(CraftingContainer pContainer) {
        List<ItemStack> ingredients = Lists.newArrayList();
        TagKey<Item> tagKey = null;
        ItemStack armor = null;
        boolean hasInsulation = false;
        boolean hasWaterproof = false;
        boolean hasRadiation = false;

        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            ItemStack ingredient = pContainer.getItem(i);

            if (ingredient.is(TagManager.Items.INSULATION) && !hasWaterproof && !hasRadiation) {
                hasInsulation = true;
                tagKey = TagManager.Items.INSULATION;
                ingredients.add(ingredient);
            }
            else if (ingredient.is(TagManager.Items.WATERPROOF) && !hasInsulation && !hasRadiation) {
                hasWaterproof = true;
                tagKey = TagManager.Items.WATERPROOF;
                ingredients.add(ingredient);
            }
            else if (ingredient.is(TagManager.Items.RADIATION_PROTECTION) && !hasWaterproof && !hasInsulation) {
                hasRadiation = true;
                tagKey = TagManager.Items.RADIATION_PROTECTION;
                ingredients.add(ingredient);
            }
            else if (ingredient.getItem() instanceof ArmorItem) {
                armor = ingredient;
            }
        }

        if (ingredients.size() != 3) {
            tagKey = null;
        }

        return Pair.of(armor, tagKey);
    }

    public static class Type implements RecipeType<ArmorEnhancement> {

        public static final Type INSTANCE = new Type();

        private Type() {}

    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ArmorEnhancement> {

        @Override
        public ArmorEnhancement fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            return new ArmorEnhancement(pRecipeId);
        }

        @Override
        public ArmorEnhancement fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            return new ArmorEnhancement(pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ArmorEnhancement pRecipe) {}

    }

}
