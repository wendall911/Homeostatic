package homeostatic.common.item;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import homeostatic.common.TagManager;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import homeostatic.Homeostatic;
import homeostatic.util.RegistryHelper;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DrinkableItemManager extends SimpleJsonResourceReloadListener {

    private static final Map<ResourceLocation, DrinkableItem> ITEMS = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(DrinkableItem.class, new DrinkableItem.Serializer()).create();
    private static DrinkableItemManager INSTANCE;

    private static final DrinkableItem FRUIT = new DrinkableItem(new ResourceLocation("forge:fruits"), 2, 0.6F, 0, 0, 0.0F);

    public DrinkableItemManager() {
        super(GSON, "environment/drinkable");
    }

    public static JsonElement parseDrinkableItem(DrinkableItem drinkableItem) {
        return GSON.toJsonTree(drinkableItem);
    }

    public static DrinkableItem get(ItemStack stack) {
        ResourceLocation loc = RegistryHelper.getRegistry(Registries.ITEM).getKey(stack.getItem());
        DrinkableItem drinkableItem = ITEMS.get(loc);

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

                ITEMS.put(drinkableItem.loc(), drinkableItem);
            }
            catch (Exception e) {
                Homeostatic.LOGGER.error("Couldn't parse drinkable item %s %s", entry.getKey(), e);
            }
        }

        Homeostatic.LOGGER.info("Loaded %d drinkable items", ITEMS.size());
    }

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        event.addListener(INSTANCE = new DrinkableItemManager());
    }

}
