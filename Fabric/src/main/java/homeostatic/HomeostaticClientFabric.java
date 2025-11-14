package homeostatic;

import java.util.Map;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import homeostatic.common.book.PageCustomCrafting;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.event.ClientEventListener;
import homeostatic.network.SyncDrinkingFluids;

public class HomeostaticClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientEventListener.init();
        PageCustomCrafting.init();

        FluidRenderHandlerRegistry.INSTANCE.register(HomeostaticFluids.PURIFIED_WATER, HomeostaticFluids.PURIFIED_WATER_FLOWING, new SimpleFluidRenderHandler(
            HomeostaticFluids.STILL_FLUID_TEXTURE,
            HomeostaticFluids.FLOWING_FLUID_TEXTURE
        ));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            for (Map.Entry<ResourceLocation, Item> entry : HomeostaticItems.getAll().entrySet()) {
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
    }

}
