package homeostaticseasons;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

import technology.roughness.whitenoise.config.WhiteNoiseConfigInitializer;

import homeostaticseasons.config.ConfigHandler;

public class FabricConfigInitializer implements WhiteNoiseConfigInitializer {

    @Override
    public void onInitializeConfig() {
        HomeostaticSeasons.initConfig();

        ServerLifecycleEvents.SERVER_STARTING.register(ConfigHandler::initCommon);
    }

}
