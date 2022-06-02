package homeostatic.config;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.function.Supplier;
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
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Server.CONFIG_SPEC);
    }

    public static final class Client {

        public static final ForgeConfigSpec CONFIG_SPEC;
        private static final Client CONFIG;

        private static final List<String> positions = Arrays.asList("TOPLEFT", "TOPRIGHT", "BOTTOMLEFT", "BOTTOMRIGHT");
        private static final List<String> fieldList = Arrays.asList("fields");
        private static final String[] fieldStrings = new String[] { "temperature", "time", "season" };
        private static Color temperatureColorDark = ColorHelper.decode("#b02e26");
        private static Color temperatureColorBright = ColorHelper.decode("#ffd83d");
        private static Color timeColorDark = ColorHelper.decode("#474f52");
        private static Color timeColorBright = ColorHelper.decode("#ffd83d");
        private static Color labelColorDecoded;
        private static final Predicate<Object> hexValidator = s -> s instanceof String
                && ((String) s).matches("#[a-zA-Z\\d]{6}");
        private static final Predicate<Object> hexRangeValidator = s -> s instanceof String
                && ((String) s).matches("#[a-zA-Z\\d]{6}->#[a-zA-Z\\d]{6}");

        public final BooleanValue enabled;
        public final BooleanValue useFahrenheit;
        public final BooleanValue textShadow;
        public final ConfigValue<String> position;
        public final IntValue offsetX;
        public final IntValue offsetY;
        public final DoubleValue scale;
        public final ConfigValue<List<? extends String>> fields;
        public final ConfigValue<String> labelColor;
        public final ConfigValue<String> temperatureLabel;
        public final ConfigValue<String> temperatureColorRange;
        public final ConfigValue<String> timeLabel;
        public final ConfigValue<String> timeColorRange;

        static {
            Pair<Client,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);

            CONFIG_SPEC = specPair.getRight();
            CONFIG = specPair.getLeft();
        }

        Client(ForgeConfigSpec.Builder builder) {
            enabled = builder
                .comment("Show temperature info.")
                .define("enabled", true);
            useFahrenheit = builder
                .comment("Use Fahrenheit, otherwise use Celcius.")
                .define("useFahrenheit", true);
            position = builder
                .comment("Position, one of: " + positions)
                .defineInList("position", "TOPRIGHT", positions);
            offsetX = builder
                .comment("X offset")
                .defineInRange("offsetX", 3, -100, 100);
            offsetY = builder
                .comment("Y offset")
                .defineInRange("offsetY", 3, -100, 100);
            scale = builder
                .comment("The size of the text info (multiplier)")
                .defineInRange("scale", 1.0, 0.5, 2.0);
            fields = builder
                .comment("Fields to show. Will display in same order as defined. Options: "
                        + "[\"" + String.join("\", \"", fieldStrings) + "\"]")
                .defineListAllowEmpty(fieldList, getFields(), s -> (s instanceof String));
            textShadow = builder
                .comment("Show text shadow.")
                .define("textShadow", true);
            labelColor = builder
                .comment("Label color (Format: #9c9d97)")
                .define("labelColor", "#9c9d97", hexValidator);
            temperatureLabel = builder
                .comment("Label for temperature level.")
                .define("temperatureLabel", "Temperature: ");
            temperatureColorRange = builder
                .comment("Temperature color range (Format (dark->bright): #b02e26->#ffd83d)")
                .define("temperatureColorRange", "#b02e26->#ffd83d", hexRangeValidator);
            timeLabel = builder
                .comment("Label for time.")
                .define("timeLabel", "");
            timeColorRange = builder
                .comment("Time color range (Format (dark->bright): #474f52->#ffd83d)")
                .define("timeColorRange", "#474f52->#ffd83d", hexRangeValidator);
        }

        public static boolean enabled() {
            return CONFIG.enabled.get();
        }

        public static boolean useFahrenheit() {
            return CONFIG.useFahrenheit.get();
        }

        public static String position() {
            return CONFIG.position.get();
        }

        public static int offsetX() {
            return CONFIG.offsetX.get();
        }

        public static int offsetY() {
            return CONFIG.offsetY.get();
        }

        public static double scale() {
            return CONFIG.scale.get();
        }

        @SuppressWarnings("unchecked")
        public static List<String> fields() {
            List<String> fields = (List<String>) CONFIG.fields.get();

            if (CONFIG.position.get().startsWith("BOTTOM")) {
                Collections.reverse(fields);
            }

            // Populate range colors here to initialize when building fields list.
            String[] temperatureColors = CONFIG.temperatureColorRange.get().split("->");
            String[] timeColors = CONFIG.timeColorRange.get().split("->");

            temperatureColorDark = ColorHelper.decode(temperatureColors[0]);
            temperatureColorBright = ColorHelper.decode(temperatureColors[1]);
            timeColorDark = ColorHelper.decode(timeColors[0]);
            timeColorBright = ColorHelper.decode(timeColors[1]);
            labelColorDecoded = ColorHelper.decode(CONFIG.labelColor.get());

            return fields;
        }

        public static boolean textShadow() {
            return CONFIG.textShadow.get();
        }

        public static Color labelColor() {
            return labelColorDecoded;
        }

        public static String temperatureLabel() {
            return CONFIG.temperatureLabel.get();
        }

        public static Color temperatureColorDark() {
            return temperatureColorDark;
        }

        public static Color temperatureColorBright() {
            return temperatureColorBright;
        }

        public static String timeLabel() {
            return CONFIG.timeLabel.get();
        }

        public static Color timeColorDark() {
            return timeColorDark;
        }

        public static Color timeColorBright() {
            return timeColorBright;
        }

        private static Supplier<List<? extends String>> getFields() {
            return () -> Arrays.asList(fieldStrings);
        }

    }

    public static final class Server {
        public static final ForgeConfigSpec CONFIG_SPEC;
        private static final Server CONFIG;

        private static IntValue DRINK_AMOUNT;
        private static DoubleValue DRINK_SATURATION;
        private static IntValue EFFECT_POTENCY;
        private static IntValue EFFECT_DURATION;
        private static DoubleValue EFFECT_CHANCE;

        static {
            Pair<Server,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);

            CONFIG_SPEC = specPair.getRight();
            CONFIG = specPair.getLeft();
        }

        Server(ForgeConfigSpec.Builder builder) {
            DRINK_AMOUNT = builder
                .comment("Amount of water that can drank per interaction in half shanks. Default 1")
                .defineInRange("DRINK_AMOUNT", 1, 1, 20);
            DRINK_SATURATION = builder
                .comment("Amount of saturation gained per drink. Default 0.2")
                .defineInRange("DRINK_SATURATION", 0.2, 0.0, 20.0);
            EFFECT_POTENCY = builder
                .comment("Potency of thirst effect when drinking from an open water source. Default 25")
                .defineInRange("EFFECT_POTENCY", 25, 1, 255);
            EFFECT_DURATION = builder
                .comment("Duration of thirst effect in ticks (20/second). Default 200")
                .defineInRange("EFFECT_DURATION", 200, 1, 6000);
            EFFECT_CHANCE = builder
                .comment("Chance to give thirst status effect. Default 0.2")
                .defineInRange("EFFECT_CHANCE", 0.2, 0.0, 1.0);
        }

        public static int drinkAmount() {
            return CONFIG.DRINK_AMOUNT.get();
        }

        public static float drinkSaturation() {
            double hydration = CONFIG.DRINK_SATURATION.get();

            return (float) hydration;
        }

        public static int effectPotency() {
            return CONFIG.EFFECT_POTENCY.get();
        }

        public static int effectDuration() {
            return CONFIG.EFFECT_DURATION.get();
        }

        public static float effectChance() {
            double chance = CONFIG.EFFECT_CHANCE.get();

            return (float) chance;
        }

    }

}
