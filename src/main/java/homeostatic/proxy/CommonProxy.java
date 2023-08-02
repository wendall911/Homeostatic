package homeostatic.proxy;

import java.util.Map;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import homeostatic.config.ConfigHandler;
import homeostatic.common.biome.BiomeCategory;
import homeostatic.common.biome.BiomeCategoryManager;
import homeostatic.common.biome.BiomeData;
import homeostatic.common.biome.BiomeRegistry;
import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.recipe.ArmorEnhancement;
import homeostatic.common.recipe.HelmetThermometer;
import homeostatic.common.recipe.HomeostaticRecipes;
import homeostatic.common.recipe.PurifiedLeatherFlask;
import homeostatic.common.recipe.RemoveArmorEnhancement;
import homeostatic.Homeostatic;
import homeostatic.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = Homeostatic.MODID)
public class CommonProxy {

    public CommonProxy() {}

    public void start() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ConfigHandler.init();
        registerListeners(bus);

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
            HelmetThermometer.init();
            RemoveArmorEnhancement.init();
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
            event.getRegistry().register(ArmorEnhancement.ARMOR_ENHANCEMENT_SERIALIZER);
            event.getRegistry().register(PurifiedLeatherFlask.PURIFIED_LEATHER_FLASK_SERIALIZER);
            event.getRegistry().register(HelmetThermometer.HELMET_THERMOMETER_SERIALIZER);
            event.getRegistry().register(RemoveArmorEnhancement.REMOVE_ARMOR_ENHANCEMENT_SERIALIZER);
        }

    }

    public void serverStart(ServerStartedEvent event) {
        Registry<Biome> biomeRegistry = BuiltinRegistries.BIOME;

        for (Map.Entry<ResourceKey<Biome>, Biome> entry : biomeRegistry.entrySet()) {
            ResourceKey<Biome> biomeResourceKey = entry.getKey();
            ResourceLocation biomeName = biomeResourceKey.location();

            Holder<Biome> biomeHolder = biomeRegistry.getOrCreateHolder(biomeResourceKey);
            BiomeCategory.Type biomeCategory = BiomeCategoryManager.getBiomeCategory(biomeHolder);
            BiomeData biomeData = BiomeRegistry.getDataForBiome(biomeHolder);
            Biome biome = biomeHolder.value();
            Biome.Precipitation precipitation = biome.getPrecipitation();
            String temperatureModifier = biomeData.isFrozen() ? "FROZEN" : "NONE";
            float dayNightOffset = biomeData.getDayNightOffset(precipitation);
            double humidity = biomeData.getHumidity(precipitation);

            if (!biomeName.toString().equals("terrablender:deferred_placeholder")) {
                if (biomeCategory == BiomeCategory.Type.MISSING) {
                    Homeostatic.LOGGER.warn("Missing biome in registry, will set to neutral temperature for: %s", biomeName);
                }
                Homeostatic.LOGGER.debug("Biome: " + biomeName
                        + "\nprecipitation_type=" + precipitation
                        + "\ntemperature=" + biomeData.getTemperature(precipitation)
                        + "\ntemperatureModifier=" + temperatureModifier
                        + "\ndownfall=" + biome.getDownfall()
                        + "\ndayNightOffset=" + dayNightOffset
                        + "\nhumidity=" + humidity
                        + "\nbiomeCategory=" + biomeCategory);
            }
        }
    }

}
