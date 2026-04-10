package homeostatic.util;

import net.minecraft.world.entity.player.Player;

import homeostatic.data.integration.ModIntegration;
import homeostatic.platform.Services;

import static technology.roughness.whitenoise.platform.Services.WN_PLATFORM;

public class VampirismHelper {

    public static boolean isVampire(Player player) {
        if (WN_PLATFORM.isModLoaded(ModIntegration.VAMPIRISM_MODID)) {
            return Services.PLATFORM.isVampire(player);
        }

        return false;
    }

}
