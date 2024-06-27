package homeostatic.data;

import net.minecraft.data.DataGenerator;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import homeostatic.data.integration.patchouli.HomeostaticBookProvider;
import homeostatic.data.recipe.NeoForgeRecipeProvider;
import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(modid = Homeostatic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class NeoForgeDataGenerators {

    private NeoForgeDataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        gen.addProvider(event.includeServer(), new NeoForgeRecipeProvider(gen.getPackOutput()));
        //gen.addProvider(event.includeServer(), new MixingRecipeProvider(gen.getPackOutput()));
        //gen.addProvider(event.includeServer(), new FillingRecipeProvider(gen.getPackOutput()));
        gen.addProvider(event.includeServer(), new HomeostaticBookProvider(gen.getPackOutput()));
        RegistryDataGenerator.addProviders(event.includeServer(), gen, gen.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper());
    }

}
