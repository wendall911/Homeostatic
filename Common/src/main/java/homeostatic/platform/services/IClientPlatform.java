package homeostatic.platform.services;

import net.minecraft.world.entity.player.Player;

public interface IClientPlatform {

    void sendDrinkWaterPacket(Player player);

}
