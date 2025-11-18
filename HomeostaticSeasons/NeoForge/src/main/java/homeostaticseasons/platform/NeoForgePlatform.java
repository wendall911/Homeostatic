package homeostaticseasons.platform;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;

import net.neoforged.fml.loading.FMLLoader;

import homeostaticseasons.mixin.ServerLevelAccessor;
import homeostaticseasons.platform.services.IPlatform;

public class NeoForgePlatform implements IPlatform {

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public ServerLevelData getServerLevelData(ServerLevel level) {
        ServerLevelAccessor serverLevel = (ServerLevelAccessor) level;

        return serverLevel.getServerLevelData();
    }

}
