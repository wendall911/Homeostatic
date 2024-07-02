package homeostatic.network;

import homeostatic.util.WaterHelper;
import net.minecraft.client.Minecraft;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NeoForgeNetworkManager {

    public static final NeoForgeNetworkManager INSTANCE = new NeoForgeNetworkManager();

    public static NeoForgeNetworkManager getInstance() {
        return INSTANCE;
    }

    public void processTemperatureData(NeoForgeTemperatureData msgData, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            TemperatureData.process(Minecraft.getInstance().player, msgData.getData());
        });
    }
/*
    public void processThermometerData(NeoForgeThermometerData msgData, PlayPayloadContext ctx) {
        ctx.workHandler().submitAsync(() -> ThermometerData.process(Minecraft.getInstance().player, msgData.getThermometerData()));
    }

    public void processWaterData(NeoForgeWaterData msgData, PlayPayloadContext ctx) {
        ctx.workHandler().submitAsync(() -> WaterData.process(Minecraft.getInstance().player, msgData.getWaterData()));
    }

    public void processWetnessData(NeoForgeWetnessData msgData, PlayPayloadContext ctx) {
        ctx.workHandler().submitAsync(() -> WetnessData.process(Minecraft.getInstance().player, msgData.getWetnessData()));
    }
*/
    public void processDrinkWater(DrinkWater msgData, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            WaterHelper.drinkWater((ServerPlayer) ctx.player());
        });
    }

}
