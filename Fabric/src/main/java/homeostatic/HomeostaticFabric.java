package homeostatic;

import java.util.function.BiConsumer;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.FabricPotionBrewingBuilder;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

import homeostatic.common.block.FabricBlockRadiationManager;
import homeostatic.common.block.HomeostaticBlocks;
import homeostatic.common.component.HomeostaticComponents;
import homeostatic.common.effect.HomeostaticEffects;
import homeostatic.common.FabricCreativeTabs;
import homeostatic.common.fluid.FabricDrinkingFluidManager;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.FabricDrinkableItemManager;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.particle.HomeostaticParticles;
import homeostatic.common.potions.HomeostaticPotions;
import homeostatic.common.recipe.HomeostaticRecipes;
import homeostatic.event.ServerEventListener;
import homeostatic.network.DrinkWater;
import homeostatic.network.SyncDrinkableItems;
import homeostatic.network.SyncDrinkingFluids;
import homeostatic.util.WaterHelper;

public class HomeostaticFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        registryInit();
        Homeostatic.init();
        ServerEventListener.init();

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new FabricBlockRadiationManager());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new FabricDrinkingFluidManager());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new FabricDrinkableItemManager());

        PayloadTypeRegistry.serverboundPlay().register(DrinkWater.TYPE, DrinkWater.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(DrinkWater.TYPE, ((payload, context) -> {
            WaterHelper.drinkWater(context.player());
        }));
        PayloadTypeRegistry.clientboundPlay().register(SyncDrinkingFluids.TYPE, SyncDrinkingFluids.CODEC);
        PayloadTypeRegistry.clientboundPlay().register(SyncDrinkableItems.TYPE, SyncDrinkableItems.CODEC);

        FabricPotionBrewingBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(
                Potions.AWKWARD,
                Ingredient.of(Items.SNOWBALL),
                BuiltInRegistries.POTION.wrapAsHolder(HomeostaticPotions.FROST_RESISTANCE)
            );
            builder.registerPotionRecipe(
                BuiltInRegistries.POTION.wrapAsHolder(HomeostaticPotions.FROST_RESISTANCE),
                Ingredient.of(Items.REDSTONE),
                BuiltInRegistries.POTION.wrapAsHolder(HomeostaticPotions.LONG_FROST_RESISTANCE)
            );
        });
    }

    private void registryInit() {
        HomeostaticBlocks.init(bind(BuiltInRegistries.BLOCK));
        HomeostaticEffects.init(bind(BuiltInRegistries.MOB_EFFECT));
        HomeostaticFluids.init(bind(BuiltInRegistries.FLUID));
        HomeostaticRecipes.init(bind(BuiltInRegistries.RECIPE_SERIALIZER));
        HomeostaticItems.init(bind(BuiltInRegistries.ITEM));
        FabricCreativeTabs.init(bind(BuiltInRegistries.CREATIVE_MODE_TAB));
        HomeostaticPotions.init(bind(BuiltInRegistries.POTION));
        HomeostaticParticles.init(bind(BuiltInRegistries.PARTICLE_TYPE));
        HomeostaticComponents.registerDataComponents();
    }

    private static <T> BiConsumer<T, Identifier> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }

}
