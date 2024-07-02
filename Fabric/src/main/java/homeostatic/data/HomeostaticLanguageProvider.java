package homeostatic.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import homeostatic.Homeostatic;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class HomeostaticLanguageProvider extends FabricLanguageProvider {

    protected HomeostaticLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryFuture) {
        super(dataOutput, registryFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.
            add(Homeostatic.MODID + ".items", "Homeostatic");

        addAttack(translationBuilder, "hasHyperthermia", "Hyperthermia has claimed poor %1$s");
        addAttack(translationBuilder, "hasHyperthermia.player", "Hyperthermia has claimed poor %1$s whilst fighting %2$s");
        addAttack(translationBuilder, "isScalding", "%1$s died of scalding");
        addAttack(translationBuilder, "isScalding.player", "%1$s died of scalding whilst fighting %2$s");
        addAttack(translationBuilder, "hasDehydration", "%1$s turned into dead dried %1$s");
        addAttack(translationBuilder, "hasDehydration.player", "%1$s turned into dead dried %1$s whilst fighting %2$s");
        addEffect(translationBuilder, "thirst", "Thirst");
        addTooltip(translationBuilder,"insulation", "Insulation");
        addTooltip(translationBuilder, "waterproof", "Waterproof");
        addTooltip(translationBuilder, "radiation_protection", "Radiation Protection");
        addTooltip(translationBuilder, "water_container.empty", "Empty");
        addTooltip(translationBuilder, "thermometer", "Thermovision");
        addItem(translationBuilder, "book", "Homeostatic Tome");
        addItem(translationBuilder, "leather_flask", "Leather Flask");
        addItem(translationBuilder, "purified_water_bucket", "Purified Water Bucket");
        addItem(translationBuilder, "water_filter", "Water Filter");
        addItem(translationBuilder, "thermometer", "Thermometer");
        addFluid(translationBuilder, "purified_water_type", "Purified Water");
        addBlock(translationBuilder, "purified_water_fluid", "Purified Water");
        addBookEntry(translationBuilder, "intro", "Temperature and hydration dynamics that don't defy logic...$(br)" +
                "$(li)The $(l:homeostatic:education/environment)environment$() has temperature that is affected by " +
                    "$(l:https://simple.wikipedia.org/wiki/Thermal_radiation)thermal radiation$(), like sun, lava, " +
                    "campfires, etc.$(li)Body temperature must be maintained or you can get " +
                    "$(l:https://simple.wikipedia.org/wiki/Hyperthermia)hyperthermia$() or " +
                    "$(l:https://simple.wikipedia.org/wiki/Hypothermia)hypothermia$()" +
                "$(li)Hydration is necessary to survive.");
        addBookEntry(translationBuilder, "subtitle", "About Temperature and Water");
        addBookEntry(translationBuilder, "gameplay.name", "Gameplay");
        addBookEntry(translationBuilder, "gameplay.desc", "Gameplay is enhanced through several mechanics." +
                "$(li)Environment Temperature" +
                "$(li)Body Temperature" +
                "$(li2)Wetness" +
                "$(li2)Scalding/Hypothermia" +
                "$(li2)Freezing/Hyperthermia" +
                "$(li2)Simply how the body is reacting to environment temperature." +
                "$(li2)Protect yourself by enhancing your armor. Even just wearing armor helps." +
                "$(li)Hydration" +
                "$(li2)You get thirsty now as well as hungry. Stay hydrated!");
        addBookEntry(translationBuilder, "gameplay.environment.name", "Environment Temperature");
        addBookEntry(translationBuilder, "gameplay.environment.title", "Environment °F/C HUD");
        addBookEntry(translationBuilder, "gameplay.environment.intro", "The arrow above points to the location of the local "
                + "temperature in the HUD. Can be displayed in Fahrenheit or Celsius.");
        addBookEntry(translationBuilder, "gameplay.environment.details", "There are several factors that are used to calculate " +
                    "the local temperature:$(br)" +
                "$(li)Current and influence from surrounding biomes." +
                "$(li)Sun exposure." +
                "$(li)Heat radiation sources, such as, lava, torches, lit furnaces, etc." +
                "$(li)Elevation. Mountain tops are cooler, underground is more consistent temperature.");
        addBookEntry(translationBuilder, "gameplay.body_temp.name", "Body Temperature");
        addBookEntry(translationBuilder, "gameplay.body_temp.title", "Body °F/C HUD");
        addBookEntry(translationBuilder, "gameplay.body_temp.intro", "The arrow above points to the location of the body " +
                "temperature in the HUD. Can be displayed in Fahrenheit or Celsius.");
        addBookEntry(translationBuilder, "gameplay.body_temp.details", "There are several factors used to calculate the body " +
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
        addBookEntry(translationBuilder, "gameplay.body_temp.wetness.title", "Wetness");
        addBookEntry(translationBuilder, "gameplay.body_temp.wetness.intro", "When wet, droplets will appear as an overlay on the edges " +
                "of the screen as shown in the above image.");
        addBookEntry(translationBuilder, "gameplay.body_temp.wetness.details", "How wet/dry a player is determines how quickly " +
                "$(l:homeostatic:gameplay/body_temp)body temperature$() lowers. This can be a good or bad thing " +
                "depending on the situation:" +
                "$(li)When hot, being wet or in water can help the player rapidly cool down." +
                "$(li)When cold, being wet can increase the risk of the player freezing to death." +
                "$(li2)Seeking shelter or adding $(l:homeostatic:gameplay/body_temp#waterproofing)Waterproofing$() to " +
                "armor is essential for staying dry.");
        addBookEntry(translationBuilder, "gameplay.body_temp.scalding.title", "Scalding");
        addBookEntry(translationBuilder, "gameplay.body_temp.scalding.intro", "Scalding can happen when a player is near a very " +
                "hot source above 140°F or 60°C.");
        addBookEntry(translationBuilder, "gameplay.body_temp.hyperthermia.title", "Hyperthermia");
        addBookEntry(translationBuilder, "gameplay.body_temp.hyperthermia.intro", "Hyperthermia can occur when the core " +
                "temperature is above 106°F or 41°C.");
        addBookEntry(translationBuilder, "gameplay.body_temp.hypothermia.title", "Freezing / Hypothermia");
        addBookEntry(translationBuilder, "gameplay.body_temp.hypothermia.intro", "Hypothermia can occur when the core " +
                "temperature is below 95°F or 35°C.");
        addBookEntry(translationBuilder, "gameplay.body_temp.insulation.title", "Insulation");
        addBookEntry(translationBuilder, "gameplay.body_temp.insulation.text", "Any armor piece can be insulated. The more " +
                "pieces that are insulated, the better the player is insulated. See JEI/REI for recipes.");
        addBookEntry(translationBuilder, "gameplay.body_temp.remove_insulation.title", "Remove Insulation");
        addBookEntry(translationBuilder, "gameplay.body_temp.remove_insulation.text", "Insulation can be removed from any armor " +
                "piece. Recipe does not appear in JEI/REI.");
        addBookEntry(translationBuilder, "gameplay.body_temp.radiation_protection.title", "Radiation Protection");
        addBookEntry(translationBuilder, "gameplay.body_temp.radiation_protection.text", "Any armor piece can have radiation " +
                "protection added. Add to multiple pieces to achieve maximum protection.$(br)" +
                "Addionally, fire resistance fully protects the player from radiation sources.");
        addBookEntry(translationBuilder, "gameplay.body_temp.remove_radiation_protection.title", "Remove Radiation Protection");
        addBookEntry(translationBuilder, "gameplay.body_temp.remove_radiation_protection.text", "Radiation protection can be " +
                "removed from any armor piece. Recipe does not appear in JEI/REI.");
        addBookEntry(translationBuilder, "gameplay.body_temp.waterproof.title", "Waterproofing");
        addBookEntry(translationBuilder, "gameplay.body_temp.waterproof.text", "Waterproofing can be added to any armor piece. " +
                "Add to multiple pieces to increase protection.");
        addBookEntry(translationBuilder, "gameplay.body_temp.remove_waterproof.title", "Waterproofing");
        addBookEntry(translationBuilder, "gameplay.body_temp.remove_waterproof.text", "Waterproofing can be removed from any armor " +
                "piece. Recipe does not appear in JEI/REI.");
        addBookEntry(translationBuilder, "gameplay.hydration.name", "Hydration/Thirst");
        addBookEntry(translationBuilder, "gameplay.hydration.title", "Hydration HUD");
        addBookEntry(translationBuilder, "gameplay.hydration.intro", "The arrow above points to the hydration bar. " +
                "This works similar to hunger. Brighter highlights indicate saturation level.");
        addBookEntry(translationBuilder, "gameplay.hydration.details", "There are a few ways to hydrate:$(br)" +
                "$(li)Drink water with bare hands." +
                "$(li2)Crouch and right click while targeting any water block and you can drink water directly. " +
                    "This can cause a negative status effect." +
                "$(li)Obtain a flask. Recipe and details on following pages." +
                "$(li)Eat foods or drinkable items with water content. A small preview will flash over the water bar " +
                "if holding any hydration item.");
        addBookEntry(translationBuilder, "gameplay.hydration.leather_flask.title", "Leather Flask");
        addBookEntry(translationBuilder, "gameplay.hydration.leather_flask.text", "Leather flask is critical in environments " +
                "where water is limited. To fill, simply interact with any water source block and it will be \"filled\". " +
                "However, this is dirty water. See following page to purify.");
        addBookEntry(translationBuilder, "gameplay.hydration.leather_flask.smelting.title", "Leather Flask Purification");
        addBookEntry(translationBuilder, "gameplay.hydration.leather_flask.smelting.text", "To purify the dirty water in a " +
                "flask, there are several methods: " +
                "$(li)Smelt in a Furnace (shown above)" +
                "$(li)Cook over a Campfire" +
                "$(li)Smoke in a Smoker" +
                "$(li)Combine with a filter. (Recipes on next page.)");
        addBookEntry(translationBuilder, "gameplay.hydration.water_filter.title", "Water Filter");
        addBookEntry(translationBuilder, "gameplay.hydration.water_filter.text", "Water filters can also be used in crafting to " +
                "filter water in a flask.");
        addBookEntry(translationBuilder, "gameplay.hydration.leather_flask_water_filter.title", "Leather Flask Purification");
        addBookEntry(translationBuilder, "gameplay.hydration.leather_flask_water_filter.text", "Just combine a filled water " +
                "flask with a water filter to craft a flask of purified water.");
        addBookEntry(translationBuilder, "education.name", "Education");
        addBookEntry(translationBuilder, "education.desc", "This section is specifically for those who want to argue " +
                "publicly that running around can generate enough body heat to even matter when you are wet, " +
                "cold and only wearing a t-shirt and pants (even if you are kinda superhuman). " +
                "This is just silly.$(br)$(br)" +
                "If needed, please refer to the following pages to get acquainted with how this actually works. " +
                "HINT: You will freeze to death if wet and cold, no matter how much you jump around or run. " +
                "Also, really hot things will scald, boil or burn you...");
        addBookEntry(translationBuilder, "education.environment.name", "Education: Environment");
        addBookEntry(translationBuilder, "education.environment.intro", "More info coming soon ...");
        addBookEntry(translationBuilder, "education.body_temp.name", "Education: Body Temperature");
        addBookEntry(translationBuilder, "education.body_temp.intro", "More info coming soon ...");
        addBookEntry(translationBuilder, "education.hydration.name", "Education: Hydration");
        addBookEntry(translationBuilder, "education.hydration.intro", "More info coming soon ...");
    }

    protected void addAttack(TranslationBuilder translationBuilder, String name, String text) {
        translationBuilder.add("death.attack." + name, text);
    }

    protected void addEffect(TranslationBuilder translationBuilder, String name, String text) {
        translationBuilder.add("effect." + Homeostatic.MODID + "." + name, text);
    }

    protected void addTooltip(TranslationBuilder translationBuilder, String name, String text) {
        translationBuilder.add("tooltip." + name, text);
    }

    protected void addItem(TranslationBuilder translationBuilder, String name, String text) {
        translationBuilder.add("item." + Homeostatic.MODID + "." + name, text);
    }

    protected void addBlock(TranslationBuilder translationBuilder, String name, String text) {
        translationBuilder.add("block." + Homeostatic.MODID + "." + name, text);
    }

    protected void addFluid(TranslationBuilder translationBuilder, String name, String text) {
        translationBuilder.add("fluid_type." + Homeostatic.MODID + "." + name, text);
    }

    protected void addBookEntry(TranslationBuilder translationBuilder, String name, String text) {
        translationBuilder.add("info." + Homeostatic.MODID + ".book." + name, text);
    }

}
