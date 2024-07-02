package homeostatic.common.components;

import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

import static homeostatic.Homeostatic.loc;

public class HomeostaticCardinalComponents implements EntityComponentInitializer {

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
        registry.registerForPlayers(TEMPERATURE_DATA, player -> new ComponentTemperatureData(), RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(THERMOMETER_DATA, player -> new ComponentThermometerData(), RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(WATER_DATA, player -> new ComponentWaterData(), RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(WETNESS_DATA, player -> new ComponentWetnessData(), RespawnCopyStrategy.LOSSLESS_ONLY);
    }

}
