package homeostatic.common.recipe;

import java.util.function.BiConsumer;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;

import static homeostatic.Homeostatic.prefix;

public class HomeostaticRecipes {

    public static void init(BiConsumer<RecipeSerializer<?>, Identifier> consumer) {
        consumer.accept(ArmorEnhancement.SERIALIZER, prefix("armor_enhancement"));
        consumer.accept(PurifiedLeatherFlask.SERIALIZER, prefix("purified_leather_flask"));
        consumer.accept(HelmetThermometer.SERIALIZER, prefix("helmet_thermometer"));
        consumer.accept(RemoveArmorEnhancement.SERIALIZER, prefix("remove_armor_enhancement"));
        consumer.accept(CampfirePurifiedLeatherFlask.SERIALIZER, prefix("campfire_purified_leather_flask"));
        consumer.accept(SmeltingPurifiedLeatherFlask.SERIALIZER, prefix("smelting_purified_leather_flask"));
        consumer.accept(SmokingPurifiedLeatherFlask.SERIALIZER, prefix("smoking_purified_leather_flask"));
        consumer.accept(CampfirePurifiedWaterBottle.SERIALIZER, prefix("campfire_purified_water_bottle"));
        consumer.accept(SmeltingPurifiedWaterBottle.SERIALIZER, prefix("smelting_purified_water_bottle"));
        consumer.accept(SmokingPurifiedWaterBottle.SERIALIZER, prefix("smoking_purified_water_bottle"));
    }

}
