package homeostatic.common.biome;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;

import net.minecraft.resources.ResourceLocation;

import static homeostatic.Homeostatic.loc;

public class FabricBiomeCategoryManager extends BiomeCategoryManager implements IdentifiableResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
        return loc("reload_biome_category");
    }

}
