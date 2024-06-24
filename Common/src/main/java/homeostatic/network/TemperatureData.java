package homeostatic.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import homeostatic.platform.Services;

public class TemperatureData {

    public float localTemperature;
    public float skinTemperature;
    public float coreTemperature;

    public TemperatureData(float localTemperature, float skinTemperature, float coreTemperature) {
        this.localTemperature = localTemperature;
        this.skinTemperature = skinTemperature;
        this.coreTemperature = coreTemperature;
    }

    public TemperatureData(FriendlyByteBuf buf) {
        localTemperature = buf.readFloat();
        skinTemperature = buf.readFloat();
        coreTemperature = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(localTemperature);
        buf.writeFloat(skinTemperature);
        buf.writeFloat(coreTemperature);
    }

    public void process(Player player, TemperatureData temperatureData) {
        Services.PLATFORM.getTemperatureData(player).ifPresent(data -> {
            data.setLocalTemperature(temperatureData.localTemperature);
            data.setSkinTemperature(temperatureData.skinTemperature);
            data.setCoreTemperature(temperatureData.coreTemperature);
        });
    }

}
