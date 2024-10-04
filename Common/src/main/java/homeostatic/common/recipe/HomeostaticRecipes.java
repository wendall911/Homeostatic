package homeostatic.common.recipe;

import java.util.function.BiConsumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import static homeostatic.Homeostatic.loc;

public class HomeostaticRecipes {

    public static RecipeSerializer<ArmorEnhancement> ARMOR_ENHANCEMENT_SERIALIZER;
    public static RecipeSerializer<PurifiedLeatherFlask> PURIFIED_LEATHER_FLASK_SERIALIZER;
    public static RecipeSerializer<HelmetThermometer> HELMET_THERMOMETER_SERIALIZER;
    public static RecipeSerializer<RemoveArmorEnhancement> REMOVE_ARMOR_ENHANCEMENT_SERIALIZER;
    public static RecipeSerializer<CampfirePurifiedLeatherFlask> CAMPFIRE_PURIFIED_LEATHER_FLASK_SERIALIZER;
    public static RecipeSerializer<SmeltingPurifiedLeatherFlask> SMELTING_PURIFIED_LEATHER_FLASK_SERIALIZER;
    public static RecipeSerializer<SmokingPurifiedLeatherFlask> SMOKING_PURIFIED_LEATHER_FLASK_SERIALIZER;
    public static RecipeSerializer<CampfirePurifiedWaterBottle> CAMPFIRE_PURIFIED_WATER_BOTTLE_SERIALIZER;
    public static RecipeSerializer<SmeltingPurifiedWaterBottle> SMELTING_PURIFIED_WATER_BOTTLE_SERIALIZER;
    public static RecipeSerializer<SmokingPurifiedWaterBottle> SMOKING_PURIFIED_WATER_BOTTLE_SERIALIZER;

    public static void init(BiConsumer<RecipeSerializer<?>, ResourceLocation> consumer) {
        ARMOR_ENHANCEMENT_SERIALIZER = new SimpleCraftingRecipeSerializer<>(ArmorEnhancement::new);
        PURIFIED_LEATHER_FLASK_SERIALIZER = new SimpleCraftingRecipeSerializer<>(PurifiedLeatherFlask::new);
        HELMET_THERMOMETER_SERIALIZER = new SimpleCraftingRecipeSerializer<>(HelmetThermometer::new);
        REMOVE_ARMOR_ENHANCEMENT_SERIALIZER = new SimpleCraftingRecipeSerializer<>(RemoveArmorEnhancement::new);
        CAMPFIRE_PURIFIED_LEATHER_FLASK_SERIALIZER = new SimpleCookingSerializerWrapper<>(CampfirePurifiedLeatherFlask::new, 200);
        SMELTING_PURIFIED_LEATHER_FLASK_SERIALIZER = new SimpleCookingSerializerWrapper<>(SmeltingPurifiedLeatherFlask::new, 150);
        SMOKING_PURIFIED_LEATHER_FLASK_SERIALIZER = new SimpleCookingSerializerWrapper<>(SmokingPurifiedLeatherFlask::new, 100);
        CAMPFIRE_PURIFIED_WATER_BOTTLE_SERIALIZER = new SimpleCookingSerializerWrapper<>(CampfirePurifiedWaterBottle::new, 100);
        SMELTING_PURIFIED_WATER_BOTTLE_SERIALIZER = new SimpleCookingSerializerWrapper<>(SmeltingPurifiedWaterBottle::new, 75);
        SMOKING_PURIFIED_WATER_BOTTLE_SERIALIZER = new SimpleCookingSerializerWrapper<>(SmokingPurifiedWaterBottle::new, 50);

        consumer.accept(ARMOR_ENHANCEMENT_SERIALIZER, loc("armor_enhancement"));
        consumer.accept(PURIFIED_LEATHER_FLASK_SERIALIZER, loc("purified_leather_flask"));
        consumer.accept(HELMET_THERMOMETER_SERIALIZER, loc("helmet_thermometer"));
        consumer.accept(REMOVE_ARMOR_ENHANCEMENT_SERIALIZER, loc("remove_armor_enhancement"));
        consumer.accept(CAMPFIRE_PURIFIED_LEATHER_FLASK_SERIALIZER, loc("campfire_purified_leather_flask"));
        consumer.accept(SMELTING_PURIFIED_LEATHER_FLASK_SERIALIZER, loc("smelting_purified_leather_flask"));
        consumer.accept(SMOKING_PURIFIED_LEATHER_FLASK_SERIALIZER, loc("smoking_purified_leather_flask"));
        consumer.accept(CAMPFIRE_PURIFIED_WATER_BOTTLE_SERIALIZER, loc("campfire_purified_water_bottle"));
        consumer.accept(SMELTING_PURIFIED_WATER_BOTTLE_SERIALIZER, loc("smelting_purified_water_bottle"));
        consumer.accept(SMOKING_PURIFIED_WATER_BOTTLE_SERIALIZER, loc("smoking_purified_water_bottle"));
    }

}