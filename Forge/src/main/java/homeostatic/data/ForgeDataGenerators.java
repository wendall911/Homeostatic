package homeostatic.data;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import homeostatic.data.integration.create.FillingRecipeProvider;
import homeostatic.data.integration.create.MixingRecipeProvider;
import homeostatic.data.integration.patchouli.HomeostaticBookProvider;
import homeostatic.data.recipe.ForgeRecipeProvider;
import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(modid = Homeostatic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ForgeDataGenerators {

    private ForgeDataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        gen.addProvider(event.includeServer(), new ForgeRecipeProvider(gen.getPackOutput()));
        gen.addProvider(event.includeServer(), new MixingRecipeProvider(gen.getPackOutput()));
        gen.addProvider(event.includeServer(), new FillingRecipeProvider(gen.getPackOutput()));
        gen.addProvider(event.includeServer(), new HomeostaticBookProvider(gen.getPackOutput()));
        RegistryDataGenerator.addProviders(event.includeServer(), gen, gen.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper());
    }

}
