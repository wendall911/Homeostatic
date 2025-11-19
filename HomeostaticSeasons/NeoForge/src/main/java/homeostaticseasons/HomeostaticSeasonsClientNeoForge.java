package homeostaticseasons;

import net.neoforged.bus.api.IEventBus;

import homeostaticseasons.event.ClientEventListener;

public class HomeostaticSeasonsClientNeoForge {

    public static void init(IEventBus bus) {
        HomeostaticSeasonsClient.init();

        bus.register(ClientEventListener.class);
    }

}
