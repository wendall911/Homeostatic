package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.water.WaterData;

public class StatsData implements IData {

    float localTemperature;
    float skinTemperature;
    float coreTemperature;
    int waterLevel;
    float waterSaturationLevel;
    float waterExhaustionLevel;

    public StatsData(float localTemperature, BodyTemperature bodyTemperature) {
        WaterData waterData = bodyTemperature.getWaterData();

        this.localTemperature = localTemperature;
        this.skinTemperature = bodyTemperature.getSkinTemperature();
        this.coreTemperature = bodyTemperature.getCoreTemperature();
        this.waterLevel = waterData.getWaterLevel();
        this.waterSaturationLevel = waterData.getWaterSaturationLevel();
        this.waterExhaustionLevel = waterData.getWaterExhaustionLevel();
    }

    public StatsData(FriendlyByteBuf buf) {
        localTemperature = buf.readFloat();
        skinTemperature = buf.readFloat();
        coreTemperature = buf.readFloat();
        waterLevel = buf.readInt();
        waterSaturationLevel = buf.readFloat();
        waterExhaustionLevel = buf.readFloat();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(localTemperature);
        buf.writeFloat(skinTemperature);
        buf.writeFloat(coreTemperature);
        buf.writeInt(waterLevel);
        buf.writeFloat(waterSaturationLevel);
        buf.writeFloat(waterExhaustionLevel);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> Minecraft.getInstance().player.getCapability(CapabilityRegistry.STATS_CAPABILITY).ifPresent(data -> {
                data.setLocalTemperature(localTemperature);
                data.setSkinTemperature(skinTemperature);
                data.setCoreTemperature(coreTemperature);
                data.setWaterLevel(waterLevel);
                data.setWaterSaturationLevel(waterSaturationLevel);
                data.setWaterExhaustionLevel(waterExhaustionLevel);
            }));
        }
    }

}
