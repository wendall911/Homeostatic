package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import homeostatic.common.wetness.WetnessInfo;

public interface IWetness {

    void setWetnessLevel(int wetnessLevel);

    void setMoistureLevel(float moistureLevel);

    void setWetnessData(WetnessInfo wetnessInfo);

    int getWetnessLevel();

    float getMoistureLevel();

    ListTag write();

    CompoundTag write(CompoundTag tag);

    void read(ListTag tag);

    void read(CompoundTag tag);

}
