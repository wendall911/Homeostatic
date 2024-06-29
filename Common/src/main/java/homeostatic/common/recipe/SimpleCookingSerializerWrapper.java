package homeostatic.common.recipe;

import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

public class SimpleCookingSerializerWrapper<T extends AbstractCookingRecipe> extends SimpleCookingSerializer<T> {

    public SimpleCookingSerializerWrapper(AbstractCookingRecipe.Factory<T> factory, int cookingTime) {
        super(factory, cookingTime);
    }

}
