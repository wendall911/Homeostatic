package homeostatic.data.book;

import net.minecraft.resources.Identifier;

import handbook.api.data.EntryBuilder;
import handbook.api.data.page.RecipePageBuilder;

import homeostatic.Homeostatic;

public class CustomRecipePageBuilder extends RecipePageBuilder<CustomRecipePageBuilder> {

    protected CustomRecipePageBuilder(Identifier recipe, EntryBuilder parent) {
        super(Homeostatic.MODID + ":custom_crafting", recipe, parent);
    }

}
