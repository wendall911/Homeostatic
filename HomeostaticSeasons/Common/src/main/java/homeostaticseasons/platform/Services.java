package homeostaticseasons.platform;

import technology.roughness.whitenoise.platform.ServicesBase;

import homeostaticseasons.HomeostaticSeasons;
import homeostaticseasons.platform.services.IPlatform;

public class Services extends ServicesBase {

    public static final IPlatform PLATFORM = load(HomeostaticSeasons.LOGGER, IPlatform.class);

}
