package climatesettings.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NeoForgeNetworkManager {

    public static final NeoForgeNetworkManager INSTANCE = new NeoForgeNetworkManager();

    public static NeoForgeNetworkManager getInstance() {
        return INSTANCE;
    }

    public void processBiomeTypeDataPacket(SyncBiomeTypeData syncBiomeTypeData, IPayloadContext ctx) {
        ctx.enqueueWork(() -> syncBiomeTypeData.handle(ctx.player()));
    }

}
