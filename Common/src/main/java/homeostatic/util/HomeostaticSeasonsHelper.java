package homeostatic.util;

import net.minecraft.world.level.Level;

import homeostaticseasons.api.HomeostaticSeasonsAPI;

public class HomeostaticSeasonsHelper {

    public static int getCurrentSeason(Level level) {
        return HomeostaticSeasonsAPI.getCurrentSeason(level).ordinal();
    }

}
