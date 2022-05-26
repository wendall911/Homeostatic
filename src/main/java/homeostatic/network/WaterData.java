package homeostatic.network;

import java.util.function.Supplier;

import homeostatic.common.water.WaterInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import homeostatic.common.capabilities.CapabilityRegistry;

public class WaterData implements IData {

    int waterLevel;
    float waterSaturationLevel;
    float waterExhaustionLevel;

    public WaterData(WaterInfo waterInfo) {
        this.waterLevel = waterInfo.getWaterLevel();
        this.waterSaturationLevel = waterInfo.getWaterSaturationLevel();
        this.waterExhaustionLevel = waterInfo.getWaterExhaustionLevel();
    }

    public WaterData(FriendlyByteBuf buf) {
        waterLevel = buf.readInt();
        waterSaturationLevel = buf.readFloat();
        waterExhaustionLevel = buf.readFloat();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(waterLevel);
        buf.writeFloat(waterSaturationLevel);
        buf.writeFloat(waterExhaustionLevel);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> Minecraft.getInstance().player.getCapability(CapabilityRegistry.WATER_CAPABILITY).ifPresent(data -> {
                data.setWaterLevel(waterLevel);
                data.setWaterSaturationLevel(waterSaturationLevel);
                data.setWaterExhaustionLevel(waterExhaustionLevel);
            }));
        }
    }

}
