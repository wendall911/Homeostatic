package homeostaticseasons;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import technology.roughness.whitenoise.platform.Services;

import homeostaticseasons.command.SeasonCommand;
import homeostaticseasons.event.ServerEventListener;
import homeostaticseasons.network.NeoForgeNetworkManager;
import homeostaticseasons.network.SyncBiomeColormap;

@Mod(HomeostaticSeasons.MODID)
public class HomeostaticSeasonsNeoForge {

    public HomeostaticSeasonsNeoForge(IEventBus bus) {
        HomeostaticSeasons.initConfig();
        bus.addListener(this::setup);
        bus.addListener(this::registerPayloadHandler);

        if (Services.PLATFORM.isPhysicalClient()) {
            HomeostaticSeasonsClientNeoForge.init(bus);
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(ServerEventListener.class);
        NeoForge.EVENT_BUS.addListener((RegisterCommandsEvent e) -> SeasonCommand.register(e.getDispatcher()));
    }

    private void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(HomeostaticSeasons.MODID).versioned("1.0");

        registrar.playToClient(SyncBiomeColormap.TYPE, SyncBiomeColormap.CODEC, NeoForgeNetworkManager.getInstance()::processBiomeColormapPacket);
    }

}
