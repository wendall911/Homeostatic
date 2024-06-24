package homeostatic.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;

public class ServerEventListener {

    public static void init() {
        ServerEntityEvents.EQUIPMENT_CHANGE.register(PlayerEventHandler::onEquipmentChange);
    }

}
