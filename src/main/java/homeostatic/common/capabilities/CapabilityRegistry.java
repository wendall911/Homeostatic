package homeostatic.common.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import homeostatic.Homeostatic;

@Mod.EventBusSubscriber(modid=Homeostatic.MODID)
public class CapabilityRegistry {

    public static final Capability<Temperature> TEMPERATURE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<Water> WATER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.register(Temperature.class);
        event.register(Water.class);
    }

}
