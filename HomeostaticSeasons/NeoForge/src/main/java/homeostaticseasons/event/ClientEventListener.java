package homeostaticseasons.event;

import net.minecraft.client.Minecraft;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class ClientEventListener {

    @SubscribeEvent
    public static void onClientTickEvent(ClientTickEvent.Post event) {
        ClientEventHandler.onClientTick(Minecraft.getInstance());
    }

}
