package homeostatic.data;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(modid = Homeostatic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {

    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);

        gen.addProvider(true, blockTags);
        gen.addProvider(true, new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        gen.addProvider(true, new ModRecipesProvider(gen));
        gen.addProvider(true, new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(true, new ModBlockStateProvider(gen, existingFileHelper));
    }

}
