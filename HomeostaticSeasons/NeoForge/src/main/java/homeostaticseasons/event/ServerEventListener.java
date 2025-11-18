package homeostaticseasons.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import homeostaticseasons.config.ConfigHandler;

public class ServerEventListener {

    @SubscribeEvent
    private static void initConfig(final ServerStartingEvent event) {
        ConfigHandler.initCommon(event.getServer());
    }

}
