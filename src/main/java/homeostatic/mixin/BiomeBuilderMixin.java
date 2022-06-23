package homeostatic.mixin;

import javax.annotation.Nullable;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeBuilder;

import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import homeostatic.common.biome.BiomeData;
import homeostatic.common.biome.BiomeRegistry;
import homeostatic.Homeostatic;

@Mixin(BiomeBuilder.class)
public abstract class BiomeBuilderMixin {

    @Shadow @Nullable private BiomeGenerationSettings generationSettings;

    @Shadow private Biome.TemperatureModifier temperatureModifier;

    @Shadow public abstract BiomeBuilder temperature(float pTemperature);

    @Shadow @Nullable private Biome.Precipitation precipitation;

    @Shadow @Nullable private MobSpawnSettings mobSpawnSettings;

    @Shadow @Nullable private Float temperature;

    @Shadow @Nullable private Float downfall;

    /*
     * Override default minecraft temperatures with ones that make a little more sense.
     * It is already silly that we can have icy next to desert and whatnot, but this at
     * least makes it so we can blend a little better. Cold biomes should just be below
     * freezing, not insane cold temperatures. Hot biomes shouldn't be 120 degrees in the
     * spring, etc. Also remove dumb things like savanna being hotter than a desert. I
     * guess the Minecraft devs never even did a basic lookup of weather and temperature.
     *
     * This, combined with humidity is what will determine the actual temperature the player
     * experiences, so these numbers could be adjusted a bit depending on how play testing
     * works out.
     */
    @Inject(method = "build",
        at = @At("HEAD"))
    private void injectBuild(CallbackInfoReturnable<Biome> cir) {
        BiomeData biomeData = null;
        BiomeRegistry.BiomeCategory biomeCategory = BiomeRegistry.BiomeCategory.MISSING;

        if (this.generationSettings != null
                && this.temperature != null
                && this.mobSpawnSettings != null
                && this.downfall != null) {
            biomeCategory = BiomeRegistry.getBiomeCategory(this.generationSettings, this.temperature, this.mobSpawnSettings, this.temperatureModifier, this.downfall, this.precipitation);
        }

        if (biomeCategory != BiomeRegistry.BiomeCategory.MISSING) {
            biomeData = BiomeRegistry.BIOMES.get(biomeCategory);
        }

        if (biomeData != null) {
            this.temperature(biomeData.getTemperature(this.temperatureModifier, this.precipitation));
        }
        else {
            Homeostatic.LOGGER.warn("Not overriding biome %s", this.toString());
        }
    }

}
