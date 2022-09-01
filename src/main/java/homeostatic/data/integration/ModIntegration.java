package homeostatic.data.integration;

import net.minecraft.resources.ResourceLocation;

public class ModIntegration {

    public static final String BYG_MODID = "byg";
    public static final String CROPTOPIA_MODID = "croptopia";
    public static final String FD_MODID = "farmersdelight";
    public static final String MC_MODID = "minecraft";
    public static final String MORECRAFT_MODID = "morecraft";
    public static final String XERCA_MODID = "xercamod";

    public static ResourceLocation bygLoc(String path) {
        return new ResourceLocation(BYG_MODID, path);
    }

    public static ResourceLocation croptopiaLoc(String path) {
        return new ResourceLocation(CROPTOPIA_MODID, path);
    }

    public static ResourceLocation fdLoc(String path) {
        return new ResourceLocation(FD_MODID, path);
    }

    public static ResourceLocation mcLoc(String path) {
        return new ResourceLocation(MC_MODID, path);
    }

    public static ResourceLocation morecraftLoc(String path) {
        return new ResourceLocation(MORECRAFT_MODID, path);
    }

    public static ResourceLocation xercaLoc(String path) {
        return new ResourceLocation(XERCA_MODID, path);
    }

}
