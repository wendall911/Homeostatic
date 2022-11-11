package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.temperature.ThermometerInfo;

public class ThermometerData implements IData {

    boolean hasThermometer;

    public ThermometerData(ThermometerInfo info) {
        this.hasThermometer = info.hasThermometer();
    }

    public ThermometerData(FriendlyByteBuf buf) {
        this.hasThermometer = buf.readBoolean();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(hasThermometer);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> Minecraft.getInstance().player.getCapability(CapabilityRegistry.THERMOMETER_CAPABILITY).ifPresent(data -> {
                data.setHasThermometer(hasThermometer);
            }));
        }
    }

}