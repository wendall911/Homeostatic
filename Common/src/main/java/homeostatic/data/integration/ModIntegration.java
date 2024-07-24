package homeostatic.data.integration;

import net.minecraft.resources.ResourceLocation;

public class ModIntegration {

    public static final String ALEX_MODID = "alexsmobs";
    public static final String ARS_MODID = "ars_nouveau";
    public static final String BOP_MODID = "biomesoplenty";
    public static final String BYG_MODID = "byg";
    public static final String CREATE_MODID = "create";
    public static final String CC_MODID = "createcafe";
    public static final String CROPTOPIA_MODID = "croptopia";
    public static final String ECO_MODID = "ecologics";
    public static final String FD_MODID = "farmersdelight";
    public static final String FT_MODID = "fruitfulfun";
    public static final String IE_MODID = "immersiveengineering";
    public static final String KOBOLDS_MODID = "kobolds";
    public static final String MC_MODID = "minecraft";
    public static final String MORE_FOOD_MODID = "more_food";
    public static final String MORECRAFT_MODID = "morecraft";
    public static final String PATCHOULI_MODID = "patchouli";
    public static final String PW_MODID = "primalwinter";
    public static final String SEASONS_MODID = "seasons";
    public static final String SPROUT_MODID = "sprout";
    public static final String SCUBA_GEAR_MODID = "scuba_gear";
    public static final String SK_MODID = "sewingkit";
    public static final String SS_MODID = "sereneseasons";
    public static final String TCON_MODID = "tconstruct";
    public static final String TF_MODID = "twilightforest";
    public static final String XERCA_MODID = "xercamod";

    public static ResourceLocation alexLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(ALEX_MODID, path);
    }

    public static ResourceLocation arsLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(ARS_MODID, path);
    }

    public static ResourceLocation bopLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(BOP_MODID, path);
    }

    public static ResourceLocation bygLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(BYG_MODID, path);
    }

    public static ResourceLocation ccLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(CC_MODID, path);
    }

    public static ResourceLocation croptopiaLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(CROPTOPIA_MODID, path);
    }

    public static ResourceLocation ecoLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(ECO_MODID, path);
    }

    public static ResourceLocation fdLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(FD_MODID, path);
    }

    public static ResourceLocation ftLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(FT_MODID, path);
    }

    public static ResourceLocation ieLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(IE_MODID, path);
    }

    public static ResourceLocation mcLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MC_MODID, path);
    }

    public static ResourceLocation sproutLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(SPROUT_MODID, path);
    }

    public static ResourceLocation morecraftLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MORECRAFT_MODID, path);
    }

    public static ResourceLocation xercaLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(XERCA_MODID, path);
    }

    public static ResourceLocation scubaLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(SCUBA_GEAR_MODID, path);
    }

    public static ResourceLocation koboldsLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(KOBOLDS_MODID, path);
    }

    public static ResourceLocation tfLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(TF_MODID, path);
    }

    public static ResourceLocation createLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(CREATE_MODID, path);
    }

    public static ResourceLocation tconLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(TCON_MODID, path);
    }

    public static ResourceLocation skLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(SK_MODID, name);
    }

    public static ResourceLocation mfLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(MORE_FOOD_MODID, name);
    }

}
