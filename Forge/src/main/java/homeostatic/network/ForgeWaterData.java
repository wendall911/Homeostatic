package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import homeostatic.common.water.WaterInfo;

public class ForgeWaterData implements IData {

    private final WaterData waterData;

    public ForgeWaterData(WaterInfo waterInfo) {
        waterData = new WaterData(waterInfo);
    }

    public ForgeWaterData(FriendlyByteBuf buf) {
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
