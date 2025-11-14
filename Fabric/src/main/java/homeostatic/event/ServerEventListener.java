package homeostatic.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import homeostatic.common.fluid.DrinkingFluidManager;

public class ServerEventListener {

    public static void init() {
        ServerEntityEvents.EQUIPMENT_CHANGE.register(PlayerEventHandler::onEquipmentChange);
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
            DrinkingFluidManager.syncWithClient(player);
        });
    }

}
