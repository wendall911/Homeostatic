package homeostatic.data;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

import homeostatic.Homeostatic;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(PackOutput packOutput) {
        super(packOutput, Homeostatic.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(Homeostatic.MODID + ".items", "Homeostatic");
        addAttack("hasHyperthermia", "Hyperthermia has claimed poor %1$s");
        addAttack("hasHyperthermia.player", "Hyperthermia has claimed poor %1$s whilst fighting %2$s");
        addAttack("isScalding", "%1$s died of scalding");
        addAttack("isScalding.player", "%1$s died of scalding whilst fighting %2$s");
        addAttack("hasDehydration", "%1$s turned into dead dried %1$s");
        addAttack("hasDehydration.player", "%1$s turned into dead dried %1$s whilst fighting %2$s");
        addEffect("thirst", "Thirst");
        addTooltip("insulation", "Insulation");
        addTooltip("waterproof", "Waterproof");
        addTooltip("radiation_protection", "Radiation Protection");
        addTooltip("water_container.empty", "Empty");
        addTooltip("thermometer", "Thermovision");
        addItem("book", "Homeostatic Tome");
        addItem("leather_flask", "Leather Flask");
        addItem("purified_water_bucket", "Purified Water Bucket");
        addItem("water_filter", "Water Filter");
        addItem("thermometer", "Thermometer");
        addFluid("purified_water_type", "Purified Water");
        addBookEntry("intro", "Temperature and hydration dynamics that don't defy logic...$(br)" +
                "$(li)The $(l:homeostatic:education/environment)environment$() has temperature that is affected by " +
                    "$(l:https://simple.wikipedia.org/wiki/Thermal_radiation)thermal radiation$(), like sun, lava, " +
                    "campfires, etc.$(li)Body temperature must be maintained or you can get " +
                    "$(l:https://simple.wikipedia.org/wiki/Hyperthermia)hyperthermia$() or " +
                    "$(l:https://simple.wikipedia.org/wiki/Hypothermia)hypothermia$()" +
                "$(li)Hydration is necessary to survive.");
        addBookEntry("subtitle", "About Temperature and Water");
        addBookEntry("gameplay.name", "Gameplay");
        addBookEntry("gameplay.desc", "Gameplay is enhanced through several mechanics." +
                "$(li)Environment Temperature" +
                "$(li)Body Temperature" +
                "$(li2)Wetness" +
                "$(li2)Scalding/Hypothermia" +
                "$(li2)Freezing/Hyperthermia" +
                "$(li2)Simply how the body is reacting to environment temperature." +
                "$(li2)Protect yourself by enhancing your armor. Even just wearing armor helps." +
                "$(li)Hydration" +
                "$(li2)You get thirsty now as well as hungry. Stay hydrated!");
        addBookEntry("gameplay.environment.name", "Environment Temperature");
        addBookEntry("gameplay.environment.title", "Environment °F/C HUD");
        addBookEntry("gameplay.environment.intro", "The arrow above points to the location of the local "
                + "temperature in the HUD. Can be displayed in Fahrenheit or Celsius.");
        addBookEntry("gameplay.environment.details", "There are several factors that are used to calculate " +
                    "the local temperature:$(br)" +
                "$(li)Current and influence from surrounding biomes." +
                "$(li)Sun exposure." +
                "$(li)Heat radiation sources, such as, lava, torches, lit furnaces, etc." +
                "$(li)Elevation. Mountain tops are cooler, underground is more consistent temperature.");
        addBookEntry("gameplay.body_temp.name", "Body Temperature");
        addBookEntry("gameplay.body_temp.title", "Body °F/C HUD");
        addBookEntry("gameplay.body_temp.intro", "The arrow above points to the location of the body " +
                "temperature in the HUD. Can be displayed in Fahrenheit or Celsius.");
        addBookEntry("gameplay.body_temp.details", "There are several factors used to calculate the body " +
                "temperature:$(br)" +
                "$(li)Environment temperature." +
                "$(li)Armor - Details on following pages." +
                "$(li2)$(l:homeostatic:gameplay/body_temp#insulation)Insulation$()" +
                "$(li2)$(l:homeostatic:gameplay/body_temp#radiation_protection)Radiation Resistance$()" +
                "$(li2)$(l:homeostatic:gameplay/body_temp#waterproofing)Waterproofing$()" +
                "$(li)$(l:homeostatic:gameplay/body_temp#wetness)Wetness$()" +
                "$(li2)How wet a player is determines how quickly they can cool down or heat up." +
                "$(li)$(l:homeostatic:gameplay/body_temp#scalding)Scalding$() / $(l:homeostatic:gameplay/body_temp#hyperthermia)Hyperthermia$()" +
                "$(li)$(l:homeostatic:gameplay/body_temp#hypothermia)Freezing / Hypothermia$()");
        addBookEntry("gameplay.body_temp.wetness.title", "Wetness");
        addBookEntry("gameplay.body_temp.wetness.intro", "When wet, droplets will appear as an overlay on the edges " +
                "of the screen as shown in the above image.");
        addBookEntry("gameplay.body_temp.wetness.details", "How wet/dry a player is determines how quickly " +
                "$(l:homeostatic:gameplay/body_temp)body temperature$() lowers. This can be a good or bad thing " +
                "depending on the situation:" +
                "$(li)When hot, being wet or in water can help the player rapidly cool down." +
                "$(li)When cold, being wet can increase the risk of the player freezing to death." +
                "$(li2)Seeking shelter or adding $(l:homeostatic:gameplay/body_temp#waterproofing)Waterproofing$() to " +
                "armor is essential for staying dry.");
        addBookEntry("gameplay.body_temp.scalding.title", "Scalding");
        addBookEntry("gameplay.body_temp.scalding.intro", "Scalding can happen when a player is near a very " +
                "hot source above 140°F or 60°C.");
        addBookEntry("gameplay.body_temp.hyperthermia.title", "Hyperthermia");
        addBookEntry("gameplay.body_temp.hyperthermia.intro", "Hyperthermia can occur when the core " +
                "temperature is above 106°F or 41°C.");
        addBookEntry("gameplay.body_temp.hypothermia.title", "Freezing / Hypothermia");
        addBookEntry("gameplay.body_temp.hypothermia.intro", "Hypothermia can occur when the core " +
                "temperature is below 95°F or 35°C.");
        addBookEntry("gameplay.body_temp.insulation.title", "Insulation");
        addBookEntry("gameplay.body_temp.insulation.text", "Any armor piece can be insulated. The more " +
                "pieces that are insulated, the better the player is insulated. See JEI/REI for recipes.");
        addBookEntry("gameplay.body_temp.remove_insulation.title", "Remove Insulation");
        addBookEntry("gameplay.body_temp.remove_insulation.text", "Insulation can be removed from any armor " +
                "piece. Recipe does not appear in JEI/REI.");
        addBookEntry("gameplay.body_temp.radiation_protection.title", "Radiation Protection");
        addBookEntry("gameplay.body_temp.radiation_protection.text", "Any armor piece can have radiation " +
                "protection added. Add to multiple pieces to achieve maximum protection.$(br)" +
                "Addionally, fire resistance fully protects the player from radiation sources.");
        addBookEntry("gameplay.body_temp.remove_radiation_protection.title", "Remove Radiation Protection");
        addBookEntry("gameplay.body_temp.remove_radiation_protection.text", "Radiation protection can be " +
                "removed from any armor piece. Recipe does not appear in JEI/REI.");
        addBookEntry("gameplay.body_temp.waterproof.title", "Waterproofing");
        addBookEntry("gameplay.body_temp.waterproof.text", "Waterproofing can be added to any armor piece. " +
                "Add to multiple pieces to increase protection.");
        addBookEntry("gameplay.body_temp.remove_waterproof.title", "Waterproofing");
        addBookEntry("gameplay.body_temp.remove_waterproof.text", "Waterproofing can be removed from any armor " +
                "piece. Recipe does not appear in JEI/REI.");
        addBookEntry("gameplay.hydration.name", "Hydration/Thirst");
        addBookEntry("gameplay.hydration.title", "Hydration HUD");
        addBookEntry("gameplay.hydration.intro", "The arrow above points to the hydration bar. " +
                "This works similar to hunger. Brighter highlights indicate saturation level.");
        addBookEntry("gameplay.hydration.details", "There are a few ways to hydrate:$(br)" +
                "$(li)Drink water with bare hands." +
                "$(li2)Crouch and right click while targeting any water block and you can drink water directly. " +
                    "This can cause a negative status effect." +
                "$(li)Obtain a flask. Recipe and details on following pages." +
                "$(li)Eat foods or drinkable items with water content. A small preview will flash over the water bar " +
                "if holding any hydration item.");
        addBookEntry("gameplay.hydration.leather_flask.title", "Leather Flask");
        addBookEntry("gameplay.hydration.leather_flask.text", "Leather flask is critical in environments " +
                "where water is limited. To fill, simply interact with any water source block and it will be \"filled\". " +
                "However, this is dirty water. See following page to purify.");
        addBookEntry("gameplay.hydration.leather_flask.smelting.title", "Leather Flask Purification");
        addBookEntry("gameplay.hydration.leather_flask.smelting.text", "To purify the dirty water in a " +
                "flask, there are several methods: " +
                "$(li)Smelt in a Furnace (shown above)" +
                "$(li)Cook over a Campfire" +
                "$(li)Smoke in a Smoker" +
                "$(li)Combine with a filter. (Recipes on next page.)");
        addBookEntry("gameplay.hydration.water_filter.title", "Water Filter");
        addBookEntry("gameplay.hydration.water_filter.text", "Water filters can also be used in crafting to " +
                "filter water in a flask.");
        addBookEntry("gameplay.hydration.leather_flask_water_filter.title", "Leather Flask Purification");
        addBookEntry("gameplay.hydration.leather_flask_water_filter.text", "Just combine a filled water " +
                "flask with a water filter to craft a flask of purified water.");
        addBookEntry("education.name", "Education");
        addBookEntry("education.desc", "This section is specifically for those who want to argue " +
                "publicly that running around can generate enough body heat to even matter when you are wet, " +
                "cold and only wearing a t-shirt and pants (even if you are kinda superhuman). " +
                "This is just silly.$(br)$(br)" +
                "If needed, please refer to the following pages to get acquainted with how this actually works. " +
                "HINT: You will freeze to death if wet and cold, no matter how much you jump around or run. " +
                "Also, really hot things will scald, boil or burn you...");
        addBookEntry("education.environment.name", "Education: Environment");
        addBookEntry("education.environment.intro", "More info coming soon ...");
        addBookEntry("education.body_temp.name", "Education: Body Temperature");
        addBookEntry("education.body_temp.intro", "More info coming soon ...");
        addBookEntry("education.hydration.name", "Education: Hydration");
        addBookEntry("education.hydration.intro", "More info coming soon ...");
    }

    protected void addAttack(String name, String text) {
        add("death.attack." + name, text);
    }

    protected void addEffect(String name, String text) {
        add("effect.homeostatic." + name, text);
    }

    protected void addTooltip(String name, String text) {
        add("tooltip." + name, text);
    }

    protected void addItem(String name, String text) {
        add("item." + Homeostatic.MODID + "." + name, text);
    }

    protected void addFluid(String name, String text) {
        add("fluid_type." + Homeostatic.MODID + "." + name, text);
    }

    protected void addBookEntry(String name, String text) {
        add("info." + Homeostatic.MODID + ".book." + name, text);
    }

}