package homeostatic.common.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;

import net.minecraft.resources.ResourceLocation;

import static homeostatic.Homeostatic.loc;

public class HomeostaticComponents implements EntityComponentInitializer {

    public static ResourceLocation DRINK_WATER_KEY = loc("drink_water");
    public static final ComponentKey<ComponentTemperatureData> TEMPERATURE_DATA =
        ComponentRegistry.getOrCreate(loc("temperature_data_provider"), ComponentTemperatureData.class);
    public static final ComponentKey<ComponentThermometerData> THERMOMETER_DATA =
        ComponentRegistry.getOrCreate(loc("thermometer_data_provider"), ComponentThermometerData.class);
    public static final ComponentKey<ComponentWaterData> WATER_DATA =
        ComponentRegistry.getOrCreate(loc("water_data_provider"), ComponentWaterData.class);
    public static final ComponentKey<ComponentWetnessData> WETNESS_DATA =
        ComponentRegistry.getOrCreate(loc("wetness_data_provider"), ComponentWetnessData.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(TEMPERATURE_DATA, player -> new ComponentTemperatureData(), RespawnCopyStrategy.NEVER_COPY);
        registry.registerForPlayers(THERMOMETER_DATA, player -> new ComponentThermometerData(), RespawnCopyStrategy.NEVER_COPY);
        registry.registerForPlayers(WATER_DATA, player -> new ComponentWaterData(), RespawnCopyStrategy.NEVER_COPY);
        registry.registerForPlayers(WETNESS_DATA, player -> new ComponentWetnessData(), RespawnCopyStrategy.NEVER_COPY);
    }

}
