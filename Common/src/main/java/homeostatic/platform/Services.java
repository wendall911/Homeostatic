package homeostatic.platform;

import homeostatic.Homeostatic;
import homeostatic.platform.services.IClientPlatform;
import homeostatic.platform.services.IPlatform;

import technology.roughness.whitenoise.platform.ServicesBase;

public class Services extends ServicesBase {

    public static final IClientPlatform CLIENT_PLATFORM = load(Homeostatic.LOGGER, IClientPlatform.class);
    public static final IPlatform PLATFORM = load(Homeostatic.LOGGER, IPlatform.class);

}
