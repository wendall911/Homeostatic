package homeostaticseasons.api;

import java.util.Locale;

import org.jetbrains.annotations.NotNull;

import net.minecraft.util.StringRepresentable;

import homeostaticseasons.HomeostaticSeasons;
import homeostaticseasons.config.ConfigHandler;

public enum Season implements StringRepresentable {

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
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String getTranslationKey() {
        return HomeostaticSeasons.MODID + ".season." + getSerializedName();
    }

    public long getSeasonLength() {
        return switch (this) {
            case EARLY_SPRING -> ConfigHandler.Common.earlySpringDaysLength();
            case MID_SPRING -> ConfigHandler.Common.midSpringDaysLength();
            case LATE_SPRING -> ConfigHandler.Common.lateSpringDaysLength();
            case EARLY_SUMMER -> ConfigHandler.Common.earlySummerDaysLength();
            case MID_SUMMER -> ConfigHandler.Common.midSummerDaysLength();
            case LATE_SUMMER -> ConfigHandler.Common.lateSummerDaysLength();
            case EARLY_AUTUMN -> ConfigHandler.Common.earlyAutumnDaysLength();
            case MID_AUTUMN -> ConfigHandler.Common.midAutumnDaysLength();
            case LATE_AUTUMN -> ConfigHandler.Common.lateAutumnDaysLength();
            case EARLY_WINTER -> ConfigHandler.Common.earlyWinterDaysLength();
            case MID_WINTER -> ConfigHandler.Common.midWinterDaysLength();
            case LATE_WINTER -> ConfigHandler.Common.lateWinterDaysLength();
        };
    }

    public String getHumanReadableName() {
        String name = this.name().toLowerCase(Locale.ROOT).replace('_', ' ');
        String[] words = name.split(" ");
        StringBuilder humanReadableName = new StringBuilder();

        for (String word : words) {
            humanReadableName.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        return humanReadableName.toString().trim();
    }

    public Season next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public boolean isWetSeason() {
        if (ConfigHandler.Common.seasonChangeMethod() == SeasonChangeMethod.REALTIME) {
            if (ConfigHandler.Common.hemisphere() == Hemisphere.NORTHERN) {
                return ordinal() > 6;
            }
            else {
                return ordinal() < 7 && ordinal() > 1;
            }
        }
        else {
            return ordinal() > 6;
        }
    }

}
