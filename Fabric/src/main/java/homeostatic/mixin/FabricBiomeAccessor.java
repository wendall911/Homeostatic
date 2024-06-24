package homeostatic.mixin;

import net.minecraft.world.level.biome.Biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Biome.class)
public interface FabricBiomeAccessor {

    @Accessor("climateSettings")
    Biome.ClimateSettings homoestatic$getClimateSettings();

}
