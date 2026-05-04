package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.temperature.EnvironmentData;

public interface ITemperature {

    void setSkinTemperature(float skinTemperature);

    void setLastSkinTemperature(float lastSkinTemperature);

    void setCoreTemperature(float coreTemperature);

    void setLocalTemperature(float temperature);

    void setRelativeHumidity(double relativeHumidity);

    void setTemperatureData(EnvironmentData environmentData, BodyTemperature bodyTemperature);

    float getSkinTemperature();

    float getLastSkinTemperature();

    float getCoreTemperature();

    float getLocalTemperature();

    double getRelativeHumidity();

    void checkTemperatureLevel(ServerPlayer player);

    CompoundTag write(CompoundTag tag);

    void read(CompoundTag tag);

    ValueOutput write(ValueOutput tag);

    void read(ValueInput tag);

}
