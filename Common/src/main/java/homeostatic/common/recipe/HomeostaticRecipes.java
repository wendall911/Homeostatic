package homeostatic.common.recipe;

import java.util.function.BiConsumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;

import static homeostatic.Homeostatic.prefix;

public class HomeostaticRecipes {

    public static RecipeSerializer<ArmorEnhancement> ARMOR_ENHANCEMENT_SERIALIZER;
    public static RecipeSerializer<PurifiedLeatherFlask> PURIFIED_LEATHER_FLASK_SERIALIZER;
    public static RecipeSerializer<HelmetThermometer> HELMET_THERMOMETER_SERIALIZER;
    public static RecipeSerializer<RemoveArmorEnhancement> REMOVE_ARMOR_ENHANCEMENT_SERIALIZER;
    public static RecipeSerializer<CampfireCookingRecipe> CAMPFIRE_PURIFIED_LEATHER_FLASK_SERIALIZER;
    public static RecipeSerializer<SmeltingRecipe> SMELTING_PURIFIED_LEATHER_FLASK_SERIALIZER;
    public static RecipeSerializer<SmokingRecipe> SMOKING_PURIFIED_LEATHER_FLASK_SERIALIZER;
    public static RecipeSerializer<CampfireCookingRecipe> CAMPFIRE_PURIFIED_WATER_BOTTLE_SERIALIZER;
    public static RecipeSerializer<SmeltingRecipe> SMELTING_PURIFIED_WATER_BOTTLE_SERIALIZER;
    public static RecipeSerializer<SmokingRecipe> SMOKING_PURIFIED_WATER_BOTTLE_SERIALIZER;

    public static void init(BiConsumer<RecipeSerializer<?>, ResourceLocation> consumer) {
        ARMOR_ENHANCEMENT_SERIALIZER = new CustomRecipe.Serializer<>(ArmorEnhancement::new);
        PURIFIED_LEATHER_FLASK_SERIALIZER = new CustomRecipe.Serializer<>(PurifiedLeatherFlask::new);
        HELMET_THERMOMETER_SERIALIZER = new CustomRecipe.Serializer<>(HelmetThermometer::new);
        REMOVE_ARMOR_ENHANCEMENT_SERIALIZER = new CustomRecipe.Serializer<>(RemoveArmorEnhancement::new);
        CAMPFIRE_PURIFIED_LEATHER_FLASK_SERIALIZER = new AbstractCookingRecipe.Serializer<>(CampfirePurifiedLeatherFlask::new, 200);
        SMELTING_PURIFIED_LEATHER_FLASK_SERIALIZER = new AbstractCookingRecipe.Serializer<>(SmeltingPurifiedLeatherFlask::new, 150);
        SMOKING_PURIFIED_LEATHER_FLASK_SERIALIZER = new AbstractCookingRecipe.Serializer<>(SmokingPurifiedLeatherFlask::new, 100);
        CAMPFIRE_PURIFIED_WATER_BOTTLE_SERIALIZER = new AbstractCookingRecipe.Serializer<>(CampfirePurifiedWaterBottle::new, 100);
        SMELTING_PURIFIED_WATER_BOTTLE_SERIALIZER = new AbstractCookingRecipe.Serializer<>(SmeltingPurifiedWaterBottle::new, 75);
        SMOKING_PURIFIED_WATER_BOTTLE_SERIALIZER = new AbstractCookingRecipe.Serializer<>(SmokingPurifiedWaterBottle::new, 50);

        consumer.accept(ARMOR_ENHANCEMENT_SERIALIZER, prefix("armor_enhancement"));
        consumer.accept(PURIFIED_LEATHER_FLASK_SERIALIZER, prefix("purified_leather_flask"));
        consumer.accept(HELMET_THERMOMETER_SERIALIZER, prefix("helmet_thermometer"));
        consumer.accept(REMOVE_ARMOR_ENHANCEMENT_SERIALIZER, prefix("remove_armor_enhancement"));
        consumer.accept(CAMPFIRE_PURIFIED_LEATHER_FLASK_SERIALIZER, prefix("campfire_purified_leather_flask"));
        consumer.accept(SMELTING_PURIFIED_LEATHER_FLASK_SERIALIZER, prefix("smelting_purified_leather_flask"));
        consumer.accept(SMOKING_PURIFIED_LEATHER_FLASK_SERIALIZER, prefix("smoking_purified_leather_flask"));
        consumer.accept(CAMPFIRE_PURIFIED_WATER_BOTTLE_SERIALIZER, prefix("campfire_purified_water_bottle"));
        consumer.accept(SMELTING_PURIFIED_WATER_BOTTLE_SERIALIZER, prefix("smelting_purified_water_bottle"));
        consumer.accept(SMOKING_PURIFIED_WATER_BOTTLE_SERIALIZER, prefix("smoking_purified_water_bottle"));
    }

}
