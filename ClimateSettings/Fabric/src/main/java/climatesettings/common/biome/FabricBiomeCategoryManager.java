package climatesettings.common.biome;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;

import net.minecraft.resources.ResourceLocation;

import static climatesettings.ClimateSettings.prefix;

public class FabricBiomeCategoryManager extends BiomeCategoryManager implements IdentifiableResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
        return prefix("reload_biome_category");
    }

}
