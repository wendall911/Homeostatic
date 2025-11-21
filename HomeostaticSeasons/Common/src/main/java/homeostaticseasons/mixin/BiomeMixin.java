package homeostaticseasons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import homeostaticseasons.api.SeasonWeather;

/*
 * These redirects ar needed to ensure that rain can occur in biomes
 * that are cold, but can rain in certain seasons.
 */
@Mixin(Biome.class)
public abstract class BiomeMixin {

    @Redirect(method = "shouldSnow", at = @At(value = "INVOKE", target = "net/minecraft/world/level/biome/Biome.warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"))
    public boolean homeostaticseasons$shouldSnow_warmEnoughToRain(Biome biome, BlockPos pos, LevelReader level) {
        return SeasonWeather.warmEnoughToRain(biome, pos, level);
    }


    @Redirect(method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z", at=@At(value = "INVOKE", target = "net/minecraft/world/level/biome/Biome.warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"))
    public boolean homeostaticseasons$shouldFreeze_warmEnoughToRain(Biome biome, BlockPos pos, LevelReader level) {
        return SeasonWeather.warmEnoughToRain(biome, pos, level);
    }

}

