package homeostatic.common;

import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import org.slf4j.helpers.MessageFormatter;

public class Translations {

    private static final Joiner LINE_JOINER = Joiner.on("\n");
    private static final Map<String, String> translations = Maps.newHashMap();

    static {
        translations.put("usefahrenheit.title", "Use Fahrenheit");
        translations.put("usefahrenheit", "Set to on/true to use Fahrenheit, otherwise set to off/false to use Celcius.");
        translations.put("showdegreesymbol.title", "Show Degree Symbol");
        translations.put("showdegreesymbol", "Set to on/true to show degree symbol next to temperature value.");
        translations.put("position.title", "Debug Info Position");
        translations.put("position", "Position of debug info.");
        translations.put("debugoffsetx.title", "Debug Text X Offset");
        translations.put("debugoffsetx", "Sets the debug text X offset");
        translations.put("debugoffsety.title", "Debug Text Y Offset");
        translations.put("debugoffsety", "Sets the debug text Y offset");
        translations.put("scale.title", "Text Scale");
        translations.put("scale", "The size of the text info (multiplier)");
        translations.put("temperaturecolorrange.title", "Temperature Color Range");
        translations.put("temperaturecolorrange", "Temperature color range (Format (cold->hot): #3ab3da->#f9801d)");
        translations.put("temperaturehudoption.title", "Temperature HUD Option");
        translations.put("temperaturehudoption", "Select which hud element to display for body and area temperature.");
        translations.put("thermometerposition.title", "Thermometer Position");
        translations.put("thermometerposition", "Position of the RIGHT_THERMOMETER HUD if enabled.");
        translations.put("thermometeroffsetx.title", "Thermometer X Offset");
        translations.put("thermometeroffsetx", "RIGHT_THERMOMETER HUD X offset");
        translations.put("thermometeroffsety.title", "Thermometer Y Offset");
        translations.put("thermometeroffsety", "RIGHT_THERMOMETER HUD Y offset");
        translations.put("thermometertextoffsety.title", "Thermometer Text Y Offset");
        translations.put("thermometertextoffsety", "RIGHT_THERMOMETER HUD Y offset");
        translations.put("showthermometerratechangesymbols.title", "Show Thermometer Rate Change Symbols");
        translations.put("showthermometerratechangesymbols", "Show rate change symbols to left/right of thermometer. Left is core temp, right is skin temperature.");
        translations.put("globeposition.title", "Globe Position");
        translations.put("globeposition", "Position of the CENTER_GLOBE HUD if enabled.");
        translations.put("globeoffsetx.title", "Globe X Offset");
        translations.put("globeoffsetx", "CENTER_GLOBE HUD X offset");
        translations.put("globeoffsety.title", "Globe Y Offset");
        translations.put("globeoffsety", "CENTER_GLOBE HUD Y offset");
        translations.put("globetextoffsety.title", "Globe Text Y Offset");
        translations.put("globetextoffsety", "CENTER_GLOBE HUD Y offset");
        translations.put("forcewaterbarposition.title", "Force Water Bar Position");
        translations.put("forcewaterbarposition", "Set to true/on to force position of the water bar.");
        translations.put("waterbarposition.title", "Water Bar Position");
        translations.put("waterbarposition", "Position of the Water Bar HUD if forceWaterBarPosition is true.");
        translations.put("waterbaroffsetx.title", "Water Bar X Offset");
        translations.put("waterbaroffsetx", "Water Bar HUD X offset");
        translations.put("waterbaroffsety.title", "Water Bar HUD Y Offset");
        translations.put("waterbaroffsety", "Water Bar HUD Y offset");
        translations.put("debugenabled.title", "Debug Enabled");
        translations.put("debugenabled", "Set to on/true to show temperature debug info.");
        translations.put("showtemperaturevalues.title", "Show Temperature Values");
        translations.put("showtemperaturevalues", "Set to on/true to show temperature values in HUD.");
        translations.put("requirethermometer.title", "Require Thermometer");
        translations.put("requirethermometer", "Set to on/true to require thermometer helmet enhancement to display temperature values.");
        translations.put("randomwaterloss.title", "Random Water Loss");
        translations.put("randomwaterloss", "Water loss speed when not sweating. Increase to make water loss more prevalent.");
        translations.put("radiationreductionpercent.title", "Radiation Reduction Percent");
        translations.put("radiationreductionpercent", joiner(
            "Percentage of radiation reduced when 'Radiation Protection' is added to an armor piece.",
            "For example, 0.25 with four pieces will be 100%. 1.0 will be 100% for one piece.",
            "Default gives 80% for four pieces."
        ));
    }

    public static String get(String key) {
        return translations.getOrDefault(key, key);
    }

    public static String get(String key, String... values) {
        return MessageFormatter.arrayFormat(translations.getOrDefault(key, key), values).getMessage();
    }

    private static String joiner(String... string) {
        return LINE_JOINER.join(string);
    }

}
