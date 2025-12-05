package homeostaticseasons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

import homeostaticseasons.common.biome.FabricBiomeColormapManager;
import homeostaticseasons.event.ServerEventListener;

public class HomeostaticSeasonsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ServerEventListener.init();
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new FabricBiomeColormapManager());
    }

}
