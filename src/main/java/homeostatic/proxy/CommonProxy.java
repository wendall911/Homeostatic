package homeostatic.proxy;

import net.minecraft.core.Registry;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import homeostatic.config.ConfigHandler;
import homeostatic.common.block.BlockRegistry;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.recipe.HomeostaticRecipes;
import homeostatic.Homeostatic;
import homeostatic.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = Homeostatic.MODID)
public class CommonProxy {

    public CommonProxy() {}

    public void start() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ConfigHandler.init();
        registerListeners(bus);
        BlockRegistry.init();
    }

    public void registerListeners(IEventBus bus) {
        bus.register(RegistryListener.class);
    }

    public static final class RegistryListener {

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) {
            NetworkHandler.init();
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerEvent(RegisterEvent event) {
            event.register(Registry.MOB_EFFECT_REGISTRY, HomeostaticEffects::init);
            event.register(Registry.ITEM_REGISTRY, HomeostaticItems::init);
            event.register(Registry.BLOCK_REGISTRY, HomeostaticBlocks::init);
            event.register(Registry.FLUID_REGISTRY, HomeostaticFluids::init);
            event.register(ForgeRegistries.FLUID_TYPES.get().getRegistryKey(), HomeostaticFluids::initTypes);

            event.register(Registry.RECIPE_SERIALIZER_REGISTRY, HomeostaticRecipes::init);
        }

    }

}
