package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import homeostatic.common.water.WaterInfo;

public class NeoForgeWaterData implements IData {

    private final WaterData waterData;

    public NeoForgeWaterData(WaterInfo waterInfo) {
        waterData = new WaterData(waterInfo);
    }

    public NeoForgeWaterData(FriendlyByteBuf buf) {
        waterData = new WaterData(buf);
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        waterData.toBytes(buf);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> waterData.process(Minecraft.getInstance().player, waterData));
        }
    }

}
