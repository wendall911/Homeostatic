package homeostatic.data;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//import homeostatic.data.integration.create.FillingRecipeProvider;
//import homeostatic.data.integration.create.MixingRecipeProvider;
import homeostatic.data.integration.patchouli.ModBookProvider;
import homeostatic.Homeostatic;

import homeostatic.data.recipe.ModRecipesProvider;
import homeostatic.data.recipe.SpecialRecipeProvider;

@Mod.EventBusSubscriber(modid = Homeostatic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {

    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen.getPackOutput(), event.getLookupProvider(), existingFileHelper);

        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(), new ModItemTagsProvider(gen.getPackOutput(), event.getLookupProvider(), blockTags, existingFileHelper));
        gen.addProvider(event.includeServer(), new ModFluidTagsProvider(gen.getPackOutput(), event.getLookupProvider(), existingFileHelper));
        gen.addProvider(event.includeServer(), new ModRecipesProvider(gen.getPackOutput()));
        gen.addProvider(event.includeClient(), new ModItemModelProvider(gen.getPackOutput(), existingFileHelper));
        gen.addProvider(event.includeClient(), new ModBlockStateProvider(gen.getPackOutput(), existingFileHelper));
        gen.addProvider(event.includeServer(), new RadiationBlocksProvider(gen.getPackOutput()));
        gen.addProvider(event.includeServer(), new DrinkingFluidsProvider(gen.getPackOutput()));
        gen.addProvider(event.includeServer(), new DrinkableItemsProvider(gen.getPackOutput()));
        // TODO Add for Create 1.19.3/4 once released
        /*
        gen.addProvider(event.includeServer(), new MixingRecipeProvider(gen.getPackOutput()));
        gen.addProvider(event.includeServer(), new FillingRecipeProvider(gen.getPackOutput()));
         */
        gen.addProvider(event.includeServer(), new ModBookProvider(gen.getPackOutput()));
        gen.addProvider(event.includeClient(), new ModLanguageProvider(gen.getPackOutput()));
        gen.addProvider(event.includeServer(), new SpecialRecipeProvider(gen.getPackOutput()));
    }

}