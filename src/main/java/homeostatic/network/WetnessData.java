package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import homeostatic.common.capabilities.CapabilityRegistry;
import homeostatic.common.wetness.WetnessInfo;

public class WetnessData implements IData {

    int wetnessLevel;
    float moistureLevel;

    public WetnessData(WetnessInfo wetnessInfo) {
        this.wetnessLevel = wetnessInfo.getWetnessLevel();
        this.moistureLevel = wetnessInfo.getMoistureLevel();
    }

    public WetnessData(FriendlyByteBuf buf) {
        wetnessLevel = buf.readInt();
        moistureLevel = buf.readFloat();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(wetnessLevel);
        buf.writeFloat(moistureLevel);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> Minecraft.getInstance().player.getCapability(CapabilityRegistry.WETNESS_CAPABILITY).ifPresent(data -> {
                data.setWetnessLevel(wetnessLevel);
                data.setMoistureLevel(moistureLevel);
            }));
        }
    }

}
