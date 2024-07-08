package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import homeostatic.common.water.WaterInfo;

public class NeoForgeWaterData extends Water implements CustomPacketPayload {

    public static final Type<NeoForgeWaterData> TYPE = new Type<>(WaterData.ID);
    public static final StreamCodec<FriendlyByteBuf, NeoForgeWaterData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG,
        NeoForgeWaterData::getData,
        NeoForgeWaterData::new
    );

    private final WaterData waterData;
    private final CompoundTag data;

    public NeoForgeWaterData(WaterInfo waterInfo) {
        waterData = new WaterData(waterInfo);

        this.setWaterData(waterInfo);

        data = this.write(new CompoundTag());
    }

    public NeoForgeWaterData(CompoundTag tag) {
        this.read(tag);
        data = this.write(new CompoundTag());
        waterData = new WaterData(new WaterInfo(getWaterLevel(), getWaterSaturationLevel(), getWaterExhaustionLevel()));
    }

    public WaterData getWaterData() {
        return waterData;
    }

    CompoundTag getData() {
        return data;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
