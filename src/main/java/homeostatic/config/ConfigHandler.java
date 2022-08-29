package homeostatic.config;

import java.awt.Color;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.List;
import java.util.function.Supplier;

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
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Common.CONFIG_SPEC);
    }

    public static final class Client {

        public static final ForgeConfigSpec CONFIG_SPEC;
        private static final Client CONFIG;

        private static final List<String> positions = Arrays.asList("TOPLEFT", "TOPRIGHT", "BOTTOMLEFT", "BOTTOMRIGHT");
        private static Color temperatureColorCold = ColorHelper.decode("#3ab3da");
        private static Color temperatureColorHot = ColorHelper.decode("#f9801d");
        private static final Predicate<Object> hexRangeValidator = s -> s instanceof String
                && ((String) s).matches("#[a-zA-Z\\d]{6}->#[a-zA-Z\\d]{6}");

        public final BooleanValue debugEnabled;
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
            debugEnabled = builder
                .comment("Show temperature debug info.")
                .define("debugEnabled", false);
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

        public static boolean debugEnabled() {
            return CONFIG.debugEnabled.get();
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

    public static final class Common {
        public static final ForgeConfigSpec CONFIG_SPEC;
        private static final Common CONFIG;

        private static IntValue DRINK_AMOUNT;
        private static DoubleValue DRINK_SATURATION;
        private static IntValue EFFECT_POTENCY;
        private static IntValue EFFECT_DURATION;
        private static DoubleValue EFFECT_CHANCE;
        private static final List<String> RADIATION_BLOCKS_LIST = List.of("radiation_blocks");
        private static final String[] radiationBlocksStrings = new String[] {
            "minecraft:soul_campfire-8325",
            "minecraft:campfire-5550",
            "minecraft:soul_fire-1950",
            "minecraft:blast_furnace-1800",
            "minecraft:lava-1550",
            "minecraft:fire-1300",
            "minecraft:furnace-1300",
            "minecraft:magma_block-1200",
            "minecraft:smoker-1100",
            "minecraft:soul_torch-525",
            "minecraft:soul_wall_torch-525",
            "minecraft:soul_lantern-525",
            "minecraft:nether_portal-350",
            "minecraft:torch-350",
            "minecraft:wall_torch-350",
            "minecraft:lantern-350"
        };
        private static final Predicate<Object> radiationBlocksValidator = s -> s instanceof String
                && ((String) s).matches("[a-z]+[:]{1}[a-z_]+[-]{1}[0-9]{3,4}+");
        private final ConfigValue<List<? extends String>> RADIATION_BLOCKS;

        static {
            Pair<Common,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);

            CONFIG_SPEC = specPair.getRight();
            CONFIG = specPair.getLeft();
        }

        Common(ForgeConfigSpec.Builder builder) {
            DRINK_AMOUNT = builder
                .comment("Amount of water that can drank per interaction in half shanks. Purified water is 3X. Default 1")
                .defineInRange("DRINK_AMOUNT", 1, 1, 20);
            DRINK_SATURATION = builder
                .comment("Amount of saturation gained per drink of purified water. Default 0.7")
                .defineInRange("DRINK_SATURATION", 0.7, 0.0, 20.0);
            EFFECT_POTENCY = builder
                .comment("Potency of thirst effect when drinking from an open water source. Default 25")
                .defineInRange("EFFECT_POTENCY", 45, 1, 255);
            EFFECT_DURATION = builder
                .comment("Duration of thirst effect in ticks (20/second). Default 200")
                .defineInRange("EFFECT_DURATION", 200, 1, 6000);
            EFFECT_CHANCE = builder
                .comment("Chance to give thirst status effect. Default 0.2")
                .defineInRange("EFFECT_CHANCE", 0.2, 0.0, 1.0);
            RADIATION_BLOCKS = builder
                .comment("List of radiation generating blocks and the radiation output amount. Format modid:item-amount"
                        + "[\"" + String.join("\", \"", radiationBlocksStrings) + "\"]")
                .defineListAllowEmpty(RADIATION_BLOCKS_LIST, getFields(radiationBlocksStrings), radiationBlocksValidator);
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

        public static List<String> getRadiationBlocksData() {
            List<String> blocks = (List<String>) CONFIG.RADIATION_BLOCKS.get();

            return blocks;
        }

        private static Supplier<List<? extends String>> getFields(String[] strings) {
            return () -> Arrays.asList(strings);
        }

    }

}
