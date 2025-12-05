package homeostaticseasons;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

import homeostaticseasons.event.ClientEventListener;

public class HomeostaticSeasonsClientNeoForge {

    public static void init(IEventBus bus) {
        HomeostaticSeasonsClient.init();

        NeoForge.EVENT_BUS.register(ClientEventListener.class);
    }

}
