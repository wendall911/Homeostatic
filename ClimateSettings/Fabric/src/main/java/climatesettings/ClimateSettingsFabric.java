package climatesettings;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

import climatesettings.common.biome.FabricBiomeCategoryManager;
import climatesettings.common.biome.FabricBiomeTypeDataManager;
import climatesettings.network.SyncBiomeCategoryData;
import climatesettings.network.SyncBiomeTypeData;

public class ClimateSettingsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new FabricBiomeCategoryManager());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new FabricBiomeTypeDataManager());
        PayloadTypeRegistry.clientboundPlay().register(SyncBiomeTypeData.TYPE, SyncBiomeTypeData.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncBiomeCategoryData.TYPE, SyncBiomeCategoryData.CODEC);
    }

}
