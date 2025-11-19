package homeostaticseasons.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

import homeostaticseasons.common.biome.BiomeColormapManager;

public class ClientEventRegistration {

    @SubscribeEvent
    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new BiomeColormapManager());
    }

}
