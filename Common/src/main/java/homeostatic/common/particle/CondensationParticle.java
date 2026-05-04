package homeostatic.common.particle;

import org.jspecify.annotations.NonNull;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import homeostatic.config.ConfigHandler;

public class CondensationParticle extends BaseAshSmokeParticle {

    protected CondensationParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z, 0.1F, 0.1F, 0.1F, xSpeed, ySpeed, zSpeed, 1.0F, spriteSet, 0.3F, 8, 0.01F, true);

        setColor(1.0F, 1.0F, 1.0F);
        setAlpha(0.5F);
    }

    @Override
    public @NonNull Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();

        if (age >= (lifetime / 3) * 2) {
            alpha = Mth.clamp(alpha - 0.1F, 0.1F, 1.0F);
        }
    }

    public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(@NonNull SimpleParticleType simpleParticleType,
                @NonNull ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed,
                double zSpeed, @NonNull RandomSource randomSource) {
            CondensationParticle particle = new CondensationParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);

            particle.setAlpha(ConfigHandler.Client.condensationOpacity());

            return particle;
        }

    }

}
