package homeostatic.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.temperature.ThermometerInfo;
import homeostatic.platform.Services;

public class ThermometerData {

    public boolean hasThermometer;

    public ThermometerData(ThermometerInfo info) {
        this.hasThermometer = info.hasThermometer();
    }

    public ThermometerData(FriendlyByteBuf buf) {
        this.hasThermometer = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(hasThermometer);
    }

    public void process(Player player, ThermometerData thermometerData) {
        Services.PLATFORM.getThermometerCapability(player).ifPresent(data -> {
            data.setHasThermometer(thermometerData.hasThermometer);
        });
    }

}