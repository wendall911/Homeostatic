package homeostatic.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;

import homeostatic.Homeostatic;
import homeostatic.common.item.DrinkableItem;
import homeostatic.common.item.DrinkableItemManager;

public class DrinkableItemsProvider implements DataProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Map<ResourceLocation, DrinkableItem> DRINKABLE_ITEMS = new HashMap<>();
    private final DataGenerator dataGenerator;
    private final String modid;

    public DrinkableItemsProvider(DataGenerator dataGenerator, String modid) {
        this.dataGenerator = dataGenerator;
        this.modid = modid;
    }

    protected void addDrinkableItems() {
        // Minecraft
        add(mcLoc("honey_bottle"), 4, 1.0F,  0, 0, 0.0F);
        add(mcLoc("milk_bucket"), 9, 2.1F,  0, 0, 0.0F);
        add(mcLoc("enchanted_golden_apple"), 9, 2.1F,  0, 0, 0.0F);
        add(mcLoc("golden_apple"), 6, 1.2F,  0, 0, 0.0F);
        add(mcLoc("beetroot_soup"), 3, 0.7F,  0, 0, 0.0F);
        add(mcLoc("mushroom_stew"), 3, 0.7F,  0, 0, 0.0F);
        add(mcLoc("rabbit_stew"), 3, 0.7F,  0, 0, 0.0F);
        add(mcLoc("glow_berries"), 2, 0.3F,  0, 0, 0.0F);
        add(mcLoc("sweet_berries"), 2, 0.3F,  0, 0, 0.0F);
        add(mcLoc("melon_slice"), 2, 0.6F,  0, 0, 0.0F);
        add(mcLoc("apple"), 2, 0.6F,  0, 0, 0.0F);
        add(mcLoc("suspicious_stew"), 1, 0.0F, 45, 200, 0.3F);
    }

    protected void add(ResourceLocation loc, int amount, float saturation, int potency, int duration, float chance) {
        DRINKABLE_ITEMS.put(loc, new DrinkableItem(loc, amount, saturation, potency, duration, chance));
    }

    @Override
    public String getName() {
        return "Homeostatic - Drinkable Items";
    }

    @Override
    public void run(HashCache pCache) throws IOException {
        addDrinkableItems();

        Path output = dataGenerator.getOutputFolder();

        for (Map.Entry<ResourceLocation, DrinkableItem> entry : DRINKABLE_ITEMS.entrySet()) {
            Path drinkableItemsPath = getPath(output, entry.getKey());

            try {
                DataProvider.save(GSON, pCache, DrinkableItemManager.parseDrinkableItem(entry.getValue()), drinkableItemsPath);
            }
            catch (IOException e) {
                Homeostatic.LOGGER.error("Couldn't save homeostatic drinkable items %s %s", drinkableItemsPath, e);
            }
        }
    }

    private static Path getPath(Path output, ResourceLocation loc) {
        return output.resolve("data/" + loc.getNamespace() + "/environment/drinkable/" + loc.getPath() + ".json");
    }

    private static ResourceLocation mcLoc(String path) {
        return new ResourceLocation("minecraft", path);
    }

}
