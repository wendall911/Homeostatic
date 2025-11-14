package climatesettings.platform;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

import climatesettings.common.biome.HomeostaticClimateSettings;
import climatesettings.platform.services.IClimate;

public class NeoForgeClimate implements IClimate {

    @Override
    public HomeostaticClimateSettings getClimateSettings(Holder<Biome> biomeHolder) {
        Biome.ClimateSettings climateSettings = biomeHolder.value().getModifiedClimateSettings();

        return new HomeostaticClimateSettings(
            biomeHolder,
            climateSettings.hasPrecipitation(),
            climateSettings.temperature(),
            climateSettings.temperatureModifier(),
            climateSettings.downfall()
        );
    }

}
