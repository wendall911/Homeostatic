package homeostatic.common.recipe;

import java.util.List;

import org.jspecify.annotations.NonNull;

import com.google.common.collect.Lists;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import homeostatic.common.component.HomeostaticComponents;

public class RemoveArmorEnhancement extends CustomRecipe {

    public static final RemoveArmorEnhancement INSTANCE = new RemoveArmorEnhancement();
    public static final MapCodec<RemoveArmorEnhancement> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, RemoveArmorEnhancement> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<RemoveArmorEnhancement> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public boolean matches(@NonNull CraftingInput craftingInput, @NonNull Level level) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingInput);
        ItemStack armor = check.getFirst();
        ItemStack removalItem = check.getSecond();

        return removalItem != null && armor != null;
    }

    @Override
    public @NonNull ItemStack assemble(CraftingInput craftingInput) {
        Pair<ItemStack, ItemStack> check = checkContainer(craftingInput);
        ItemStack armorCopy = check.getFirst().copy();
        ItemStack removalItem = check.getSecond();
        CompoundTag tags = armorCopy.getOrDefault(HomeostaticComponents.ARMOR, CustomData.EMPTY).copyTag();
        boolean save = false;

        if (removalItem.is(Items.SHEARS)) {
            if (tags.contains("insulation")) {
                save = true;
                tags.remove("insulation");
            }
        }
        else if (removalItem.is(Items.LAVA_BUCKET)) {
            if (tags.contains("waterproof")) {
                save = true;
                tags.remove("waterproof");
            }
        }
        else if (removalItem.is(Items.WATER_BUCKET)) {
            if (tags.contains("radiation_protection")) {
                save = true;
                tags.remove("radiation_protection");
            }
        }

        if (save) {
            armorCopy.set(HomeostaticComponents.ARMOR, CustomData.of(tags));
        }

        return armorCopy;
    }

    @Override
    public @NonNull RecipeSerializer<RemoveArmorEnhancement> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NonNull NonNullList<ItemStack> getRemainingItems(CraftingInput craftingInput) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(craftingInput.size(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack stack = craftingInput.getItem(i);

            if (stack.is(Items.SHEARS) && stack.getItem().getCraftingRemainder() != null) {
                nonnulllist.set(i, stack.getItem().getCraftingRemainder().create());
            }
            else if (stack.is(Items.WATER_BUCKET) || stack.is(Items.LAVA_BUCKET)) {
                ItemStack bucket = new ItemStack(Items.BUCKET);
                nonnulllist.set(i, bucket);
            }
        }

        return nonnulllist;
    }

    public Pair<ItemStack, ItemStack> checkContainer(CraftingInput craftingInput) {
        List<ItemStack> ingredients = Lists.newArrayList();
        ItemStack removalItem = null;
        ItemStack armor = null;
        boolean hasInsulation = false;
        boolean hasWaterproof = false;
        boolean hasRadiation = false;

        for (int i = 0; i < craftingInput.size(); i++) {
            ItemStack ingredient = craftingInput.getItem(i);

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
            else if (ingredient.get(DataComponents.EQUIPPABLE) != null) {
                armor = ingredient;
            }
        }

        if (ingredients.size() != 1) {
            removalItem = null;
        }

        return Pair.of(armor, removalItem);
    }

}