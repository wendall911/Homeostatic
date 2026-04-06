package homeostatic.common.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jspecify.annotations.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import homeostatic.common.TagManager;
import homeostatic.Homeostatic;
import homeostatic.network.SyncDrinkableItems;
import homeostatic.platform.Services;

public class DrinkableItemManager extends SimpleJsonResourceReloadListener<JsonElement> {

    private static final Map<Item, DrinkableItem> ITEMS = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(DrinkableItem.class, new DrinkableItem.Serializer()).create();

    private static final DrinkableItem FRUIT = new DrinkableItem(TagManager.Items.FRUITS.location(), 2, 0.6F, 0, 0, 0.0F);
    private static final DrinkableItem ROOT_VEGETABLE = new DrinkableItem(TagManager.Items.ROOT_VEGETABLES.location(), 1, 0.1F, 0, 0, 0.0F);
    private static final DrinkableItem VEGETABLE = new DrinkableItem(TagManager.Items.VEGETABLES.location(), 1, 0.1F, 0, 0, 0.0F);

    public DrinkableItemManager() {
        super(ExtraCodecs.JSON, FileToIdConverter.json("environment/drinkable"));
    }

    public static JsonElement parseDrinkableItem(DrinkableItem drinkableItem) {
        return GSON.toJsonTree(drinkableItem);
    }

    public static DrinkableItem get(ItemStack stack) {
        DrinkableItem drinkableItem = ITEMS.get(stack.getItem());

        if (drinkableItem != null) {
            return drinkableItem;
        }
        else if(stack.is(TagManager.Items.FRUITS)) {
            return FRUIT;
        }
        else if(stack.is(TagManager.Items.ROOT_VEGETABLES)) {
            return ROOT_VEGETABLE;
        }
        else if(stack.is(TagManager.Items.VEGETABLES)) {
            return VEGETABLE;
        }

        return null;
    }

    public static void update(List<DrinkableItem> drinkableItems) {
        ITEMS.clear();

        for (DrinkableItem drinkableItem : drinkableItems) {
            Optional<Reference<Item>> optionalItemReference = BuiltInRegistries.ITEM.get(drinkableItem.loc());

            optionalItemReference.ifPresent(itemReference -> ITEMS.put(itemReference.value(), drinkableItem));
        }

        Homeostatic.LOGGER.info("Updated {} drinkable items", ITEMS.size());
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> pObject, @NonNull ResourceManager pResourceManager, @NonNull ProfilerFiller pProfiler) {
        ITEMS.clear();

        for (Map.Entry<Identifier, JsonElement> entry : pObject.entrySet()) {
            try {
                DrinkableItem drinkableItem = GSON.fromJson(entry.getValue(), DrinkableItem.class);
                Optional<Holder.Reference<Item>> item = BuiltInRegistries.ITEM.get(drinkableItem.loc());

                item.ifPresent(itemReference -> ITEMS.put(itemReference.value(), drinkableItem));
            }
            catch (Exception e) {
                Homeostatic.LOGGER.error("Couldn't parse drinkable item {} {}", entry.getKey(), e);
            }
        }

        Homeostatic.LOGGER.info("Loaded {} drinkable items", ITEMS.size());
    }

    public static void syncWithClient(ServerPlayer player) {
        if (player != null) {
            List<DrinkableItem> drinkableItems = ITEMS.values().stream().toList();
            DataResult<Tag> result = Codec.list(DrinkableItem.CODEC).encodeStart(NbtOps.INSTANCE, drinkableItems);
            Tag data = result.getOrThrow((items) -> {
                throw new IllegalStateException("Failed to encode drinkable items: " + items);
            });

            Services.PLATFORM.sendPacketToPlayer(new SyncDrinkableItems(data), player);
        }
    }


}
