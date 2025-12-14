package homeostatic.data.integration;

import net.minecraft.resources.Identifier;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.loc;

public class ModIntegration {

    public static final String ALEX_MODID = "alexsmobs";
    public static final String ALLOY_MODID = "alloy-forgery";
    public static final String ARSE_MODID = "ars_elemental";
    public static final String ARS_MODID = "ars_nouveau";
    public static final String BOP_MODID = "biomesoplenty";
    public static final String BYG_MODID = "biomeswevegone";
    public static final String CREATE_MODID = "create";
    public static final String CC_MODID = "createcafe";
    public static final String CROPTOPIA_MODID = "croptopia";
    public static final String ECLIPTIC_MODID = "eclipticseasons";
    public static final String ECO_MODID = "ecologics";
    public static final String ES_MODID = "eternal_starlight";
    public static final String FD_MODID = "farmersdelight";
    public static final String FT_MODID = "fruitfulfun";
    public static final String IE_MODID = "immersiveengineering";
    public static final String HS_MODID = "homeostaticseasons";
    public static final String KOBOLDS_MODID = "kobolds";
    public static final String LD_BAKERY_MODID = "bakery";
    public static final String LD_BEACH_MODID = "beachparty";
    public static final String LD_BLOOMING_MODID = "bloomingnature";
    public static final String LD_BREWERY_MODID = "brewery";
    public static final String LD_CANDLELIGHT_MODID = "candlelight";
    public static final String LD_FARM_MODID = "farm_and_charm";
    public static final String LD_HERBAL_MODID = "herbalbrews";
    public static final String LD_MEADOW_MODID = "meadow";
    public static final String LD_VINERY_MODID = "vinery";
    public static final String LD_WILDER_MODID = "wildernature";
    public static final String LMBA_MODID = "leavemybarsalone";
    public static final String MC_MODID = "minecraft";
    public static final String MORE_FOOD_MODID = "more_food";
    public static final String MORECRAFT_MODID = "morecraft";
    public static final String PHC_CORE_MODID = "pamhc2foodcore";
    public static final String PHC_CROPS_MODID = "pamhc2crops";
    public static final String PHC_FOOD_EXTENDED_MODID = "pamhc2foodextended";
    public static final String PW_MODID = "primalwinter";
    public static final String REGIONS_MODID = "regions_unexplored";
    public static final String SEASONS_MODID = "seasons";
    public static final String SPROUT_MODID = "sprout";
    public static final String SCUBA_GEAR_MODID = "scuba_gear";
    public static final String SK_MODID = "sewingkit";
    public static final String SS_MODID = "sereneseasons";
    public static final String TCON_MODID = "tconstruct";
    public static final String TL_MODID = "terralith";
    public static final String TF_MODID = "twilightforest";
    public static final String UG_MODID = "undergarden";
    public static final String XERCA_MODID = "xercamod";
    public static final String VAMPIRISM_MODID = "vampirism";

    public static Identifier alexLoc(String path) {
        return loc(ALEX_MODID, path);
    }

    public static Identifier arseLoc(String path) {
        return loc(ARSE_MODID, path);
    }

    public static Identifier arsLoc(String path) {
        return loc(ARS_MODID, path);
    }

    public static Identifier bopLoc(String path) {
        return loc(BOP_MODID, path);
    }

    public static Identifier bygLoc(String path) {
        return loc(BYG_MODID, path);
    }

    public static Identifier ccLoc(String path) {
        return loc(CC_MODID, path);
    }

    public static Identifier croptopiaLoc(String path) {
        return loc(CROPTOPIA_MODID, path);
    }

    public static Identifier ecoLoc(String path) {
        return loc(ECO_MODID, path);
    }

    public static Identifier fdLoc(String path) {
        return loc(FD_MODID, path);
    }

    public static Identifier ftLoc(String path) {
        return loc(FT_MODID, path);
    }

    public static Identifier ieLoc(String path) {
        return loc(IE_MODID, path);
    }

    public static Identifier mcLoc(String path) {
        return loc(MC_MODID, path);
    }

    public static Identifier sproutLoc(String path) {
        return loc(SPROUT_MODID, path);
    }

    public static Identifier morecraftLoc(String path) {
        return loc(MORECRAFT_MODID, path);
    }

    public static Identifier xercaLoc(String path) {
        return loc(XERCA_MODID, path);
    }

    public static Identifier scubaLoc(String path) {
        return loc(SCUBA_GEAR_MODID, path);
    }

    public static Identifier koboldsLoc(String path) {
        return loc(KOBOLDS_MODID, path);
    }

    public static Identifier tfLoc(String path) {
        return loc(TF_MODID, path);
    }

    public static Identifier createLoc(String path) {
        return loc(CREATE_MODID, path);
    }

    public static Identifier tconLoc(String path) {
        return loc(TCON_MODID, path);
    }

    public static Identifier skLoc(String name) {
        return loc(SK_MODID, name);
    }

    public static Identifier mfLoc(String name) {
        return loc(MORE_FOOD_MODID, name);
    }

    public static Identifier phcLoc(String name) {
        return loc(PHC_CORE_MODID, name);
    }

    public static Identifier pcropsLoc(String name) {
        return loc(PHC_CROPS_MODID, name);
    }

    public static Identifier pheLoc(String name) {
        return loc(PHC_FOOD_EXTENDED_MODID, name);
    }

    public static Identifier esLoc(String name) {
        return loc(ES_MODID, name);
    }

    public static Identifier terralithLoc(String name) {
        return loc(TL_MODID, name);
    }

    public static Identifier ugLoc(String name) {
        return loc(UG_MODID, name);
    }

    public static Identifier regionsLoc(String name) {
        return loc(REGIONS_MODID, name);
    }

    public static Identifier ldBakeryLoc(String name) {
        return loc(LD_BAKERY_MODID, name);
    }

    public static Identifier ldBeachLoc(String name) {
        return loc(LD_BEACH_MODID, name);
    }

    public static Identifier ldBloomingLoc(String name) {
        return loc(LD_BLOOMING_MODID, name);
    }

    public static Identifier ldBreweryLoc(String name) {
        return loc(LD_BREWERY_MODID, name);
    }

    public static Identifier ldCandlelightLoc(String name) {
        return loc(LD_CANDLELIGHT_MODID, name);
    }

    public static Identifier ldFarmLoc(String name) {
        return loc(LD_FARM_MODID, name);
    }

    public static Identifier ldHerbalLoc(String name) {
        return loc(LD_HERBAL_MODID, name);
    }

    public static Identifier ldMeadowLoc(String name) {
        return loc(LD_MEADOW_MODID, name);
    }

    public static Identifier ldVineryLoc(String name) {
        return loc(LD_VINERY_MODID, name);
    }

    public static Identifier ldWilderLoc(String name) {
        return loc(LD_WILDER_MODID, name);
    }

    public static Identifier alloyForgeryLoc(String path) {
        return loc(ALLOY_MODID, path);
    }

}
