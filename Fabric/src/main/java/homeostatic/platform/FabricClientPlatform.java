package homeostatic.platform;

import homeostatic.common.components.HomeostaticComponents;
import homeostatic.platform.services.IClientPlatform;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;

public class FabricClientPlatform implements IClientPlatform {

    @Override
    public void sendDrinkWaterPacket() {
        ClientPlayNetworking.send(HomeostaticComponents.DRINK_WATER_KEY, PacketByteBufs.create());
    }

}
