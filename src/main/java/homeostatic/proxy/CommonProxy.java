package homeostatic.proxy;

import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.recipe.PurifiedLeatherFlask;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import homeostatic.config.ConfigHandler;
import homeostatic.common.block.BlockRegistry;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.recipe.ArmorEnhancement;
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
        public static void registerMobEffects(RegistryEvent.Register<MobEffect> event) {
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

            bus.register(new HomeostaticEffects());
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerItems(RegistryEvent.Register<Item> event) {
            HomeostaticItems.init(event.getRegistry());
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            HomeostaticBlocks.init(event.getRegistry());
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerFluids(RegistryEvent.Register<Fluid> event) {
            HomeostaticFluids.init(event.getRegistry());
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void setupRegistries(FMLConstructModEvent event) {
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

            HomeostaticEffects.EFFECT_REGISTRY.register(bus);
            HomeostaticRecipes.RECIPE_REGISTRY.register(bus);
            ArmorEnhancement.init();
            PurifiedLeatherFlask.init();
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
            event.getRegistry().register(ArmorEnhancement.ARMOR_ENHANCEMENT_SERIALIZER);
            event.getRegistry().register(PurifiedLeatherFlask.PURIFIED_LEATHER_FLASK_SERIALIZER);
        }

    }

}
