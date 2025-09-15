package homeostatic;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.ResourceLocation;

import technology.roughness.whitenoise.config.WhiteNoiseConfig;
import technology.roughness.whitenoise.config.WhiteNoiseConfigLoader;

import technology.roughness.whitenoise.platform.Services;

import homeostatic.common.damagesource.HomeostaticDamageTypes;
import homeostatic.config.ConfigHandler;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.loc;

public class Homeostatic {

    public static final String MODID = "homeostatic";
    public static final String MOD_NAME = "Homeostatic";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Random RANDOM = new Random();
    public static boolean DATA_GEN = System.getenv("DATA_GEN") != null && System.getenv("DATA_GEN").contains("all");

    public static void init() {
        HomeostaticDamageTypes.init();
    }
   
    public static void initConfig() {
        WhiteNoiseConfigLoader.add(WhiteNoiseConfig.Type.COMMON, ConfigHandler.COMMON_SPEC, MODID);

        if (Services.PLATFORM.isPhysicalClient()) {
            WhiteNoiseConfig clientConfig = WhiteNoiseConfigLoader.add(WhiteNoiseConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC, MODID);
            clientConfig.addLoadListener((config, flag) -> ConfigHandler.Client.init());
        }
    }

    public static ResourceLocation prefix(String path) {
        return loc(MODID, path);
    }

}
