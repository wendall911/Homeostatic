package homeostatic.network;

import net.minecraft.client.Minecraft;

import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class NeoForgeNetworkManager {

    public static final NeoForgeNetworkManager INSTANCE = new NeoForgeNetworkManager();

    public static NeoForgeNetworkManager getInstance() {
        return INSTANCE;
    }

    public void processTemperatureData(NeoForgeTemperatureData msgData, PlayPayloadContext ctx) {
        ctx.workHandler().submitAsync(() -> TemperatureData.process(Minecraft.getInstance().player, msgData.getTemperatureData()));
    }

    public void processThermometerData(NeoForgeThermometerData msgData, PlayPayloadContext ctx) {
        ctx.workHandler().submitAsync(() -> ThermometerData.process(Minecraft.getInstance().player, msgData.getThermometerData()));
    }

    public void processWaterData(NeoForgeWaterData msgData, PlayPayloadContext ctx) {
        ctx.workHandler().submitAsync(() -> WaterData.process(Minecraft.getInstance().player, msgData.getWaterData()));
    }

    public void processWetnessData(NeoForgeWetnessData msgData, PlayPayloadContext ctx) {
        ctx.workHandler().submitAsync(() -> WetnessData.process(Minecraft.getInstance().player, msgData.getWetnessData()));
    }

}
