package homeostatic.proxy;

import java.util.Map;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
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
import homeostatic.common.biome.BiomeRegistry;
import homeostatic.common.CreativeTabs;
import homeostatic.common.damagesource.HomeostaticDamageTypes;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.recipe.HomeostaticRecipes;
import homeostatic.common.HomeostaticModule;
import homeostatic.Homeostatic;
import homeostatic.network.NetworkHandler;
import homeostatic.util.BiomeHelper;
import homeostatic.util.RegistryHelper;

@Mod.EventBusSubscriber(modid = Homeostatic.MODID)
public class CommonProxy {

    public CommonProxy() {}

    public void start() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ConfigHandler.init();
        registerListeners(bus);
        BiomeRegistry.init();

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, this::serverStart);
    }

    public void registerListeners(IEventBus bus) {
        bus.register(RegistryListener.class);
    }

    public static final class RegistryListener {

        private static boolean setupDone = false;

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) {
            NetworkHandler.init();
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerEvent(RegisterEvent event) {
            event.register(Registries.MOB_EFFECT, HomeostaticEffects::init);
            event.register(Registries.ITEM, HomeostaticItems::init);
            event.register(Registries.BLOCK, HomeostaticBlocks::init);
            event.register(Registries.FLUID, HomeostaticFluids::init);
            event.register(ForgeRegistries.FLUID_TYPES.get().getRegistryKey(), HomeostaticFluids::initTypes);
            event.register(Registries.RECIPE_SERIALIZER, HomeostaticRecipes::init);
            event.register(Registries.DAMAGE_TYPE, HomeostaticDamageTypes::init);
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void setupRegistries(FMLConstructModEvent event) {
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

            if (setupDone) {
                return;
            }
            setupDone = true;

            HomeostaticModule.initRegistries(bus);
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerCreativeTab(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES
                    || event.getTab() == CreativeTabs.ALL_ITEMS_TAB.get()) {
                for (Map.Entry<ResourceLocation, Item> entry : HomeostaticItems.getAll().entrySet()) {
                    Item item = entry.getValue();

                    event.accept(new ItemStack(item));
                }
            }
        }

    }

    public void serverStart(ServerStartedEvent event) {
        Registry<Biome> biomeRegistry = RegistryHelper.getRegistry(event.getServer(), Registries.BIOME);

        for (Map.Entry<ResourceKey<Biome>, Biome> entry : biomeRegistry.entrySet()) {
            ResourceKey<Biome> biomeResourceKey = entry.getKey();
            ResourceLocation biomeName = biomeResourceKey.location();
            Biome biome = entry.getValue();
            BiomeRegistry.BiomeCategory biomeCategory = BiomeRegistry.getBiomeCategory(biomeRegistry.getHolderOrThrow(biomeResourceKey));

            if (!biomeName.toString().equals("terrablender:deferred_placeholder")) {
                if (biomeCategory != BiomeRegistry.BiomeCategory.MISSING) {
                    Homeostatic.LOGGER.warn("Missing biome in registry, will set to neutral temperature for: %s", biomeName);
                }
                Homeostatic.LOGGER.debug(
                        "Biome: " + biomeName
                                + "\npreciptitation=" + BiomeHelper.getPrecipitation(biome.getModifiedClimateSettings())
                                + "\ntemperature=" + biome.getBaseTemperature()
                                + "\ntemperatureModifier=" + biome.getModifiedClimateSettings().temperatureModifier()
                                + "\ndownfall=" + biome.getModifiedClimateSettings().downfall()
                                + "\nbiomeCategory=" + biomeCategory
                );
            }
        }
    }

}
