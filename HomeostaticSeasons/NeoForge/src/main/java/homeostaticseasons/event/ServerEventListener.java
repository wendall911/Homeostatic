package homeostaticseasons.event;

import net.minecraft.server.level.ServerLevel;

import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

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

}
