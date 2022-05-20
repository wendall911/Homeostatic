package homeostatic.mixin;

import javax.annotation.Nullable;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import homeostatic.Homeostatic;

@Mixin(BiomeBuilder.class)
public abstract class BiomeBuilderMixin {

    @Shadow @Nullable private Biome.BiomeCategory biomeCategory;

    @Shadow private Biome.TemperatureModifier temperatureModifier;

    @Shadow public abstract BiomeBuilder temperature(float pTemperature);

    @Shadow @Nullable private Biome.Precipitation precipitation;

    @Shadow @Nullable private Float temperature;

    /*
     * Override default minecraft temperatures with ones that make a little more sense.
     * It is already silly that we can have icy next to desert and whatnot, but this at
     * least makes it so we can blend a little better. Icy biomes should just be below
     * freezing, not insane cold temperatures. Hot biomes shouldn't be 120 degrees in the
     * spring, etc. Also remove dumb things like savanna being hotter than a desert. I
     * guess the Minecraft devs never even did a basic lookup of weather and temperature.
     *
     * This, combined with humidity is what will determine the actual temperature the player
     * experiences, so these numbers could be adjusted a bit depending on how play testing
     * works out.
     */
    @Inject(method = "build",
        at = @At("HEAD"),
        cancellable = true)
    private void injectBuild(CallbackInfoReturnable<Biome> cir) {
        if (this.biomeCategory != null) {
            switch(this.biomeCategory) {
                case BEACH:
                    this.temperature(0.886F);
                    break;
                case DESERT:
                    this.temperature(1.354F);
                    break;
                case EXTREME_HILLS:
                    this.temperature(0.841F);
                    break;
                case FOREST:
                    this.temperature(0.886F);
                    break;
                case ICY:
                    this.temperature(0.105F);
                    break;
                case JUNGLE:
                    this.temperature(1.108F);
                    break;
                case MESA:
                    this.temperature(1.309F);
                    break;
                case MOUNTAIN:
                    this.temperature(0.841F);
                    break;
                case MUSHROOM:
                    this.temperature(0.908F);
                    break;
                case NETHER:
                    this.temperature(1.666F);
                    break;
                case NONE:
                    this.temperature(0.15F);
                    break;
                case OCEAN:
                    this.temperature(0.663F);
                    break;
                case PLAINS:
                    this.temperature(0.997F);
                    break;
                case RIVER:
                    this.temperature(0.663F);
                    break;
                case SAVANNA:
                    this.temperature(1.220F);
                    break;
                case SWAMP:
                    this.temperature(0.886F);
                    break;
                case TAIGA:
                    this.temperature(0.730F);
                    break;
                case THEEND:
                    this.temperature(0.551F);
                    break;
                case UNDERGROUND:
                    this.temperature(0.663F);
                    break;
            }

            switch(this.precipitation) {
                case SNOW:
                    if (this.temperature > 0.105F) {
                        this.temperature(0.217F);
                    }
                    break;
            }

            if (this.temperatureModifier == Biome.TemperatureModifier.FROZEN) {
                this.temperature(0.105F);
            }

        }
        Homeostatic.LOGGER.warn(this.toString());
    }

}
