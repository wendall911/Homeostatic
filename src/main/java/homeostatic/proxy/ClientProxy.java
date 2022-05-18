package homeostatic.proxy;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public final class ClientProxy extends CommonProxy {

    public ClientProxy() {}

    @Override
    public void start() {
        super.start();
    }

    public void clientSetup(final FMLClientSetupEvent event) {
    }

}
