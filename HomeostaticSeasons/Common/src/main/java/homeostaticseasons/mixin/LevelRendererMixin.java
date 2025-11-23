package homeostaticseasons.mixin;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import homeostaticseasons.api.SeasonWeather;

/*
 * This updates the renderer to account for seasonal weather changes.
 * Should render rain or snow based on the current season and biome temperature.
 */
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Unique
    private Level renderLevel;

    @Unique
    private BlockPos renderBlockPos;

    @Redirect(method="renderSnowAndRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBiome(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Holder;"))
    public Holder<Biome> renderSnowAndRain_getBiome(Level level, BlockPos pos) {
        this.renderLevel = level;
        this.renderBlockPos = pos;

        return level.getBiome(pos);
    }

    @Redirect(method="renderSnowAndRain", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;hasPrecipitation()Z"))
    public boolean renderSnowAndRain_hasPrecipitation(Biome biome) {
        Biome.Precipitation precipitationType = SeasonWeather.getPrecipitationType(
            this.renderBlockPos,
            this.renderLevel
        );

        return precipitationType != Biome.Precipitation.NONE;
    }

}
