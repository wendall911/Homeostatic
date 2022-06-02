package homeostatic.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import homeostatic.common.temperature.Environment;
import homeostatic.Homeostatic;

public class InsulationHelper {

    /*
     * Base is 9 = shirt+pants+shoes (3) * 3F
     * Armor is calculated in as 4F per piece base value + modifiers.
     */
    public static double getInsulationModifier(ServerPlayer sp, int wetness, float localTemperature) {
        double modifier = 9.0;
        Iterable<ItemStack> slots = sp.getArmorSlots();

        for (ItemStack stack : slots) {
            if (stack.getItem() instanceof ArmorItem armorItem) {
                // Now need to check for insulation ... blah blah

                modifier += 3.0;
            }
        }

        /*
         * If temperature is cool, decrease effectiveness of armor by percent wet.
         * If in a hot environment, increase effectiveness of armor by percent wet.
         */
        if (localTemperature < Environment.PARITY) {
            modifier = modifier * (1 - (wetness / 20.0));
        }
        else if (localTemperature > Environment.HOT) {
            modifier = modifier * (1 + (wetness / 20.0));
        }

        return modifier;
    }
}
