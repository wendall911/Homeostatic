package homeostatic.common.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.temperature.BodyTemperature;

public interface ITemperature {

    void setSkinTemperature(float skinTemperature);

    void setLastSkinTemperature(float lastSkinTemperature);

    void setCoreTemperature(float coreTemperature);

    void setLocalTemperature(float temperature);

    void setTemperatureData(float localTemperature, BodyTemperature bodyTemperature);

    float getSkinTemperature();

    float getLastSkinTemperature();

    float getCoreTemperature();

    float getLocalTemperature();

    void checkTemperatureLevel(Player player);

    CompoundTag write();

    void read(CompoundTag tag);

}
