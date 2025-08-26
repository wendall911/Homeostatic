package homeostatic.util;

import net.minecraft.world.level.Level;

//import static io.github.lucaargolo.seasons.FabricSeasons.CONFIG;
// TODO: reenable once Fabric Seasons is available for 1.21.8+

public class FabricSeasonsHelper {

    public static int getSeasonDuration() {
        //return CONFIG.getSpringLength();
        return 0; // Placeholder, replace with actual logic when CONFIG is available
    }

    public static boolean isSeasonDimension(Level level) {
        //return CONFIG.isValidInDimension(level.dimension());
        return false; // Placeholder, replace with actual logic when CONFIG is available
    }

}
