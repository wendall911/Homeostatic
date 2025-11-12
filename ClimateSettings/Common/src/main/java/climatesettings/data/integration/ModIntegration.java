package climatesettings.data.integration;

import net.minecraft.resources.ResourceLocation;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.loc;

public class ModIntegration {

    public static final String ARS_MODID = "ars_nouveau";
    public static final String ARSE_MODID = "ars_elemental";
    public static final String ES_MODID = "eternal_starlight";
    public static final String BOP_MODID = "biomesoplenty";
    public static final String BYG_MODID = "biomeswevegone";
    public static final String TL_MODID = "terralith";
    public static final String TF_MODID = "twilightforest";
    public static final String REGIONS_MODID = "regions_unexplored";
    public static final String UG_MODID = "undergarden";

    public static ResourceLocation arsLoc(String path) {
        return loc(ARS_MODID, path);
    }

    public static ResourceLocation arseLoc(String path) {
        return loc(ARSE_MODID, path);
    }

    public static ResourceLocation esLoc(String name) {
        return loc(ES_MODID, name);
    }

    public static ResourceLocation bopLoc(String path) {
        return loc(BOP_MODID, path);
    }

    public static ResourceLocation bygLoc(String path) {
        return loc(BYG_MODID, path);
    }

    public static ResourceLocation regionsLoc(String name) {
        return loc(REGIONS_MODID, name);
    }
    public static ResourceLocation terralithLoc(String name) {
        return loc(TL_MODID, name);
    }
    public static ResourceLocation tfLoc(String path) {
        return loc(TF_MODID, path);
    }

    public static ResourceLocation ugLoc(String name) {
        return loc(UG_MODID, name);
    }

}
