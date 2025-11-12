package climatesettings.platform.services;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

import climatesettings.common.biome.HomeostaticClimateSettings;

public interface IClimate {

    HomeostaticClimateSettings getClimateSettings(Holder<Biome> biomeHolder);

}
