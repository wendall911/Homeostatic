package climatesettings.platform;

import climatesettings.ClimateSettings;
import climatesettings.platform.services.IClimate;

import technology.roughness.whitenoise.platform.ServicesBase;

public class Services extends ServicesBase {

    public static final IClimate CLIMATE = load(ClimateSettings.LOGGER, IClimate.class);

}
