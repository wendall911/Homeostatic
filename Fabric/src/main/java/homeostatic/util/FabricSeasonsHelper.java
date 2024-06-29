package homeostatic.util;

import net.minecraft.world.level.Level;

//import static io.github.lucaargolo.seasons.FabricSeasons.CONFIG;

public class FabricSeasonsHelper {

    public static int getSeasonDuration() {
        // Hardcoding for now so this  will work if they update
        //return CONFIG.getSpringLength();
        return 672000;
    }

    public static boolean isSeasonDimension(Level level) {
        return level.dimension() == Level.OVERWORLD;
        //return CONFIG.isValidInDimension(level.dimension());
    }

}
