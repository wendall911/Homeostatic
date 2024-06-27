package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import homeostatic.common.wetness.WetnessInfo;

public class NeoForgeWetnessData implements IData {

    private final WetnessData wetnessData;

    public NeoForgeWetnessData(WetnessInfo wetnessInfo) {
        wetnessData = new WetnessData(wetnessInfo);
    }

    public NeoForgeWetnessData(FriendlyByteBuf buf) {
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
