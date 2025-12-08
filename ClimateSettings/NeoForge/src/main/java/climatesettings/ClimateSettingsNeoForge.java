package climatesettings;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import climatesettings.event.ServerEventListener;
import climatesettings.network.NeoForgeNetworkManager;
import climatesettings.network.SyncBiomeCategoryData;
import climatesettings.network.SyncBiomeTypeData;

@Mod(ClimateSettings.MODID)
public class ClimateSettingsNeoForge {

    public ClimateSettingsNeoForge(IEventBus bus) {
        bus.addListener(this::setup);
        bus.addListener(this::registerPayloadHandler);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(ServerEventListener.class);
    }

    private void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(ClimateSettings.MODID).versioned("1.0");

        registrar.playToClient(SyncBiomeTypeData.TYPE, SyncBiomeTypeData.CODEC, NeoForgeNetworkManager.getInstance()::processBiomeTypeDataPacket);
        registrar.playToClient(SyncBiomeCategoryData.TYPE, SyncBiomeCategoryData.CODEC, NeoForgeNetworkManager.getInstance()::processBiomeCategoryDataPacket);
    }

}
