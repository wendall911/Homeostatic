package homeostatic;

import java.util.Map;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.block.FluidModel.Unbaked;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import homeostatic.common.book.PageCustomCrafting;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.particle.CondensationParticle;
import homeostatic.common.particle.HomeostaticParticles;
import homeostatic.event.ClientEventListener;
import homeostatic.network.SyncDrinkableItems;
import homeostatic.network.SyncDrinkingFluids;

public class HomeostaticClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientEventListener.init();
        PageCustomCrafting.init();

        ParticleProviderRegistry.getInstance().register(HomeostaticParticles.CONDENSATION, CondensationParticle.Provider::new);

        FluidRenderingRegistry.register(HomeostaticFluids.PURIFIED_WATER, HomeostaticFluids.PURIFIED_WATER_FLOWING, new Unbaked(
            new Material(HomeostaticFluids.STILL_FLUID_TEXTURE),
            new Material(HomeostaticFluids.FLOWING_FLUID_TEXTURE),
            new Material(HomeostaticFluids.OVERLAY_FLUID_TEXTURE),
            BlockTintSources.constant(0xFF73bbd4)
        ));

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            for (Map.Entry<Identifier, Item> entry : HomeostaticItems.getAll().entrySet()) {
                entries.accept(entry.getValue());
            }
        });
        ClientPlayNetworking.registerGlobalReceiver(
            SyncDrinkingFluids.TYPE,
            (SyncDrinkingFluids packet, ClientPlayNetworking.Context context) -> {
                context.client().execute(() -> {
                    packet.handle(context.player());
                });
            }
        );
        ClientPlayNetworking.registerGlobalReceiver(
            SyncDrinkableItems.TYPE,
            (SyncDrinkableItems packet, ClientPlayNetworking.Context context) -> {
                context.client().execute(() -> {
                    packet.handle(context.player());
                });
            }
        );
    }

}
