package homeostatic.event;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import homeostatic.Homeostatic;
import homeostatic.common.block.BlockRegistry;

@Mod.EventBusSubscriber(modid = Homeostatic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigReloadEventHandler {

    @SubscribeEvent
    public static void onConfigReload(final ModConfigEvent.Reloading event) {
        BlockRegistry.init();
    }

}
