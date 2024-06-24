package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import homeostatic.common.wetness.WetnessInfo;

public class ForgeWetnessData implements IData {

    private final WetnessData wetnessData;

    public ForgeWetnessData(WetnessInfo wetnessInfo) {
        wetnessData = new WetnessData(wetnessInfo);
    }

    public ForgeWetnessData(FriendlyByteBuf buf) {
        wetnessData = new WetnessData(buf);
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        wetnessData.toBytes(buf);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> wetnessData.process(Minecraft.getInstance().player, wetnessData));
        }
    }

}
