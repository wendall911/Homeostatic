package homeostatic.util;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.water.WaterInfo;
import homeostatic.network.NetworkHandler;
import homeostatic.network.WaterData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class WaterHelper {

    public static void updateWaterInfo(ServerPlayer sp, float sweatLevel) {
        sp.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
            WaterInfo waterInfo = new WaterInfo(
                data.getWaterLevel(),
                data.getWaterSaturationLevel(),
                data.getWaterExhaustionLevel()
            );

            waterInfo.update(sweatLevel);
            data.setWaterData(waterInfo);

            NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> sp),
                new WaterData(waterInfo)
            );
        });
    }

}
