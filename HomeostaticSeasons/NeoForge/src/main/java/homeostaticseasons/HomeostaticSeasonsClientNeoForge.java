package homeostaticseasons;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

import homeostaticseasons.event.ClientEventListener;
import homeostaticseasons.event.ClientEventRegistration;

public class HomeostaticSeasonsClientNeoForge {

    public static void init(IEventBus bus) {
        HomeostaticSeasonsClient.init();

        bus.register(ClientEventRegistration.class);
        NeoForge.EVENT_BUS.register(ClientEventListener.class);
    }

}
