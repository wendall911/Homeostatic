package homeostatic.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.wetness.WetnessInfo;
import homeostatic.Homeostatic;
import homeostatic.platform.Services;

public class WetnessData {

    private final int wetnessLevel;
    private final float moistureLevel;
    private final WetnessInfo wetnessInfo;
    public static final ResourceLocation ID = new ResourceLocation(Homeostatic.MODID, "wetness_data");

    public WetnessData(WetnessInfo wetnessInfo) {
        this.wetnessLevel = wetnessInfo.getWetnessLevel();
        this.moistureLevel = wetnessInfo.getMoistureLevel();
        this.wetnessInfo = wetnessInfo;
    }

    public WetnessData(FriendlyByteBuf buf) {
        wetnessLevel = buf.readInt();
        moistureLevel = buf.readFloat();
        wetnessInfo = new WetnessInfo(wetnessLevel, moistureLevel);
    }

    public WetnessInfo getWetnessInfo() {
        return wetnessInfo;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(wetnessLevel);
        buf.writeFloat(moistureLevel);
    }

    public static void process(Player player, WetnessData wetnessData) {
        Services.PLATFORM.getWetnessCapability(player).ifPresent(data -> {
            data.setWetnessData(wetnessData.getWetnessInfo());
        });
    }

}
