package homeostatic.network;

import net.minecraft.server.level.ServerPlayer;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import homeostatic.util.LocalPlayerHelper;
import homeostatic.util.WaterHelper;

public class NeoForgeNetworkManager {

    public static final NeoForgeNetworkManager INSTANCE = new NeoForgeNetworkManager();

    public static NeoForgeNetworkManager getInstance() {
        return INSTANCE;
    }

    public void processTemperatureData(NeoForgeTemperatureData msgData, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            TemperatureData.process(LocalPlayerHelper.getLocalPlayer(), msgData.getData());
        });
    }

    public void processThermometerData(NeoForgeThermometerData msgData, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ThermometerData.process(LocalPlayerHelper.getLocalPlayer(), msgData.getData());
        });
    }

    public void processWaterData(NeoForgeWaterData msgData, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            WaterData.process(LocalPlayerHelper.getLocalPlayer(), msgData.getData());
        });
    }

    public void processWetnessData(NeoForgeWetnessData msgData, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            WetnessData.process(LocalPlayerHelper.getLocalPlayer(), msgData.getData());
        });
    }

    public void processDrinkWater(DrinkWater msgData, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            WaterHelper.drinkWater((ServerPlayer) ctx.player());
        });
    }

}
