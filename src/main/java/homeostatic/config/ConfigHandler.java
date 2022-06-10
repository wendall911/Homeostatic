package homeostatic.config;

import java.awt.Color;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import org.jetbrains.annotations.NotNull;

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
        private static Color temperatureColorCold = ColorHelper.decode("#3ab3da");
        private static Color temperatureColorHot = ColorHelper.decode("#f9801d");
        private static final Predicate<Object> hexRangeValidator = s -> s instanceof String
                && ((String) s).matches("#[a-zA-Z\\d]{6}->#[a-zA-Z\\d]{6}");

        public final BooleanValue enabled;
        public final BooleanValue useFahrenheit;
        public final ConfigValue<String> position;
        public final IntValue offsetX;
        public final IntValue offsetY;
        public final DoubleValue scale;
        public final ConfigValue<String> temperatureColorRange;

        static {
            Pair<Client,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);

            CONFIG_SPEC = specPair.getRight();
            CONFIG = specPair.getLeft();
        }

        Client(ForgeConfigSpec.@NotNull Builder builder) {
            enabled = builder
                .comment("Show temperature debug info.")
                .define("enabled", true);
            useFahrenheit = builder
                .comment("Use Fahrenheit, otherwise use Celcius.")
                .define("useFahrenheit", true);
            position = builder
                .comment("Position of debug info, one of: " + positions)
                .defineInList("position", "TOPRIGHT", positions);
            offsetX = builder
                .comment("X offset")
                .defineInRange("offsetX", 3, -100, 100);
            offsetY = builder
                .comment("Y offset")
                .defineInRange("offsetY", 3, -100, 100);
            scale = builder
                .comment("The size of the text info (multiplier)")
                .defineInRange("scale", 0.5, 0.5, 2.0);
            temperatureColorRange = builder
                .comment("Temperature color range (Format (cold->hot): #3ab3da->#f9801d)")
                .define("temperatureColorRange", "#3ab3da->#f9801d", hexRangeValidator);
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

        public static Color temperatureColorCold() {
            return temperatureColorCold;
        }

        public static Color temperatureColorHot() {
            return temperatureColorHot;
        }

        public static void init() {
            String[] temperatureColors = CONFIG.temperatureColorRange.get().split("->");

            temperatureColorCold = ColorHelper.decode(temperatureColors[0]);
            temperatureColorHot = ColorHelper.decode(temperatureColors[1]);
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
                .defineInRange("EFFECT_POTENCY", 45, 1, 255);
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
