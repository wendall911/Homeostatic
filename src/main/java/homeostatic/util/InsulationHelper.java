package homeostatic.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import homeostatic.common.TagManager;
import homeostatic.common.temperature.Environment;
import homeostatic.common.temperature.TemperatureDirection;

public class InsulationHelper {

    private static float localTemperature;

    /*
     *
     */
    public static double getInsulationModifier(ServerPlayer sp, int wetness, TemperatureDirection direction, float lTemperature) {
        double modifier = 0.0;
        double wetnessModifier = -1;

        localTemperature = lTemperature;

        if (wetness > 0) {
            if (localTemperature < Environment.PARITY_HIGH) {
                wetnessModifier = 1.0 - (wetness / 20.0);
            } else {
                wetnessModifier = wetness / 20.0;
            }
        }

        if (direction != TemperatureDirection.WARMING_RAPIDLY && direction != TemperatureDirection.COOLING_RAPIDLY) {
            modifier += getInsulationModifier(0.0, sp.getItemBySlot(EquipmentSlot.HEAD), wetnessModifier);
            modifier += getInsulationModifier(4.3, sp.getItemBySlot(EquipmentSlot.CHEST), wetnessModifier);
            modifier += getInsulationModifier(4.3, sp.getItemBySlot(EquipmentSlot.LEGS), wetnessModifier);
            modifier += getInsulationModifier(4.3, sp.getItemBySlot(EquipmentSlot.FEET), wetnessModifier);
        }

        return modifier;
    }

    private static double getInsulationModifier(double coldBase, ItemStack armor, double wetnessModifier) {
        double modifier;
        double armorModifier = !armor.isEmpty() ? 3.0 : 0.0;
        boolean isCold = localTemperature < Environment.PARITY_LOW;
        boolean isWarm = localTemperature > Environment.PARITY_HIGH;

        if (isCold) {
            modifier = coldBase + armorModifier;
        }
        else {
            // Don't include coldBase if warm
            modifier = armorModifier;

            // Increase base armor insulation effectiveness if wet
            if (isWarm && wetnessModifier != -1) {
                if (armorModifier != 0.0) {
                    modifier = armorModifier * (1.0 + wetnessModifier);
                }
                else {
                    modifier = coldBase * wetnessModifier;
                }
            }
        }

        // Add modifier for any armor type
        if (armorModifier != 0.0) {
            CompoundTag tags = armor.getTag();

            if ((tags != null && tags.contains("insulation")) || armor.is(TagManager.Items.INSULATED_ARMOR)) {
                if (isCold) {
                    modifier += 4.0;
                }
                else {
                    modifier += 2.0;
                }

                if (isCold && wetnessModifier != -1) {
                    // Cut wetness effect in half when item has insulation
                    modifier = modifier * (wetnessModifier / 2.0);
                }
            }
        }
        else if (wetnessModifier != -1 && isCold) {
            modifier = modifier * wetnessModifier;
        }

        return modifier;
    }

}
