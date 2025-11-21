package homeostaticseasons.event;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import homeostaticseasons.command.SeasonCommand;

public class ServerEventListener {

    public static void init() {
        CommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess, environment) -> SeasonCommand.register(dispatcher)
        );

        ServerTickEvents.END_WORLD_TICK.register(ServerEventHandler::onLevelTick);
    }

}
