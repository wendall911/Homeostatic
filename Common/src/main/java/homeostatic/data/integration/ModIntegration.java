package homeostatic.data.integration;

import net.minecraft.resources.ResourceLocation;

public class ModIntegration {

    public static final String ALEX_MODID = "alexsmobs";
    public static final String ARSE_MODID = "ars_elemental";
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
    public static final String MORE_FOOD_FABRIC_MODID = "morefood";
    public static final String MORECRAFT_MODID = "morecraft";
    public static final String PATCHOULI_MODID = "patchouli";
    public static final String PHC_CORE_MODID = "pamhc2foodcore";
    public static final String PHC_CROPS_MODID = "pamhc2crops";
    public static final String PHC_FOOD_EXTENDED_MODID = "pamhc2foodextended";
    public static final String PW_MODID = "primalwinter";
    public static final String SEASONS_MODID = "seasons";
    public static final String SPROUT_MODID = "sprout";
    public static final String SCUBA_GEAR_MODID = "scuba_gear";
    public static final String SK_MODID = "sewingkit";
    public static final String SS_MODID = "sereneseasons";
    public static final String TCON_MODID = "tconstruct";
    public static final String TL_MODID = "terralith";
    public static final String TF_MODID = "twilightforest";
    public static final String UG_MODID = "undergarden";
    public static final String VEGGIE_MODID = "veggie_way";
    public static final String XERCA_MODID = "xercamod";

    public static ResourceLocation alexLoc(String path) {
        return new ResourceLocation(ALEX_MODID, path);
    }

    public static ResourceLocation arseLoc(String path) {
        return new ResourceLocation(ARSE_MODID, path);
    }

    public static ResourceLocation arsLoc(String path) {
        return new ResourceLocation(ARS_MODID, path);
    }

    public static ResourceLocation bopLoc(String path) {
        return new ResourceLocation(BOP_MODID, path);
    }

    public static ResourceLocation bygLoc(String path) {
        return new ResourceLocation(BYG_MODID, path);
    }

    public static ResourceLocation ccLoc(String path) {
        return new ResourceLocation(CC_MODID, path);
    }

    public static ResourceLocation croptopiaLoc(String path) {
        return new ResourceLocation(CROPTOPIA_MODID, path);
    }

    public static ResourceLocation ecoLoc(String path) {
        return new ResourceLocation(ECO_MODID, path);
    }

    public static ResourceLocation fdLoc(String path) {
        return new ResourceLocation(FD_MODID, path);
    }

    public static ResourceLocation ftLoc(String path) {
        return new ResourceLocation(FT_MODID, path);
    }

    public static ResourceLocation ieLoc(String path) {
        return new ResourceLocation(IE_MODID, path);
    }

    public static ResourceLocation mcLoc(String path) {
        return new ResourceLocation(MC_MODID, path);
    }

    public static ResourceLocation sproutLoc(String path) {
        return new ResourceLocation(SPROUT_MODID, path);
    }

    public static ResourceLocation morecraftLoc(String path) {
        return new ResourceLocation(MORECRAFT_MODID, path);
    }

    public static ResourceLocation xercaLoc(String path) {
        return new ResourceLocation(XERCA_MODID, path);
    }

    public static ResourceLocation scubaLoc(String path) {
        return new ResourceLocation(SCUBA_GEAR_MODID, path);
    }

    public static ResourceLocation koboldsLoc(String path) {
        return new ResourceLocation(KOBOLDS_MODID, path);
    }

    public static ResourceLocation tfLoc(String path) {
        return new ResourceLocation(TF_MODID, path);
    }

    public static ResourceLocation createLoc(String path) {
        return new ResourceLocation(CREATE_MODID, path);
    }

    public static ResourceLocation tconLoc(String path) {
        return new ResourceLocation(TCON_MODID, path);
    }

    public static ResourceLocation skLoc(String name) {
        return new ResourceLocation(SK_MODID, name);
    }

    public static ResourceLocation mfLoc(String name) {
        return new ResourceLocation(MORE_FOOD_MODID, name);
    }

    public static ResourceLocation mffLoc(String name) {
        return new ResourceLocation(MORE_FOOD_FABRIC_MODID, name);
    }

    public static ResourceLocation phcLoc(String name) {
        return new ResourceLocation(PHC_CORE_MODID, name);
    }

    public static ResourceLocation pcropsLoc(String name) {
        return new ResourceLocation(PHC_CROPS_MODID, name);
    }

    public static ResourceLocation pheLoc(String name) {
        return new ResourceLocation(PHC_FOOD_EXTENDED_MODID, name);
    }

    public static ResourceLocation veggieLoc(String name) {
        return new ResourceLocation(VEGGIE_MODID, name);
    }

    public static ResourceLocation terralithLoc(String name) {
        return new ResourceLocation(TL_MODID, name);
    }

    public static ResourceLocation ugLoc(String name) {
        return new ResourceLocation(UG_MODID, name);
    }

}
