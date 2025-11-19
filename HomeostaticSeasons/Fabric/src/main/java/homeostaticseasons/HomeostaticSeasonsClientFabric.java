package homeostaticseasons;

import net.fabricmc.api.ClientModInitializer;

import homeostaticseasons.event.ClientEventListener;

public class HomeostaticSeasonsClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HomeostaticSeasonsClient.init();
        ClientEventListener.init();
    }

}
