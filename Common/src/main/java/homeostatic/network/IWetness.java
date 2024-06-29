package homeostatic.network;

import net.minecraft.nbt.ListTag;

import homeostatic.common.wetness.WetnessInfo;

public interface IWetness {

    void setWetnessLevel(int wetnessLevel);

    void setMoistureLevel(float moistureLevel);

    void setWetnessData(WetnessInfo wetnessInfo);

    int getWetnessLevel();

    float getMoistureLevel();

    ListTag write();

    void read(ListTag tag);

}
