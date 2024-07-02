package homeostatic;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.illusivesoulworks.spectrelib.config.SpectreConfig;
import com.illusivesoulworks.spectrelib.config.SpectreConfigLoader;

import net.minecraft.resources.ResourceLocation;

import homeostatic.common.biome.BiomeRegistry;
import homeostatic.common.damagesource.HomeostaticDamageTypes;
import homeostatic.config.ConfigHandler;
import homeostatic.platform.Services;

public class Homeostatic {

    public static final String MODID = "homeostatic";
    public static final String MOD_NAME = "Homeostatic";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Random RANDOM = new Random();
    public static boolean DATA_GEN = System.getenv("DATA_GEN") != null && System.getenv("DATA_GEN").contains("all");

    public static void init() {
        BiomeRegistry.init();
        HomeostaticDamageTypes.init();
    }
   
    public static void initConfig() {
        SpectreConfig commonConfig = SpectreConfigLoader.add(SpectreConfig.Type.COMMON, ConfigHandler.COMMON_SPEC, MODID);

        if (Services.PLATFORM.isPhysicalClient()) {
            SpectreConfigLoader.add(SpectreConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC, MODID);
            commonConfig.addLoadListener((config, flag) -> ConfigHandler.Client.init());
        }
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

}
