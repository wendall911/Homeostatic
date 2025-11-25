package homeostaticseasons.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import org.apache.commons.lang3.tuple.Pair;

import technology.roughness.whitenoise.config.WhiteNoiseConfigSpec;

import homeostaticseasons.HomeostaticSeasons;
import homeostaticseasons.api.Hemisphere;
import homeostaticseasons.api.Season;
import homeostaticseasons.api.SeasonChangeMethod;
import homeostaticseasons.common.Translations;
import homeostaticseasons.platform.Services;

public class ConfigHandler {

    public static final WhiteNoiseConfigSpec CLIENT_SPEC;
    public static final WhiteNoiseConfigSpec COMMON_SPEC;

    private static final Client CLIENT;
    private static final Common COMMON;

    static {
        final Pair<Client, WhiteNoiseConfigSpec> specPairClient = new WhiteNoiseConfigSpec.Builder().configure(Client::new);
        final Pair<Common, WhiteNoiseConfigSpec> specPairCommon = new WhiteNoiseConfigSpec.Builder().configure(Common::new);

        CLIENT_SPEC = specPairClient.getRight();
        CLIENT = specPairClient.getLeft();
        COMMON_SPEC = specPairCommon.getRight();
        COMMON = specPairCommon.getLeft();
    }

    public static void initClient() {
    }

    public static void initCommon() {
        Common.seasonLengths = new SeasonLengths(
            COMMON.earlySpringDaysLength.get(),
            COMMON.midSpringDaysLength.get(),
            COMMON.lateSpringDaysLength.get(),
            COMMON.earlySummerDaysLength.get(),
            COMMON.midSummerDaysLength.get(),
            COMMON.lateSummerDaysLength.get(),
            COMMON.earlyAutumnDaysLength.get(),
            COMMON.midAutumnDaysLength.get(),
            COMMON.lateAutumnDaysLength.get(),
            COMMON.earlyWinterDaysLength.get(),
            COMMON.midWinterDaysLength.get(),
            COMMON.lateWinterDaysLength.get()
        );

        // Initialize season map
        Common.seasonMap.clear();
        Season startingSeason = Common.startingSeason();

        for (int i = startingSeason.ordinal(); i < Season.values().length; i++) {
            Season season = Season.values()[i];
            long cumulativeLength = 0L;

            for (int j = startingSeason.ordinal(); j <= i; j++) {
                cumulativeLength += Season.values()[j].getSeasonLength();
            }

            Common.seasonMap.put(cumulativeLength - startingSeason.getSeasonLength(), season);
            Common.seasonStartTimes.put(season, cumulativeLength - startingSeason.getSeasonLength());
        }

        if (Services.PLATFORM.isDevelopmentEnvironment()) {
            HomeostaticSeasons.LOGGER.warn("initialized seasonMap {}", Common.seasonMap);
        }
    }

    public static final class Client {

        private final WhiteNoiseConfigSpec.BooleanValue changeFoliageColor;
        private final WhiteNoiseConfigSpec.BooleanValue changeGrassColor;
        private final WhiteNoiseConfigSpec.BooleanValue changeBirchColor;

        Client(WhiteNoiseConfigSpec.Builder builder) {

            builder.push("visuals").comment(getTranslation("visuals"));

            changeFoliageColor = builder
                .comment(getTranslation("changefoliagecolor"))
                .define("changeFoliageColor", true);
            changeGrassColor = builder
                .comment(getTranslation("changegrasscolor"))
                .define("changeGrassColor", true);
            changeBirchColor = builder
                .comment(getTranslation("changebirchcolor"))
                .define("changeBirchColor", true);

            builder.pop(); // visuals
        }

        public static boolean changeFoliageColor() {
            return CLIENT.changeFoliageColor.get();
        }

        public static boolean changeGrassColor() {
            return CLIENT.changeGrassColor.get();
        }

        public static boolean changeBirchColor() {
            return CLIENT.changeBirchColor.get();
        }

    }

    public static final class Common {

