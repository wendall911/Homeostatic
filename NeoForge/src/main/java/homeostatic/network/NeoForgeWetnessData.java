package homeostatic.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import homeostatic.common.wetness.WetnessInfo;

public class NeoForgeWetnessData implements CustomPacketPayload {

    private final WetnessData wetnessData;

    public NeoForgeWetnessData(WetnessInfo wetnessInfo) {
        wetnessData = new WetnessData(wetnessInfo);
    }

    public NeoForgeWetnessData(FriendlyByteBuf buf) {
        wetnessData = new WetnessData(buf);
    }

    public WetnessData getWetnessData() {
        return wetnessData;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        wetnessData.toBytes(buf);
    }

    @Override
    public ResourceLocation id() {
        return WetnessData.ID;
    }

}
