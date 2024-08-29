package homeostatic.event;

import net.minecraft.client.Minecraft;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import homeostatic.overlay.HydrationOverlay;
import homeostatic.overlay.WaterHud;

public class ClientEventListener {

    @SubscribeEvent
    public static void onClientTickEvent(ClientTickEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();

        HydrationOverlay.onClientTick(minecraft);
        WaterHud.onClientTick(minecraft);
    }

}
