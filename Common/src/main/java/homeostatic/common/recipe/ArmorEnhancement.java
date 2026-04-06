package homeostatic.common.recipe;

import java.util.List;

import org.jspecify.annotations.NonNull;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import homeostatic.common.component.HomeostaticComponents;
import homeostatic.common.TagManager;

public class ArmorEnhancement extends CustomRecipe {

    public static final ArmorEnhancement INSTANCE = new ArmorEnhancement();
    public static final MapCodec<ArmorEnhancement> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorEnhancement> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<ArmorEnhancement> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public boolean matches(@NonNull CraftingInput craftingInput, @NonNull Level level) {
        Pair<ItemStack, TagKey<Item>> check = checkContainer(craftingInput);
        ItemStack armor = check.getFirst();
        TagKey<Item> tagKey = check.getSecond();

        return tagKey != null && armor != null;
    }

    @Override
    public @NonNull ItemStack assemble(CraftingInput craftingInput) {
        Pair<ItemStack, TagKey<Item>> check = checkContainer(craftingInput);
        ItemStack armorCopy = check.getFirst().copy();
        TagKey<Item> tagKey = check.getSecond();
        CompoundTag tags = armorCopy.getOrDefault(HomeostaticComponents.ARMOR, CustomData.EMPTY).copyTag();
        boolean save = false;

        if (tagKey == TagManager.Items.INSULATION) {
            if (!tags.contains("insulation")) {
                save = true;
                tags.putBoolean("insulation", true);
            }
        }
        else if (tagKey == TagManager.Items.WATERPROOF) {
            if (!tags.contains("waterproof")) {
                save = true;
                tags.putBoolean("waterproof", true);
            }
        }
        else if (tagKey == TagManager.Items.RADIATION_PROTECTION) {
            if (!tags.contains("radiation_protection")) {
                save = true;
                tags.putBoolean("radiation_protection", true);
            }
        }

        if (save) {
            armorCopy.set(HomeostaticComponents.ARMOR, CustomData.of(tags));
        }

        return armorCopy;
    }

    @Override
    public @NonNull RecipeSerializer<ArmorEnhancement> getSerializer() {
        return SERIALIZER;
    }

    public Pair<ItemStack, TagKey<Item>> checkContainer(CraftingInput craftingInput) {
        List<ItemStack> ingredients = Lists.newArrayList();
        TagKey<Item> tagKey = null;
        ItemStack armor = null;
        boolean hasInsulation = false;
        boolean hasWaterproof = false;
        boolean hasRadiation = false;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack ingredient = craftingInput.getItem(i);

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
            else if (ingredient.get(DataComponents.EQUIPPABLE) != null) {
                armor = ingredient;
            }
        }

        if (ingredients.size() != 3) {
            tagKey = null;
        }

        return Pair.of(armor, tagKey);
    }

}
