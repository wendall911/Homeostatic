package homeostatic.proxy;

import java.util.Map;

import biomesoplenty.api.biome.BOPBiomes;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
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
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerCreativeTab(CreativeModeTabEvent.Register event) {

            event.registerCreativeModeTab(new ResourceLocation(Homeostatic.MODID, "items"), builder -> builder.icon(() -> new ItemStack(HomeostaticItems.PURIFIED_WATER_BUCKET))
                .title(Component.translatable(Homeostatic.MODID + ".items"))
                .displayItems((features, output, tab) -> {
                    for (Map.Entry<ResourceLocation, Item> entry : HomeostaticItems.getAll().entrySet()) {
                        Item item = entry.getValue();

                        output.accept(new ItemStack(item));
                    }
                }));
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerCreativeTab(CreativeModeTabEvent.BuildContents event) {
            if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                for (Map.Entry<ResourceLocation, Item> entry : HomeostaticItems.getAll().entrySet()) {
                    Item item = entry.getValue();

                    event.accept(new ItemStack(item));
                }
            }
        }

    }

    public void serverStart(ServerStartedEvent event) {
        if (ModList.get().isLoaded("biomesoplenty")) {
            for (ResourceKey<Biome> biomeResourceKey : BOPBiomes.getAllBiomes()) {
                logBiomeInfo(event.getServer(), biomeResourceKey);
            }
        }

        if (ModList.get().isLoaded("byg")) {
            for (RegistryObject<Biome> biomeRegistryObject : BYGBiomes.BIOMES_BY_TAG.values()) {
                ResourceKey<Biome> biomeResourceKey = biomeRegistryObject.getResourceKey();

                logBiomeInfo(event.getServer(), biomeResourceKey);
            }
        }
    }

    private void logBiomeInfo(MinecraftServer server, ResourceKey<Biome> biomeResourceKey) {
        Registry<Biome> biomeRegistry = RegistryHelper.getRegistry(server, Registries.BIOME);
        String biomeName = biomeResourceKey.location().toString();
        Biome biome = biomeRegistry.get(biomeResourceKey);
        BiomeRegistry.BiomeCategory biomeCategory = BiomeRegistry.getBiomeCategory(biomeRegistry.getHolderOrThrow(biomeResourceKey));

        Homeostatic.LOGGER.debug(
                "Biome: " + biomeName
                        + "\npreciptitation=" + biome.getPrecipitation()
                        + "\ntemperature=" + biome.getBaseTemperature()
                        + "\ndownfall=" + biome.getDownfall()
                        + "\nbiomeCategory=" + biomeCategory
        );
    }

}