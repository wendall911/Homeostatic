package homeostatic.config;

import java.awt.Color;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import technology.roughness.whitenoise.config.WhiteNoiseConfigSpec;

import homeostatic.common.Translations;
import homeostatic.util.Alignment;
import homeostatic.util.ColorHelper;

public class ConfigHandler {

    public static boolean loaded = false;

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

    public static final class Client {

        private static Color temperatureColorCold = ColorHelper.decode("#3ab3da");
        private static Color temperatureColorHot = ColorHelper.decode("#f9801d");
        private static final Predicate<Object> hexRangeValidator = s -> s instanceof String
                && ((String) s).matches("#[a-fA-F\\d]{6}->#[a-fA-F\\d]{6}");
        public final WhiteNoiseConfigSpec.BooleanValue useFahrenheit;
        public final WhiteNoiseConfigSpec.BooleanValue showDegreeSymbol;
        public final WhiteNoiseConfigSpec.EnumValue<Alignment.AlignmentType> debugPosition;
        public final WhiteNoiseConfigSpec.IntValue debugOffsetX;
        public final WhiteNoiseConfigSpec.IntValue debugOffsetY;
        public final WhiteNoiseConfigSpec.DoubleValue scale;
        public final WhiteNoiseConfigSpec.ConfigValue<String> temperatureColorRange;
        public final WhiteNoiseConfigSpec.EnumValue<Alignment.AlignmentType> globePosition;
        public final WhiteNoiseConfigSpec.IntValue globeOffsetX;
        public final WhiteNoiseConfigSpec.IntValue globeOffsetY;
        public final WhiteNoiseConfigSpec.IntValue globeTextOffsetY;
        public final WhiteNoiseConfigSpec.BooleanValue forceWaterBarPosition;
        public final WhiteNoiseConfigSpec.EnumValue<Alignment.AlignmentType> waterBarPosition;
        public final WhiteNoiseConfigSpec.IntValue waterBarOffsetX;
        public final WhiteNoiseConfigSpec.IntValue waterBarOffsetY;
        public final WhiteNoiseConfigSpec.DoubleValue condensationOpacity;
        public final WhiteNoiseConfigSpec.IntValue condensationMin;
        public final WhiteNoiseConfigSpec.IntValue condensationMax;

        Client(WhiteNoiseConfigSpec.Builder builder) {
            useFahrenheit = builder
                .comment(getTranslation("usefahrenheit"))
                .define("useFahrenheit", true);
            showDegreeSymbol = builder
                .comment(getTranslation("showdegreesymbol"))
                .define("showDegreeSymbol", true);
            debugPosition = builder
                .comment(getTranslation("position"))
                .defineEnum("position", Alignment.AlignmentType.TOPRIGHT);
            debugOffsetX = builder
                .comment(getTranslation("debugoffsetx"))
                .defineInRange("debugOffsetX", 3, -100, 100);
            debugOffsetY = builder
                .comment(getTranslation("debugoffsety"))
                .defineInRange("debugOffsetY", 3, -100, 100);
            scale = builder
                .comment(getTranslation("scale"))
                .defineInRange("scale", 0.5, 0.5, 2.0);
            temperatureColorRange = builder
                .comment(getTranslation("temperaturecolorrange"))
                .define("temperatureColorRange", "#3ab3da->#f9801d", hexRangeValidator);
            globePosition = builder
                .comment(getTranslation("globeposition"))
                .defineEnum("globePosition", Alignment.AlignmentType.BOTTOMCENTER);
            globeOffsetX = builder
                .comment(getTranslation("globeoffsetx"))
                .defineInRange("globeOffsetX", 0, -500, 500);
            globeOffsetY = builder
                .comment(getTranslation("globeoffsety"))
                .defineInRange("globeOffsetY", 50, -500, 500);
            globeTextOffsetY = builder
                .comment(getTranslation("globetextoffsety"))
                .defineInRange("globeTextOffsetY", 90, -500, 500);
            forceWaterBarPosition = builder
                .comment(getTranslation("forcewaterbarposition"))
                .define("forceWaterBarPosition", false);
            waterBarPosition = builder
                .comment(getTranslation("waterbarposition"))
                .defineEnum("waterBarPosition", Alignment.AlignmentType.BOTTOMCENTER);
            waterBarOffsetX = builder
                .comment(getTranslation("waterbaroffsetx"))
                .defineInRange("waterBarOffsetX", 96, -500, 500);
            waterBarOffsetY = builder
                .comment(getTranslation("waterbaroffsety"))
                .defineInRange("waterBarOffsetY", 50, -500, 500);
            condensationOpacity = builder
                .comment(getTranslation("condensationopacity"))
                .defineInRange("condensationOpacity", 0.5, 0.0, 1.0);
            condensationMin = builder
                .comment(getTranslation("condensationmin"))
                .defineInRange("condensationMin", 1, 0, 5);
            condensationMax = builder
                .comment(getTranslation("condensationmax"))
                .defineInRange("condensationMax", 10, 6, 20);
        }

