package homeostatic.network;

import org.jetbrains.annotations.NotNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import homeostatic.common.temperature.BodyTemperature;

public class NeoForgeTemperatureData extends Temperature implements CustomPacketPayload {

    public static final Type<NeoForgeTemperatureData> TYPE = new Type<>(TemperatureData.ID);
    public static final StreamCodec<FriendlyByteBuf, NeoForgeTemperatureData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG,
        NeoForgeTemperatureData::getData,
        NeoForgeTemperatureData::new
    );
    private final TemperatureData temperatureData;
    private final CompoundTag data;

    public NeoForgeTemperatureData(float localTemperature, BodyTemperature bodyTemperature) {
        temperatureData = new TemperatureData(localTemperature, bodyTemperature.getSkinTemperature(), bodyTemperature.getCoreTemperature());

        this.setLocalTemperature(temperatureData.localTemperature);
        this.setSkinTemperature(temperatureData.skinTemperature);
        this.setCoreTemperature(temperatureData.coreTemperature);

        data = this.write(new CompoundTag());
    }

    public NeoForgeTemperatureData(CompoundTag tag) {
        data = this.write(tag);
        temperatureData = new TemperatureData(getLocalTemperature(), getSkinTemperature(), getCoreTemperature());
    }

    public TemperatureData getTemperatureData() {
        return temperatureData;
    }

    public CompoundTag getData() {
        return data;
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
