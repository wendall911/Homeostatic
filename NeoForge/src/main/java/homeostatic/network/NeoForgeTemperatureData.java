package homeostatic.network;

import org.jspecify.annotations.NonNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.EnvironmentData;

public class NeoForgeTemperatureData extends Temperature implements CustomPacketPayload {

    public static final Type<NeoForgeTemperatureData> TYPE = new Type<>(TemperatureData.ID);
    public static final StreamCodec<FriendlyByteBuf, NeoForgeTemperatureData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.COMPOUND_TAG,
        NeoForgeTemperatureData::getData,
        NeoForgeTemperatureData::new
    );
    private final TemperatureData temperatureData;
    private final CompoundTag data;

    public NeoForgeTemperatureData(EnvironmentData environmentData, BodyTemperature bodyTemperature) {
        temperatureData = new TemperatureData(
            environmentData.getLocalTemperature(),
            bodyTemperature.getSkinTemperature(),
            bodyTemperature.getCoreTemperature(),
            environmentData.getRelativeHumidity()
        );

        this.setLocalTemperature(temperatureData.localTemperature);
        this.setSkinTemperature(temperatureData.skinTemperature);
        this.setCoreTemperature(temperatureData.coreTemperature);
        this.setRelativeHumidity(temperatureData.relativeHumidity);

        data = this.write(new CompoundTag());
    }

    public NeoForgeTemperatureData(CompoundTag tag) {
        this.read(tag);
        data = this.write(new CompoundTag());
        temperatureData = new TemperatureData(
            getLocalTemperature(),
            getSkinTemperature(),
            getCoreTemperature(),
            getRelativeHumidity()
        );
    }

    public TemperatureData getTemperatureData() {
        return temperatureData;
    }

    public CompoundTag getData() {
        return data;
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
