package homeostatic.common.block;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

import static homeostatic.Homeostatic.prefix;

public class FabricBlockRadiationManager extends BlockRadiationManager implements IdentifiableResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
        return prefix("reload_block_radiation");
    }

}
