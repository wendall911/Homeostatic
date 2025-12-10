package climatesettings;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.loc;

public class ClimateSettings {

    public static final String MODID = "climatesettings";
    public static final String MOD_NAME = "ClimateSettings";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static Identifier prefix(String path) {
        return loc(MODID, path);
    }

}
