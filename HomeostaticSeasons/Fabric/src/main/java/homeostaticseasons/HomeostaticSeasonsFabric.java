package homeostaticseasons;

import net.fabricmc.api.ModInitializer;

import homeostaticseasons.event.ServerEventListener;

public class HomeostaticSeasonsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerEventListener.init();
    }

}
