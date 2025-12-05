package homeostaticseasons.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ClientEventListener {

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(ClientEventHandler::onClientTick);
    }

}
