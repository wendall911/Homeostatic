package homeostatic.network;

import org.jetbrains.annotations.NotNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import homeostatic.common.wetness.WetnessInfo;

public interface IWetness {

    void setWetnessLevel(int wetnessLevel);

    void setMoistureLevel(float moistureLevel);

    void setWetnessData(WetnessInfo wetnessInfo);

    int getWetnessLevel();

    float getMoistureLevel();

    CompoundTag write(CompoundTag tag);

    ValueOutput write(@NotNull ValueOutput tag);

    void read(CompoundTag tag);

    void read(@NotNull ValueInput tag);

}
