package climatesettings;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

import climatesettings.event.ServerEventListener;

@Mod(ClimateSettings.MODID)
public class ClimateSettingsNeoForge {

    public ClimateSettingsNeoForge(IEventBus bus) {
        bus.addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(ServerEventListener.class);
    }

}
