package homeostatic.network;

import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;

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

    ListTag write();

    void read(ListTag tag);

}
