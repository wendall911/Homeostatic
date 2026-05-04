package homeostatic.util;

import net.minecraft.world.level.Level;

import sereneseasons.api.season.SeasonHelper;

import static sereneseasons.init.ModConfig.seasons;

import homeostatic.common.temperature.SubSeason;

public class SereneSeasonsFabricHelper {

    public static boolean isSeasonDimension(Level level) {
        return seasons.isDimensionWhitelisted(level.dimension());
    }

    public static SubSeason getSubSeason(Level level) {
        return SubSeason.values()[SeasonHelper.getSeasonState(level).getSubSeason().ordinal()];
    }

}
