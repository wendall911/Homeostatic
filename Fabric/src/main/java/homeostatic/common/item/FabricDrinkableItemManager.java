package homeostatic.common.item;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;

import net.minecraft.resources.ResourceLocation;

import static homeostatic.Homeostatic.loc;

public class FabricDrinkableItemManager extends DrinkableItemManager implements IdentifiableResourceReloadListener {

    @Override
    public ResourceLocation getFabricId() {
        return loc("reload_drinkable_items");
    }

}
