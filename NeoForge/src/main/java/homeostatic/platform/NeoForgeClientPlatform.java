package homeostatic.platform;

import homeostatic.network.DrinkWater;
import homeostatic.platform.services.IClientPlatform;

import net.minecraft.world.entity.player.Player;

import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class NeoForgeClientPlatform implements IClientPlatform {

    @Override
    public void sendDrinkWaterPacket(Player player) {
        ClientPacketDistributor.sendToServer(new DrinkWater(player.getId()));
    }

}
