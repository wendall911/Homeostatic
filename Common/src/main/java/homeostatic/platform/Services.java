package homeostatic.platform;

import java.util.ServiceLoader;

import homeostatic.Homeostatic;
import homeostatic.platform.services.IClientPlatform;
import homeostatic.platform.services.IPlatform;

public class Services {

    public static final IClientPlatform CLIENT_PLATFORM = load(IClientPlatform.class);
    public static final IPlatform PLATFORM = load(IPlatform.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
            .findFirst()
            .orElseThrow(
                () -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Homeostatic.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);

        return loadedService;
    }

}
