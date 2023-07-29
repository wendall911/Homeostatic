package homeostatic.mixin;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import homeostatic.common.biome.BiomeData;
import homeostatic.common.biome.BiomeRegistry;
import homeostatic.common.biome.ExtendedBiome;

@Mixin(Biome.class)
public abstract class BiomeMixin implements ExtendedBiome {

    @Shadow @Final private Biome.ClimateSettings climateSettings;

    private BiomeRegistry.BiomeCategory biomeCategory = BiomeRegistry.BiomeCategory.MISSING;

    @Override
    public void setBiomeCategory(BiomeRegistry.BiomeCategory biomeCategory) {
        this.biomeCategory = biomeCategory;
    }

    @Override
    public BiomeRegistry.BiomeCategory getBiomeCategory() {
        return this.biomeCategory;
    }

    @Inject(method = "getBaseTemperature", at = @At("HEAD"), cancellable = true)
    private void homeosttic$overrideBaseTemperature(CallbackInfoReturnable<Float> cir) {
        if (this.biomeCategory != BiomeRegistry.BiomeCategory.MISSING) {
            BiomeData biomeData = BiomeRegistry.BIOMES.get(this.biomeCategory);

            cir.setReturnValue(biomeData.getTemperature(this.climateSettings.temperatureModifier(), biomeData.getPrecipitation(this.climateSettings.temperature(), this.climateSettings.precipitation())));
        }
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void homeosttic$injectInit(Biome.ClimateSettings climateSettings, BiomeSpecialEffects specialEffects, BiomeGenerationSettings biomeGenerationSettings, MobSpawnSettings mobSpawnSettings, CallbackInfo ci) {
        this.biomeCategory = BiomeRegistry.getBiomeCategory(
                biomeGenerationSettings,
                climateSettings.temperature(),
                mobSpawnSettings,
                climateSettings.temperatureModifier(),
                climateSettings.downfall(),
                this.climateSettings.precipitation(),
                specialEffects
        );
    }

}
