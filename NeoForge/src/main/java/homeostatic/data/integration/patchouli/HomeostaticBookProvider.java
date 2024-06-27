package homeostatic.data.integration.patchouli;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import xyz.brassgoggledcoders.patchouliprovider.BookBuilder;
import xyz.brassgoggledcoders.patchouliprovider.CategoryBuilder;
import xyz.brassgoggledcoders.patchouliprovider.EntryBuilder;
import xyz.brassgoggledcoders.patchouliprovider.PatchouliBookProvider;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.Homeostatic;

import static homeostatic.Homeostatic.loc;

public class HomeostaticBookProvider extends PatchouliBookProvider {

    private final String translationLoc = "info.homeostatic.book";
    private int categorySortNum = -1;
    private int entrySortNum = -1;

    public HomeostaticBookProvider(@NotNull final PackOutput packOutput) {
        super(packOutput, Homeostatic.MODID, "en_us");
    }

    @Override
    protected void addBooks(Consumer<BookBuilder> consumer) {
        BookBuilder bookBuilder = createBookBuilder("book", translationLoc, prefix("intro"))
            .setSubtitle(prefix("subtitle"))
            .setCustomBookItem(new ItemStack(HomeostaticItems.BOOK))
            .setCreativeTab(Homeostatic.MODID + ".items")
            .setModel(Homeostatic.MODID + ":book")
            .setDontGenerateBook(true)
            .setShowProgress(false)
            .setUseBlockyFont(false)
            .setI18n(true)
            .setUseResourcePack(true);

        bookBuilder = addGameplay(bookBuilder);
        bookBuilder = addEducation(bookBuilder);

        bookBuilder.build(consumer);
    }

