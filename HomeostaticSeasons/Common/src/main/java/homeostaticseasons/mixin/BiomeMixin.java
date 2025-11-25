package homeostaticseasons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import homeostaticseasons.api.SeasonWeather;

/*
 * These redirects ar needed to ensure that rain can occur in biomes
 * that are cold, but can rain in certain seasons.
 */
@Mixin(Biome.class)
public abstract class BiomeMixin {

    @Inject(method = "shouldSnow", at = @At("HEAD"), cancellable = true)
    public void homeostaticseasons$canPlaceSnow(LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(SeasonWeather.canPlaceSnow((Biome)(Object)this, pos, level));
    }

    @Redirect(method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z", at=@At(value = "INVOKE", target = "net/minecraft/world/level/biome/Biome.warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"))
    public boolean homeostaticseasons$shouldFreeze_warmEnoughToRain(Biome biome, BlockPos pos, LevelReader level) {
        return SeasonWeather.warmEnoughToRain(biome, pos, level);
    }

}

