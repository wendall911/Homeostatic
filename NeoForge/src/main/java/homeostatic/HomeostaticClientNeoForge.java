package homeostatic;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

import homeostatic.data.integration.ModIntegration;
import homeostatic.event.ToolTipEventListener;
import homeostatic.event.ClientEventListener;
//import homeostatic.integrations.patchouli.PageCustomCrafting;
import homeostatic.platform.Services;

public class HomeostaticClientNeoForge {
    public static void init(IEventBus bus) {
        NeoForge.EVENT_BUS.register(ClientEventListener.class);
        NeoForge.EVENT_BUS.register(ToolTipEventListener.class);

        if (Services.PLATFORM.isModLoaded(ModIntegration.PATCHOULI_MODID)) {
            //PageCustomCrafting.init();
        }
    }

}
