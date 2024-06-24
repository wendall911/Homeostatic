package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import homeostatic.common.temperature.BodyTemperature;

public class ForgeTemperatureData implements IData {

    private final TemperatureData temperatureData;

    public ForgeTemperatureData(float localTemperature, BodyTemperature bodyTemperature) {
        temperatureData = new TemperatureData(localTemperature, bodyTemperature.getSkinTemperature(), bodyTemperature.getCoreTemperature());
    }

    public ForgeTemperatureData(FriendlyByteBuf buf) {
        temperatureData = new TemperatureData(buf);
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        temperatureData.toBytes(buf);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> temperatureData.process(Minecraft.getInstance().player, temperatureData));
        }
    }

}
