package homeostaticseasons.platform;

import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;

import homeostaticseasons.mixin.ServerLevelAccessor;
import homeostaticseasons.platform.services.IPlatform;

public class FabricPlatform implements IPlatform {

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public ServerLevelData getServerLevelData(ServerLevel level) {
        ServerLevelAccessor serverLevel = (ServerLevelAccessor) level;

        return serverLevel.getServerLevelData();
    }

}
