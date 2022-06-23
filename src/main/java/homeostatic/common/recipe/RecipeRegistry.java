package homeostatic.common.recipe;

import homeostatic.Homeostatic;
import net.minecraft.world.item.crafting.RecipeSerializer;

import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Homeostatic.MODID);
    public static RegistryObject<RecipeSerializer<ArmorEnhancement>> ARMOR_ENHANCEMENT_SERIALIZER;
    public static RegistryObject<RecipeSerializer<PurifiedLeatherFlask>> PURIFIED_LEATHER_FLASK_SERIALIZER;

    public static void init() {
        ARMOR_ENHANCEMENT_SERIALIZER = RECIPES.register("armor_enhancement", () -> new SimpleRecipeSerializer<>(ArmorEnhancement::new));
        PURIFIED_LEATHER_FLASK_SERIALIZER = RECIPES.register("purified_leather_flask", () -> new SimpleRecipeSerializer<>(PurifiedLeatherFlask::new));
    }

}
