package homeostatic.proxy;

import biomesoplenty.api.biome.BOPBiomes;

import java.util.function.Consumer;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import potionstudios.byg.common.world.biome.BYGBiomes;
import potionstudios.byg.reg.RegistryObject;

import homeostatic.config.ConfigHandler;
import homeostatic.common.biome.BiomeRegistry;
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
        BiomeRegistry.init();

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, this::serverStart);
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

    public void serverStart(ServerStartedEvent event) {
        if (ModList.get().isLoaded("biomesoplenty")) {
            for (ResourceKey<Biome> biomeResourceKey : BOPBiomes.getAllBiomes()) {
                logBiomeInfo(biomeResourceKey);
            }
        }

        if (ModList.get().isLoaded("byg")) {
            for (RegistryObject<Biome> biomeRegistryObject : BYGBiomes.BIOMES_BY_TAG.values()) {
                ResourceKey<Biome> biomeResourceKey = biomeRegistryObject.getResourceKey();

                logBiomeInfo(biomeResourceKey);
            }
        }
    }

    private void logBiomeInfo(ResourceKey<Biome> biomeResourceKey) {
        Registry<Biome> biomeRegistry = BuiltinRegistries.BIOME;
        String biomeName = biomeResourceKey.location().toString();
        Biome biome = biomeRegistry.get(biomeResourceKey);
        Consumer<String> error = x -> Homeostatic.LOGGER.error("error getting holder for %s", biomeName);
        BiomeRegistry.BiomeCategory biomeCategory = BiomeRegistry.getBiomeCategory(biomeRegistry.getOrCreateHolder(biomeResourceKey).getOrThrow(false, error));

        Homeostatic.LOGGER.debug(
                "Biome: " + biomeName
                        + "\npreciptitation=" + biome.getPrecipitation()
                        + "\ntemperature=" + biome.getBaseTemperature()
                        + "\ndownfall=" + biome.getDownfall()
                        + "\nbiomeCategory=" + biomeCategory
        );
    }

}
