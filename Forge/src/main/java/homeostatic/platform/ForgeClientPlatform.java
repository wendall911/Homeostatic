package homeostatic.platform;

import homeostatic.network.ForgeDrinkWater;
import homeostatic.network.NetworkHandler;
import homeostatic.platform.services.IClientPlatform;

public class ForgeClientPlatform implements IClientPlatform {

    @Override
    public void sendDrinkWaterPacket() {
        NetworkHandler.INSTANCE.sendToServer(new ForgeDrinkWater());
    }

}
