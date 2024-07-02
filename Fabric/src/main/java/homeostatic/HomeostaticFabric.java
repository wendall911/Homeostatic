package homeostatic;

import java.util.function.BiConsumer;

import homeostatic.network.DrinkWater;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import homeostatic.common.biome.FabricBiomeCategoryManager;
import homeostatic.common.block.FabricBlockRadiationManager;
import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.component.HomeostaticComponents;
import homeostatic.common.components.HomeostaticCardinalComponents;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.FabricCreativeTabs;
import homeostatic.common.fluid.FabricDrinkingFluidManager;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.FabricDrinkableItemManager;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.recipe.HomeostaticRecipes;
import homeostatic.event.ServerEventListener;
import homeostatic.util.WaterHelper;

public class HomeostaticFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        registryInit();
        Homeostatic.init();
        ServerEventListener.init();

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new FabricBiomeCategoryManager());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new FabricBlockRadiationManager());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new FabricDrinkingFluidManager());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new FabricDrinkableItemManager());

        PayloadTypeRegistry.playC2S().register(DrinkWater.TYPE, DrinkWater.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(DrinkWater.TYPE, ((payload, context) -> {
            WaterHelper.drinkWater(context.player());
        }));
    }

    private void registryInit() {
        HomeostaticBlocks.init(bind(BuiltInRegistries.BLOCK));
        HomeostaticEffects.init(bind(BuiltInRegistries.MOB_EFFECT));
        HomeostaticFluids.init(bind(BuiltInRegistries.FLUID));
        HomeostaticRecipes.init(bind(BuiltInRegistries.RECIPE_SERIALIZER));
        HomeostaticItems.init(bind(BuiltInRegistries.ITEM));
        FabricCreativeTabs.init(bind(BuiltInRegistries.CREATIVE_MODE_TAB));
        HomeostaticComponents.registerDataComponents();
    }

    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }

}
