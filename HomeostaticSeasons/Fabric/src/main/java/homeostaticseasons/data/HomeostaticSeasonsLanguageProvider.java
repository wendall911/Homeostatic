package homeostaticseasons.data;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import net.minecraft.core.HolderLookup.Provider;

import homeostaticseasons.HomeostaticSeasons;
import homeostaticseasons.api.Season;
import homeostaticseasons.common.Translations;

public class HomeostaticSeasonsLanguageProvider extends FabricLanguageProvider {

    protected HomeostaticSeasonsLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(Provider provider, TranslationBuilder translationBuilder) {
        addCommand(translationBuilder, "query_season", "The current season is %s.");
        addCommand(translationBuilder, "query_next_season", "There are %s days (%s ticks) left until %s.");

        addConfigTitle(translationBuilder, "Homeostatic Seasons");
        addConfigTranslation(translationBuilder, "visuals");
        addConfigTranslation(translationBuilder, "changefoliagecolor");
        addConfigTranslation(translationBuilder, "changegrasscolor");
        addConfigTranslation(translationBuilder, "changebirchcolor");
        addConfigTranslation(translationBuilder, "seasons");
        addConfigTranslation(translationBuilder, "whitelistdimensions");
        addConfigTranslation(translationBuilder, "seasonchangemethod");
        addConfigTranslation(translationBuilder, "hemisphere");
        addConfigTranslation(translationBuilder, "fixedseason");
        addConfigTranslation(translationBuilder, "startingseason");
        addConfigTranslation(translationBuilder, "earlyspringdayslength");
        addConfigTranslation(translationBuilder, "midspringdayslength");
        addConfigTranslation(translationBuilder, "latespringdayslength");
        addConfigTranslation(translationBuilder, "earlysummerdayslength");
        addConfigTranslation(translationBuilder, "midsummerdayslength");
        addConfigTranslation(translationBuilder, "latesummerdayslength");
        addConfigTranslation(translationBuilder, "earlyautumndayslength");
        addConfigTranslation(translationBuilder, "midautumndayslength");
        addConfigTranslation(translationBuilder, "lateautumndayslength");
        addConfigTranslation(translationBuilder, "earlywinterdayslength");
        addConfigTranslation(translationBuilder, "midwinterdayslength");
        addConfigTranslation(translationBuilder, "latewinterdayslength");
        addConfigTranslation(translationBuilder, "weather");
        addConfigTranslation(translationBuilder, "seasonalsnowreplacevegetation");

        addSeasonTranslations(translationBuilder);
    }

    private void addSeasonTranslations(TranslationBuilder builder) {
        for (Season season : Season.values()) {
            builder.add(season.getTranslationKey(), season.getHumanReadableName());
        }
    }

    private void addCommand(TranslationBuilder builder, String command, String description) {
        builder.add(HomeostaticSeasons.MODID + ".command." + command, description);
    }

    private void addConfigTitle(TranslationBuilder builder, String title) {
        builder.add(HomeostaticSeasons.MODID + ".configuration.title", title);
    }

    private void addConfigName(TranslationBuilder builder, String id, String name) {
        builder.add(HomeostaticSeasons.MODID + ".configuration." + id + ".name", name);
    }

    private void addConfigDescription(TranslationBuilder builder, String id) {
        builder.add(HomeostaticSeasons.MODID + ".configuration." + id + ".description", Translations.get(id));
    }

    private void addConfigTranslation(TranslationBuilder buildder, String id) {
        addConfigName(buildder, id, Translations.get(id + ".title"));
        addConfigDescription(buildder, id);
    }

}
