package homeostatic.data;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;

import homeostatic.data.recipe.NeoForgeRecipeProvider;
import homeostatic.Homeostatic;

@EventBusSubscriber(modid = Homeostatic.MODID)
public final class NeoForgeDataGenerators {

    @SubscribeEvent
    public static void gatherRegistries(GatherDataRegistryEntriesEvent event) {
        event.gatherFor("minecraft", Homeostatic.MODID).recipe(NeoForgeRecipeProvider::new);
    }

}
