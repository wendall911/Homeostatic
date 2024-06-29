package homeostatic.network;

import net.minecraft.nbt.ListTag;
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

    ListTag write();

    void read(ListTag tag);

}
