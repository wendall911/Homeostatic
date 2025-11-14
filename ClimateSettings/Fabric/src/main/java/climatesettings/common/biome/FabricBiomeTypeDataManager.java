package climatesettings.common.biome;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;

public class FabricBiomeTypeDataManager extends BiomeTypeDataManager implements IdentifiableResourceReloadListener {

    @Override
    public net.minecraft.resources.ResourceLocation getFabricId() {
        return climatesettings.ClimateSettings.prefix("reload_biome_type_data");
    }

}
