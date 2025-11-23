package homeostaticseasons.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import homeostaticseasons.api.SeasonWeather;

@Mixin(Biome.class)
public abstract class ClientBiomeMixin {

    @Inject(method = "getPrecipitationAt", at = @At("HEAD"), cancellable = true)
    public void homeostaticseasons$getPrecipitationAt(BlockPos pos, CallbackInfoReturnable<Precipitation> cir) {
        Minecraft minecraft = Minecraft.getInstance();
        Level level = minecraft.level;

        if (level instanceof ClientLevel) {
            Precipitation precipitation = SeasonWeather.getPrecipitationType(pos, level);

            if (precipitation == null) {
                precipitation = Biome.Precipitation.NONE;
            }

            cir.setReturnValue(precipitation);
        }
    }

}
