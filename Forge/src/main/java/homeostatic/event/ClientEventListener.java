package homeostatic.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import homeostatic.overlay.HydrationOverlay;
import homeostatic.overlay.WaterHud;

public class ClientEventListener {

    @SubscribeEvent
    public static void onRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
        final Player player = event.getEntity();

        if (player != null && player.level().isClientSide) {
            drinkWater(player, event);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        final Player player = event.getEntity();

        if (player != null && player.level().isClientSide) {
            drinkWater(player, event);
        }
    }

    @SubscribeEvent
    public static void onClientTickEvent(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        Minecraft minecraft = Minecraft.getInstance();

        HydrationOverlay.onClientTick(minecraft);
        WaterHud.onClientTick(minecraft);
    }

    private static void drinkWater(Player player, PlayerInteractEvent event) {
        ClientPlayerEventHandler.drinkWater(player, event.getLevel(), event.getHand());
    }

}
