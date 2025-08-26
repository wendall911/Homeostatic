package homeostatic.common.fluid;

import net.minecraft.resources.ResourceLocation;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import homeostatic.Homeostatic;

@EventBusSubscriber(modid = Homeostatic.MODID)
public class NeoForgeFluidClientHandler {

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return HomeostaticFluids.STILL_FLUID_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return HomeostaticFluids.FLOWING_FLUID_TEXTURE;
            }
        }, NeoForgeFluidType.PURIFIED_WATER_TYPE);
    }

}
