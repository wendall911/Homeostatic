package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import homeostatic.common.temperature.ThermometerInfo;

public class ForgeThermometerData implements IData {

    private final ThermometerData thermometerData;

    public ForgeThermometerData(ThermometerInfo info) {
        thermometerData = new ThermometerData(info);
    }

    public ForgeThermometerData(FriendlyByteBuf buf) {
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
