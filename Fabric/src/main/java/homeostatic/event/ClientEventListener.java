package homeostatic.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

import homeostatic.overlay.HydrationOverlay;
import homeostatic.overlay.WaterHud;

public class ClientEventListener {

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(HydrationOverlay::onClientTick);
        ClientTickEvents.START_CLIENT_TICK.register(WaterHud::onClientTick);
        ItemTooltipCallback.EVENT.register((itemStack, context, toolTip, lines) -> {
            TooltipEventHandler.onItemToolTip(itemStack, lines);
        });
    }

}
