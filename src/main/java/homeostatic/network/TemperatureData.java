package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import homeostatic.common.capabilities.CapabilityRegistry;

public class TemperatureData implements IData {

    float localTemperature;
    float bodyTemperature;

    public TemperatureData(float localTemperature, float bodyTemperature) {
        this.localTemperature = localTemperature;
        this.bodyTemperature = bodyTemperature;
    }

    public TemperatureData(FriendlyByteBuf buf) {
        localTemperature = buf.readFloat();
        bodyTemperature = buf.readFloat();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(localTemperature);
        buf.writeFloat(bodyTemperature);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> Minecraft.getInstance().player.getCapability(CapabilityRegistry.TEMPERATURE).ifPresent(data -> {
                data.setLocalTemperature(localTemperature);
                data.setBodyTemperature(bodyTemperature);
            }));
        }
    }

}
