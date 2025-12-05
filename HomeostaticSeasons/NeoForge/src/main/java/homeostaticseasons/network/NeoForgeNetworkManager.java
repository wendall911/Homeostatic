package homeostaticseasons.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;

public class NeoForgeNetworkManager {

    public static final NeoForgeNetworkManager INSTANCE = new NeoForgeNetworkManager();

    public static NeoForgeNetworkManager getInstance() {
        return INSTANCE;
    }

    public void processBiomeColormapPacket(SyncBiomeColormap biomeColormap, IPayloadContext ctx) {
        ctx.enqueueWork(() -> biomeColormap.handle(ctx.player()));
    }

}
