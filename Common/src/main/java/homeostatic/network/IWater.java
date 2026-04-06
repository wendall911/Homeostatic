package homeostatic.network;

import org.jspecify.annotations.NonNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import homeostatic.common.water.WaterInfo;

public interface IWater {

    void setWaterLevel(int waterLevel);

    void increaseWaterLevel(int level);

    void increaseSaturationLevel(float level);

    void setWaterSaturationLevel(float waterSaturationLevel);

    void setWaterExhaustionLevel(float waterExhaustionLevel);

    void setWaterData(WaterInfo waterInfo);

    int getWaterLevel();

    float getWaterExhaustionLevel();

    float getWaterSaturationLevel();

    void checkWaterLevel(ServerPlayer player);

    CompoundTag write(CompoundTag tag);

    ValueOutput write(@NonNull ValueOutput tag);

    void read(CompoundTag tag);

    void read(ValueInput tag);

}
