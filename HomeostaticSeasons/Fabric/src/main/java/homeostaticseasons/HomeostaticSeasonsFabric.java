package homeostaticseasons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import homeostaticseasons.command.SeasonCommand;

public class HomeostaticSeasonsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess, environment) -> SeasonCommand.register(dispatcher)
        );
    }

}
