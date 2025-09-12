package homeostatic.util;

import net.minecraft.resources.ResourceLocation;

import homeostatic.Homeostatic;

public class ResourceLocationHelper extends technology.roughness.whitenoise.util.ResourceLocationHelper {

    public static ResourceLocation loc(String path) {
        return loc(Homeostatic.MODID, path);
    }

}
