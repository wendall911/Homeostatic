package homeostaticseasons.event;

import net.minecraft.server.level.ServerLevel;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import homeostaticseasons.common.biome.BiomeColormapManager;

public class ServerEventListener {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onWorldTick(LevelTickEvent.Post event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        if (event.getLevel() instanceof ServerLevel level) {
            ServerEventHandler.onLevelTick(level);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onServerTick(ServerTickEvent.Post event) {
        SnowAndIceEventHandler.onEndServerTick();
    }

    @SubscribeEvent
    public static void onResourceReload(AddReloadListenerEvent event) {
        event.addListener(new BiomeColormapManager());
    }

}
