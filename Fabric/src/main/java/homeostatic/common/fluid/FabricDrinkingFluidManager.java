package homeostatic.common.fluid;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;

import net.minecraft.resources.Identifier;

import static homeostatic.Homeostatic.prefix;

public class FabricDrinkingFluidManager extends DrinkingFluidManager implements IdentifiableResourceReloadListener {

    @Override
    public Identifier getFabricId() {
        return prefix("reload_drinking_fluids");
    }

}
