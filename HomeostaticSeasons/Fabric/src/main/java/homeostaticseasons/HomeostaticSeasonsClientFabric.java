package homeostaticseasons;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

import homeostaticseasons.common.biome.FabricBiomeColormapManager;

public class HomeostaticSeasonsClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new FabricBiomeColormapManager());
    }

}
