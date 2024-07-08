package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import homeostatic.common.wetness.WetnessInfo;

public class NeoForgeWetnessData extends Wetness implements CustomPacketPayload {

    public static final Type<NeoForgeWetnessData> TYPE = new Type<>(WetnessData.ID);
    public static final StreamCodec<FriendlyByteBuf, NeoForgeWetnessData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG,
        NeoForgeWetnessData::getData,
        NeoForgeWetnessData::new
    );
    private final CompoundTag data;
    private final WetnessData wetnessData;

    public NeoForgeWetnessData(WetnessInfo wetnessInfo) {
        wetnessData = new WetnessData(wetnessInfo);

        this.setWetnessData(wetnessInfo);

        data = this.write(new CompoundTag());
    }

    public NeoForgeWetnessData(CompoundTag tag) {
        this.read(tag);
        data = this.write(new CompoundTag());
        wetnessData = new WetnessData(new WetnessInfo(getWetnessLevel(), getMoistureLevel()));
    }

    public CompoundTag getData() {
        return data;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
