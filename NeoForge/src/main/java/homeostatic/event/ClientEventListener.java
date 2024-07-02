package homeostatic.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import homeostatic.overlay.HydrationOverlay;
import homeostatic.overlay.WaterHud;

public class ClientEventListener {

    @SubscribeEvent
    public static void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
        final Player player = event.getEntity();

        if (player.level().isClientSide) {
            drinkWater(player, event);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        final Player player = event.getEntity();

        if (player.level().isClientSide) {
            drinkWater(player, event);
        }
    }

    @SubscribeEvent
    public static void onClientTickEvent(ClientTickEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();

        HydrationOverlay.onClientTick(minecraft);
        WaterHud.onClientTick(minecraft);
    }

    private static void drinkWater(Player player, PlayerInteractEvent event) {
        ClientPlayerEventHandler.drinkWater(player, event.getLevel(), event.getHand());
    }

}
