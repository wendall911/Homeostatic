package homeostatic.data;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import homeostatic.data.integration.create.FillingRecipeProvider;
import homeostatic.data.integration.create.MixingRecipeProvider;
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
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);

        gen.addProvider(blockTags);
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        gen.addProvider(new ModFluidTagsProvider(gen, existingFileHelper));
        gen.addProvider(new ModRecipesProvider(gen));
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new RadiationBlocksProvider(gen, Homeostatic.MODID));
        gen.addProvider(new DrinkingFluidsProvider(gen, Homeostatic.MODID));
        gen.addProvider(new DrinkableItemsProvider(gen, Homeostatic.MODID));
        gen.addProvider(new MixingRecipeProvider(gen));
        gen.addProvider(new FillingRecipeProvider(gen));
        gen.addProvider(new ModBookProvider(gen));
        gen.addProvider(new ModLanguageProvider(gen));
        gen.addProvider(new SpecialRecipeProvider(gen));
    }

}
