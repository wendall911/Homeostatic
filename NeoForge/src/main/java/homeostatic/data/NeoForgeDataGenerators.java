package homeostatic.data;

import net.minecraft.data.DataGenerator;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import homeostatic.data.recipe.NeoForgeRecipeProvider;
import homeostatic.Homeostatic;

@EventBusSubscriber(modid = Homeostatic.MODID)
public final class NeoForgeDataGenerators {

    private NeoForgeDataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator gen = event.getGenerator();

        gen.addProvider(true, new NeoForgeRecipeProvider(gen.getPackOutput(), event.getLookupProvider()));
        //gen.addProvider(event.includeServer(), new MixingRecipeProvider(gen.getPackOutput()));
        //gen.addProvider(event.includeServer(), new FillingRecipeProvider(gen.getPackOutput()));
    }

}