        private final WhiteNoiseConfigSpec.ConfigValue<List<? extends String>> whitelistDimensions;
        private static final List<String> whitelistDimensionsList = List.of("whitelistDimensions");
        private static final String[] defaultWhitelistDimensions = new String[] {
            "minecraft:overworld"
        };
        private static final Predicate<Object> resourceLocationValidator = s -> s instanceof String
            && ((String) s).matches("[a-z]+[:]{1}[a-z_]+");
        private final WhiteNoiseConfigSpec.EnumValue<SeasonChangeMethod> seasonChangeMethod;
        private final WhiteNoiseConfigSpec.EnumValue<Hemisphere> hemisphere;
        private static SeasonLengths seasonLengths;
        private final WhiteNoiseConfigSpec.EnumValue<Season> fixedSeason;
        private final WhiteNoiseConfigSpec.EnumValue<Season> startingSeason;
        private static final NavigableMap<Long, Season> seasonMap = new TreeMap<>();
        private static final Map<Season, Long> seasonStartTimes = new HashMap<>(12);
        private final WhiteNoiseConfigSpec.LongValue earlySpringDaysLength;
        private final WhiteNoiseConfigSpec.LongValue midSpringDaysLength;
        private final WhiteNoiseConfigSpec.LongValue lateSpringDaysLength;
        private final WhiteNoiseConfigSpec.LongValue earlySummerDaysLength;
        private final WhiteNoiseConfigSpec.LongValue midSummerDaysLength;
        private final WhiteNoiseConfigSpec.LongValue lateSummerDaysLength;
        private final WhiteNoiseConfigSpec.LongValue earlyAutumnDaysLength;
        private final WhiteNoiseConfigSpec.LongValue midAutumnDaysLength;
        private final WhiteNoiseConfigSpec.LongValue lateAutumnDaysLength;
        private final WhiteNoiseConfigSpec.LongValue earlyWinterDaysLength;
        private final WhiteNoiseConfigSpec.LongValue midWinterDaysLength;
        private final WhiteNoiseConfigSpec.LongValue lateWinterDaysLength;
        private final WhiteNoiseConfigSpec.BooleanValue seasonalSnowReplaceVegetation;

        Common(WhiteNoiseConfigSpec.Builder builder) {
            builder.push("seasons").comment(getTranslation("seasons"));

            whitelistDimensions = builder
                .comment(getTranslation("whitelistdimensions"))
                .defineListAllowEmpty(whitelistDimensionsList, getDefaultWhitelistDimensions(), resourceLocationValidator);
            seasonChangeMethod = builder
                .comment(getTranslation("seasonchangemethod"))
                .defineEnum("seasonChangeMethod", SeasonChangeMethod.CONFIGURED);
            hemisphere = builder
                .comment(getTranslation("hemisphere"))
                .defineEnum("hemisphere", Hemisphere.NORTHERN);
            fixedSeason = builder
                .comment(getTranslation("fixedseason"))
                .defineEnum("fixedSeason", Season.EARLY_SPRING);
            startingSeason = builder
                .comment("The season that the world starts in.")
                .defineEnum("startingSeason", Season.EARLY_SPRING);
            earlySpringDaysLength = builder
                .comment(getTranslation("earlyspringdayslength"))
                .defineInRange("earlySpringDaysLength", 3L, 1L, 60L);
            midSpringDaysLength = builder
                .comment(getTranslation("midspringdayslength"))
                .defineInRange("midSpringDaysLength", 3L, 1L, 60L);
            lateSpringDaysLength = builder
                .comment(getTranslation("latespringdayslength"))
                .defineInRange("lateSpringDaysLength", 3L, 1L, 60L);
            earlySummerDaysLength = builder
                .comment(getTranslation("earlysummerdayslength"))
                .defineInRange("earlySummerDaysLength", 3L, 1L, 60L);
            midSummerDaysLength = builder
                .comment(getTranslation("midsummerdayslength"))
                .defineInRange("midSummerDaysLength", 3L, 1L, 60L);
            lateSummerDaysLength = builder
                .comment(getTranslation("latesummerdayslength"))
                .defineInRange("lateSummerDaysLength", 3L, 1L, 60L);
            earlyAutumnDaysLength = builder
                .comment(getTranslation("earlyautumndayslength"))
                .defineInRange("earlyAutumnDaysLength", 3L, 1L, 60L);
            midAutumnDaysLength = builder
                .comment(getTranslation("midautumndayslength"))
                .defineInRange("midAutumnDaysLength", 3L, 1L, 60L);
            lateAutumnDaysLength = builder
                .comment(getTranslation("lateautumndayslength"))
                .defineInRange("lateAutumnDaysLength", 3L, 1L, 60L);
            earlyWinterDaysLength = builder
                .comment(getTranslation("earlywinterdayslength"))
                .defineInRange("earlyWinterDaysLength", 3L, 1L, 60L);
            midWinterDaysLength = builder
                .comment(getTranslation("midwinterdayslength"))
                .defineInRange("midWinterDaysLength", 3L, 1L, 60L);
            lateWinterDaysLength = builder
                .comment(getTranslation("latewinterdayslength"))
                .defineInRange("lateWinterDaysLength", 3L, 1L, 60L);

            builder.pop(); // seasons

            builder.push("weather").comment(getTranslation("weather"));

            seasonalSnowReplaceVegetation = builder
                .comment(getTranslation("seasonalsnowreplacevegetation"))
                .define("seasonalSnowReplaceVegetation", true);

            builder.pop(); // weather
        }

