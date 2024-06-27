package homeostatic.common.recipe;

import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

public class SimpleCookingSerializerWrapper<T extends AbstractCookingRecipe> extends SimpleCookingSerializer<T> {

    private final Factory<T> factory;
    private final int cookingTime;

    public SimpleCookingSerializerWrapper(Factory<T> factory, int cookingTime) {
        super(null, cookingTime);
        this.factory = factory;
        this.cookingTime = cookingTime;
    }

    @Override
    public T fromNetwork(FriendlyByteBuf buf) {
        String id = buf.readUtf();
        CookingBookCategory category = buf.readEnum(CookingBookCategory.class);
        Ingredient ingredient = Ingredient.fromNetwork(buf);
        ItemStack result = buf.readItem();
        float experience = buf.readFloat();
        int cookingTime = buf.readVarInt();
        return this.factory.create(id, category, ingredient, result, experience, cookingTime);
    }

    public interface Factory<T extends AbstractCookingRecipe> {
        T create(
            String id,
            CookingBookCategory category,
            Ingredient ingredient,
            ItemStack result,
            float experience,
            int cookingTime
        );
    }
}
