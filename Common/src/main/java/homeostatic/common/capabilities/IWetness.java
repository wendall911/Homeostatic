package homeostatic.common.capabilities;

import net.minecraft.nbt.CompoundTag;

import homeostatic.common.wetness.WetnessInfo;

public interface IWetness {

    void setWetnessLevel(int wetnessLevel);

    void setMoistureLevel(float moistureLevel);

    void setWetnessData(WetnessInfo wetnessInfo);

    int getWetnessLevel();

    float getMoistureLevel();

    CompoundTag write();

    void read(CompoundTag tag);

}
