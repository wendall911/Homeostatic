package homeostaticseasons.platform.services;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.ServerLevelData;

public interface IPlatform {

    boolean isDevelopmentEnvironment();

    boolean isPhysicalClient();

    ServerLevelData getServerLevelData(ServerLevel level);

}
