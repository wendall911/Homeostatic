package homeostatic.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class ToolTipEventListener {

    @SubscribeEvent
    public static void onItemToolTip(ItemTooltipEvent event) {
        TooltipEventHandler.onItemToolTip(event.getItemStack(), event.getToolTip());
    }

}
