package homeostatic.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import homeostatic.common.temperature.ThermometerInfo;
import homeostatic.Homeostatic;
/*
public class NeoForgeThermometerData implements CustomPacketPayload {

    private final ThermometerData thermometerData;

    public NeoForgeThermometerData(ThermometerInfo info) {
        thermometerData = new ThermometerData(info);
    }

    public NeoForgeThermometerData(FriendlyByteBuf buf) {
        thermometerData = new ThermometerData(buf);
    }

    public ThermometerData getThermometerData() {
        return thermometerData;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        thermometerData.toBytes(buf);
    }

    @Override
    public ResourceLocation id() {
        return ThermometerData.ID;
    }

}


 */