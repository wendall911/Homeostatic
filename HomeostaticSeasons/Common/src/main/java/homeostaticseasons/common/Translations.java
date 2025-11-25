package homeostaticseasons.common;

import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

public class Translations {

    private static final Joiner LINE_JOINER = Joiner.on("\n");
    private static final Map<String, String> translations = Maps.newHashMap();

    static {
        translations.put("visuals.title", "Visual Settings");
        translations.put("visuals", "All settings related to visual effects.");
        translations.put("changefoliagecolor.title", "Change Foliage Color");
        translations.put("changefoliagecolor", "Whether or not the foliage color changes based on the current season.");
        translations.put("changegrasscolor.title", "Change Grass Color");
        translations.put("changegrasscolor", "Whether or not the grass color changes based on the current season.");
        translations.put("changebirchcolor.title", "Change Birch Color");
        translations.put("changebirchcolor", "Whether or not the birch leaves color changes based on the current season.");
        translations.put("seasons.title", "Seasons Settings");
        translations.put("seasons", "All settings related to the seasons system.");
        translations.put("whitelistdimensions.title", "Whitelisted Dimensions");
        translations.put("whitelistdimensions", "Dimensions where seasons are active.");
        translations.put("seasonchangemethod.title", "Season Change Method");
        translations.put("seasonchangemethod", joiner(
            "The method by which seasons change.",
            "Realtime: Seasons change based on the real-world time.",
            "Fixed: Is always set to the configuration fixed season.",
            "Configured: Seasons change based on the configured term length."
        ));
        translations.put("hemisphere.title", "Hemisphere");
        translations.put("hemisphere", joiner(
            "The hemisphere in which the seasons progress.",
            "Northern: Spring starts in March.",
            "Southern: Spring starts in September."
        ));
        translations.put("fixedseason.title", "Fixed Season");
        translations.put("fixedseason", "The season that is always active when the season change method is set to Fixed.");
        translations.put("startingseason.title", "Starting Season");
        translations.put("startingseason", "The season that is active when a world is created.");
        translations.put("earlyspringdayslength.title", "Early Spring Subseason Length");
        translations.put("earlyspringdayslength", "The length of Early Spring subseason in Minecraft days.");
        translations.put("midspringdayslength.title", "Mid Spring Subseason Length");
        translations.put("midspringdayslength", "The length of Mid Spring subseason in Minecraft days.");
        translations.put("latespringdayslength.title", "Late Spring Subseason Length");
        translations.put("latespringdayslength", "The length of Late Spring subseason in Minecraft days.");
        translations.put("earlysummerdayslength.title", "Early Summer Subseason Length");
        translations.put("earlysummerdayslength", "The length of Early Summer subseason in Minecraft days.");
        translations.put("midsummerdayslength.title", "Mid Summer Subseason Length");
        translations.put("midsummerdayslength", "The length of Mid Summer subseason in Minecraft days.");
        translations.put("latesummerdayslength.title", "Late Summer Subseason Length");
        translations.put("latesummerdayslength", "The length of Late Summer subseason in Minecraft days.");
        translations.put("earlyautumndayslength.title", "Early Autumn Subseason Length");
        translations.put("earlyautumndayslength", "The length of Early Autumn subseason in Minecraft days.");
        translations.put("midautumndayslength.title", "Mid Autumn Subseason Length");
        translations.put("midautumndayslength", "The length of Mid Autumn subseason in Minecraft days.");
        translations.put("lateautumndayslength.title", "Late Autumn Subseason Length");
        translations.put("lateautumndayslength", "The length of Late Autumn subseason in Minecraft days.");
        translations.put("earlywinterdayslength.title", "Early Winter Subseason Length");
        translations.put("earlywinterdayslength", "The length of Early Winter subseason in Minecraft days.");
        translations.put("midwinterdayslength.title", "Mid Winter Subseason Length");
        translations.put("midwinterdayslength", "The length of Mid Winter subseason in Minecraft days.");
        translations.put("latewinterdayslength.title", "Late Winter Subseason Length");
        translations.put("latewinterdayslength", "The length of Late Winter subseason in Minecraft days.");
        translations.put("weather.title", "Weather Settings");
        translations.put("weather", "All settings related to weather effects.");
        translations.put("seasonalsnowreplacevegetation.title", "Seasonal Snow Replaces Vegetation");
        translations.put("seasonalsnowreplacevegetation", joiner(
            "Whether or not snow layers placed by seasonal snow replace vegetation such as tall grass and flowers.",
            "Disable this if you want seasonal snow to behave like normal snow."
        ));
    }

    public static String get(String key) {
        return translations.getOrDefault(key, key);
    }

    private static String joiner(String... string) {
        return LINE_JOINER.join(string);
    }

}
