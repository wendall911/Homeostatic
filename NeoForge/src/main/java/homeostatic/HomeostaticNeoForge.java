package homeostatic;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import homeostatic.common.attachments.AttachmentsRegistry;
import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.CreativeTabs;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.fluid.NeoForgeFluidType;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.HomeostaticModule;
import homeostatic.common.item.FluidHandlerItem;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.item.LeatherFlask;
import homeostatic.common.recipe.HomeostaticRecipes;
import homeostatic.event.GameOverlayEventHandler;
import homeostatic.event.ServerEventListener;
import homeostatic.network.NeoForgeNetworkManager;
import homeostatic.network.NeoForgeTemperatureData;
import homeostatic.network.NeoForgeThermometerData;
import homeostatic.network.NeoForgeWaterData;
import homeostatic.network.NeoForgeWetnessData;
import homeostatic.network.TemperatureData;
import homeostatic.network.ThermometerData;
import homeostatic.network.WaterData;
import homeostatic.network.WetnessData;

@Mod(Homeostatic.MODID)
public class HomeostaticNeoForge {

    public HomeostaticNeoForge(IEventBus bus) {
        registryInit(bus);
        bus.register(RegistryListener.class);
        bus.addListener(this::setup);
        bus.addListener(this::registerPayloadHandler);
        Homeostatic.init();
        Homeostatic.initConfig();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            HomeostaticClientNeoForge.init(bus);
            bus.addListener(GameOverlayEventHandler.INSTANCE::onRegisterOverlays);
        }

        HomeostaticModule.initRegistries(bus);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NeoForge.EVENT_BUS.register(ServerEventListener.class);
    }

    public static final class RegistryListener {

        private static boolean setupDone = false;

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerEvent(RegisterEvent event) {
            event.register(NeoForgeRegistries.FLUID_TYPES.key(), NeoForgeFluidType::initTypes);

            if (event.getRegistryKey().equals(Registries.CREATIVE_MODE_TAB)) {
                CreativeTabs.init();
            }
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerCreativeTab(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES
                    || event.getTab() == CreativeTabs.ALL_ITEMS_TAB) {
                for (Map.Entry<ResourceLocation, Item> entry : HomeostaticItems.getAll().entrySet()) {
                    Item item = entry.getValue();

                    event.accept(new ItemStack(item));
                }
            }
        }

        @SubscribeEvent
        public static void registerCapabilities(RegisterCapabilitiesEvent event) {
            event.registerItem(Capabilities.FluidHandler.ITEM, (item, b) -> new FluidHandlerItem(item, (int) LeatherFlask.LEATHER_FLASK_CAPACITY), HomeostaticItems.LEATHER_FLASK);
        }

    }

    private void registryInit(IEventBus bus) {
        AttachmentsRegistry.init(bus);
        bind(bus, Registries.BLOCK, HomeostaticBlocks::init);
        bind(bus, Registries.MOB_EFFECT, HomeostaticEffects::init);
        bind(bus, Registries.FLUID, HomeostaticFluids::init);
        bind(bus, Registries.RECIPE_SERIALIZER, HomeostaticRecipes::init);
        bind(bus, Registries.ITEM, HomeostaticItems::init);
    }

    private void registerPayloadHandler(final RegisterPayloadHandlerEvent event) {
        event.registrar(Homeostatic.MODID).play(TemperatureData.ID, NeoForgeTemperatureData::new,
            handler -> handler.client(NeoForgeNetworkManager.getInstance()::processTemperatureData));
        event.registrar(Homeostatic.MODID).play(ThermometerData.ID, NeoForgeThermometerData::new,
            handler -> handler.client(NeoForgeNetworkManager.getInstance()::processThermometerData));
        event.registrar(Homeostatic.MODID).play(WaterData.ID, NeoForgeWaterData::new,
            handler -> handler.client(NeoForgeNetworkManager.getInstance()::processWaterData));
        event.registrar(Homeostatic.MODID).play(WetnessData.ID, NeoForgeWetnessData::new,
            handler -> handler.client(NeoForgeNetworkManager.getInstance()::processWetnessData));
    }

    private static <T> void bind(IEventBus bus, ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        bus.addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }

}
