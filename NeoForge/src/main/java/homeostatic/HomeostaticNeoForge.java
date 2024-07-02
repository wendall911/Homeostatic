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
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import homeostatic.common.attachments.AttachmentsRegistry;
import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.component.HomeostaticComponents;
import homeostatic.common.CreativeTabs;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.fluid.NeoForgeFluidType;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.HomeostaticModule;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.recipe.HomeostaticRecipes;
import homeostatic.event.GameOverlayEventHandler;
import homeostatic.event.ServerEventListener;
import homeostatic.network.DrinkWater;
import homeostatic.network.NeoForgeNetworkManager;
import homeostatic.network.NeoForgeTemperatureData;
import homeostatic.registries.HomeostaticNeoForgeRegistries;

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

    }

    private void registryInit(IEventBus bus) {
        AttachmentsRegistry.init(bus);
        bind(bus, Registries.BLOCK, HomeostaticBlocks::init);
        bind(bus, Registries.MOB_EFFECT, HomeostaticEffects::init);
        bind(bus, Registries.FLUID, HomeostaticFluids::init);
        bind(bus, Registries.RECIPE_SERIALIZER, HomeostaticRecipes::init);
        bind(bus, Registries.ITEM, HomeostaticItems::init);
        HomeostaticNeoForgeRegistries.COMPONENT_TYPE_DEFERRED_REGISTER.register(bus);
        HomeostaticComponents.registerDataComponents();
    }

    private void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Homeostatic.MODID).versioned("1.0");

        registrar.playToServer(DrinkWater.TYPE, DrinkWater.STREAM_CODEC, NeoForgeNetworkManager.getInstance()::processDrinkWater);
        registrar.playToClient(NeoForgeTemperatureData.TYPE, NeoForgeTemperatureData.STREAM_CODEC, NeoForgeNetworkManager.getInstance()::processTemperatureData);
        /*
        event.registrar(Homeostatic.MODID).play(ThermometerData.ID, NeoForgeThermometerData::new,
            handler -> handler.client(NeoForgeNetworkManager.getInstance()::processThermometerData));
        event.registrar(Homeostatic.MODID).play(WaterData.ID, NeoForgeWaterData::new,
            handler -> handler.client(NeoForgeNetworkManager.getInstance()::processWaterData));
        event.registrar(Homeostatic.MODID).play(WetnessData.ID, NeoForgeWetnessData::new,
            handler -> handler.client(NeoForgeNetworkManager.getInstance()::processWetnessData));

         */
    }

    private static <T> void bind(IEventBus bus, ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        bus.addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }

}
