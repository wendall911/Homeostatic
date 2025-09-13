package homeostatic;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import homeostatic.common.potions.HomeostaticPotions;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, modid = Homeostatic.MODID)
public class HomeostaticCommonEvents {

    @SubscribeEvent
    public static void registerPotionRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(
            Potions.AWKWARD,
            Items.SNOWBALL,
            BuiltInRegistries.POTION.wrapAsHolder(HomeostaticPotions.FROST_RESISTANCE)
        );
        builder.addMix(
            BuiltInRegistries.POTION.wrapAsHolder(HomeostaticPotions.FROST_RESISTANCE),
            Items.REDSTONE,
            BuiltInRegistries.POTION.wrapAsHolder(HomeostaticPotions.LONG_FROST_RESISTANCE)
        );
    }

}
