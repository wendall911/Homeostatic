package homeostatic.common.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import net.minecraftforge.registries.RegisterEvent;

public class HomeostaticRecipes {

    public static RecipeSerializer<ArmorEnhancement> ARMOR_ENHANCEMENT_SERIALIZER;
    public static RecipeSerializer<PurifiedLeatherFlask> PURIFIED_LEATHER_FLASK_SERIALIZER;
    public static RecipeSerializer<HelmetThermometer> HELMET_THERMOMETER_SERIALIZER;
    public static RecipeSerializer<RemoveArmorEnhancement> REMOVE_ARMOR_ENHANCEMENT_SERIALIZER;

    public static void init(RegisterEvent.RegisterHelper<RecipeSerializer<?>> registerHelper) {
        ARMOR_ENHANCEMENT_SERIALIZER = new SimpleCraftingRecipeSerializer<>(ArmorEnhancement::new);
        PURIFIED_LEATHER_FLASK_SERIALIZER = new SimpleCraftingRecipeSerializer<>(PurifiedLeatherFlask::new);
        HELMET_THERMOMETER_SERIALIZER = new SimpleCraftingRecipeSerializer<>(HelmetThermometer::new);
        REMOVE_ARMOR_ENHANCEMENT_SERIALIZER = new SimpleCraftingRecipeSerializer<>(RemoveArmorEnhancement::new);

        registerHelper.register("armor_enhancement", ARMOR_ENHANCEMENT_SERIALIZER);
        registerHelper.register("purified_leather_flask", PURIFIED_LEATHER_FLASK_SERIALIZER);
        registerHelper.register("helmet_thermometer", HELMET_THERMOMETER_SERIALIZER);
        registerHelper.register("remove_armor_enhancement", REMOVE_ARMOR_ENHANCEMENT_SERIALIZER);
    }

}