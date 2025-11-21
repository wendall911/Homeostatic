package homeostaticseasons.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import homeostaticseasons.api.SeasonWeather;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {

    /*
     * Determine the precipitation type based on the current season and biome.
     */
    @Redirect(method="tickPrecipitation", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;getPrecipitationAt(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/biome/Biome$Precipitation;"))
    public Biome.Precipitation homeostaticseasons$tickPrecipitation(Biome biome, BlockPos pos) {
        return SeasonWeather.getPrecipitationType(biome, pos, (ServerLevel)(Object) this);
    }

}
