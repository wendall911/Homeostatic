package homeostatic.data.integration.patchouli;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;

import xyz.brassgoggledcoders.patchouliprovider.AbstractPageBuilder;
import xyz.brassgoggledcoders.patchouliprovider.EntryBuilder;

public abstract class AbstractRecipePageBuilder<T extends AbstractRecipePageBuilder<T>> extends AbstractPageBuilder<T> {

    private final String recipe;
    private final String processor;
    private String title;
    private String text;

    protected AbstractRecipePageBuilder(String type, String processor, ResourceLocation recipe, EntryBuilder parent) {
        super(type, parent);

        this.recipe = recipe.toString();
        this.processor = processor;
    }

    @Override
    protected void serialize(JsonObject json) {
        json.addProperty("recipe", recipe);
        json.addProperty("processor", processor);

        if (title != null) {
            json.addProperty("title", title);
        }

        if (text != null) {
            json.addProperty("text", text);
        }
    }

    public T setTitle(String title) {
        this.title = title;

        return (T) this;
    }

    public T setText(String text) {
        this.text = text;

        return (T) this;
    }

}
