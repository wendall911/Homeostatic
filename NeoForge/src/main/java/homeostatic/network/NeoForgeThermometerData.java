package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import homeostatic.common.temperature.ThermometerInfo;

public class NeoForgeThermometerData extends Thermometer implements CustomPacketPayload {

    public static final Type<NeoForgeThermometerData> TYPE = new Type<>(ThermometerData.ID);
    public static final StreamCodec<FriendlyByteBuf, NeoForgeThermometerData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG,
        NeoForgeThermometerData::getData,
        NeoForgeThermometerData::new
    );
    private final ThermometerData thermometerData;
    private final CompoundTag data;

    public NeoForgeThermometerData(ThermometerInfo info) {
        thermometerData = new ThermometerData(info);

        this.setHasThermometer(thermometerData.hasThermometer);

        data = this.write(new CompoundTag());
    }

    public NeoForgeThermometerData(CompoundTag tag) {
        this.read(tag);
        data = this.write(new CompoundTag());
        thermometerData = new ThermometerData(new ThermometerInfo(this.hasThermometer()));
    }

    public ThermometerData getThermometerData() {
        return thermometerData;
    }

    public CompoundTag getData() {
        return data;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
