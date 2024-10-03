package homeostatic.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import homeostatic.common.item.DrinkableItem;
import homeostatic.common.item.DrinkableItemManager;
import homeostatic.data.integration.ModIntegration;

import static homeostatic.Homeostatic.loc;

public class DrinkableItemsProvider implements DataProvider {

    private final Map<ResourceLocation, DrinkableItem> DRINKABLE_ITEMS = new HashMap<>();
    private final PackOutput packOutput;

    public DrinkableItemsProvider(@NotNull final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected void addDrinkableItems() {
        // Homeostatic
        add(loc("purified_water_bottle"), 3, 0.7F,  0, 0, 0.0F);

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
        addCake(ModIntegration.mcLoc("cake"));
        addVeggie(ModIntegration.mcLoc("baked_potato"));
        addVeggie(ModIntegration.mcLoc("golden_carrot"));
        addFruit(ModIntegration.mcLoc("glistering_melon_slice"));

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
        addDrink(ModIntegration.xercaLoc("carbonated_water"));

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

        // Ecologics
        addFruit(ModIntegration.ecoLoc("cooked_prickly_pear"));
        addFruit(ModIntegration.ecoLoc("prickly_pear"));
        addSoup(ModIntegration.ecoLoc("tropical_stew"));

        // Ars Nouveau
        addBerries(ModIntegration.arsLoc("source_berry"));
        addBerries(ModIntegration.arsLoc("source_berry_roll"));
        add(ModIntegration.arsLoc("source_berry_pie"), 6, 0.9F,  0, 0, 0.0F);

        // Sprout
        addVeggie(ModIntegration.sproutLoc("peanut_butter"));
        addJam(ModIntegration.sproutLoc("sweet_berry_jam"));
        addPie(ModIntegration.sproutLoc("pbj"));
        addVeggie(ModIntegration.sproutLoc("peanut_butter_cookie"));
        addPie(ModIntegration.sproutLoc("glow_berry_pie"));
        addPie(ModIntegration.sproutLoc("sweet_berry_pie"));
        addVeggie(ModIntegration.sproutLoc("water_sausage"));
        addPie(ModIntegration.sproutLoc("apple_pie"));
        addFruit(ModIntegration.sproutLoc("candy_apple"));
        add(ModIntegration.sproutLoc("golden_candy_apple"), 6, 1.2F,  0, 0, 0.0F);

        // Create Cafe
        addCake(ModIntegration.ccLoc("oreo"));
        addVeggie(ModIntegration.ccLoc("cassava_root"));
        addDrink(ModIntegration.ccLoc("aloe_milk_tea"));
        addDrink(ModIntegration.ccLoc("apple_milk_tea"));
        addDrink(ModIntegration.ccLoc("apricot_milk_tea"));
        addDrink(ModIntegration.ccLoc("avocado_milk_tea"));
        addDrink(ModIntegration.ccLoc("banana_milk_tea"));
        addDrink(ModIntegration.ccLoc("blackberry_milk_tea"));
        addDrink(ModIntegration.ccLoc("blueberry_milk_tea"));
        addDrink(ModIntegration.ccLoc("cherry_milk_tea"));
        addDrink(ModIntegration.ccLoc("citron_milk_tea"));
        addDrink(ModIntegration.ccLoc("coconut_milk_tea"));
        addDrink(ModIntegration.ccLoc("dragonfruit_milk_tea"));
        addDrink(ModIntegration.ccLoc("fig_milk_tea"));
        addDrink(ModIntegration.ccLoc("grape_milk_tea"));
        addDrink(ModIntegration.ccLoc("grapefruit_milk_tea"));
        addDrink(ModIntegration.ccLoc("kiwi_milk_tea"));
        addDrink(ModIntegration.ccLoc("lavender_milk_tea"));
        addDrink(ModIntegration.ccLoc("lemmon_milk_tea"));
        addDrink(ModIntegration.ccLoc("lime_milk_tea"));
        addDrink(ModIntegration.ccLoc("mandarin_milk_tea"));
        addDrink(ModIntegration.ccLoc("mango_milk_tea"));
        addDrink(ModIntegration.ccLoc("oreo_milk_tea"));
        addDrink(ModIntegration.ccLoc("orange_milk_tea"));
        addDrink(ModIntegration.ccLoc("peach_milk_tea"));
        addDrink(ModIntegration.ccLoc("persimmon_milk_tea"));
        addDrink(ModIntegration.ccLoc("pineapple_milk_tea"));
        addDrink(ModIntegration.ccLoc("plum_milk_tea"));
        addDrink(ModIntegration.ccLoc("pomello_milk_tea"));
        addDrink(ModIntegration.ccLoc("pumpkin_milk_tea"));
        addDrink(ModIntegration.ccLoc("raspberry_milk_tea"));
        addDrink(ModIntegration.ccLoc("redlove_milk_tea"));
        addDrink(ModIntegration.ccLoc("strawberry_milk_tea"));
        addDrink(ModIntegration.ccLoc("sweetberry_milk_tea"));
        addDrink(ModIntegration.ccLoc("vanilla_milk_tea"));
        addDrink(ModIntegration.ccLoc("watermelon_milk_tea"));

        // Fruit Trees
        addSpecialCake(ModIntegration.ftLoc("grapefruit_panna_cotta"));
        addSpecialCake(ModIntegration.ftLoc("donauwelle"));
        add(ModIntegration.ftLoc("honey_pomelo_tea"), 6, 0.9F,  0, 0, 0.0F);
        add(ModIntegration.ftLoc("rice_with_fruits"), 6, 0.9F,  0, 0, 0.0F);
        addMeal(ModIntegration.ftLoc("lemon_roast_chicken"));
        addPie(ModIntegration.ftLoc("chorus_fruit_pie_slice"));

        // Kobolds
        addDrink(ModIntegration.koboldsLoc("kobold_potion_health"));
        addDrink(ModIntegration.koboldsLoc("kobold_potion_fire"));
        addDrink(ModIntegration.koboldsLoc("kobold_potion_stealth"));
        addDrink(ModIntegration.koboldsLoc("kobold_potion_combat"));
        addDrink(ModIntegration.koboldsLoc("kobold_potion_water"));
        addDrink(ModIntegration.koboldsLoc("kobold_potion_leaping"));
        addDrink(ModIntegration.koboldsLoc("kobold_potion_levitation"));
        addDrink(ModIntegration.koboldsLoc("kobold_potion_mining"));

        // More Food xD
        addMeal(ModIntegration.mfLoc("apple_bread"));
        addPie(ModIntegration.mfLoc("apple_pie"));
        addSoup(ModIntegration.mfLoc("apple_soup"));
        addSoup(ModIntegration.mfLoc("bamboo_soup"));
        addMeal(ModIntegration.mfLoc("carrot_bread"));
        addPie(ModIntegration.mfLoc("carrot_pie"));
        addSoup(ModIntegration.mfLoc("carrot_soup"));
        addMeal(ModIntegration.mfLoc("chocolate"));
        addMeal(ModIntegration.mfLoc("chocolate_bar"));
        addFruit(ModIntegration.mfLoc("cooked_apple"));
        add(ModIntegration.mfLoc("diamond_apple"), 6, 1.2F,  0, 0, 0.0F);
        addVeggie(ModIntegration.mfLoc("diamond_carrot"));
        addVeggie(ModIntegration.mfLoc("diamond_kelp"));
        addFruit(ModIntegration.mfLoc("diamond_melon_slice"));
        addVeggie(ModIntegration.mfLoc("diamond_potato"));
        addVeggie(ModIntegration.mfLoc("emerald_carrot"));
        addVeggie(ModIntegration.mfLoc("emerald_kelp"));
        addFruit(ModIntegration.mfLoc("emerald_melon_slice"));
        addVeggie(ModIntegration.mfLoc("emerald_potato"));
        addVeggie(ModIntegration.mfLoc("golden_kelp"));
        addFruit(ModIntegration.mfLoc("golden_melon_slice"));
        addVeggie(ModIntegration.mfLoc("golden_potato"));
        addVeggie(ModIntegration.mfLoc("iron_carrot"));
        addVeggie(ModIntegration.mfLoc("iron_kelp"));
        addFruit(ModIntegration.mfLoc("iron_melon_slice"));
        addVeggie(ModIntegration.mfLoc("iron_potato"));
        addSoup(ModIntegration.mfLoc("kelp_soup"));
        addSoup(ModIntegration.mfLoc("phantom_soup"));
        addMeal(ModIntegration.mfLoc("potato_bread"));
        addSoup(ModIntegration.mfLoc("potato_soup"));
        addSoup(ModIntegration.mfLoc("pufferfish_soup"));
        addSoup(ModIntegration.mfLoc("pumpking_soup"));
        addMeal(ModIntegration.mfLoc("rice_chicken_bowl"));
        addMeal(ModIntegration.mfLoc("rice_cod_bowl"));
        addMeal(ModIntegration.mfLoc("rice_honey_pudding"));
        addMeal(ModIntegration.mfLoc("rice_salmon_bowl"));
        addMeal(ModIntegration.mfLoc("rice_vegetable_bowl"));
        addMeal(ModIntegration.mfLoc("sushi_bamboo"));
        addMeal(ModIntegration.mfLoc("sushi_beetroot"));
        addMeal(ModIntegration.mfLoc("sushi_carrot"));
        addMeal(ModIntegration.mfLoc("sushi_salmon"));

        // Pam's Harvestcraft
        // Smoothie
        addShake(ModIntegration.pheLoc("mulberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("peachsmoothieitem"));
        addShake(ModIntegration.pheLoc("grapefruitsmoothieitem"));
        addShake(ModIntegration.pheLoc("lemonsmoothieitem"));
        addShake(ModIntegration.pheLoc("lycheesmoothieitem"));
        addShake(ModIntegration.pheLoc("grapesmoothieitem"));
        addShake(ModIntegration.pheLoc("raspberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("apricotsmoothieitem"));
        addShake(ModIntegration.pheLoc("blueberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("plumsmoothieitem"));
        addShake(ModIntegration.pheLoc("candleberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("rambutansmoothieitem"));
        addShake(ModIntegration.pheLoc("cherrysmoothieitem"));
        addShake(ModIntegration.pheLoc("passionfruitsmoothieitem"));
        addShake(ModIntegration.pheLoc("strawberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("huckleberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("papayasmoothieitem"));
        addShake(ModIntegration.pheLoc("pearsmoothieitem"));
        addShake(ModIntegration.pheLoc("figsmoothieitem"));
        addShake(ModIntegration.pheLoc("orangesmoothieitem"));
        addShake(ModIntegration.pheLoc("jackfruitsmoothieitem"));
        addShake(ModIntegration.pheLoc("gooseberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("greengrapesmoothieitem"));
        addShake(ModIntegration.pheLoc("persimmonsmoothieitem"));
        addShake(ModIntegration.pheLoc("juniperberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("pomegranatesmoothieitem"));
        addShake(ModIntegration.pheLoc("starfruitsmoothieitem"));
        addShake(ModIntegration.pheLoc("breadfruitsmoothieitem"));
        addShake(ModIntegration.pheLoc("tamarindsmoothieitem"));
        addShake(ModIntegration.pheLoc("dragonfruitsmoothieitem"));
        addShake(ModIntegration.pheLoc("elderberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("soursopsmoothieitem"));
        addShake(ModIntegration.pheLoc("limesmoothieitem"));
        addShake(ModIntegration.pheLoc("blackberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("cranberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("bananasmoothieitem"));
        addShake(ModIntegration.pheLoc("kiwismoothieitem"));
        addShake(ModIntegration.pheLoc("duriansmoothieitem"));
        addShake(ModIntegration.pheLoc("pineapplesmoothieitem"));
        addShake(ModIntegration.pheLoc("guavasmoothieitem"));
        addShake(ModIntegration.pheLoc("cactusfruitsmoothieitem"));
        addShake(ModIntegration.pheLoc("datesmoothieitem"));
        addShake(ModIntegration.pheLoc("cantaloupesmoothieitem"));
        addShake(ModIntegration.pheLoc("pawpawsmoothieitem"));
        addShake(ModIntegration.pheLoc("mangosmoothieitem"));
        addShake(ModIntegration.phcLoc("applesmoothieitem"));
        addShake(ModIntegration.phcLoc("melonsmoothieitem"));
        addShake(ModIntegration.phcLoc("glowberrysmoothieitem"));
        addShake(ModIntegration.phcLoc("chorussmoothieitem"));
        addShake(ModIntegration.phcLoc("sweetberrysmoothieitem"));
        addShake(ModIntegration.pheLoc("chocolatemilkshakeitem"));

        // Juice
        addDrink(ModIntegration.pheLoc("tamarindjuiceitem"));
        addDrink(ModIntegration.pheLoc("peachjuiceitem"));
        addDrink(ModIntegration.pheLoc("candleberryjuiceitem"));
        addDrink(ModIntegration.pheLoc("cactusfruitjuiceitem"));
        addDrink(ModIntegration.pheLoc("passionfruitjuiceitem"));
        addDrink(ModIntegration.pheLoc("blueberryjuiceitem"));
        addDrink(ModIntegration.pheLoc("datejuiceitem"));
        addDrink(ModIntegration.pheLoc("cantaloupejuiceitem"));
        addDrink(ModIntegration.pheLoc("mangojuiceitem"));
        addDrink(ModIntegration.pheLoc("mulberryjuiceitem"));
        addDrink(ModIntegration.pheLoc("elderberryjuiceitem"));
        addDrink(ModIntegration.pheLoc("pineapplejuiceitem"));
        addDrink(ModIntegration.pheLoc("limejuiceitem"));
        addDrink(ModIntegration.pheLoc("huckleberryjuiceitem"));
        addDrink(ModIntegration.pheLoc("dragonfruitjuiceitem"));
        addDrink(ModIntegration.pheLoc("pearjuiceitem"));
        addDrink(ModIntegration.pheLoc("plumjuiceitem"));
        addDrink(ModIntegration.pheLoc("strawberryjuiceitem"));
        addDrink(ModIntegration.pheLoc("breadfruitjuiceitem"));
        addDrink(ModIntegration.pheLoc("pawpawjuiceitem"));
        addDrink(ModIntegration.pheLoc("soursopjuiceitem"));
        addDrink(ModIntegration.pheLoc("lycheejuiceitem"));
        addDrink(ModIntegration.pheLoc("persimmonjuiceitem"));
        addDrink(ModIntegration.pheLoc("starfruitjuiceitem"));
        addDrink(ModIntegration.pheLoc("grapejuiceitem"));
        addDrink(ModIntegration.pheLoc("papayajuiceitem"));
        addDrink(ModIntegration.pheLoc("figjuiceitem"));
        addDrink(ModIntegration.pheLoc("orangejuiceitem"));
        addDrink(ModIntegration.pheLoc("jackfruitjuiceitem"));
        addDrink(ModIntegration.pheLoc("kiwijuiceitem"));
        addDrink(ModIntegration.pheLoc("cherryjuiceitem"));
        addDrink(ModIntegration.pheLoc("greengrapejuiceitem"));
        addDrink(ModIntegration.pheLoc("cranberryjuiceitem"));
        addDrink(ModIntegration.pheLoc("raspberryjuiceitem"));
        addDrink(ModIntegration.pheLoc("pomegranatejuiceitem"));
        addDrink(ModIntegration.pheLoc("bananajuiceitem"));
        addDrink(ModIntegration.pheLoc("gooseberryjuiceitem"));
        addDrink(ModIntegration.pheLoc("blackberryjuiceitem"));
        addDrink(ModIntegration.pheLoc("rambutanjuiceitem"));
        addDrink(ModIntegration.pheLoc("juniperberryjuiceitem"));
        addDrink(ModIntegration.pheLoc("durianjuiceitem"));
        addDrink(ModIntegration.pheLoc("grapefruitjuiceitem"));
        addDrink(ModIntegration.pheLoc("lemonjuiceitem"));
        addDrink(ModIntegration.pheLoc("carrotjuiceitem"));
        addDrink(ModIntegration.pheLoc("apricotjuiceitem"));
        addDrink(ModIntegration.pheLoc("guavajuiceitem"));
        addDrink(ModIntegration.phcLoc("sweetberryjuiceitem"));
        addDrink(ModIntegration.phcLoc("chorusjuiceitem"));
        addDrink(ModIntegration.phcLoc("melonjuiceitem"));
        addDrink(ModIntegration.phcLoc("p8juiceitem"));
        addDrink(ModIntegration.phcLoc("glowberryjuiceitem"));
        addDrink(ModIntegration.phcLoc("carrotjuiceitem"));
        addDrink(ModIntegration.phcLoc("applejuiceitem"));
        addDrink(ModIntegration.phcLoc("fruitpunchitem"));

        // Tea / Coffee / Hot Chocolate
        addDrink(ModIntegration.pheLoc("sundayhighteaitem"));
        addDrink(ModIntegration.pheLoc("rosepetalteaitem"));
        addDrink(ModIntegration.pheLoc("earlgreyteaitem"));
        addDrink(ModIntegration.pheLoc("garlicsteakitem"));
        addDrink(ModIntegration.pheLoc("dandelionteaitem"));
        addDrink(ModIntegration.pheLoc("mushroomsteakitem"));
        addDrink(ModIntegration.pheLoc("salisburysteakitem"));
        addDrink(ModIntegration.pheLoc("bamboosteamedriceitem"));
        addDrink(ModIntegration.pheLoc("cheesesteakitem"));
        addDrink(ModIntegration.pheLoc("greenteaitem"));
        addDrink(ModIntegration.pheLoc("chaiteaitem"));
        addDrink(ModIntegration.pheLoc("sweetteaitem"));
        addDrink(ModIntegration.pheLoc("steaktartareitem"));
        addDrink(ModIntegration.pcropsLoc("hotnettleteaitem"));
        addDrink(ModIntegration.pcropsLoc("hotteaitem"));
        addDrink(ModIntegration.pcropsLoc("hotcoffeeitem"));
        addDrink(ModIntegration.pheLoc("hazelnutcoffeeitem"));
        addDrink(ModIntegration.phcLoc("hotchocolateitem"));

        // Bread
        addCake(ModIntegration.pheLoc("walnutraisinbreaditem"));
        addCake(ModIntegration.pheLoc("fruitcreamfestivalbreaditem"));
        addCake(ModIntegration.pheLoc("garlicbreaditem"));
        addCake(ModIntegration.pheLoc("vanillaconchasbreaditem"));
        addCake(ModIntegration.pheLoc("lavendershortbreaditem"));
        addCake(ModIntegration.pheLoc("banananutbreaditem"));
        addCake(ModIntegration.pheLoc("cinnamonbreaditem"));
        addCake(ModIntegration.pheLoc("fairybreaditem"));
        addCake(ModIntegration.pheLoc("cornbreaditem"));
        addCake(ModIntegration.pheLoc("festivalbreaditem"));
        addCake(ModIntegration.phcLoc("carrotbreaditem"));
        addCake(ModIntegration.phcLoc("pumpkinbreaditem"));

        // Toast
        addCake(ModIntegration.pheLoc("pomegranatejellytoastitem"));
        addCake(ModIntegration.pheLoc("lycheejellytoastitem"));
        addCake(ModIntegration.pheLoc("orangejellytoastitem"));
        addCake(ModIntegration.pheLoc("candleberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("pawpawjellytoastitem"));
        addCake(ModIntegration.pheLoc("apricotjellytoastitem"));
        addCake(ModIntegration.pheLoc("dragonfruitjellytoastitem"));
        addCake(ModIntegration.pheLoc("huckleberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("persimmonjellytoastitem"));
        addCake(ModIntegration.pheLoc("greengrapejellytoastitem"));
        addCake(ModIntegration.pheLoc("gooseberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("peachjellytoastitem"));
        addCake(ModIntegration.pheLoc("datejellytoastitem"));
        addCake(ModIntegration.pheLoc("jackfruitjellytoastitem"));
        addCake(ModIntegration.pheLoc("avocadotoastitem"));
        addCake(ModIntegration.pheLoc("figjellytoastitem"));
        addCake(ModIntegration.pheLoc("blueberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("pearjellytoastitem"));
        addCake(ModIntegration.pheLoc("papayajellytoastitem"));
        addCake(ModIntegration.pheLoc("guavajellytoastitem"));
        addCake(ModIntegration.pheLoc("juniperberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("starfruitjellytoastitem"));
        addCake(ModIntegration.pheLoc("cactusfruitjellytoastitem"));
        addCake(ModIntegration.pheLoc("plumjellytoastitem"));
        addCake(ModIntegration.pheLoc("raspberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("grapejellytoastitem"));
        addCake(ModIntegration.pheLoc("tamarindjellytoastitem"));
        addCake(ModIntegration.pheLoc("cheeseontoastitem"));
        addCake(ModIntegration.pheLoc("rambutanjellytoastitem"));
        addCake(ModIntegration.pheLoc("breadfruitjellytoastitem"));
        addCake(ModIntegration.pheLoc("durianjellytoastitem"));
        addCake(ModIntegration.pheLoc("cherryjellytoastitem"));
        addCake(ModIntegration.pheLoc("lemonjellytoastitem"));
        addCake(ModIntegration.pheLoc("cranberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("pineapplejellytoastitem"));
        addCake(ModIntegration.pheLoc("mulberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("passionfruitjellytoastitem"));
        addCake(ModIntegration.pheLoc("soursopjellytoastitem"));
        addCake(ModIntegration.phcLoc("melonjellytoastitem"));
        addCake(ModIntegration.phcLoc("glowberryjellytoastitem"));
        addCake(ModIntegration.phcLoc("chorusjellytoastitem"));
        addCake(ModIntegration.phcLoc("applejellytoastitem"));
        addCake(ModIntegration.phcLoc("toastitem"));
        addCake(ModIntegration.phcLoc("sweetberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("vegemiteontoastitem"));
        addCake(ModIntegration.pheLoc("cinnamontoastitem"));
        addCake(ModIntegration.pheLoc("mangojellytoastitem"));
        addCake(ModIntegration.pheLoc("blackberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("cantaloupejellytoastitem"));
        addCake(ModIntegration.pheLoc("bananajellytoastitem"));
        addCake(ModIntegration.pheLoc("strawberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("grapefruitjellytoastitem"));
        addCake(ModIntegration.pheLoc("grilledcheesevegemitetoastitem"));
        addCake(ModIntegration.pheLoc("limejellytoastitem"));
        addCake(ModIntegration.pheLoc("kiwijellytoastitem"));
        addCake(ModIntegration.pheLoc("beansontoastitem"));
        addCake(ModIntegration.pheLoc("elderberryjellytoastitem"));
        addCake(ModIntegration.pheLoc("frenchtoastitem"));

        // Icecream
        addIcecream(ModIntegration.pheLoc("sorbetitem"));
        addIcecream(ModIntegration.pheLoc("cherryicecreamitem"));
        addIcecream(ModIntegration.pheLoc("neapolitanicecreamitem"));
        addIcecream(ModIntegration.pheLoc("mintchocolatechipicecreamitem"));
        addIcecream(ModIntegration.pheLoc("mochaicecreamitem"));
        addIcecream(ModIntegration.pheLoc("vanillaicecreamitem"));
        addIcecream(ModIntegration.pheLoc("nuttoppedicecreamitem"));
        addIcecream(ModIntegration.phcLoc("icecreamitem"));
        addIcecream(ModIntegration.phcLoc("caramelicecreamitem"));
        addIcecream(ModIntegration.phcLoc("chocolateicecreamitem"));
        addIcecream(ModIntegration.pheLoc("bananasplititem"));
        addIcecream(ModIntegration.pheLoc("applesnowitem"));
        addIcecream(ModIntegration.pheLoc("mochiitem"));
        addIcecream(ModIntegration.phcLoc("glowberrypopsicleitem"));
        addIcecream(ModIntegration.phcLoc("sweetberrypopsicleitem"));
        addIcecream(ModIntegration.phcLoc("melonpopsicleitem"));
        addIcecream(ModIntegration.phcLoc("choruspopsicleitem"));
        addIcecream(ModIntegration.phcLoc("applepopsicleitem"));

        // Jelly
        addJam(ModIntegration.pheLoc("cherryjellyitem"));
        addJam(ModIntegration.pheLoc("persimmonjellyitem"));
        addJam(ModIntegration.pheLoc("strawberryjellyitem"));
        addJam(ModIntegration.pheLoc("dragonfruitjellyitem"));
        addJam(ModIntegration.pheLoc("guavajellyitem"));
        addJam(ModIntegration.pheLoc("lycheejellyitem"));
        addJam(ModIntegration.pheLoc("starfruitjellyitem"));
        addJam(ModIntegration.pheLoc("jackfruitjellyitem"));
        addJam(ModIntegration.pheLoc("figjellyitem"));
        addJam(ModIntegration.pheLoc("lemonjellyitem"));
        addJam(ModIntegration.pheLoc("apricotjellyitem"));
        addJam(ModIntegration.pheLoc("gooseberryjellyitem"));
        addJam(ModIntegration.pheLoc("juniperberryjellyitem"));
        addJam(ModIntegration.pheLoc("kiwijellyitem"));
        addJam(ModIntegration.pheLoc("rambutanjellyitem"));
        addJam(ModIntegration.pheLoc("blackberryjellyitem"));
        addJam(ModIntegration.pheLoc("durianjellyitem"));
        addJam(ModIntegration.pheLoc("greengrapejellyitem"));
        addJam(ModIntegration.pheLoc("cranberryjellyitem"));
        addJam(ModIntegration.pheLoc("orangejellyitem"));
        addJam(ModIntegration.pheLoc("mangojellyitem"));
        addJam(ModIntegration.pheLoc("raspberryjellyitem"));
        addJam(ModIntegration.pheLoc("datejellyitem"));
        addJam(ModIntegration.pheLoc("bananajellyitem"));
        addJam(ModIntegration.pheLoc("soursopjellyitem"));
        addJam(ModIntegration.pheLoc("grapefruitjellyitem"));
        addJam(ModIntegration.pheLoc("papayajellyitem"));
        addJam(ModIntegration.pheLoc("candleberryjellyitem"));
        addJam(ModIntegration.pheLoc("cantaloupejellyitem"));
        addJam(ModIntegration.pheLoc("passionfruitjellyitem"));
        addJam(ModIntegration.pheLoc("tamarindjellyitem"));
        addJam(ModIntegration.pheLoc("plumjellyitem"));
        addJam(ModIntegration.pheLoc("pineapplejellyitem"));
        addJam(ModIntegration.pheLoc("peachjellyitem"));
        addJam(ModIntegration.pheLoc("blueberryjellyitem"));
        addJam(ModIntegration.pheLoc("pawpawjellyitem"));
        addJam(ModIntegration.pheLoc("huckleberryjellyitem"));
        addJam(ModIntegration.pheLoc("limejellyitem"));
        addJam(ModIntegration.pheLoc("pomegranatejellyitem"));
        addJam(ModIntegration.pheLoc("cactusfruitjellyitem"));
        addJam(ModIntegration.pheLoc("mulberryjellyitem"));
        addJam(ModIntegration.pheLoc("elderberryjellyitem"));
        addJam(ModIntegration.pheLoc("pearjellyitem"));
        addJam(ModIntegration.phcLoc("applejellyitem"));
        addJam(ModIntegration.phcLoc("sweetberryjellyitem"));
        addJam(ModIntegration.phcLoc("chorusjellyitem"));
        addJam(ModIntegration.phcLoc("melonjellyitem"));
        addJam(ModIntegration.phcLoc("glowberryjellyitem"));
        addJam(ModIntegration.pheLoc("breadfruitjellyitem"));
        addJam(ModIntegration.pheLoc("grapejellyitem"));

        // Sandwich
        addMeal(ModIntegration.pheLoc("figjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("cherryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("blueberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("groiledcheesesandwichitem"));
        addMeal(ModIntegration.pheLoc("loxbagelsandwichitem"));
        addMeal(ModIntegration.pheLoc("melonjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("kiwijellysandwichitem"));
        addMeal(ModIntegration.pheLoc("mangojellysandwichitem"));
        addMeal(ModIntegration.pheLoc("pearjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("datejellysandwichitem"));
        addMeal(ModIntegration.pheLoc("greengrapejellysandwichitem"));
        addMeal(ModIntegration.pheLoc("mulberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("guavajellysandwichitem"));
        addMeal(ModIntegration.pheLoc("bananajellysandwichitem"));
        addMeal(ModIntegration.pheLoc("lemonjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("peachjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("meatloafsandwichitem"));
        addMeal(ModIntegration.pheLoc("durianjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("juniperberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("cucumbersandwichitem"));
        addMeal(ModIntegration.pheLoc("breadfruitjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("lycheejellysandwichitem"));
        addMeal(ModIntegration.pheLoc("pineapplejellysandwichitem"));
        addMeal(ModIntegration.pheLoc("cranberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("papayajellysandwichitem"));
        addMeal(ModIntegration.pheLoc("bolognasandwichitem"));
        addMeal(ModIntegration.pheLoc("grapefruitjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("tamarindjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("gooseberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("huckleberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("raspberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("rambutanjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("limejellysandwichitem"));
        addMeal(ModIntegration.pheLoc("hamandcheesesandwichitem"));
        addMeal(ModIntegration.pheLoc("apricotjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("blackberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("pomegranatejellysandwichitem"));
        addMeal(ModIntegration.pheLoc("sweetberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("strawberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("candleberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("friedbolognasandwichitem"));
        addMeal(ModIntegration.pheLoc("passionfruitjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("dragonfruitjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("grapejellysandwichitem"));
        addMeal(ModIntegration.pheLoc("peanutbutterbananasandwichitem"));
        addMeal(ModIntegration.phcLoc("basicfishsandwichitem"));
        addMeal(ModIntegration.phcLoc("basicmuttonsandwichitem"));
        addMeal(ModIntegration.phcLoc("basicchickensandwichitem"));
        addMeal(ModIntegration.phcLoc("basicrabbitsandwichitem"));
        addMeal(ModIntegration.phcLoc("basicporksandwichitem"));
        addMeal(ModIntegration.pheLoc("toastsandwichitem"));
        addMeal(ModIntegration.pheLoc("persimmonjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("leafychickensandwichitem"));
        addMeal(ModIntegration.pheLoc("applejellysandwichitem"));
        addMeal(ModIntegration.pheLoc("starfruitjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("cactusfruitjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("hamsweetpicklesandwichitem"));
        addMeal(ModIntegration.pheLoc("orangejellysandwichitem"));
        addMeal(ModIntegration.pheLoc("jackfruitjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("cantaloupejellysandwichitem"));
        addMeal(ModIntegration.pheLoc("pawpawjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("elderberryjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("honeysandwichitem"));
        addMeal(ModIntegration.pheLoc("leafyfishsandwichitem"));
        addMeal(ModIntegration.pheLoc("plumjellysandwichitem"));
        addMeal(ModIntegration.pheLoc("soursopjellysandwichitem"));

        // Pancakes
        addMeal(ModIntegration.pheLoc("peanutbutterbananapancakesitem"));
        addMeal(ModIntegration.pheLoc("baconpancakesitem"));
        addMeal(ModIntegration.pheLoc("maplesyruppancakesitem"));
        addMeal(ModIntegration.pheLoc("chocolatechippancakesitem"));
        addMeal(ModIntegration.pheLoc("pancakesitem"));

        // Soda
        addDrink(ModIntegration.pheLoc("cherrysodaitem"));
        addDrink(ModIntegration.pheLoc("rootbeersodaitem"));
        addDrink(ModIntegration.pheLoc("gingersodaitem"));
        addDrink(ModIntegration.pheLoc("colasodaitem"));
        addDrink(ModIntegration.pheLoc("lemonlimesodaitem"));
        addDrink(ModIntegration.pheLoc("grapesodaitem"));
        addDrink(ModIntegration.pheLoc("strawberrysodaitem"));
        addDrink(ModIntegration.pheLoc("orangesodaitem"));
        addDrink(ModIntegration.pheLoc("grapefruitsodaitem"));
        addDrink(ModIntegration.pheLoc("rootbeerfloatitem"));

        // Pie
        addPie(ModIntegration.pheLoc("jackfruitpieitem"));
        addPie(ModIntegration.pheLoc("mincepieitem"));
        addPie(ModIntegration.pheLoc("peachpieitem"));
        addPie(ModIntegration.pheLoc("raspberrypieitem"));
        addPie(ModIntegration.pheLoc("candleberrypieitem"));
        addPie(ModIntegration.pheLoc("sweetpotatopieitem"));
        addPie(ModIntegration.pheLoc("elderberrypieitem"));
        addPie(ModIntegration.pheLoc("limepieitem"));
        addPie(ModIntegration.pheLoc("mangopieitem"));
        addPie(ModIntegration.pheLoc("orangepieitem"));
        addPie(ModIntegration.pheLoc("persimmonpieitem"));
        addPie(ModIntegration.pheLoc("rambutanpieitem"));
        addPie(ModIntegration.pheLoc("pomegranatepieitem"));
        addPie(ModIntegration.pheLoc("pearpieitem"));
        addPie(ModIntegration.pheLoc("strawberryrhubarbpieitem"));
        addPie(ModIntegration.pheLoc("shepardspieitem"));
        addPie(ModIntegration.pheLoc("greengrapepieitem"));
        addPie(ModIntegration.pheLoc("plumpieitem"));
        addPie(ModIntegration.pheLoc("datepieitem"));
        addPie(ModIntegration.pheLoc("meatpieitem"));
        addPie(ModIntegration.pheLoc("cactusfruitpieitem"));
        addPie(ModIntegration.pheLoc("pawpawpieitem"));
        addPie(ModIntegration.pheLoc("keylimepieitem"));
        addPie(ModIntegration.pheLoc("tamarindpieitem"));
        addPie(ModIntegration.pheLoc("passionfruitpieitem"));
        addPie(ModIntegration.pheLoc("cranberrypieitem"));
        addPie(ModIntegration.pheLoc("lycheepieitem"));
        addPie(ModIntegration.pheLoc("kiwipieitem"));
        addPie(ModIntegration.pheLoc("huckleberrypieitem"));
        addPie(ModIntegration.pheLoc("juniperberrypieitem"));
        addPie(ModIntegration.pheLoc("soursoppieitem"));
        addPie(ModIntegration.pheLoc("grapefruitpieitem"));
        addPie(ModIntegration.pheLoc("breadfruitpieitem"));
        addPie(ModIntegration.pheLoc("bananapieitem"));
        addPie(ModIntegration.pheLoc("dragonfruitpieitem"));
        addPie(ModIntegration.pheLoc("cantaloupepieitem"));
        addPie(ModIntegration.pheLoc("strawberrypieitem"));
        addPie(ModIntegration.pheLoc("starfruitpieitem"));
        addPie(ModIntegration.pheLoc("pineapplepieitem"));
        addPie(ModIntegration.pheLoc("guavapieitem"));
        addPie(ModIntegration.pheLoc("mulberrypieitem"));
        addPie(ModIntegration.pheLoc("blackberrypieitem"));
        addPie(ModIntegration.pheLoc("durianpieitem"));
        addPie(ModIntegration.phcLoc("honeypieitem"));
        addPie(ModIntegration.phcLoc("applepieitem"));
        addPie(ModIntegration.phcLoc("sweetberrypieitem"));
        addPie(ModIntegration.phcLoc("melonpieitem"));
        addPie(ModIntegration.phcLoc("carrotpieitem"));
        addPie(ModIntegration.phcLoc("rabbitpotpieitem"));
        addPie(ModIntegration.phcLoc("chickenpotpieitem"));
        addPie(ModIntegration.phcLoc("glowberrypieitem"));
        addPie(ModIntegration.phcLoc("beefpotpieitem"));
        addPie(ModIntegration.phcLoc("porkpotpieitem"));
        addPie(ModIntegration.phcLoc("chocolatepieitem"));
        addPie(ModIntegration.phcLoc("muttonpotpieitem"));
        addPie(ModIntegration.phcLoc("caramelpieitem"));
        addPie(ModIntegration.phcLoc("fishpotpieitem"));
        addPie(ModIntegration.phcLoc("choruspieitem"));
        addPie(ModIntegration.phcLoc("veggiepotpieitem"));
        addPie(ModIntegration.pheLoc("grapepieitem"));
        addPie(ModIntegration.pheLoc("lemonpieitem"));
        addPie(ModIntegration.pheLoc("gooseberrypieitem"));
        addPie(ModIntegration.pheLoc("cherrypieitem"));
        addPie(ModIntegration.pheLoc("blueberrypieitem"));
        addPie(ModIntegration.pheLoc("papayapieitem"));
        addPie(ModIntegration.pheLoc("spinachpieitem"));
        addPie(ModIntegration.pheLoc("cottagepieitem"));
        addPie(ModIntegration.pheLoc("figpieitem"));
        addPie(ModIntegration.pheLoc("pecanpieitem"));
        addPie(ModIntegration.pheLoc("apricotpieitem"));
        addPie(ModIntegration.pheLoc("brownieitem"));

        // Yogurt
        addIcecream(ModIntegration.pheLoc("durianyogurtitem"));
        addIcecream(ModIntegration.pheLoc("limeyogurtitem"));
        addIcecream(ModIntegration.pheLoc("raspberryyogurtitem"));
        addIcecream(ModIntegration.pheLoc("tamarindyogurtitem"));
        addIcecream(ModIntegration.pheLoc("dragonfruityogurtitem"));
        addIcecream(ModIntegration.pheLoc("blueberryyogurtitem"));
        addIcecream(ModIntegration.pheLoc("strawberryyogurtitem"));
        addIcecream(ModIntegration.pheLoc("jackfruityogurtitem"));
        addIcecream(ModIntegration.pheLoc("mulberryyogurtitem"));
        addIcecream(ModIntegration.pheLoc("plumyogurtitem"));
        addIcecream(ModIntegration.pheLoc("peachyogurtitem"));
        addIcecream(ModIntegration.pheLoc("juniperberryyogurtitem"));
        addIcecream(ModIntegration.pheLoc("papayayogurtitem"));
        addIcecream(ModIntegration.pheLoc("cantaloupeyogurtitem"));
        addIcecream(ModIntegration.pheLoc("dateyogurtitem"));
        addIcecream(ModIntegration.pheLoc("breadfruityogurtitem"));
        addIcecream(ModIntegration.pheLoc("kiwiyogurtitem"));
        addIcecream(ModIntegration.pheLoc("passionfruityogurtitem"));
        addIcecream(ModIntegration.pheLoc("blackberryyogurtitem"));
        addIcecream(ModIntegration.pheLoc("pineappleyogurtitem"));
        addIcecream(ModIntegration.pheLoc("grapeyogurtitem"));
        addIcecream(ModIntegration.pheLoc("pearyogurtitem"));
        addIcecream(ModIntegration.pheLoc("elderberryyogurtitem"));
        addIcecream(ModIntegration.pheLoc("apricotyogurtitem"));
        addIcecream(ModIntegration.pheLoc("starfruityogurtitem"));
        addIcecream(ModIntegration.pheLoc("bananayogurtitem"));
        addIcecream(ModIntegration.pheLoc("soursopyogurtitem"));
        addIcecream(ModIntegration.pheLoc("cranberryyogurtitem"));
        addIcecream(ModIntegration.pheLoc("guavayogurtitem"));
        addIcecream(ModIntegration.pheLoc("pawpawyogurtitem"));
        addIcecream(ModIntegration.pheLoc("mangoyogurtitem"));
        addIcecream(ModIntegration.pheLoc("grapefruityogurtitem"));
        addIcecream(ModIntegration.pheLoc("rambutanyogurtitem"));
        addIcecream(ModIntegration.pheLoc("lemonyogurtitem"));
        addIcecream(ModIntegration.pheLoc("figyogurtitem"));
        addIcecream(ModIntegration.pheLoc("candleberryyogurtitem"));
        addIcecream(ModIntegration.pheLoc("persimmonyogurtitem"));
        addIcecream(ModIntegration.pheLoc("greengrapeyogurtitem"));
        addIcecream(ModIntegration.pheLoc("pomegranateyogurtitem"));
        addIcecream(ModIntegration.pheLoc("huckleberryyogurtitem"));
        addIcecream(ModIntegration.phcLoc("glowberryyogurtitem"));
        addIcecream(ModIntegration.phcLoc("melonyogurtitem"));
        addIcecream(ModIntegration.phcLoc("chorusyogurtitem"));
        addIcecream(ModIntegration.phcLoc("yogurtitem"));
        addIcecream(ModIntegration.phcLoc("chocolateyogurtitem"));
        addIcecream(ModIntegration.phcLoc("appleyogurtitem"));
        addIcecream(ModIntegration.phcLoc("caramelyogurtitem"));
        addIcecream(ModIntegration.phcLoc("sweetberryyogurtitem"));
        addIcecream(ModIntegration.phcLoc("pumpkinyogurtitem"));
        addIcecream(ModIntegration.pheLoc("cherryyogurtitem"));
        addIcecream(ModIntegration.pheLoc("orangeyogurtitem"));
        addIcecream(ModIntegration.pheLoc("gooseberryyogurtitem"));
        addIcecream(ModIntegration.pheLoc("orangeyogurtitem"));
        addIcecream(ModIntegration.pheLoc("lycheeyogurtitem"));
        addIcecream(ModIntegration.pheLoc("cactusfruityogurtitem"));
        addIcecream(ModIntegration.phcLoc("fudgesicleitem"));

        // Veggies
        addVeggie(ModIntegration.pheLoc("cornonthecobitem"));
        addVeggie(ModIntegration.pcropsLoc("aloeitem"));
        addVeggie(ModIntegration.pcropsLoc("agaveitem"));
        addVeggie(ModIntegration.pheLoc("bakedcactusitem"));
        addVeggie(ModIntegration.phcLoc("bakedvegetablemedlyitem"));
        addVeggie(ModIntegration.pcropsLoc("bakedarrowrootitem"));
        addVeggie(ModIntegration.pcropsLoc("bakedsweetpotatoitem"));
        addVeggie(ModIntegration.pcropsLoc("bakedjicamaitem"));
        addVeggie(ModIntegration.pcropsLoc("bakedtaroitem"));
        addVeggie(ModIntegration.pcropsLoc("bakedsunchokeitem"));
        addVeggie(ModIntegration.pcropsLoc("bakedparsnipitem"));
        addVeggie(ModIntegration.pcropsLoc("bakedwaterchestnutitem"));
        addVeggie(ModIntegration.pcropsLoc("bakedrutabagaitem"));
        addVeggie(ModIntegration.pcropsLoc("bakedturnipitem"));
        addVeggie(ModIntegration.pcropsLoc("bakedcassavaitem"));
        addVeggie(ModIntegration.pcropsLoc("roastedscallionitem"));
        addVeggie(ModIntegration.pcropsLoc("roastedrhubarbitem"));
        addVeggie(ModIntegration.pcropsLoc("roastedleekitem"));
        addVeggie(ModIntegration.pcropsLoc("roastedgarlicitem"));
        addVeggie(ModIntegration.pcropsLoc("roastedradishitem"));
        addVeggie(ModIntegration.pcropsLoc("roastedmushroomitem"));

        // Soup
        addSoup(ModIntegration.pheLoc("chickennoodlesoupitem"));
        addSoup(ModIntegration.pheLoc("wontonsoupitem"));
        addSoup(ModIntegration.pheLoc("misosoupitem"));
        addSoup(ModIntegration.pheLoc("potatoandleeksoupitem"));
        addSoup(ModIntegration.pheLoc("cactussoupitem"));
        addSoup(ModIntegration.pheLoc("peaandhamsoupitem"));
        addSoup(ModIntegration.pheLoc("seedsoupitem"));
        addSoup(ModIntegration.pheLoc("creamedbroccolisoupitem"));
        addSoup(ModIntegration.phcLoc("carrotsoupitem"));
        addSoup(ModIntegration.phcLoc("noodlesoupitem"));
        addSoup(ModIntegration.phcLoc("pumpkinsoupitem"));
        addSoup(ModIntegration.phcLoc("chickennoodlesoupitem"));
        addSoup(ModIntegration.phcLoc("fishnoodlesoupitem"));
        addSoup(ModIntegration.phcLoc("porknoodlesoupitem"));
        addSoup(ModIntegration.phcLoc("vegetablenoodlesoupitem"));
        addSoup(ModIntegration.phcLoc("muttonnoodlesoupitem"));
        addSoup(ModIntegration.phcLoc("rabbitnoodlesoupitem"));
        addSoup(ModIntegration.phcLoc("potatosoupitem"));
        addSoup(ModIntegration.phcLoc("beefnoodlesoupitem"));
        addSoup(ModIntegration.pheLoc("tomatosoupitem"));
        addSoup(ModIntegration.pheLoc("creamofavocadosoupitem"));
        addSoup(ModIntegration.pheLoc("gardensoupitem"));
        addSoup(ModIntegration.pheLoc("pizzasoupitem"));
        addSoup(ModIntegration.pheLoc("splitpeasoupitem"));
        addSoup(ModIntegration.pheLoc("lambbarleysoupitem"));
        addSoup(ModIntegration.pheLoc("vegetablesoupitem"));
        addSoup(ModIntegration.pheLoc("hotandsoursoupitem"));
        addSoup(ModIntegration.pheLoc("oldworldveggiesoupitem"));
        addSoup(ModIntegration.pheLoc("driedsoupitem"));
        addSoup(ModIntegration.pheLoc("leekbaconsoupitem"));
        addSoup(ModIntegration.phcLoc("stewitem"));

        // Fluids
        addDrink(ModIntegration.pheLoc("energydrinkitem"));
        addDrink(ModIntegration.pheLoc("eggnogitem"));
        addDrink(ModIntegration.pheLoc("applecideritem"));
        addDrink(ModIntegration.phcLoc("vinegaritem"));
        addDrink(ModIntegration.phcLoc("freshmilkitem"));
        addDrink(ModIntegration.phcLoc("creamitem"));
        addDrink(ModIntegration.phcLoc("stockitem"));
        addDrink(ModIntegration.pheLoc("chocolatemilkitem"));
        addDrink(ModIntegration.pheLoc("soymilkitem"));
        addDrink(ModIntegration.phcLoc("chocolatemilkitem"));
        add(ModIntegration.phcLoc("freshwateritem"), 1, 0.0F, 45, 200, 0.2F);

        // Cake / Cupcake / Donut
        addCake(ModIntegration.pheLoc("sprinklescupcakeitem"));
        addCake(ModIntegration.pheLoc("redvelvetcupcakeitem"));
        addCake(ModIntegration.pheLoc("chocolatesprinklecakeitem"));
        addCake(ModIntegration.pheLoc("lemondrizzlecakeitem"));
        addCake(ModIntegration.pheLoc("pineappleupsidedowncakeitem"));
        addCake(ModIntegration.pheLoc("rivermudcakeitem"));
        addCake(ModIntegration.pheLoc("ricecakeitem"));
        addCake(ModIntegration.pheLoc("holidaycakeitem"));
        addCake(ModIntegration.pheLoc("fruitcakeitem"));
        addCake(ModIntegration.pheLoc("mochicakeitem"));
        addCake(ModIntegration.phcLoc("cheesecakeitem"));
        addCake(ModIntegration.phcLoc("carrotcakeitem"));
        addCake(ModIntegration.phcLoc("chocolatecakeitem"));
        addCake(ModIntegration.phcLoc("pumpkincheesecakeitem"));
        addCake(ModIntegration.pheLoc("powdereddonutitem"));
        addCake(ModIntegration.pheLoc("frosteddonutitem"));
        addCake(ModIntegration.pheLoc("cinnamonsugardonutitem"));
        addCake(ModIntegration.phcLoc("carameldonutitem"));
        addCake(ModIntegration.phcLoc("plaindonutitem"));
        addCake(ModIntegration.phcLoc("pumpkindonutitem"));
        addCake(ModIntegration.phcLoc("sprinklesdonutitem"));
        addCake(ModIntegration.phcLoc("carrotdonutitem"));
        addCake(ModIntegration.phcLoc("glowberrydonutitem"));
        addCake(ModIntegration.phcLoc("chorusdonutitem"));
        addCake(ModIntegration.phcLoc("melondonutitem"));
        addCake(ModIntegration.phcLoc("appledonutitem"));
        addCake(ModIntegration.phcLoc("powdereddonutitem"));
        addCake(ModIntegration.phcLoc("chocolatedonutitem"));
        addCake(ModIntegration.phcLoc("sweetberrydonutitem"));
        addCake(ModIntegration.phcLoc("honeyglazeddonutitem"));
        addCake(ModIntegration.phcLoc("glowberrymuffinitem"));
        addCake(ModIntegration.phcLoc("chorusmuffinitem"));
        addCake(ModIntegration.phcLoc("chocolatemuffinitem"));
        addCake(ModIntegration.phcLoc("caramelmuffinitem"));
        addCake(ModIntegration.phcLoc("sweetberrymuffinitem"));
        addCake(ModIntegration.phcLoc("honeymuffinitem"));
        addCake(ModIntegration.phcLoc("applemuffinitem"));
        addCake(ModIntegration.phcLoc("melonmuffinitem"));
        addCake(ModIntegration.phcLoc("carrotmuffinitem"));
        addCake(ModIntegration.phcLoc("pumpkinmuffinitem"));

        // Misc
        addVeggie(ModIntegration.pheLoc("mushroomketchupitem"));
        addVeggie(ModIntegration.pheLoc("sesameballitem"));
        addVeggie(ModIntegration.pheLoc("candiedsweetpotatoesitem"));
        addVeggie(ModIntegration.pheLoc("rawtofishitem"));
        addVeggie(ModIntegration.pheLoc("rawtofickenitem"));
        addVeggie(ModIntegration.pheLoc("rawtofaconitem"));
        addVeggie(ModIntegration.pheLoc("rawtofuttonitem"));
        addVeggie(ModIntegration.pheLoc("rawtofabbititem"));
        addVeggie(ModIntegration.pheLoc("rawtofeakitem"));
        addVeggie(ModIntegration.pheLoc("bbqsauceitem"));

        // Meal
        addMeal(ModIntegration.pheLoc("steakfajitaitem"));
        addMeal(ModIntegration.pheLoc("toastedwesternitem"));
        addMeal(ModIntegration.pheLoc("generaltsochickenitem"));
        addMeal(ModIntegration.pheLoc("cevicheitem"));
        addMeal(ModIntegration.pheLoc("zucchinibakeitem"));
        addMeal(ModIntegration.pheLoc("roastedrootveggiemedleyitem"));
        addMeal(ModIntegration.pheLoc("meringueitem"));
        addMeal(ModIntegration.pheLoc("berrymeringuenestitem"));
        addMeal(ModIntegration.pheLoc("meringuebrownieitem"));
        addMeal(ModIntegration.pheLoc("meringuebombeitem"));
        addMeal(ModIntegration.pheLoc("meringuecookieitem"));
        addMeal(ModIntegration.pheLoc("lemonmeringueitem"));
        addMeal(ModIntegration.pheLoc("meringuerouladeitem"));
        addMeal(ModIntegration.pheLoc("raspberrytrifleitem"));
        addMeal(ModIntegration.pheLoc("herbcheeseballitem"));
        addMeal(ModIntegration.pheLoc("spagettiandmeatballsitem"));
        addMeal(ModIntegration.pheLoc("sweetandsourmeatballsitem"));
        addMeal(ModIntegration.pheLoc("swedishmeatballsitem"));
        addMeal(ModIntegration.pheLoc("pineapplehamitem"));
        addMeal(ModIntegration.pheLoc("bakedalaskaitem"));
        addMeal(ModIntegration.pheLoc("grilledskewersitem"));
        addMeal(ModIntegration.phcLoc("grilledmuttonskeweritem"));
        addMeal(ModIntegration.phcLoc("grilledveggieskeweritem"));
        addMeal(ModIntegration.phcLoc("grilledrabbitskeweritem"));
        addMeal(ModIntegration.phcLoc("grilledporkskeweritem"));
        addMeal(ModIntegration.phcLoc("grilledbeefskeweritem"));
        addMeal(ModIntegration.phcLoc("grilledfishskeweritem"));
        addMeal(ModIntegration.phcLoc("grilledchickenskeweritem"));
        addMeal(ModIntegration.pheLoc("rainbowcurryitem"));
        addMeal(ModIntegration.pheLoc("deluxechickencurryitem"));
        addMeal(ModIntegration.pheLoc("curryitem"));
        addMeal(ModIntegration.pheLoc("chickencurryitem"));
        addMeal(ModIntegration.pheLoc("broccolimacitem"));
        addMeal(ModIntegration.pheLoc("chickenkatsuitem"));
        addMeal(ModIntegration.pheLoc("lambkebabitem"));
        addMeal(ModIntegration.pheLoc("fishlettucewrapitem"));
        addMeal(ModIntegration.pheLoc("gourmetmuttonburgeritem"));
        addMeal(ModIntegration.pheLoc("spicebunitem"));
        addMeal(ModIntegration.pheLoc("mashedpotatoeschickenbiscuititem"));
        addMeal(ModIntegration.pheLoc("potatosaladitem"));
        addMeal(ModIntegration.pheLoc("phoitem"));
        addMeal(ModIntegration.pheLoc("ceasarsaladitem"));
        addMeal(ModIntegration.pheLoc("strawberrysouffleitem"));
        addMeal(ModIntegration.pheLoc("creamofchickenitem"));
        addMeal(ModIntegration.pheLoc("gourmetporkpattyitem"));
        addMeal(ModIntegration.pheLoc("summersquashwithradishitem"));
        addMeal(ModIntegration.pheLoc("tacoitem"));
        addMeal(ModIntegration.pheLoc("baconwrappeddatesitem"));
        addMeal(ModIntegration.pheLoc("babaganoushitem"));
        addMeal(ModIntegration.pheLoc("berryvinaigrettesaladitem"));
        addMeal(ModIntegration.pheLoc("surfandturfitem"));
        addMeal(ModIntegration.pheLoc("potatocakesitem"));
        addMeal(ModIntegration.pheLoc("stuffedmushroomitem"));
        addMeal(ModIntegration.pheLoc("imitationcrabsticksitem"));
        addMeal(ModIntegration.pheLoc("cherrycoconutchocolatebaritem"));
        addMeal(ModIntegration.pheLoc("beanburritoitem"));
        addMeal(ModIntegration.pheLoc("pastagardeniaitem"));
        addMeal(ModIntegration.pheLoc("bbqchickenpizzaitem"));
        addMeal(ModIntegration.pheLoc("chocolateorangebiscuititem"));
        addMeal(ModIntegration.pheLoc("beefwellingtonitem"));
        addMeal(ModIntegration.pheLoc("broccolindipitem"));
        addMeal(ModIntegration.pheLoc("chickenalfredoitem"));
        addMeal(ModIntegration.pheLoc("pepperonipizzaitem"));
        addMeal(ModIntegration.pheLoc("avocadoburritoitem"));
        addMeal(ModIntegration.pheLoc("ovenroastedcaulifloweritem"));
        addMeal(ModIntegration.pheLoc("lambwithmintsauceitem"));
        addMeal(ModIntegration.pheLoc("cheesedanishitem"));
        addMeal(ModIntegration.pheLoc("hamandpineapplepizzaitem"));
        addMeal(ModIntegration.pheLoc("veggiestirfryitem"));
        addMeal(ModIntegration.pheLoc("sweetandsourchickenitem"));
        addMeal(ModIntegration.pheLoc("cantonesegreensitem"));
        addMeal(ModIntegration.pheLoc("ploughmanslunchitem"));
        addMeal(ModIntegration.pheLoc("grandmasmacaronicasseroleitem"));
        addMeal(ModIntegration.pheLoc("peanutchocolatebaritem"));
        addMeal(ModIntegration.pheLoc("chocolatecherryitem"));
        addMeal(ModIntegration.pheLoc("deluxecheeseburgeritem"));
        addMeal(ModIntegration.pheLoc("lemonchickenitem"));
        addMeal(ModIntegration.pheLoc("honeysoyribsitem"));
        addMeal(ModIntegration.pheLoc("toadintheholeitem"));
        addMeal(ModIntegration.pheLoc("figbaritem"));
        addMeal(ModIntegration.pheLoc("batteredsausageitem"));
        addMeal(ModIntegration.pheLoc("tatertotsitem"));
        addMeal(ModIntegration.pheLoc("baklavaitem"));
        addMeal(ModIntegration.pheLoc("zucchinifriesitem"));
        addMeal(ModIntegration.pheLoc("chilipoppersitem"));
        addMeal(ModIntegration.pheLoc("chocolatestrawberryitem"));
        addMeal(ModIntegration.pheLoc("cinnamonappleoatmealitem"));
        addMeal(ModIntegration.pheLoc("meatystirfryitem"));
        addMeal(ModIntegration.pheLoc("corndogitem"));
        addMeal(ModIntegration.pheLoc("pumpkinspicelatteitem"));
        addMeal(ModIntegration.pheLoc("museliitem"));
        addMeal(ModIntegration.pheLoc("pinkelitem"));
        addMeal(ModIntegration.pheLoc("spicygreensitem"));
        addMeal(ModIntegration.pheLoc("creamofmushroomitem"));
        addMeal(ModIntegration.pheLoc("sausagerollitem"));
        addMeal(ModIntegration.pheLoc("pavlovaitem"));
        addMeal(ModIntegration.pheLoc("potstickersitem"));
        addMeal(ModIntegration.pheLoc("weekendpicnicitem"));
        addMeal(ModIntegration.pheLoc("bulgogiitem"));
        addMeal(ModIntegration.pheLoc("candiedlemonitem"));
        addMeal(ModIntegration.pheLoc("timtamitem"));
        addMeal(ModIntegration.pheLoc("supremepizzaitem"));
        addMeal(ModIntegration.pheLoc("chickencelerycasseroleitem"));
        addMeal(ModIntegration.pheLoc("chickengumboitem"));
        addMeal(ModIntegration.pheLoc("honeyglazedhamitem"));
        addMeal(ModIntegration.pheLoc("chickencordonbleuitem"));
        addMeal(ModIntegration.pheLoc("sesamesnapsitem"));
        addMeal(ModIntegration.pheLoc("szechuaneggplantitem"));
        addMeal(ModIntegration.pheLoc("beancornmealitem"));
        addMeal(ModIntegration.pheLoc("beansandriceitem"));
        addMeal(ModIntegration.pheLoc("pitepaltitem"));
        addMeal(ModIntegration.pheLoc("koreandinneritem"));
        addMeal(ModIntegration.pheLoc("lamingtonitem"));
        addMeal(ModIntegration.pheLoc("chickenandwafflesitem"));
        addMeal(ModIntegration.pheLoc("braisedonionsitem"));
        addMeal(ModIntegration.pheLoc("cheezepuffsitem"));
        addMeal(ModIntegration.pheLoc("delightedmealitem"));
        addMeal(ModIntegration.pheLoc("vindalooitem"));
        addMeal(ModIntegration.pheLoc("merveilleuxitem"));
        addMeal(ModIntegration.pheLoc("couscousitem"));
        addMeal(ModIntegration.pheLoc("fishtacoitem"));
        addMeal(ModIntegration.pheLoc("quesadillaitem"));
        addMeal(ModIntegration.pheLoc("deluxenachoesitem"));
        addMeal(ModIntegration.pheLoc("coffeeconlecheitem"));
        addMeal(ModIntegration.pheLoc("baconmushroomburgeritem"));
        addMeal(ModIntegration.pheLoc("biscuitsandgravyitem"));
        addMeal(ModIntegration.pheLoc("stuffingitem"));
        addMeal(ModIntegration.pheLoc("battenbergitem"));
        addMeal(ModIntegration.pheLoc("bolognaitem"));
        addMeal(ModIntegration.pheLoc("springsaladitem"));
        addMeal(ModIntegration.pheLoc("cashewbutteritem"));
        addMeal(ModIntegration.pheLoc("porklomeinitem"));
        addMeal(ModIntegration.pheLoc("candiedpecansitem"));
        addMeal(ModIntegration.pheLoc("hotdishcasseroleitem"));
        addMeal(ModIntegration.pheLoc("chikorollitem"));
        addMeal(ModIntegration.pheLoc("eggrollitem"));
        addMeal(ModIntegration.pheLoc("hummusitem"));
        addMeal(ModIntegration.pheLoc("sunflowerseedsbutteritem"));
        addMeal(ModIntegration.pheLoc("chorizoitem"));
        addMeal(ModIntegration.pheLoc("gingeredrhubarbtartitem"));
        addMeal(ModIntegration.pheLoc("chickenchowmeinitem"));
        addMeal(ModIntegration.pheLoc("peachcobbleritem"));
        addMeal(ModIntegration.pheLoc("coleslawitem"));
        addMeal(ModIntegration.pheLoc("teriyakichickenitem"));
        addMeal(ModIntegration.pheLoc("hashitem"));
        addMeal(ModIntegration.pheLoc("honeycombchocolatebaritem"));
        addMeal(ModIntegration.pheLoc("cracklinsitem"));
        addMeal(ModIntegration.pheLoc("tomatoherbchickenitem"));
        addMeal(ModIntegration.pheLoc("lasagnaitem"));
        addMeal(ModIntegration.pheLoc("gourmetporkburgeritem"));
        addMeal(ModIntegration.pheLoc("roastchickenitem"));
        addMeal(ModIntegration.pheLoc("eggsbenedictitem"));
        addMeal(ModIntegration.pheLoc("charsiuitem"));
        addMeal(ModIntegration.pheLoc("heartybreakfastitem"));
        addMeal(ModIntegration.pheLoc("gourmetbeefburgeritem"));
        addMeal(ModIntegration.pheLoc("baconwrappedchiliitem"));
        addMeal(ModIntegration.pheLoc("greeneggsandhamitem"));
        addMeal(ModIntegration.pheLoc("chickentendersitem"));
        addMeal(ModIntegration.pheLoc("bbqchickenbiscuititem"));
        addMeal(ModIntegration.pheLoc("kimchiitem"));
        addMeal(ModIntegration.pheLoc("bangersandmashitem"));
        addMeal(ModIntegration.pheLoc("bbqjackfruititem"));
        addMeal(ModIntegration.pheLoc("mochidessertitem"));
        addMeal(ModIntegration.pheLoc("fishcakesitem"));
        addMeal(ModIntegration.pheLoc("chickenbiscuititem"));
        addMeal(ModIntegration.pheLoc("potatoesobrienitem"));
        addMeal(ModIntegration.pheLoc("peachesandcreamoatmealitem"));
        addMeal(ModIntegration.pheLoc("friedpecanokraitem"));
        addMeal(ModIntegration.pheLoc("crispyricepuffcerealitem"));
        addMeal(ModIntegration.pheLoc("tiropitaitem"));
        addMeal(ModIntegration.pheLoc("friedfeastitem"));
        addMeal(ModIntegration.pheLoc("yorkshirepuddingitem"));
        addMeal(ModIntegration.pheLoc("bakedhamitem"));
        addMeal(ModIntegration.pheLoc("poachedpearitem"));
        addMeal(ModIntegration.pheLoc("spaghettidinneritem"));
        addMeal(ModIntegration.pheLoc("cranberrysauceitem"));
        addMeal(ModIntegration.pheLoc("potatoandcheesepirogiitem"));
        addMeal(ModIntegration.pheLoc("apricotglazedporkitem"));
        addMeal(ModIntegration.pheLoc("southernstylebreakfastitem"));
        addMeal(ModIntegration.pheLoc("kohlundpinkelitem"));
        addMeal(ModIntegration.pheLoc("roastpotatoesitem"));
        addMeal(ModIntegration.pheLoc("pokebowlitem"));
        addMeal(ModIntegration.pheLoc("paradiseburgeritem"));
        addMeal(ModIntegration.pheLoc("vegetarianlettucewrapitem"));
        addMeal(ModIntegration.pheLoc("veggiestripsitem"));
        addMeal(ModIntegration.pheLoc("chickenparmasanitem"));
        addMeal(ModIntegration.pheLoc("sweetandsoursauceitem"));
        addMeal(ModIntegration.pheLoc("sweetpickleitem"));
        addMeal(ModIntegration.pheLoc("buttertartitem"));
        addMeal(ModIntegration.pheLoc("jambalayaitem"));
        addMeal(ModIntegration.pheLoc("spicymustardporkitem"));
        addMeal(ModIntegration.pheLoc("maplecandiedbaconitem"));
        addMeal(ModIntegration.pheLoc("meatfeastpizzaitem"));
        addMeal(ModIntegration.pheLoc("ricepuddingitem"));
        addMeal(ModIntegration.pheLoc("onionhamburgeritem"));
        addMeal(ModIntegration.pheLoc("pambitsboxitem"));
        addMeal(ModIntegration.pheLoc("footlongitem"));
        addMeal(ModIntegration.pheLoc("saladdressingitem"));
        addMeal(ModIntegration.pheLoc("pinacoladaitem"));
        addMeal(ModIntegration.pheLoc("creamedcornitem"));
        addMeal(ModIntegration.pheLoc("garlicmashedpotatoesitem"));
        addMeal(ModIntegration.pheLoc("cornishpastyitem"));
        addMeal(ModIntegration.pheLoc("chestnutbutteritem"));
        addMeal(ModIntegration.pheLoc("almondbutteritem"));
        addMeal(ModIntegration.pheLoc("cornedbeefandcabbageitem"));
        addMeal(ModIntegration.pheLoc("dimsumitem"));
        addMeal(ModIntegration.pheLoc("refriedbeansitem"));
        addMeal(ModIntegration.pheLoc("fishfingersandcustarditem"));
        addMeal(ModIntegration.pheLoc("okracreoleitem"));
        addMeal(ModIntegration.pheLoc("turkishdelightitem"));
        addMeal(ModIntegration.pheLoc("mixedflowerssaladitem"));
        addMeal(ModIntegration.pheLoc("cinnamonrollitem"));
        addMeal(ModIntegration.pheLoc("epicbltitem"));
        addMeal(ModIntegration.pheLoc("chocolateorangeitem"));
        addMeal(ModIntegration.pheLoc("firmtofuitem"));
        addMeal(ModIntegration.pheLoc("marzipanitem"));
        addMeal(ModIntegration.pheLoc("peanutbuttercookiesitem"));
        addMeal(ModIntegration.pheLoc("honeyglazedcarrotsitem"));
        addMeal(ModIntegration.pheLoc("crispyricepuffbarsitem"));
        addMeal(ModIntegration.pheLoc("meesuaitem"));
        addMeal(ModIntegration.pheLoc("cashewchickenitem"));
        addMeal(ModIntegration.pheLoc("friedonionsitem"));
        addMeal(ModIntegration.pheLoc("poutineitem"));
        addMeal(ModIntegration.pheLoc("springfieldcashewchickenitem"));
        addMeal(ModIntegration.pheLoc("anchovypepperonipizzaitem"));
        addMeal(ModIntegration.pheLoc("cantonesenoodlesitem"));
        addMeal(ModIntegration.pheLoc("mozzerellasticksitem"));
        addMeal(ModIntegration.pheLoc("mushroomoilitem"));
        addMeal(ModIntegration.pheLoc("mushroomrisottoitem"));
        addMeal(ModIntegration.pheLoc("nachoesitem"));
        addMeal(ModIntegration.pheLoc("picklesitem"));
        addMeal(ModIntegration.pheLoc("croissantitem"));
        addMeal(ModIntegration.pheLoc("garammasalaitem"));
        addMeal(ModIntegration.pheLoc("molasseschickenitem"));
        addMeal(ModIntegration.pheLoc("porkrindsitem"));
        addMeal(ModIntegration.pheLoc("spagettiitem"));
        addMeal(ModIntegration.pheLoc("succotashitem"));
        addMeal(ModIntegration.pheLoc("gyudonitem"));
        addMeal(ModIntegration.pheLoc("candiedwalnutsitem"));
        addMeal(ModIntegration.pheLoc("omeletitem"));
        addMeal(ModIntegration.pheLoc("celeryandpeanutbutteritem"));
        addMeal(ModIntegration.pheLoc("pickledonionsitem"));
        addMeal(ModIntegration.pheLoc("misopasteitem"));
        addMeal(ModIntegration.pheLoc("espressoitem"));
        addMeal(ModIntegration.pheLoc("briochebunitem"));
        addMeal(ModIntegration.pheLoc("californiarollitem"));
        addMeal(ModIntegration.pheLoc("porklettucewrapitem"));
        addMeal(ModIntegration.pheLoc("paneeritem"));
        addMeal(ModIntegration.pheLoc("timpanoitem"));
        addMeal(ModIntegration.pheLoc("hazelnutbutteritem"));
        addMeal(ModIntegration.pheLoc("threebeansaladitem"));
        addMeal(ModIntegration.pheLoc("stuffedchilipeppersitem"));
        addMeal(ModIntegration.pheLoc("mushroomlasagnaitem"));
        addMeal(ModIntegration.pheLoc("asparagusquicheitem"));
        addMeal(ModIntegration.pheLoc("vegemiteitem"));
        addMeal(ModIntegration.pheLoc("beetburgeritem"));
        addMeal(ModIntegration.pheLoc("cookoutmealitem"));
        addMeal(ModIntegration.pheLoc("wafflesitem"));
        addMeal(ModIntegration.pheLoc("chocolatepeanutbaritem"));
        addMeal(ModIntegration.pheLoc("maplesyrupwafflesitem"));
        addMeal(ModIntegration.pheLoc("dangoitem"));
        addMeal(ModIntegration.pheLoc("imagawayakiitem"));
        addMeal(ModIntegration.pheLoc("stuffedpepperitem"));
        addMeal(ModIntegration.pheLoc("mettbrotchenitem"));
        addMeal(ModIntegration.pheLoc("pecanbutteritem"));
        addMeal(ModIntegration.pheLoc("mapleoatmealitem"));
        addMeal(ModIntegration.pheLoc("coleslawburgeritem"));
        addMeal(ModIntegration.pheLoc("pumpkinoatsconesitem"));
        addMeal(ModIntegration.pheLoc("haggisitem"));
        addMeal(ModIntegration.pheLoc("dhalitem"));
        addMeal(ModIntegration.pheLoc("sweetpotatosouffleitem"));
        addMeal(ModIntegration.pheLoc("schnitzelitem"));
        addMeal(ModIntegration.pheLoc("bratwurstitem"));
        addMeal(ModIntegration.pheLoc("sundayroastitem"));
        addMeal(ModIntegration.pheLoc("mashedsweetpotatoesitem"));
        addMeal(ModIntegration.pheLoc("cornedbeefhashitem"));
        addMeal(ModIntegration.pheLoc("eggplantparmitem"));
        addMeal(ModIntegration.pheLoc("silkentofuitem"));
        addMeal(ModIntegration.pheLoc("springrollitem"));
        addMeal(ModIntegration.pheLoc("hazelnutchocolateitem"));
        addMeal(ModIntegration.pheLoc("homestylelunchitem"));
        addMeal(ModIntegration.pheLoc("gingerchickenitem"));
        addMeal(ModIntegration.pheLoc("gritsitem"));
        addMeal(ModIntegration.pheLoc("peanutbuttercupitem"));
        addMeal(ModIntegration.pheLoc("musubiitem"));
        addMeal(ModIntegration.pheLoc("gourmetbeefpattyitem"));
        addMeal(ModIntegration.pheLoc("slawdogitem"));
        addMeal(ModIntegration.pheLoc("pistachiobutteritem"));
        addMeal(ModIntegration.pheLoc("garlicchickenitem"));
        addMeal(ModIntegration.pheLoc("maplesausageitem"));
        addMeal(ModIntegration.pheLoc("liverandonionsitem"));
        addMeal(ModIntegration.pheLoc("pemmicanitem"));
        addMeal(ModIntegration.pheLoc("ramenitem"));
        addMeal(ModIntegration.pheLoc("sausagebeanmeltitem"));
        addMeal(ModIntegration.pheLoc("citrussaladitem"));
        addMeal(ModIntegration.pheLoc("salsaitem"));
        addMeal(ModIntegration.pheLoc("mangochutneyitem"));
        addMeal(ModIntegration.pheLoc("bentoboxitem"));
        addMeal(ModIntegration.pheLoc("orangechickenitem"));
        addMeal(ModIntegration.pheLoc("pepperstirfryitem"));
        addMeal(ModIntegration.pheLoc("orangegingerbeefitem"));
        addMeal(ModIntegration.pheLoc("sausageinbreaditem"));
        addMeal(ModIntegration.pheLoc("capresesaladitem"));
        addMeal(ModIntegration.pheLoc("etonmessitem"));
        addMeal(ModIntegration.pheLoc("peasandceleryitem"));
        addMeal(ModIntegration.pheLoc("hotwingsitem"));
        addMeal(ModIntegration.pheLoc("sunflowerwheatrollsitem"));
        addMeal(ModIntegration.pheLoc("cornflakesitem"));
        addMeal(ModIntegration.pheLoc("manjuuitem"));
        addMeal(ModIntegration.pheLoc("taiyakiitem"));
        addMeal(ModIntegration.pheLoc("theatreboxitem"));
        addMeal(ModIntegration.pheLoc("walnutbutteritem"));
        addMeal(ModIntegration.pheLoc("pizzasliceitem"));
        addMeal(ModIntegration.pheLoc("greenbeancasseroleitem"));
        addMeal(ModIntegration.pheLoc("blackberrycobbleritem"));
        addMeal(ModIntegration.pheLoc("sauerbratenitem"));
        addMeal(ModIntegration.pheLoc("applefritteritem"));
        addMeal(ModIntegration.pheLoc("chiliitem"));
        addMeal(ModIntegration.pheLoc("stuffedeggplantitem"));
        addMeal(ModIntegration.pheLoc("zeppoleitem"));
        addMeal(ModIntegration.pheLoc("cornedbeefbreakfastitem"));
        addMeal(ModIntegration.pheLoc("mixedsaladitem"));
        addMeal(ModIntegration.pheLoc("sushiitem"));
        addMeal(ModIntegration.pheLoc("friedriceitem"));
        addMeal(ModIntegration.pheLoc("sunflowerbroccolisaladitem"));
        addMeal(ModIntegration.pheLoc("chimichangaitem"));
        addMeal(ModIntegration.pheLoc("oatmealraisincookiesitem"));
        addMeal(ModIntegration.pheLoc("eggtartitem"));
        addMeal(ModIntegration.pheLoc("onigiriitem"));
        addMeal(ModIntegration.pheLoc("bibimbapitem"));
        addMeal(ModIntegration.pheLoc("saucedlambkebabitem"));
        addMeal(ModIntegration.pheLoc("gourmetmuttonpattyitem"));
        addMeal(ModIntegration.pheLoc("crackersandcheeseitem"));
        addMeal(ModIntegration.pheLoc("deviledeggitem"));
        addMeal(ModIntegration.pheLoc("herbbutterparsnipsitem"));
        addMeal(ModIntegration.pheLoc("scallionbakedpotatoitem"));
        addMeal(ModIntegration.pheLoc("guisoitem"));
        addMeal(ModIntegration.pheLoc("chocovoxelsitem"));
        addMeal(ModIntegration.pheLoc("rouxitem"));
        addMeal(ModIntegration.pheLoc("bakedbeansitem"));
        addMeal(ModIntegration.pheLoc("suaderoitem"));
        addMeal(ModIntegration.pheLoc("zestyzucchiniitem"));
        addMeal(ModIntegration.pheLoc("custarditem"));
        addMeal(ModIntegration.pheLoc("hushpuppiesitem"));
        addMeal(ModIntegration.pheLoc("lemonaideitem"));
        addMeal(ModIntegration.pheLoc("paneertikkamasalaitem"));
        addMeal(ModIntegration.pheLoc("loadedbakedpotatoitem"));
        addMeal(ModIntegration.pheLoc("onionringsitem"));
        addMeal(ModIntegration.pheLoc("breakfastburritoitem"));
        addMeal(ModIntegration.pheLoc("gherkinitem"));
        addMeal(ModIntegration.pheLoc("cornmealitem"));
        addMeal(ModIntegration.pheLoc("honeylemonlambitem"));
        addMeal(ModIntegration.pheLoc("chilichocolateitem"));
        addMeal(ModIntegration.pheLoc("breadedporkchopitem"));
        addMeal(ModIntegration.pheLoc("eggsaladitem"));
        addMeal(ModIntegration.pheLoc("aebleskiversitem"));
        addMeal(ModIntegration.pheLoc("ranchfriedchickenitem"));
        addMeal(ModIntegration.pheLoc("omuriceitem"));
        addMeal(ModIntegration.pheLoc("futomakiitem"));
        addMeal(ModIntegration.pheLoc("raisinsitem"));
        addMeal(ModIntegration.pheLoc("hoisinsauceitem"));
        addMeal(ModIntegration.pheLoc("bltitem"));
        addMeal(ModIntegration.pheLoc("padthaiitem"));
        addMeal(ModIntegration.pheLoc("chilidogitem"));
        addMeal(ModIntegration.pheLoc("jalapenoburgeritem"));
        addMeal(ModIntegration.pheLoc("nopalessaladitem"));
        addMeal(ModIntegration.pheLoc("mushroomketchupomeletitem"));
        addMeal(ModIntegration.pheLoc("mcpamitem"));
        addMeal(ModIntegration.pheLoc("cornedbeefitem"));
        addMeal(ModIntegration.pheLoc("pralinesitem"));
        addMeal(ModIntegration.pheLoc("chickentendersmealitem"));
        addMeal(ModIntegration.pheLoc("saltandpepperitem"));
        addMeal(ModIntegration.pheLoc("biscuititem"));
        addMeal(ModIntegration.pheLoc("jellyrollitem"));
        addMeal(ModIntegration.pheLoc("ediblerootitem"));
        addMeal(ModIntegration.pheLoc("friedgreentomatoesitem"));
        addMeal(ModIntegration.pheLoc("enchiladaitem"));
        addMeal(ModIntegration.pheLoc("fajitaburritoitem"));
        addMeal(ModIntegration.pheLoc("chocolatecoconutbaritem"));
        addMeal(ModIntegration.pheLoc("fiestacornsaladitem"));
        addMeal(ModIntegration.pheLoc("candiedgingeritem"));
        addMeal(ModIntegration.pheLoc("damperitem"));
        addMeal(ModIntegration.pheLoc("kungpaochickenitem"));
        addMeal(ModIntegration.pheLoc("fishdinneritem"));
        addMeal(ModIntegration.phcLoc("trailmixitem"));
        addMeal(ModIntegration.phcLoc("grilledcheeseitem"));
        addMeal(ModIntegration.phcLoc("chickennuggetitem"));
        addMeal(ModIntegration.phcLoc("pickledbeetsitem"));
        addMeal(ModIntegration.phcLoc("baconcheeseburgeritem"));
        addMeal(ModIntegration.phcLoc("hotdogitem"));
        addMeal(ModIntegration.phcLoc("cookiesandmilkitem"));
        addMeal(ModIntegration.phcLoc("meatloafitem"));
        addMeal(ModIntegration.phcLoc("chocolatecaramelfudgeitem"));
        addMeal(ModIntegration.phcLoc("gummycreepersitem"));
        addMeal(ModIntegration.phcLoc("friedchickenitem"));
        addMeal(ModIntegration.phcLoc("basiccheeseburgeritem"));
        addMeal(ModIntegration.phcLoc("grilledcheeseandhamitem"));
        addMeal(ModIntegration.phcLoc("glazedcarrotsitem"));
        addMeal(ModIntegration.phcLoc("applesauceitem"));
        addMeal(ModIntegration.phcLoc("fruitsaladitem"));
        addMeal(ModIntegration.phcLoc("chocolaterollitem"));
        addMeal(ModIntegration.phcLoc("baconandeggsitem"));
        addMeal(ModIntegration.phcLoc("basicveggieburgeritem"));
        addMeal(ModIntegration.phcLoc("basichamburgeritem"));
        addMeal(ModIntegration.phcLoc("butteredbakedpotatoitem"));
        addMeal(ModIntegration.phcLoc("softpretzelitem"));
        addMeal(ModIntegration.phcLoc("macncheeseitem"));
        addMeal(ModIntegration.phcLoc("chickendinneritem"));
        addMeal(ModIntegration.phcLoc("mashedpotatoesitem"));
        addMeal(ModIntegration.phcLoc("scrambledeggitem"));
        addMeal(ModIntegration.phcLoc("fishsticksitem"));
        addMeal(ModIntegration.phcLoc("crackersandcheeseitem"));
        addMeal(ModIntegration.phcLoc("potroastitem"));
        addMeal(ModIntegration.phcLoc("caramelappleitem"));
        addMeal(ModIntegration.phcLoc("friedeggitem"));
        addMeal(ModIntegration.pcropsLoc("barrelcactusitem"));

        // The Veggie Way
        // Drinks
        addDrink(ModIntegration.veggieLoc("energy_drink"));

        // Fruit
        addFruit(ModIntegration.veggieLoc("melon_chunk"));
        addFruit(ModIntegration.veggieLoc("cactus_chunk"));

        // Pie
        addPie(ModIntegration.veggieLoc("apple_pie"));
        addPie(ModIntegration.veggieLoc("melon_pie"));
        addPie(ModIntegration.veggieLoc("sweet_berry_pie"));
        addPie(ModIntegration.veggieLoc("cactus_pie"));

        // Shake
        addShake(ModIntegration.veggieLoc("superfood_shake"));
        addShake(ModIntegration.veggieLoc("superfood_smoothie"));

        // Soup
        addSoup(ModIntegration.veggieLoc("cactus_soup"));
        addSoup(ModIntegration.veggieLoc("pumpkin_soup"));
        addSoup(ModIntegration.veggieLoc("melon_soup"));
        addSoup(ModIntegration.veggieLoc("carrot_soup"));
        addSoup(ModIntegration.veggieLoc("lentil_soup"));

        // Veggies
        addVeggie(ModIntegration.veggieLoc("corn"));
        addVeggie(ModIntegration.veggieLoc("cooked_carrot"));
        addVeggie(ModIntegration.veggieLoc("cooked_beetroot"));
        addVeggie(ModIntegration.veggieLoc("chocolate_bar"));
        addVeggie(ModIntegration.veggieLoc("pumpkin_chunk"));
        addVeggie(ModIntegration.veggieLoc("soybean"));
        addVeggie(ModIntegration.veggieLoc("lentil"));
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
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput cache) throws IllegalStateException {
        List<CompletableFuture<?>> recipeList = new ArrayList<>();

        addDrinkableItems();

        for (Map.Entry<ResourceLocation, DrinkableItem> entry : DRINKABLE_ITEMS.entrySet()) {
            PackOutput.PathProvider pathProvider = getPath(entry.getKey());

            recipeList.add(DataProvider.saveStable(cache,
                    DrinkableItemManager.parseDrinkableItem(entry.getValue()),
                    pathProvider.json(entry.getKey())));
        }

        return CompletableFuture.allOf(recipeList.toArray(CompletableFuture[]::new));
    }

    private PackOutput.PathProvider getPath(ResourceLocation loc) {
        return this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "environment/drinkable/");
    }

}
