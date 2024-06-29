package homeostatic.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import homeostatic.common.temperature.BodyTemperature;

public class NeoForgeTemperatureData implements CustomPacketPayload {

    private final TemperatureData temperatureData;

    public NeoForgeTemperatureData(float localTemperature, BodyTemperature bodyTemperature) {
        temperatureData = new TemperatureData(localTemperature, bodyTemperature.getSkinTemperature(), bodyTemperature.getCoreTemperature());
    }

    public NeoForgeTemperatureData(FriendlyByteBuf buf) {
        temperatureData = new TemperatureData(buf);
    }

    public TemperatureData getTemperatureData() {
        return temperatureData;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        temperatureData.write(buf);
    }

    @Override
    public ResourceLocation id() {
        return TemperatureData.ID;
    }

}
