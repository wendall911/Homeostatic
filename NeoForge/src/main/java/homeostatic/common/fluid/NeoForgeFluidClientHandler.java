package homeostatic.common.fluid;

import org.jspecify.annotations.NonNull;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

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
            public Identifier getRenderOverlayTexture(@NonNull Minecraft mc) {
                return HomeostaticFluids.STILL_FLUID_TEXTURE;
            }
        }, NeoForgeFluidType.PURIFIED_WATER_TYPE);
    }

}
