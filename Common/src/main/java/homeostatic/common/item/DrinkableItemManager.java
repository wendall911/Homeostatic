package homeostatic.common.item;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import homeostatic.common.TagManager;
import homeostatic.Homeostatic;
import homeostatic.platform.Services;

public class DrinkableItemManager extends SimpleJsonResourceReloadListener {

    private static final Map<Item, DrinkableItem> ITEMS = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(DrinkableItem.class, new DrinkableItem.Serializer()).create();

    private static final DrinkableItem FRUIT = new DrinkableItem(new ResourceLocation("forge:fruits"), 2, 0.6F, 0, 0, 0.0F);

    public DrinkableItemManager() {
        super(GSON, "environment/drinkable");
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

        return null;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        ITEMS.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            try {
                DrinkableItem drinkableItem = GSON.fromJson(entry.getValue(), DrinkableItem.class);
                Item item = Services.PLATFORM.getItem(drinkableItem.loc());

                if (item != Items.AIR && item != null) {
                    ITEMS.put(item, drinkableItem);
                }
            }
            catch (Exception e) {
                Homeostatic.LOGGER.error("Couldn't parse drinkable item {} {}", entry.getKey(), e);
            }
        }

        Homeostatic.LOGGER.info("Loaded {} drinkable items", ITEMS.size());
    }

}
