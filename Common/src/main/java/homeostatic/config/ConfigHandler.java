package homeostatic.config;

import java.awt.Color;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import technology.roughness.whitenoise.config.WhiteNoiseConfigSpec;

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

        private static final List<String> positions = Arrays.asList("TOPLEFT", "TOPCENTER", "TOPRIGHT", "BOTTOMLEFT", "BOTTOMCENTER", "BOTTOMRIGHT");
        private static final List<String> temperatureHudOptions = Arrays.asList("CENTER_GLOBE", "RIGHT_THERMOMETER");
        private static Color temperatureColorCold = ColorHelper.decode("#3ab3da");
        private static Color temperatureColorHot = ColorHelper.decode("#f9801d");
        private static final Predicate<Object> hexRangeValidator = s -> s instanceof String
                && ((String) s).matches("#[a-zA-Z\\d]{6}->#[a-zA-Z\\d]{6}");

        public final WhiteNoiseConfigSpec.BooleanValue useFahrenheit;
        public final WhiteNoiseConfigSpec.BooleanValue showDegreeSymbol;
        public final WhiteNoiseConfigSpec.ConfigValue<String> debugPosition;
        public final WhiteNoiseConfigSpec.IntValue debugOffsetX;
        public final WhiteNoiseConfigSpec.IntValue debugOffsetY;
        public final WhiteNoiseConfigSpec.DoubleValue scale;
        public final WhiteNoiseConfigSpec.ConfigValue<String> temperatureColorRange;
        public final WhiteNoiseConfigSpec.ConfigValue<String> temperatureHudOption;
        public final WhiteNoiseConfigSpec.ConfigValue<String> thermometerPosition;
        public final WhiteNoiseConfigSpec.IntValue thermometerOffsetX;
        public final WhiteNoiseConfigSpec.IntValue thermometerOffsetY;
        public final WhiteNoiseConfigSpec.IntValue thermometerTextOffsetY;
        public final WhiteNoiseConfigSpec.BooleanValue showThermometerRateChangeSymbols;
        public final WhiteNoiseConfigSpec.ConfigValue<String> globePosition;
        public final WhiteNoiseConfigSpec.IntValue globeOffsetX;
        public final WhiteNoiseConfigSpec.IntValue globeOffsetY;
        public final WhiteNoiseConfigSpec.IntValue globeTextOffsetY;
        public final WhiteNoiseConfigSpec.BooleanValue forceWaterBarPosition;
        public final WhiteNoiseConfigSpec.ConfigValue<String> waterBarPosition;
        public final WhiteNoiseConfigSpec.IntValue waterBarOffsetX;
        public final WhiteNoiseConfigSpec.IntValue waterBarOffsetY;

        Client(WhiteNoiseConfigSpec.Builder builder) {
            useFahrenheit = builder
                .comment("Use Fahrenheit, otherwise use Celcius.")
                .define("useFahrenheit", true);
            showDegreeSymbol = builder
                .comment("Show degree symbol next to temperature value.")
                .define("showDegreeSymbol", true);
            debugPosition = builder
                .comment("Position of debug info, one of: " + positions)
                .defineInList("position", "TOPRIGHT", positions);
            debugOffsetX = builder
                .comment("Debug text X offset")
                .defineInRange("debugOffsetX", 3, -100, 100);
            debugOffsetY = builder
                .comment("Debug text Y offset")
                .defineInRange("debugOffsetY", 3, -100, 100);
            scale = builder
                .comment("The size of the text info (multiplier)")
                .defineInRange("scale", 0.5, 0.5, 2.0);
            temperatureColorRange = builder
                .comment("Temperature color range (Format (cold->hot): #3ab3da->#f9801d)")
                .define("temperatureColorRange", "#3ab3da->#f9801d", hexRangeValidator);
            temperatureHudOption = builder
                .comment("Select which hud element to display for body and area temperature. One of: " + temperatureHudOptions)
                .defineInList("temperatureHudOption", "CENTER_GLOBE", temperatureHudOptions);
            thermometerPosition = builder
                .comment("Position of the RIGHT_THERMOMETER HUD if enabled, one of: " + positions)
                .defineInList("thermometerPosition", "BOTTOMRIGHT", positions);
            thermometerOffsetX = builder
                .comment("RIGHT_THERMOMETER HUD X offset")
                .defineInRange("thermometerOffsetX", 133, -500, 500);
            thermometerOffsetY = builder
                .comment("RIGHT_THERMOMETER HUD Y offset")
                .defineInRange("thermometerOffsetY", 27, -500, 500);
            thermometerTextOffsetY = builder
                .comment("RIGHT_THERMOMETER HUD Y offset")
                .defineInRange("thermometerTextOffsetY", 15, -500, 500);
            showThermometerRateChangeSymbols = builder
                .comment("Show rate change symbols to left/right of thermometer. Left is core temp, right is skin temperature.")
                .define("showThermometerRateChangeSymbols", true);
            globePosition = builder
                .comment("Position of the CENTER_GLOBE HUD if enabled, one of: " + positions)
                .defineInList("globePosition", "BOTTOMCENTER", positions);
            globeOffsetX = builder
                .comment("CENTER_GLOBE HUD X offset")
                .defineInRange("globeOffsetX", 0, -500, 500);
            globeOffsetY = builder
                .comment("CENTER_GLOBE HUD Y offset")
                .defineInRange("globeOffsetY", 50, -500, 500);
            globeTextOffsetY = builder
                .comment("CENTER_GLOBE HUD Y offset")
                .defineInRange("globeTextOffsetY", 90, -500, 500);
            forceWaterBarPosition = builder
                .comment("Set to true to force position of the water bar.")
                .define("forceWaterBarPosition", false);
            waterBarPosition = builder
                .comment("Position of the Water Bar HUD if forceWaterBarPosition is true, one of: " + positions)
                .defineInList("waterBarPosition", "BOTTOMCENTER", positions);
            waterBarOffsetX = builder
                .comment("Water Bar HUD X offset")
                .defineInRange("waterBarOffsetX", 96, -500, 500);
            waterBarOffsetY = builder
                .comment("Water Bar HUD Y offset")
                .defineInRange("waterBarOffsetY", 50, -500, 500);
        }

        public static boolean useFahrenheit() {
            return CLIENT.useFahrenheit.get();
        }

        public static boolean showDegreeSymbol() {
            return CLIENT.showDegreeSymbol.get();
        }

        public static String debugPosition() {
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

        public static String temperatureHudOption() {
            return CLIENT.temperatureHudOption.get();
        }

        public static String thermometerPosition() {
            return CLIENT.thermometerPosition.get();
        }

        public static int thermometerOffsetX() {
            return CLIENT.thermometerOffsetX.get();
        }

        public static int thermometerOffsetY() {
            return CLIENT.thermometerOffsetY.get();
        }

        public static int thermometerTextOffsetY() {
            return CLIENT.thermometerTextOffsetY.get();
        }

        public static boolean showThermometerRateChangeSymbols() {
            return CLIENT.showThermometerRateChangeSymbols.get();
        }

        public static String globePosition() {
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

        public static String waterBarPosition() {
            return CLIENT.waterBarPosition.get();
        }

        public static int waterBarOffsetX() {
            return CLIENT.waterBarOffsetX.get();
        }

        public static int waterBarOffsetY() {
            return CLIENT.waterBarOffsetY.get();
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
                .comment("Show temperature debug info.")
                .define("debugEnabled", false);

            showTemperatureValues = builder
                .comment("Show temperature values in HUD.")
                .define("showTemperatureValues", true);

            requireThermometer = builder
                .comment("Require thermometer helmet enhancement to display temperature values.")
                .define("requireThermometer", false);

            randomWaterLoss = builder
                .comment("Water loss speed when not sweating. Increase to make water loss more prevalent.")
                .defineInRange("randomWaterLoss", 0.15, 0.01, 1.0);

            radiationReductionPercent = builder
                .comment("Percentage of radiation reduced when 'Radiation Protection' is added to an armor piece. For example, 0.25 with four pieces will be 100%. 1.0 will be 100% for one piece. Default gives 80% for four pieces.")
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

}