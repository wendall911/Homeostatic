package homeostatic.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import sereneseasons.api.season.SeasonHelper;

import static sereneseasons.init.ModConfig.seasons;

import homeostatic.common.temperature.SubSeason;

public class SereneSeasonsHelper {

    public static boolean isDimensionWhitelisted(ResourceKey<Level> levelResourceKey) {
        return seasons.isDimensionWhitelisted(levelResourceKey);
    }

    public static SubSeason getSubSeason(Level level) {
        return SubSeason.values()[SeasonHelper.getSeasonState(level).getSubSeason().ordinal()];
    }

}
