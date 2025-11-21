package homeostaticseasons.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import technology.roughness.whitenoise.platform.Services;

import homeostaticseasons.api.HomeostaticSeasonsAPI;
import homeostaticseasons.common.biome.BiomeColormap;
import homeostaticseasons.common.biome.BiomeColormapManager;
import homeostaticseasons.config.ConfigHandler;

@Mixin(FoliageColor.class)
public class FoliageColorMixin {

    @Inject(method = "getBirchColor", at = @At("RETURN"), cancellable = true)
    private static void homeostaticseasons$modifyBirchFoliageColor(CallbackInfoReturnable<Integer> cir) {
        if (Services.PLATFORM.isPhysicalClient() && ConfigHandler.Client.changeBirchColor()) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            Level level = mc.level;
            int originalColor = cir.getReturnValue();

            if (player != null && level != null) {
                Holder<Biome> biomeHolder = level.getBiome(player.blockPosition());
                BiomeColormap biomeColormap = BiomeColormapManager.getColormap(
                    biomeHolder,
                    HomeostaticSeasonsAPI.getCurrentSeason(level)
                );

                if (biomeColormap != null) {
                    int newColor = biomeColormap.getBirchColor(originalColor, biomeHolder);

                    if (newColor != originalColor) {
                        cir.setReturnValue(newColor);
                    }
                }
            }
        }
    }

}
