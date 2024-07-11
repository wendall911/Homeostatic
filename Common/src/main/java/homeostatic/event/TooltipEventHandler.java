package homeostatic.event;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;

import homeostatic.common.component.HomeostaticComponents;
import homeostatic.common.TagManager;
import homeostatic.data.integration.ModIntegration;
import homeostatic.platform.Services;

public class TooltipEventHandler {

    public static void onItemToolTip(ItemStack itemStack, List<Component> toolTip) {
        boolean sewingKitItem = Services.PLATFORM.isModLoaded(ModIntegration.SK_MODID) && itemStack.is(TagManager.Items.SEWINGKIT_WEARABLE);

        if (itemStack.getItem() instanceof ArmorItem || sewingKitItem) {
            CompoundTag tags = itemStack.getOrDefault(HomeostaticComponents.ARMOR, CustomData.EMPTY).copyTag();;

            if ((tags.contains("insulation")) || itemStack.is(TagManager.Items.INSULATED_ARMOR)) {
                toolTip.add((Component.translatable("tooltip.insulation")).withStyle(ChatFormatting.GRAY));
            }

            if ((tags.contains("waterproof")) || itemStack.is(TagManager.Items.WATERPROOF_ARMOR)) {
                toolTip.add((Component.translatable("tooltip.waterproof")).withStyle(ChatFormatting.DARK_AQUA));
            }

            if ((tags.contains("radiation_protection")) || itemStack.is(TagManager.Items.RADIATION_PROTECTED_ARMOR)) {
                toolTip.add((Component.translatable("tooltip.radiation_protection")).withStyle(ChatFormatting.GREEN));
            }

            if (tags.contains("thermometer")) {
                toolTip.add((Component.translatable("tooltip.thermometer")).withStyle(ChatFormatting.GOLD));
            }
        }
    }

}
