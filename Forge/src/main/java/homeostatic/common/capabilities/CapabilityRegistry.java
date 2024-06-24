package homeostatic.common.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class CapabilityRegistry {

    public static final Capability<ITemperature> TEMPERATURE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IWater> WATER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IWetness> WETNESS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IThermometer> THERMOMETER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<IFluidHandlerItem> FLUID_ITEM_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.register(TemperatureCapability.class);
        event.register(WaterCapability.class);
        event.register(WetnessCapability.class);
        event.register(ThermometerCapability.class);
    }

}