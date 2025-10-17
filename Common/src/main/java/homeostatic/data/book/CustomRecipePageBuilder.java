package homeostatic.data.book;

import net.minecraft.resources.ResourceLocation;

import guidebook.api.data.EntryBuilder;
import guidebook.api.data.page.RecipePageBuilder;

import homeostatic.Homeostatic;

public class CustomRecipePageBuilder extends RecipePageBuilder<CustomRecipePageBuilder> {

    protected CustomRecipePageBuilder(ResourceLocation recipe, EntryBuilder parent) {
        super(Homeostatic.MODID + ":custom_crafting", recipe, parent);
    }

}
