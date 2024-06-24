package homeostatic.util;

import net.minecraft.world.level.Level;

import static io.github.lucaargolo.seasons.FabricSeasons.CONFIG;

public class FabricSeasonsHelper {

    public static int getSeasonDuration() {
        return CONFIG.getSpringLength();
    }

    public static boolean isSeasonDimension(Level level) {
        return CONFIG.isValidInDimension(level.dimension());
    }

}
