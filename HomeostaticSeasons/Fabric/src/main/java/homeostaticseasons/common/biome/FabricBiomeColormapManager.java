package homeostaticseasons.common.biome;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

import static homeostaticseasons.HomeostaticSeasons.prefix;

public class FabricBiomeColormapManager extends BiomeColormapManager implements IdentifiableResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
        return prefix("reload_biome_colormaps");
    }

}
