package homeostaticseasons.event;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import climatesettings.common.biome.BiomeCategoryManager;
import climatesettings.common.biome.BiomeTypeDataManager;

import homeostaticseasons.command.SeasonCommand;
import homeostaticseasons.common.biome.BiomeColormapManager;

public class ServerEventListener {

    public static void init() {
        CommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess, environment) -> SeasonCommand.register(dispatcher)
        );

        ServerTickEvents.END_WORLD_TICK.register(ServerEventHandler::onLevelTick);
        ServerTickEvents.END_SERVER_TICK.register((minecraftServer) -> {
            SnowAndIceEventHandler.onEndServerTick();
        });

        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
            BiomeTypeDataManager.syncWithClient(player);
            BiomeCategoryManager.syncWithClient(player);
            BiomeColormapManager.syncWithClient(player);
        });
    }

}
