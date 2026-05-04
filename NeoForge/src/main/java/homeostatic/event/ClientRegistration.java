package homeostatic.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import homeostatic.common.particle.CondensationParticle;
import homeostatic.common.particle.HomeostaticParticles;

public class ClientRegistration {

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(HomeostaticParticles.CONDENSATION, CondensationParticle.Provider::new);
    }

}
