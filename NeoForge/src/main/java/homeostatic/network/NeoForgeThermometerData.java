package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import homeostatic.common.temperature.ThermometerInfo;

public class NeoForgeThermometerData implements IData {

    private final ThermometerData thermometerData;

    public NeoForgeThermometerData(ThermometerInfo info) {
        thermometerData = new ThermometerData(info);
    }

    public NeoForgeThermometerData(FriendlyByteBuf buf) {
        thermometerData = new ThermometerData(buf);
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        thermometerData.toBytes(buf);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> thermometerData.process(Minecraft.getInstance().player, thermometerData));
        }
    }

}
