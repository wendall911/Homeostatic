package homeostatic.event;

import java.util.List;

import homeostatic.common.TagManager;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(modid = Homeostatic.MODID)
public class TooltipEventHandler {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onItemToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> toolTip = event.getToolTip();

        if (stack.getItem() instanceof ArmorItem) {
            CompoundTag tags = stack.getTag();

            if ((tags != null && tags.contains("insulation")) || stack.is(TagManager.Items.INSULATED_ARMOR)) {
                toolTip.add((Component.translatable("tooltip.insulation")).withStyle(ChatFormatting.GRAY));
            }

            if ((tags != null && tags.contains("waterproof")) || stack.is(TagManager.Items.WATERPROOF_ARMOR)) {
                toolTip.add((Component.translatable("tooltip.waterproof")).withStyle(ChatFormatting.DARK_AQUA));
            }

            if ((tags != null && tags.contains("radiation_protection")) || stack.is(TagManager.Items.RADIATION_PROTECTED_ARMOR)) {
                toolTip.add((Component.translatable("tooltip.radiation_protection")).withStyle(ChatFormatting.GREEN));
            }

            if (tags != null && tags.contains("thermometer")) {
                toolTip.add((Component.translatable("tooltip.thermometer")).withStyle(ChatFormatting.GOLD));
            }
        }
    }

}