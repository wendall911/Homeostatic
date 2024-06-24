package homeostatic.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.water.WaterInfo;
import homeostatic.platform.Services;

public class WaterData {

    private final int waterLevel;
    private final float waterSaturationLevel;
    private final float waterExhaustionLevel;
    private final WaterInfo waterInfo;

    public WaterData(WaterInfo waterInfo) {
        this.waterLevel = waterInfo.getWaterLevel();
        this.waterSaturationLevel = waterInfo.getWaterSaturationLevel();
        this.waterExhaustionLevel = waterInfo.getWaterExhaustionLevel();
        this.waterInfo = waterInfo;
    }

    public WaterData(FriendlyByteBuf buf) {
        waterLevel = buf.readInt();
        waterSaturationLevel = buf.readFloat();
        waterExhaustionLevel = buf.readFloat();
        waterInfo = new WaterInfo(waterLevel, waterSaturationLevel, waterExhaustionLevel);
    }

    public WaterInfo getWaterInfo() {
        return waterInfo;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(waterLevel);
        buf.writeFloat(waterSaturationLevel);
        buf.writeFloat(waterExhaustionLevel);
    }

    public void process(Player player, WaterData waterData) {
        Services.PLATFORM.getWaterCapabilty(player).ifPresent(data -> {
            data.setWaterData(waterInfo);
        });
    }

}
