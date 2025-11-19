package homeostaticseasons.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;

import net.minecraft.server.packs.PackType;

import homeostaticseasons.common.biome.FabricBiomeColormapManager;

public class ClientEventListener {

    public static void init() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new FabricBiomeColormapManager());
        ClientTickEvents.END_CLIENT_TICK.register(ClientEventHandler::onClientTick);
    }

}
