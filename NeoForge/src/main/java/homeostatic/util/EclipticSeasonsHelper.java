package homeostatic.util;

import com.teamtea.eclipticseasons.api.EclipticSeasonsApi;
import com.teamtea.eclipticseasons.api.constant.solar.SolarTerm;

import net.minecraft.world.level.Level;

import homeostatic.common.temperature.SubSeason;

public class EclipticSeasonsHelper {

    public static boolean isSeasonDimension(Level level) {
        SolarTerm solarTerm = EclipticSeasonsApi.getInstance().getSolarTerm(level);

        return solarTerm != SolarTerm.NONE;
    }

    public static SubSeason getSubSeason(Level level) {
        SolarTerm solarTerm = EclipticSeasonsApi.getInstance().getSolarTerm(level);

        return SubSeason.values()[(solarTerm.ordinal() / 2)];
    }

}
