package homeostatic.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;

import sereneseasons.config.BiomeConfig;
import sereneseasons.api.season.Season.SubSeason;
import sereneseasons.api.season.SeasonHelper;

import homeostatic.Homeostatic;
import homeostatic.util.Alignment;
import homeostatic.util.ColorHelper;
import homeostatic.util.FontHelper;

import java.util.Objects;

public class SeasonInfo extends Info {

    public SeasonInfo(String label, int lineNum) {
        super(label, lineNum);
    }

    @Override
    public void renderText(PoseStack matrix, Minecraft mc, BlockPos pos, int scaledWidth, int scaledHeight) {
        if (BiomeConfig.enablesSeasonalEffects(Objects.requireNonNull(mc.level).getBiome(pos))) {

            SubSeason subSeason = SeasonHelper.getSeasonState(mc.level).getSubSeason();

            if (BiomeConfig.enablesSeasonalEffects(mc.level.getBiome(pos))) {
                TranslatableComponent seasonName = new TranslatableComponent(Util.makeDescriptionId("season",
                        ResourceLocation.tryParse(subSeason.name().toLowerCase())));

                int x = Alignment.getX(scaledWidth, mc.font.width(super.label) + mc.font.width(seasonName));
                int y = Alignment.getY(scaledHeight, super.lineNum, mc.font.lineHeight);

                FontHelper.draw(mc, matrix, seasonName, x, y, ColorHelper.getSeasonColor(subSeason));
            }
        }
    }

}
