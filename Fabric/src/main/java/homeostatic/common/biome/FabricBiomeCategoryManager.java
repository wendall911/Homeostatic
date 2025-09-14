package homeostatic.common.biome;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;

import net.minecraft.resources.ResourceLocation;

import static homeostatic.Homeostatic.prefix;

public class FabricBiomeCategoryManager extends BiomeCategoryManager implements IdentifiableResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
        return prefix("reload_biome_category");
    }

}
