package homeostaticseasons;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import technology.roughness.whitenoise.platform.Services;

import homeostaticseasons.command.SeasonCommand;
import homeostaticseasons.event.ServerEventListener;

@Mod(HomeostaticSeasons.MODID)
public class HomeostaticSeasonsNeoForge {

    public HomeostaticSeasonsNeoForge(IEventBus bus) {
        HomeostaticSeasons.initConfig();
        bus.addListener(this::setup);

        if (Services.PLATFORM.isPhysicalClient()) {
            HomeostaticSeasonsClientNeoForge.init(bus);
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(ServerEventListener.class);
        NeoForge.EVENT_BUS.addListener((RegisterCommandsEvent e) -> SeasonCommand.register(e.getDispatcher()));
    }

}
