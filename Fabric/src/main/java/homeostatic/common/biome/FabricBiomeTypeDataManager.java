package homeostatic.common.biome;

public class FabricBiomeTypeDataManager extends BiomeTypeDataManager implements net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener {

    @Override
    public net.minecraft.resources.ResourceLocation getFabricId() {
        return homeostatic.Homeostatic.loc("reload_biome_type_data");
    }

}
