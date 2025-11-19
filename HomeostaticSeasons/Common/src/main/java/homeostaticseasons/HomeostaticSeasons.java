package homeostaticseasons;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import technology.roughness.whitenoise.config.WhiteNoiseConfig;
import technology.roughness.whitenoise.config.WhiteNoiseConfig.Type;
import technology.roughness.whitenoise.config.WhiteNoiseConfigLoader;
import technology.roughness.whitenoise.platform.Services;

import homeostaticseasons.config.ConfigHandler;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.loc;

public class HomeostaticSeasons {

    public static final String MODID = "homeostaticseasons";
    public static final String MOD_NAME = "HomeostaticSeasons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
    }

    public static void initConfig() {
        WhiteNoiseConfig commonConfig = WhiteNoiseConfigLoader.add(Type.COMMON, ConfigHandler.COMMON_SPEC, MODID);
        commonConfig.addLoadListener((config, flag) -> {
            ConfigHandler.initCommon();
        });

        if (Services.PLATFORM.isPhysicalClient()) {
            WhiteNoiseConfigLoader.add(WhiteNoiseConfig.Type.CLIENT, ConfigHandler.CLIENT_SPEC, MODID);
        }
    }

    public static ResourceLocation prefix(String path) {
        return loc(MODID, path);
    }

}
