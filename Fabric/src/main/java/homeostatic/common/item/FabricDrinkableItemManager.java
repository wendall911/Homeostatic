package homeostatic.common.item;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;

import net.minecraft.resources.Identifier;

import static homeostatic.Homeostatic.prefix;

public class FabricDrinkableItemManager extends DrinkableItemManager implements IdentifiableResourceReloadListener {

    @Override
    public Identifier getFabricId() {
        return prefix("reload_drinkable_items");
    }

}
