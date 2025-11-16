package homeostatic;

import java.util.Map;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.integration.ModIntegration;
import homeostatic.event.ClientEventListener;
import homeostatic.integrations.patchouli.PageCustomCrafting;
import homeostatic.platform.Services;

public class HomeostaticClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientEventListener.init();

        if (Services.PLATFORM.isModLoaded(ModIntegration.PATCHOULI_MODID)) {
            PageCustomCrafting.init();
        }

        FluidRenderHandlerRegistry.INSTANCE.register(HomeostaticFluids.PURIFIED_WATER, HomeostaticFluids.PURIFIED_WATER_FLOWING, new SimpleFluidRenderHandler(
            HomeostaticFluids.STILL_FLUID_TEXTURE,
            HomeostaticFluids.FLOWING_FLUID_TEXTURE
        ));

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            for (Map.Entry<ResourceLocation, Item> entry : HomeostaticItems.getAll().entrySet()) {
                entries.accept(entry.getValue());
            }
        });

    }

}
