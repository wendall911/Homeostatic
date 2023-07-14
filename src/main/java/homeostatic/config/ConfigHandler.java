package homeostatic.config;

import java.awt.Color;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;

import homeostatic.Homeostatic;
import homeostatic.util.ColorHelper;

@Mod.EventBusSubscriber(modid = Homeostatic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ConfigHandler {

    private ConfigHandler() {}

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Client.CONFIG_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Common.CONFIG_SPEC);
    }

    public static final class Client {

        public static final ForgeConfigSpec CONFIG_SPEC;
        private static final Client CONFIG;

        private static final List<String> positions = Arrays.asList("TOPLEFT", "TOPCENTER", "TOPRIGHT", "BOTTOMLEFT", "BOTTOMCENTER", "BOTTOMRIGHT");
        private static final List<String> temperatureHudOptions = Arrays.asList("CENTER_GLOBE", "RIGHT_THERMOMETER");
        private static Color temperatureColorCold = ColorHelper.decode("#3ab3da");
        private static Color temperatureColorHot = ColorHelper.decode("#f9801d");
        private static final Predicate<Object> hexRangeValidator = s -> s instanceof String
                && ((String) s).matches("#[a-zA-Z\\d]{6}->#[a-zA-Z\\d]{6}");

        public final BooleanValue useFahrenheit;
        public final BooleanValue showDegreeSymbol;
        public final ConfigValue<String> debugPosition;
        public final IntValue debugOffsetX;
        public final IntValue debugOffsetY;
        public final DoubleValue scale;
        public final ConfigValue<String> temperatureColorRange;
        public final ConfigValue<String> temperatureHudOption;
        public final ConfigValue<String> thermometerPosition;
        public final IntValue thermometerOffsetX;
        public final IntValue thermometerOffsetY;
        public final IntValue thermometerTextOffsetY;
        public final BooleanValue showThermometerRateChangeSymbols;
        public final ConfigValue<String> globePosition;
        public final IntValue globeOffsetX;
        public final IntValue globeOffsetY;
        public final IntValue globeTextOffsetY;
        public final BooleanValue forceWaterBarPosition;
        public final ConfigValue<String> waterBarPosition;
        public final IntValue waterBarOffsetX;
        public final IntValue waterBarOffsetY;

        static {
            Pair<Client,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);

            CONFIG_SPEC = specPair.getRight();
            CONFIG = specPair.getLeft();
        }

        Client(ForgeConfigSpec.Builder builder) {
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
            return CONFIG.useFahrenheit.get();
        }

        public static boolean showDegreeSymbol() {
            return CONFIG.showDegreeSymbol.get();
        }

        public static String debugPosition() {
            return CONFIG.debugPosition.get();
        }

        public static int debugOffsetX() {
            return CONFIG.debugOffsetX.get();
        }

        public static int debugOffsetY() {
            return CONFIG.debugOffsetY.get();
        }

        public static double scale() {
            return CONFIG.scale.get();
        }

        public static Color temperatureColorCold() {
            return temperatureColorCold;
        }

        public static Color temperatureColorHot() {
            return temperatureColorHot;
        }

        public static String temperatureHudOption() {
            return CONFIG.temperatureHudOption.get();
        }

        public static String thermometerPosition() {
            return CONFIG.thermometerPosition.get();
        }

        public static int thermometerOffsetX() {
            return CONFIG.thermometerOffsetX.get();
        }

        public static int thermometerOffsetY() {
            return CONFIG.thermometerOffsetY.get();
        }

        public static int thermometerTextOffsetY() {
            return CONFIG.thermometerTextOffsetY.get();
        }

        public static boolean showThermometerRateChangeSymbols() {
            return CONFIG.showThermometerRateChangeSymbols.get();
        }

        public static String globePosition() {
            return CONFIG.globePosition.get();
        }

        public static int globeOffsetX() {
            return CONFIG.globeOffsetX.get();
        }

        public static int globeOffsetY() {
            return CONFIG.globeOffsetY.get();
        }

        public static int globeTextOffsetY() {
            return CONFIG.globeTextOffsetY.get();
        }

        public static boolean forceWaterBarPosition() {
            return CONFIG.forceWaterBarPosition.get();
        }

        public static String waterBarPosition() {
            return CONFIG.waterBarPosition.get();
        }

        public static int waterBarOffsetX() {
            return CONFIG.waterBarOffsetX.get();
        }

        public static int waterBarOffsetY() {
            return CONFIG.waterBarOffsetY.get();
        }

        public static void init() {
            String[] temperatureColors = CONFIG.temperatureColorRange.get().split("->");

            temperatureColorCold = ColorHelper.decode(temperatureColors[0]);
            temperatureColorHot = ColorHelper.decode(temperatureColors[1]);
        }

    }

    public static final class Common {

        public static final ForgeConfigSpec CONFIG_SPEC;
        private static final Common CONFIG;

        public final BooleanValue debugEnabled;
        public final BooleanValue showTemperatureValues;
        public final BooleanValue requireThermometer;
        public final DoubleValue randomWaterLoss;
        public final DoubleValue radiationReductionPercent;

        static {
            Pair<Common,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);

            CONFIG_SPEC = specPair.getRight();
            CONFIG = specPair.getLeft();
        }

        Common(ForgeConfigSpec.Builder builder) {

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
                .defineInRange("randomWaterLoss", 0.05, 0.01, 1.0);

            radiationReductionPercent = builder
                .comment("Percentage of radiation reduced when 'Radiation Protection' is added to an armor piece. For example, 0.25 with four pieces will be 100%. 1.0 will be 100% for one piece. Default gives 80% for four pieces.")
                .defineInRange("radiationReductionPercent", 0.2, 0.1, 1.0);

        }

        public static boolean debugEnabled() {
            return CONFIG.debugEnabled.get();
        }

        public static boolean showTemperatureValues() {
            return CONFIG.showTemperatureValues.get();
        }

        public static boolean requireThermometer() {
            return CONFIG.requireThermometer.get();
        }

        public static float getRandomWaterLoss() {
            double waterLoss = CONFIG.randomWaterLoss.get();

            return (float) waterLoss;
        }

        public static float getRadiationReductionPercent() {
            double reduction = CONFIG.radiationReductionPercent.get();

            return (float) reduction;
        }

    }

}