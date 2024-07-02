package homeostatic.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import homeostatic.common.water.WaterInfo;
/*
public class NeoForgeWaterData implements CustomPacketPayload {

    private final WaterData waterData;

    public NeoForgeWaterData(WaterInfo waterInfo) {
        waterData = new WaterData(waterInfo);
    }

    public NeoForgeWaterData(FriendlyByteBuf buf) {
        waterData = new WaterData(buf);
    }

    public WaterData getWaterData() {
        return waterData;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        waterData.toBytes(buf);
    }

    @Override
    public ResourceLocation id() {
        return WaterData.ID;
    }

}


 */