package homeostaticseasons.api;

import java.util.Locale;

import org.jetbrains.annotations.NotNull;

import net.minecraft.util.StringRepresentable;

public enum SeasonChangeMethod implements StringRepresentable {

    REALTIME,
    FIXED,
    CONFIGURED;

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

}
