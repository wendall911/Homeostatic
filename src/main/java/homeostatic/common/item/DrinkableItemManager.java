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
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import homeostatic.common.TagManager;
import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
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
                Item item = ForgeRegistries.ITEMS.getValue(drinkableItem.loc());

                if (item != Items.AIR && item != null) {
                    ITEMS.put(item, drinkableItem);
                }
            }
            catch (Exception e) {
                Homeostatic.LOGGER.error("Couldn't parse drinkable item %s %s", entry.getKey(), e);
            }
        }

        Homeostatic.LOGGER.info("Loaded %d drinkable items", ITEMS.size());
    }

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        event.addListener(new DrinkableItemManager());
    }

}
