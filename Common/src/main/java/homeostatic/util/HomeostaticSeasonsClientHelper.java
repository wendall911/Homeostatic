package homeostatic.util;

import net.minecraft.client.Minecraft;

//import homeostaticseasons.api.HomeostaticSeasonsAPI;

public class HomeostaticSeasonsClientHelper {

    public static String getSeasonName(Minecraft mc) {
        if (mc.level == null) {
            return "UNKNOWN";
        }

        //return HomeostaticSeasonsAPI.getCurrentSeason(mc.level).toString();
        return "UNKNOWN";
    }

}