    private BookBuilder addGameplay(BookBuilder bookBuilder) {
        CategoryBuilder category = bookBuilder.addCategory(
            "gameplay",
            prefix("gameplay.name"),
            prefix("gameplay.desc"),
            new ItemStack(Items.IRON_SWORD)
        )
        .setSortnum(categorySortNum++);

        EntryBuilder gameplayEnvironmentEntry = category.addEntry(
            "gameplay/environment",
            prefix("gameplay.environment.name"),
            new ItemStack(Items.CAMPFIRE)
        ).setSortnum(entrySortNum++);

        gameplayEnvironmentEntry.addImagePage(bookImage("normal_outside"))
            .setTitle(prefix("gameplay.environment.title"))
            .setText(prefix("gameplay.environment.intro")).build()
        .addTextPage(prefix("gameplay.environment.details")).build();

        EntryBuilder gameplayBodyTempEntry = category.addEntry(
            "gameplay/body_temp",
            prefix("gameplay.body_temp.name"),
            new ItemStack(Items.LIGHT_GRAY_WOOL)
        ).setSortnum(entrySortNum++);

        gameplayBodyTempEntry.addImagePage(bookImage("normal_body"))
            .setTitle(prefix("gameplay.body_temp.title"))
            .setText(prefix("gameplay.body_temp.intro")).build()
        .addTextPage(prefix("gameplay.body_temp.details")).build()
        .addImagePage(bookImage("wetness_hud"))
            .setAnchor("wetness")
            .setTitle(prefix("gameplay.body_temp.wetness.title"))
            .setText(prefix("gameplay.body_temp.wetness.intro")).build()
        .addTextPage(prefix("gameplay.body_temp.wetness.details")).build()
        .addImagePage(bookImage("scalding_hud"))
            .setAnchor("scalding")
            .setTitle(prefix("gameplay.body_temp.scalding.title"))
            .setText(prefix("gameplay.body_temp.scalding.intro")).build()
        .addImagePage(bookImage("hyperthermia_hud"))
            .setAnchor("hyperthermia")
            .setTitle(prefix("gameplay.body_temp.hyperthermia.title"))
            .setText(prefix("gameplay.body_temp.hyperthermia.intro")).build()
        .addImagePage(bookImage("hypothermia_hud"))
            .setAnchor("hypothermia")
            .setTitle(prefix("gameplay.body_temp.hypothermia.title"))
            .setText(prefix("gameplay.body_temp.hypothermia.intro")).build()
        .addPage(new CustomRecipePageBuilder(loc("insulation"), gameplayBodyTempEntry))
            .setAnchor("insulation")
            .setTitle(prefix("gameplay.body_temp.insulation.title"))
            .setText(prefix("gameplay.body_temp.insulation.text")).build()
        .addPage(new CustomRecipePageBuilder(loc("remove_insulation"), gameplayBodyTempEntry))
            .setAnchor("remove_insulation")
            .setTitle(prefix("gameplay.body_temp.remove_insulation.title"))
            .setText(prefix("gameplay.body_temp.remove_insulation.text")).build()
        .addPage(new CustomRecipePageBuilder(loc("radiation_protection"), gameplayBodyTempEntry))
            .setAnchor("radiation_protection")
            .setTitle(prefix("gameplay.body_temp.radiation_protection.title"))
            .setText(prefix("gameplay.body_temp.radiation_protection.text")).build()
        .addPage(new CustomRecipePageBuilder(loc("remove_radiation_protection"), gameplayBodyTempEntry))
            .setAnchor("remove_radiation_protection")
            .setTitle(prefix("gameplay.body_temp.remove_radiation_protection.title"))
            .setText(prefix("gameplay.body_temp.remove_radiation_protection.text")).build()
        .addPage(new CustomRecipePageBuilder(loc("waterproof"), gameplayBodyTempEntry))
            .setAnchor("waterproofing")
            .setTitle(prefix("gameplay.body_temp.waterproof.title"))
            .setText(prefix("gameplay.body_temp.waterproof.text")).build()
        .addPage(new CustomRecipePageBuilder(loc("remove_waterproof"), gameplayBodyTempEntry))
            .setAnchor("remove_waterproofing")
            .setTitle(prefix("gameplay.body_temp.remove_waterproof.title"))
            .setText(prefix("gameplay.body_temp.remove_waterproof.text")).build();

        EntryBuilder gameplayHydrationEntry = category.addEntry(
            "gameplay/hydration",
            prefix("gameplay.hydration.name"),
            new ItemStack(HomeostaticItems.LEATHER_FLASK)
        ).setSortnum(entrySortNum++);

        gameplayHydrationEntry.addImagePage(bookImage("water_bar"))
            .setTitle(prefix("gameplay.hydration.title"))
            .setText(prefix("gameplay.hydration.intro")).build()
        .addTextPage(prefix("gameplay.hydration.details")).build()
        .addCraftingPage(loc("leather_flask"))
            .setTitle(prefix("gameplay.hydration.leather_flask.title"))
            .setText(prefix("gameplay.hydration.leather_flask.text")).build()
        .addSmeltingPage(loc("furnace_purified_leather_flask"))
            .setTitle(prefix("gameplay.hydration.leather_flask.smelting.title"))
            .setText(prefix("gameplay.hydration.leather_flask.smelting.text")).build()
        .addCraftingPage(loc("water_filter"))
            .setTitle(prefix("gameplay.hydration.water_filter.title"))
            .setText(prefix("gameplay.hydration.water_filter.text")).build()
        .addPage(new CustomRecipePageBuilder(loc("filtered_water_flask"), gameplayHydrationEntry))
            .setTitle(prefix("gameplay.hydration.leather_flask_water_filter.title"))
            .setText(prefix("gameplay.hydration.leather_flask_water_filter.text")).build();

        return category.build();
    }

    private BookBuilder addEducation(BookBuilder bookBuilder) {
        return bookBuilder.addCategory(
            "education",
            prefix("education.name"),
            prefix("education.desc"),
            new ItemStack(Items.BOOKSHELF)
        )
        .setSortnum(categorySortNum++)
        .addEntry(
            "education/environment",
            prefix("education.environment.name"),
            new ItemStack(Items.SOUL_CAMPFIRE)
        ).setSortnum(entrySortNum++)
        .addTextPage(prefix("education.environment.intro")).build()
        .build()
        .addEntry(
            "education/body_temp",
            prefix("education.body_temp.name"),
            new ItemStack(Items.GREEN_WOOL)
        ).setSortnum(entrySortNum++)
        .addTextPage(prefix("education.body_temp.intro")).build()
        .build()
        .addEntry(
            "education/hydration",
            prefix("education.hydration.name"),
            new ItemStack(HomeostaticItems.PURIFIED_WATER_BUCKET)
        ).setSortnum(entrySortNum++)
        .addTextPage(prefix("education.hydration.intro")).build()
        .build().build();
    }

    private String prefix(String name) {
        return translationLoc + "." + name;
    }

    private ResourceLocation bookImage(String id) {
        return new ResourceLocation(Homeostatic.MODID, "textures/gui/book/" + id + ".png");
    }

}