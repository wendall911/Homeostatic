package homeostatic.common.temperature;

import java.util.Locale;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;

public enum SubSeason implements StringRepresentable {
    EARLY_SPRING(),
    MID_SPRING(),
    LATE_SPRING(),
    EARLY_SUMMER(),
    MID_SUMMER(),
    LATE_SUMMER(),
    EARLY_AUTUMN(),
    MID_AUTUMN(),
    LATE_AUTUMN(),
    EARLY_WINTER(),
    MID_WINTER(),
    LATE_WINTER();

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    /*
     * Returns the correct sub-season based on season duration.
     */
    public static SubSeason getSubSeason(Level level, int seasonDuration) {
        int subSeasonLength = seasonDuration / 3;
        int dayTime = Math.toIntExact(level.getDayTime());
        int subSeasonTime = dayTime / subSeasonLength;

        return SubSeason.values()[subSeasonTime % 12];
    }

}
