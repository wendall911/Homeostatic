package homeostatic;

import net.minecraftforge.common.MinecraftForge;

import homeostatic.data.integration.ModIntegration;
import homeostatic.event.ToolTipEventListener;
import homeostatic.event.ClientEventListener;
import homeostatic.integrations.patchouli.PageCustomCrafting;
import homeostatic.platform.Services;

public class HomeostaticClientForge {

    public HomeostaticClientForge() {
        MinecraftForge.EVENT_BUS.register(ClientEventListener.class);
        MinecraftForge.EVENT_BUS.register(ToolTipEventListener.class);

        if (Services.PLATFORM.isModLoaded(ModIntegration.PATCHOULI_MODID)) {
            PageCustomCrafting.init();
        }
    }

}
