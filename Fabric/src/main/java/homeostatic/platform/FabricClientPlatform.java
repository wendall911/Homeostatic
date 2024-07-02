package homeostatic.platform;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import net.minecraft.world.entity.player.Player;

import homeostatic.network.DrinkWater;
import homeostatic.platform.services.IClientPlatform;

public class FabricClientPlatform implements IClientPlatform {

    @Override
    public void sendDrinkWaterPacket(Player player) {
        ClientPlayNetworking.send(new DrinkWater(player.getId()));
    }

}