        public static SeasonChangeMethod seasonChangeMethod() {
            return COMMON.seasonChangeMethod.get();
        }

        public static Hemisphere hemisphere() {
            return COMMON.hemisphere.get();
        }

        public static Season fixedSeason() {
            return COMMON.fixedSeason.get();
        }

        public static Season startingSeason() {
            return COMMON.startingSeason.get();
        }

        public static long earlySpringDaysLength() {
            return seasonLengths.earlySpring;
        }

        public static long midSpringDaysLength() {
            return seasonLengths.midSpring;
        }

        public static long lateSpringDaysLength() {
            return seasonLengths.lateSpring;
        }

        public static long earlySummerDaysLength() {
            return seasonLengths.earlySummer;
        }

        public static long midSummerDaysLength() {
            return seasonLengths.midSummer;
        }

        public static long lateSummerDaysLength() {
            return seasonLengths.lateSummer;
        }

        public static long earlyAutumnDaysLength() {
            return seasonLengths.earlyAutumn;
        }

        public static long midAutumnDaysLength() {
            return seasonLengths.midAutumn;
        }

        public static long lateAutumnDaysLength() {
            return seasonLengths.lateAutumn;
        }

        public static long earlyWinterDaysLength() {
            return seasonLengths.earlyWinter;
        }

        public static long midWinterDaysLength() {
            return seasonLengths.midWinter;
        }

        public static long lateWinterDaysLength() {
            return seasonLengths.lateWinter;
        }

        public static long getTotalYearLength() {
            return seasonLengths.getTotalLength();
        }

        public static Season getSeasonFromGameTime(long gameTime) {
            long timeOfYear;

            if (gameTime < getTotalYearLength()) {
                timeOfYear = gameTime;
            }
            else {
                timeOfYear = gameTime % getTotalYearLength();
            }

            return seasonMap.floorEntry(timeOfYear).getValue();
        }

        public static long getTimeUntilNextSeason(long gameTime) {
            long seasonTime = gameTime % 24000L;
            Long nextKey = seasonMap.higherKey(seasonTime);

            if (nextKey != null) {
                return nextKey - seasonTime;
            }

            return seasonMap.firstKey() - seasonTime;
        }

        public static long getTimeUntilSeason(long gameTime, Season season) {
            long timeOfYear;
            long seasonStartTime = seasonStartTimes.get(season);

            if (gameTime < getTotalYearLength()) {
                timeOfYear = gameTime;
            }
            else {
                timeOfYear = gameTime % getTotalYearLength();
            }

            if (seasonStartTime >= timeOfYear) {
                return seasonStartTime - timeOfYear;
            }
            else {
                return (getTotalYearLength() - timeOfYear) + seasonStartTime;
            }
        }

        public static long getSeasonTime(Season season) {
            return seasonStartTimes.get(season);
        }

        public static boolean isValidDimension(ResourceKey<Level> dimensionKey) {
            return COMMON.whitelistDimensions.get().contains(dimensionKey.location().toString());
        }

        private static Supplier<List<? extends String>> getDefaultWhitelistDimensions() {
            return () -> Arrays.asList(Common.defaultWhitelistDimensions);
        }

        public static boolean seasonalSnowReplaceVegetation() {
            return COMMON.seasonalSnowReplaceVegetation.get();
        }

    }

    private static String getTranslation(String key) {
        return Translations.get(key);
    }

    private record SeasonLengths(long earlySpring, long midSpring, long lateSpring, long earlySummer, long midSummer,
                                 long lateSummer, long earlyAutumn, long midAutumn, long lateAutumn, long earlyWinter,
                                 long midWinter, long lateWinter) {

            private SeasonLengths(long earlySpring, long midSpring, long lateSpring,
                                  long earlySummer, long midSummer, long lateSummer,
                                  long earlyAutumn, long midAutumn, long lateAutumn,
                                  long earlyWinter, long midWinter, long lateWinter) {
                this.earlySpring = earlySpring * 24000L;
                this.midSpring = midSpring * 24000L;
                this.lateSpring = lateSpring * 24000L;
                this.earlySummer = earlySummer * 24000L;
                this.midSummer = midSummer * 24000L;
                this.lateSummer = lateSummer * 24000L;
                this.earlyAutumn = earlyAutumn * 24000L;
                this.midAutumn = midAutumn * 24000L;
                this.lateAutumn = lateAutumn * 24000L;
                this.earlyWinter = earlyWinter * 24000L;
                this.midWinter = midWinter * 24000L;
                this.lateWinter = lateWinter * 24000L;
            }

            public long getTotalLength() {
                return earlySpring + midSpring + lateSpring
                    + earlySummer + midSummer + lateSummer
                    + earlyAutumn + midAutumn + lateAutumn
                    + earlyWinter + midWinter + lateWinter;
            }

        }

}
