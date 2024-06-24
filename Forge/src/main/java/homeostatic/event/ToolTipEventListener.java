package homeostatic.event;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ToolTipEventListener {

    @SubscribeEvent
    public static void onItemToolTip(ItemTooltipEvent event) {
        TooltipEventHandler.onItemToolTip(event.getItemStack(), event.getToolTip());
    }

}
