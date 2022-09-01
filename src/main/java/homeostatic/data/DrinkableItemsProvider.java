package homeostatic.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

import homeostatic.Homeostatic;
import homeostatic.common.item.DrinkableItem;
import homeostatic.common.item.DrinkableItemManager;
import homeostatic.data.integration.ModIntegration;

public class DrinkableItemsProvider implements DataProvider {

    private final Map<ResourceLocation, DrinkableItem> DRINKABLE_ITEMS = new HashMap<>();
    private final DataGenerator dataGenerator;
    private final String modid;

    public DrinkableItemsProvider(DataGenerator dataGenerator, String modid) {
        this.dataGenerator = dataGenerator;
        this.modid = modid;
    }

    protected void addDrinkableItems() {
        // Minecraft
        add(ModIntegration.mcLoc("honey_bottle"), 4, 1.0F,  0, 0, 0.0F);
        add(ModIntegration.mcLoc("milk_bucket"), 9, 2.1F,  0, 0, 0.0F);
        add(ModIntegration.mcLoc("enchanted_golden_apple"), 9, 2.1F,  0, 0, 0.0F);
        add(ModIntegration.mcLoc("golden_apple"), 6, 1.2F,  0, 0, 0.0F);
        addSoup(ModIntegration.mcLoc("beetroot_soup"));
        addSoup(ModIntegration.mcLoc("mushroom_stew"));
        addSoup(ModIntegration.mcLoc("rabbit_stew"));
        addBerries(ModIntegration.mcLoc("glow_berries"));
        addBerries(ModIntegration.mcLoc("sweet_berries"));
        addFruit(ModIntegration.mcLoc("melon_slice"));
        addFruit(ModIntegration.mcLoc("apple"));
        add(ModIntegration.mcLoc("suspicious_stew"), 1, 0.0F, 45, 200, 0.3F);
        add(ModIntegration.mcLoc("poisonous_potato"), 1, 0.0F, 45, 200, 0.2F);
        addVeggie(ModIntegration.mcLoc("beetroot"));
        addVeggie(ModIntegration.mcLoc("carrot"));
        addVeggie(ModIntegration.mcLoc("potato"));
        addCake(ModIntegration.mcLoc("cake"));

        // Oh The Biomes You'll Go
        add(ModIntegration.bygLoc("holly_berries"), 1, 0.0F, 45, 200, 0.5F);
        addBerries(ModIntegration.bygLoc("nightshade_berries"));
        addBerries(ModIntegration.bygLoc("blueberries"));
        addPie(ModIntegration.bygLoc("nightshade_berry_pie"));
        addPie(ModIntegration.bygLoc("blueberry_pie"));
        add(ModIntegration.bygLoc("crimson_berries"), 1, 0.0F,  0, 0, 0.0F);
        add(ModIntegration.bygLoc("crimson_berry_pie"), 2, 0.0F,  0, 0, 0.0F);
        addFruit(ModIntegration.bygLoc("baobab_fruit"));
        addFruit(ModIntegration.bygLoc("green_apple"));
        addPie(ModIntegration.bygLoc("green_apple_pie"));
        add(ModIntegration.bygLoc("aloe_vera_juice"), 4, 1.0F,  0, 0, 0.0F);
        add(ModIntegration.bygLoc("white_puffball_cap"), 1, 0.0F,  0, 0, 0.0F);
        add(ModIntegration.bygLoc("cooked_white_puffball_cap"), 1, 0.0F,  0, 0, 0.0F);
        add(ModIntegration.bygLoc("white_puffball_stew"), 2, 0.0F,  0, 0, 0.0F);

        // Croptopia
        // Drinks
        addDrink(ModIntegration.croptopiaLoc("rum"));
        addDrink(ModIntegration.croptopiaLoc("beer"));
        addDrink(ModIntegration.croptopiaLoc("wine"));
        addDrink(ModIntegration.croptopiaLoc("pumpkin_spice_latte"));
        addDrink(ModIntegration.croptopiaLoc("crema"));
        addDrink(ModIntegration.croptopiaLoc("apple_juice"));
        addDrink(ModIntegration.croptopiaLoc("orange_juice"));
        addDrink(ModIntegration.croptopiaLoc("grape_juice"));
        addDrink(ModIntegration.croptopiaLoc("cranberry_juice"));
        addDrink(ModIntegration.croptopiaLoc("saguaro_juice"));
        addDrink(ModIntegration.croptopiaLoc("tomato_juice"));
        addDrink(ModIntegration.croptopiaLoc("pineapple_juice"));
        addDrink(ModIntegration.croptopiaLoc("lemonade"));
        addDrink(ModIntegration.croptopiaLoc("soy_milk"));
        addDrink(ModIntegration.croptopiaLoc("tea"));
        addDrink(ModIntegration.croptopiaLoc("limeade"));
        addDrink(ModIntegration.croptopiaLoc("horchata"));
        addDrink(ModIntegration.croptopiaLoc("mead"));
        addDrink(ModIntegration.croptopiaLoc("coffee"));

        // Berries
        addBerries(ModIntegration.croptopiaLoc("elderberry"));
        addBerries(ModIntegration.croptopiaLoc("blackberry"));
        addBerries(ModIntegration.croptopiaLoc("cranberry"));
        addBerries(ModIntegration.croptopiaLoc("raspberry"));
        addBerries(ModIntegration.croptopiaLoc("blueberry"));
        addBerries(ModIntegration.croptopiaLoc("strawberry"));
        addBerries(ModIntegration.croptopiaLoc("grape"));

        // Jams
        addJam(ModIntegration.croptopiaLoc("elderberry_jam"));
        addJam(ModIntegration.croptopiaLoc("blackberry_jam"));
        addJam(ModIntegration.croptopiaLoc("strawberry_jam"));
        addJam(ModIntegration.croptopiaLoc("blueberry_jam"));
        addJam(ModIntegration.croptopiaLoc("raspberry_jam"));
        addJam(ModIntegration.croptopiaLoc("yam_jam"));
        addJam(ModIntegration.croptopiaLoc("grape_jam"));
        addJam(ModIntegration.croptopiaLoc("peach_jam"));
        addJam(ModIntegration.croptopiaLoc("apricot_jam"));
        addJam(ModIntegration.croptopiaLoc("cherry_jam"));
        addJam(ModIntegration.croptopiaLoc("peanut_butter_and_jam"));
        addJam(ModIntegration.croptopiaLoc("toast_with_jam"));

        // Smoothies/milkshakes
        addShake(ModIntegration.croptopiaLoc("kale_smoothie"));
        addShake(ModIntegration.croptopiaLoc("fruit_smoothie"));
        addShake(ModIntegration.croptopiaLoc("strawberry_smoothie"));
        addShake(ModIntegration.croptopiaLoc("banana_smoothie"));
        addShake(ModIntegration.croptopiaLoc("chocolate_milkshake"));
        addShake(ModIntegration.croptopiaLoc("eton_mess"));

        // Fruits
        addFruit(ModIntegration.croptopiaLoc("lemon"));
        addFruit(ModIntegration.croptopiaLoc("lime"));
        addFruit(ModIntegration.croptopiaLoc("honeydew"));
        addFruit(ModIntegration.croptopiaLoc("kumquat"));
        addFruit(ModIntegration.croptopiaLoc("nectarine"));
        addFruit(ModIntegration.croptopiaLoc("fig"));
        addFruit(ModIntegration.croptopiaLoc("kiwi"));
        addFruit(ModIntegration.croptopiaLoc("orange"));
        addFruit(ModIntegration.croptopiaLoc("zucchini"));
        addFruit(ModIntegration.croptopiaLoc("plum"));
        addFruit(ModIntegration.croptopiaLoc("persimmon"));
        addFruit(ModIntegration.croptopiaLoc("pear"));
        addFruit(ModIntegration.croptopiaLoc("candied_kumquats"));
        addFruit(ModIntegration.croptopiaLoc("banana"));
        addFruit(ModIntegration.croptopiaLoc("cantaloupe"));
        addFruit(ModIntegration.croptopiaLoc("cherry"));
        addFruit(ModIntegration.croptopiaLoc("date"));
        addFruit(ModIntegration.croptopiaLoc("currant"));
        addFruit(ModIntegration.croptopiaLoc("grapefruit"));
        addFruit(ModIntegration.croptopiaLoc("peach"));
        addFruit(ModIntegration.croptopiaLoc("saguaro"));
        addFruit(ModIntegration.croptopiaLoc("mango"));
        addFruit(ModIntegration.croptopiaLoc("dragonfruit"));
        addFruit(ModIntegration.croptopiaLoc("apricot"));
        addFruit(ModIntegration.croptopiaLoc("pineapple"));
        addFruit(ModIntegration.croptopiaLoc("starfruit"));

        // Pie
        addPie(ModIntegration.croptopiaLoc("pecan_pie"));
        addPie(ModIntegration.croptopiaLoc("apple_pie"));
        addPie(ModIntegration.croptopiaLoc("cherry_pie"));
        addPie(ModIntegration.croptopiaLoc("banana_cream_pie"));
        addPie(ModIntegration.croptopiaLoc("rhubarb_pie"));

        // Icecream
        addIcecream(ModIntegration.croptopiaLoc("mango_ice_cream"));
        addIcecream(ModIntegration.croptopiaLoc("rum_raisin_ice_cream"));
        addIcecream(ModIntegration.croptopiaLoc("pecan_ice_cream"));
        addIcecream(ModIntegration.croptopiaLoc("chocolate_ice_cream"));
        addIcecream(ModIntegration.croptopiaLoc("vanilla_ice_cream"));
        addIcecream(ModIntegration.croptopiaLoc("strawberry_ice_cream"));
        addIcecream(ModIntegration.croptopiaLoc("kiwi_sorbet"));

        // Soups and Stews
        addSoup(ModIntegration.croptopiaLoc("potato_soup"));
        addSoup(ModIntegration.croptopiaLoc("leek_soup"));
        addSoup(ModIntegration.croptopiaLoc("pumpkin_soup"));
        addSoup(ModIntegration.croptopiaLoc("beef_stew"));
        addSoup(ModIntegration.croptopiaLoc("nether_wart_stew"));

        // Cake
        addSpecialCake(ModIntegration.croptopiaLoc("tres_leche_cake"));
        addSpecialCake(ModIntegration.croptopiaLoc("fruit_cake"));

        // Veggies
        addVeggie(ModIntegration.croptopiaLoc("cucumber"));
        addVeggie(ModIntegration.croptopiaLoc("bellpepper"));
        addVeggie(ModIntegration.croptopiaLoc("spaghetti_squash"));
        addVeggie(ModIntegration.croptopiaLoc("onion"));
        addVeggie(ModIntegration.croptopiaLoc("baked_yam"));
        addVeggie(ModIntegration.croptopiaLoc("roasted_squash"));
        addVeggie(ModIntegration.croptopiaLoc("sweetpotato"));
        addVeggie(ModIntegration.croptopiaLoc("squash"));
        addVeggie(ModIntegration.croptopiaLoc("soybean"));
        addVeggie(ModIntegration.croptopiaLoc("olive"));
        addVeggie(ModIntegration.croptopiaLoc("coconut"));
        addVeggie(ModIntegration.croptopiaLoc("eggplant"));
        addVeggie(ModIntegration.croptopiaLoc("avocado"));
        addVeggie(ModIntegration.croptopiaLoc("tomato"));
        addVeggie(ModIntegration.croptopiaLoc("grilled_eggplant"));
        addVeggie(ModIntegration.croptopiaLoc("broccoli"));

        // Fruit Salad
        add(ModIntegration.croptopiaLoc("fruit_salad"), 6, 1.8F,  0, 0, 0.0F);

        // Dishes
        addMeal(ModIntegration.croptopiaLoc("shepherds_pie"));
        addMeal(ModIntegration.croptopiaLoc("ratatouille"));
        addMeal(ModIntegration.croptopiaLoc("lemon_coconut_bar"));
        addMeal(ModIntegration.croptopiaLoc("beef_stir_fry"));
        addMeal(ModIntegration.croptopiaLoc("eggplant_parmesan"));
        addMeal(ModIntegration.croptopiaLoc("cheese_cake"));
        addMeal(ModIntegration.croptopiaLoc("supreme_pizza"));
        addMeal(ModIntegration.croptopiaLoc("rhubarb_crisp"));
        addMeal(ModIntegration.croptopiaLoc("ajvar"));
        addMeal(ModIntegration.croptopiaLoc("chicken_and_dumplings"));
        addMeal(ModIntegration.croptopiaLoc("anchovy_pizza"));
        addMeal(ModIntegration.croptopiaLoc("sticky_toffee_pudding"));
        addMeal(ModIntegration.croptopiaLoc("banana_nut_bread"));
        addMeal(ModIntegration.croptopiaLoc("figgy_pudding"));
        addMeal(ModIntegration.croptopiaLoc("pineapple_pepperoni_pizza"));
        addMeal(ModIntegration.croptopiaLoc("cucumber_salad"));
        addMeal(ModIntegration.croptopiaLoc("cheese_pizza"));
        addMeal(ModIntegration.croptopiaLoc("veggie_salad"));
        addMeal(ModIntegration.croptopiaLoc("steamed_broccoli"));
        addMeal(ModIntegration.croptopiaLoc("yoghurt"));
        addMeal(ModIntegration.croptopiaLoc("roasted_radishes"));
        addMeal(ModIntegration.croptopiaLoc("pizza"));
        addMeal(ModIntegration.croptopiaLoc("tofu"));
        addMeal(ModIntegration.croptopiaLoc("treacle_tart"));
        addMeal(ModIntegration.croptopiaLoc("salsa"));
        addMeal(ModIntegration.croptopiaLoc("trifle"));
        addMeal(ModIntegration.croptopiaLoc("tofu_and_dumplings"));

        // Farmer's Delight
        // Drinks
        addDrink(ModIntegration.fdLoc("hot_cocoa"));
        addDrink(ModIntegration.fdLoc("melon_juice"));
        addDrink(ModIntegration.fdLoc("milk_bottle"));
        addDrink(ModIntegration.fdLoc("apple_cider"));

        // Veggies
        addVeggie(ModIntegration.fdLoc("mixed_salad"));
        addVeggie(ModIntegration.fdLoc("cabbage"));
        addVeggie(ModIntegration.fdLoc("onion"));
        addVeggie(ModIntegration.fdLoc("wild_beetroots"));
        addVeggie(ModIntegration.fdLoc("tomato_sauce"));
        addVeggie(ModIntegration.fdLoc("wild_cabbages"));
        addVeggie(ModIntegration.fdLoc("wild_potatoes"));
        addVeggie(ModIntegration.fdLoc("wild_onions"));
        addVeggie(ModIntegration.fdLoc("cabbage_leaf"));
        addVeggie(ModIntegration.fdLoc("wild_carrots"));
        addVeggie(ModIntegration.fdLoc("pumpkin_slice"));
        addVeggie(ModIntegration.fdLoc("wild_tomatoes"));
        addVeggie(ModIntegration.fdLoc("tomato"));

        // Pie
        addPie(ModIntegration.fdLoc("apple_pie_slice"));
        addPie(ModIntegration.fdLoc("sweet_berry_cheesecake_slice"));
        addPie(ModIntegration.fdLoc("chocolate_pie_slice"));
        addPie(ModIntegration.fdLoc("cake_slice"));

        // Icecream
        addIcecream(ModIntegration.fdLoc("melon_popsicle"));

        // Soups and Stews
        addSoup(ModIntegration.fdLoc("noodle_soup"));
        addSoup(ModIntegration.fdLoc("baked_cod_stew"));
        addSoup(ModIntegration.fdLoc("fish_stew"));
        addSoup(ModIntegration.fdLoc("vegetable_soup"));
        addSoup(ModIntegration.fdLoc("pumpkin_soup"));
        addSoup(ModIntegration.fdLoc("chicken_soup"));
        addSoup(ModIntegration.fdLoc("beef_stew"));

        // Dishes
        addMeal(ModIntegration.fdLoc("cabbage_rolls"));
        addMeal(ModIntegration.fdLoc("dumplings"));
        addMeal(ModIntegration.fdLoc("shepherds_pie"));
        addMeal(ModIntegration.fdLoc("steak_and_potatoes"));

        // Fruit Salad
        add(ModIntegration.fdLoc("fruit_salad"), 12, 1.8F,  0, 0, 0.0F);
        add(ModIntegration.fdLoc("stuffed_pumpkin"), 8, 0.6F,  0, 0, 0.0F);
        add(ModIntegration.fdLoc("stuffed_potato"), 8, 0.9F,  0, 0, 0.0F);

        // Xerca
        // Drinks
        addDrink(ModIntegration.xercaLoc("item_glass_of_milk"));
        addDrink(ModIntegration.xercaLoc("soda"));
        addDrink(ModIntegration.xercaLoc("item_ice_tea"));
        addDrink(ModIntegration.xercaLoc("item_tomato_juice"));
        addDrink(ModIntegration.xercaLoc("item_carrot_juice"));
        addDrink(ModIntegration.xercaLoc("item_wheat_juice"));
        addDrink(ModIntegration.xercaLoc("sweet_berry_juice"));
        addDrink(ModIntegration.xercaLoc("item_apple_juice"));
        addDrink(ModIntegration.xercaLoc("item_melon_juice"));
        addDrink(ModIntegration.xercaLoc("item_pumpkin_juice"));
        addDrink(ModIntegration.xercaLoc("cola"));
        addDrink(ModIntegration.xercaLoc("sake"));
        addDrink(ModIntegration.xercaLoc("item_full_teacup_0"));
        addDrink(ModIntegration.xercaLoc("item_full_teacup_6"));
        addDrink(ModIntegration.xercaLoc("item_full_teacup_5"));
        addDrink(ModIntegration.xercaLoc("item_full_teacup_4"));
        addDrink(ModIntegration.xercaLoc("item_full_teacup_3"));
        addDrink(ModIntegration.xercaLoc("item_full_teacup_2"));
        addDrink(ModIntegration.xercaLoc("item_full_teacup_1"));

        // Dirty Water
        add(ModIntegration.xercaLoc("item_glass_of_water"), 1, 0.0F, 45, 200, 0.2F);

        // Meal
        addMeal(ModIntegration.xercaLoc("item_shish_kebab"));
        addMeal(ModIntegration.xercaLoc("raw_shish_kebab"));
        addMeal(ModIntegration.xercaLoc("item_chubby_doner"));

        // Pizza
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_meat_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_chicken"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_meat_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_chicken_chicken"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_fish_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_chicken_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_fish_meat"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_fish_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_fish_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_meat_meat"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_fish_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_meat_meat_meat"));
        addVeggie(ModIntegration.xercaLoc("pizza"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_mushroom_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_meat_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_meat_meat"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_fish_fish"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_mushroom_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_meat_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_meat_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_mushroom_mushroom_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_pepperoni_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_meat_meat_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_mushroom_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_meat_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_mushroom_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_fish_meat"));
        addVeggie(ModIntegration.xercaLoc("pizza_meat_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_meat"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_fish_meat"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_fish_mushroom"));
        addVeggie(ModIntegration.xercaLoc("item_alexander"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_meat_mushroom_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_meat_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_mushroom_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_meat_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_meat_meat"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_meat_meat"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_chicken"));
        addVeggie(ModIntegration.xercaLoc("pizza_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_mushroom_mushroom_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_mushroom_mushroom_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_chicken_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_mushroom_mushroom_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_fish_fish"));
        addVeggie(ModIntegration.xercaLoc("pizza_meat_meat_meat"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_mushroom_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_fish"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_meat"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_mushroom_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_chicken_fish"));
        addVeggie(ModIntegration.xercaLoc("pizza_meat_meat_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_mushroom_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_mushroom_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_meat"));
        addVeggie(ModIntegration.xercaLoc("pizza_pepperoni_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_fish"));
        addVeggie(ModIntegration.xercaLoc("pizza_meat_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_fish_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_fish_meat"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_meat_meat"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_chicken_meat"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_meat_mushroom_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_fish_fish"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_fish"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_meat_meat"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_meat_meat_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_fish_fish"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_mushroom_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_meat"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_meat_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_meat_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_meat_meat_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_meat_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_chicken_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_chicken_fish"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_mushroom_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_meat_mushroom_mushroom"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_meat"));
        addVeggie(ModIntegration.xercaLoc("pizza_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_fish_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_fish_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_fish_mushroom"));
        addVeggie(ModIntegration.xercaLoc("pizza_mushroom_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_fish"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_chicken_chicken"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_fish_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_mushroom_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_mushroom_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_chicken_meat"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_pepperoni_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_chicken_meat_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("raw_pizza_chicken_chicken_pepperoni"));
        addVeggie(ModIntegration.xercaLoc("pizza_meat"));
        addVeggie(ModIntegration.xercaLoc("pizza_meat_mushroom_pepperoni"));

        // Cake
        addCake(ModIntegration.xercaLoc("item_apple_cupcake"));
        addCake(ModIntegration.xercaLoc("sweet_berry_cupcake_fancy"));
        addCake(ModIntegration.xercaLoc("item_fancy_apple_cupcake"));
        addCake(ModIntegration.xercaLoc("glowberry_cupcake"));
        addCake(ModIntegration.xercaLoc("item_cocoa_cupcake"));
        addCake(ModIntegration.xercaLoc("item_golden_cupcake"));
        addCake(ModIntegration.xercaLoc("item_melon_cupcake"));
        addCake(ModIntegration.xercaLoc("item_honey_cupcake"));
        addCake(ModIntegration.xercaLoc("sweet_berry_cupcake"));
        addCake(ModIntegration.xercaLoc("item_carrot_cupcake"));
        addCake(ModIntegration.xercaLoc("item_pumpkin_cupcake"));

        // Veggies
        addVeggie(ModIntegration.xercaLoc("item_tomato_slices"));
        addVeggie(ModIntegration.xercaLoc("item_potato_slices"));
        addVeggie(ModIntegration.xercaLoc("item_potato_fries"));

        // Pudding
        add(ModIntegration.xercaLoc("rice_pudding"), 4, 0.6F,  0, 0, 0.0F);
        add(ModIntegration.xercaLoc("baked_rice_pudding"), 3, 0.2F,  0, 0, 0.0F);

        // Yoghurt
        add(ModIntegration.xercaLoc("item_yoghurt"), 4, 0.6F,  0, 0, 0.0F);

        // Special
        add(ModIntegration.xercaLoc("item_honeyberry_yoghurt"), 8, 2.6F,  0, 0, 0.0F);

        // Morecraft
        // Stew and Soups
        addSoup(ModIntegration.morecraftLoc("dragon_stew"));
        addSoup(ModIntegration.morecraftLoc("beef_stew"));
        addSoup(ModIntegration.morecraftLoc("pork_stew"));
        addSoup(ModIntegration.morecraftLoc("mutton_stew"));
        addSoup(ModIntegration.morecraftLoc("chevon_stew"));
        addSoup(ModIntegration.morecraftLoc("chicken_stew"));
        addSoup(ModIntegration.morecraftLoc("fish_stew"));
        addSoup(ModIntegration.morecraftLoc("spider_stew"));

        // Nether
        add(ModIntegration.morecraftLoc("nether_apple"), 1, 0.0F, 45, 200, 0.2F);

        // Cake
        addCake(ModIntegration.morecraftLoc("cake_slice"));

        // Pie
        addPie(ModIntegration.morecraftLoc("sweetberry_pie"));
        addPie(ModIntegration.morecraftLoc("nether_apple_pie"));
        addPie(ModIntegration.morecraftLoc("apple_pie"));
    }

    protected void add(ResourceLocation loc, int amount, float saturation, int potency, int duration, float chance) {
        DRINKABLE_ITEMS.put(loc, new DrinkableItem(loc, amount, saturation, potency, duration, chance));
    }

    protected void addDrink(ResourceLocation loc) {
        add(loc, 3, 0.7F,  0, 0, 0.0F);
    }

    protected void addBerries(ResourceLocation loc) {
        add(loc, 2, 0.3F,  0, 0, 0.0F);
    }

    protected void addJam(ResourceLocation loc) {
        add(loc, 2, 0.6F,  0, 0, 0.0F);
    }

    protected void addPie(ResourceLocation loc) {
        add(loc, 3, 1.2F,  0, 0, 0.0F);
    }

    protected void addShake(ResourceLocation loc) {
        add(loc, 5, 1.2F,  0, 0, 0.0F);
    }

    protected void addFruit(ResourceLocation loc) {
        add(loc, 2, 0.6F,  0, 0, 0.0F);
    }

    protected void addIcecream(ResourceLocation loc) {
        add(loc, 5, 1.0F,  0, 0, 0.0F);
    }

    protected void addSoup(ResourceLocation loc) {
        add(loc, 3, 0.7F,  0, 0, 0.0F);
    }

    protected void addSpecialCake(ResourceLocation loc) {
        add(loc, 5, 1.3F,  0, 0, 0.0F);
    }

    protected void addVeggie(ResourceLocation loc) {
        add(loc, 1, 0.1F,  0, 0, 0.0F);
    }

    protected void addMeal(ResourceLocation loc) {
        add(loc, 2, 0.6F,  0, 0, 0.0F);
    }

    protected void addCake(ResourceLocation loc) {
        add(loc, 2, 0.2F,  0, 0, 0.0F);
    }

    @Override
    public String getName() {
        return "Homeostatic - Drinkable Items";
    }

    @Override
    public void run(CachedOutput pOutput) throws IOException {
        addDrinkableItems();

        Path output = dataGenerator.getOutputFolder();

        for (Map.Entry<ResourceLocation, DrinkableItem> entry : DRINKABLE_ITEMS.entrySet()) {
            Path drinkableItemsPath = getPath(output, entry.getKey());

            try {
                DataProvider.saveStable(pOutput, DrinkableItemManager.parseDrinkableItem(entry.getValue()), drinkableItemsPath);
            }
            catch (IOException e) {
                Homeostatic.LOGGER.error("Couldn't save homeostatic drinkable items %s %s", drinkableItemsPath, e);
            }
        }
    }

    private static Path getPath(Path output, ResourceLocation loc) {
        return output.resolve("data/" + loc.getNamespace() + "/environment/drinkable/" + loc.getPath() + ".json");
    }

}
