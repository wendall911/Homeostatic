package homeostatic.proxy;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import homeostatic.data.integration.ModIntegration;
import homeostatic.integrations.patchouli.PageCustomCrafting;

@OnlyIn(Dist.CLIENT)
public final class ClientProxy extends CommonProxy {

    public ClientProxy() {}

    @Override
    public void start() {
        super.start();

        if (ModList.get().isLoaded(ModIntegration.PATCHOULI_MODID)) {
            PageCustomCrafting.init();
        }
    }

    public void clientSetup(final FMLClientSetupEvent event) {}

}