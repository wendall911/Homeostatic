package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.temperature.BodyTemperature;

public class TemperatureData implements IData {

    float localTemperature;
    float skinTemperature;
    float coreTemperature;

    public TemperatureData(float localTemperature, BodyTemperature bodyTemperature) {
        this.localTemperature = localTemperature;
        this.skinTemperature = bodyTemperature.getSkinTemperature();
        this.coreTemperature = bodyTemperature.getCoreTemperature();
    }

    public TemperatureData(FriendlyByteBuf buf) {
        localTemperature = buf.readFloat();
        skinTemperature = buf.readFloat();
        coreTemperature = buf.readFloat();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(localTemperature);
        buf.writeFloat(skinTemperature);
        buf.writeFloat(coreTemperature);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> Minecraft.getInstance().player.getCapability(CapabilityRegistry.TEMPERATURE_CAPABILITY).ifPresent(data -> {
                data.setLocalTemperature(localTemperature);
                data.setSkinTemperature(skinTemperature);
                data.setCoreTemperature(coreTemperature);
            }));
        }
    }

}
