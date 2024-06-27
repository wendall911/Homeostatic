package homeostatic.platform;

import homeostatic.network.NeoForgeDrinkWater;
import homeostatic.network.NetworkHandler;
import homeostatic.platform.services.IClientPlatform;

public class NeoForgeClientPlatform implements IClientPlatform {

    @Override
    public void sendDrinkWaterPacket() {
        NetworkHandler.INSTANCE.sendToServer(new NeoForgeDrinkWater());
    }

}