        public static boolean useFahrenheit() {
            return CLIENT.useFahrenheit.get();
        }

        public static boolean showDegreeSymbol() {
            return CLIENT.showDegreeSymbol.get();
        }

        public static Alignment.AlignmentType debugPosition() {
            return CLIENT.debugPosition.get();
        }

        public static int debugOffsetX() {
            return CLIENT.debugOffsetX.get();
        }

        public static int debugOffsetY() {
            return CLIENT.debugOffsetY.get();
        }

        public static double scale() {
            return CLIENT.scale.get();
        }

        public static Color temperatureColorCold() {
            return temperatureColorCold;
        }

        public static Color temperatureColorHot() {
            return temperatureColorHot;
        }

        public static Alignment.AlignmentType globePosition() {
            return CLIENT.globePosition.get();
        }

        public static int globeOffsetX() {
            return CLIENT.globeOffsetX.get();
        }

        public static int globeOffsetY() {
            return CLIENT.globeOffsetY.get();
        }

        public static int globeTextOffsetY() {
            return CLIENT.globeTextOffsetY.get();
        }

        public static boolean forceWaterBarPosition() {
            return CLIENT.forceWaterBarPosition.get();
        }

        public static Alignment.AlignmentType waterBarPosition() {
            return CLIENT.waterBarPosition.get();
        }

        public static int waterBarOffsetX() {
            return CLIENT.waterBarOffsetX.get();
        }

        public static int waterBarOffsetY() {
            return CLIENT.waterBarOffsetY.get();
        }

        public static float condensationOpacity() {
            return CLIENT.condensationOpacity.get().floatValue();
        }

        public static int condensationMin() {
            return CLIENT.condensationMin.get();
        }

        public static int condensationMax() {
            return CLIENT.condensationMax.get();
        }

        public static void init() {
            String[] temperatureColors = CLIENT.temperatureColorRange.get().split("->");

            temperatureColorCold = ColorHelper.decode(temperatureColors[0]);
            temperatureColorHot = ColorHelper.decode(temperatureColors[1]);

            loaded = true;
        }

    }

    public static final class Common {
        public final WhiteNoiseConfigSpec.BooleanValue debugEnabled;
        public final WhiteNoiseConfigSpec.BooleanValue showTemperatureValues;
        public final WhiteNoiseConfigSpec.BooleanValue requireThermometer;
        public final WhiteNoiseConfigSpec.DoubleValue randomWaterLoss;
        public final WhiteNoiseConfigSpec.DoubleValue radiationReductionPercent;

        Common(WhiteNoiseConfigSpec.Builder builder) {
            debugEnabled = builder
                .comment(getTranslation("debugenabled"))
                .define("debugEnabled", false);

            showTemperatureValues = builder
                .comment(getTranslation("showtemperaturevalues"))
                .define("showTemperatureValues", true);

            requireThermometer = builder
                .comment(getTranslation("requirethermometer"))
                .define("requireThermometer", false);

            randomWaterLoss = builder
                .comment(getTranslation("randomwaterloss"))
                .defineInRange("randomWaterLoss", 0.15, 0.01, 1.0);

            radiationReductionPercent = builder
                .comment(getTranslation("radiationreductionpercent"))
                .defineInRange("radiationReductionPercent", 0.2, 0.1, 1.0);

        }

        public static boolean debugEnabled() {
            return COMMON.debugEnabled.get();
        }

        public static boolean showTemperatureValues() {
            return COMMON.showTemperatureValues.get();
        }

        public static boolean requireThermometer() {
            return COMMON.requireThermometer.get();
        }

        public static float getRandomWaterLoss() {
            double waterLoss = COMMON.randomWaterLoss.get();

            return (float) waterLoss;
        }

        public static float getRadiationReductionPercent() {
            double reduction = COMMON.radiationReductionPercent.get();

            return (float) reduction;
        }

    }

    private static String getTranslation(String key) {
        return Translations.get(key);
    }

    private static String getTranslation(String key, String... values) {
        return Translations.get(key, values);
    }

}
