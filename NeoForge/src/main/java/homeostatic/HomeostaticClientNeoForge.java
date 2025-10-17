package homeostatic;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

import homeostatic.common.book.PageCustomCrafting;
import homeostatic.event.ToolTipEventListener;
import homeostatic.event.ClientEventListener;

public class HomeostaticClientNeoForge {

    public static void init(IEventBus bus) {
        NeoForge.EVENT_BUS.register(ClientEventListener.class);
        NeoForge.EVENT_BUS.register(ToolTipEventListener.class);
        PageCustomCrafting.init();
    }

}
