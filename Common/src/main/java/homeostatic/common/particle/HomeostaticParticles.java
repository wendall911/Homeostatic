package homeostatic.common.particle;

import java.util.function.BiConsumer;
import java.util.Map;
import java.util.HashMap;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.Identifier;

import homeostatic.platform.Services;

import static homeostatic.Homeostatic.prefix;

public class HomeostaticParticles {

    public static final Map<String, ParticleType<?>> REGISTERED_PARTICLES = new HashMap<>();
    public static final SimpleParticleType CONDENSATION = registerSimple("condensation");

    private static SimpleParticleType registerSimple(String name) {
        SimpleParticleType simpleParticleType = Services.PLATFORM.simpleParticleType();

        REGISTERED_PARTICLES.put(name, simpleParticleType);

        return simpleParticleType;
    }

    public static void init(BiConsumer<ParticleType<?>, Identifier> consumer) {
        REGISTERED_PARTICLES.forEach((name, particleType) -> consumer.accept(particleType, prefix(name)));
    }

}
