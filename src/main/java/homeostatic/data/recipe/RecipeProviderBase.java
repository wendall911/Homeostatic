package homeostatic.data.recipe;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;

import homeostatic.mixin.RecipeProviderAccessor;

import static homeostatic.Homeostatic.loc;

public abstract class RecipeProviderBase implements DataProvider {

    private final DataGenerator generator;

    protected RecipeProviderBase(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(@Nonnull HashCache cache) throws IllegalStateException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> recipes = Sets.newHashSet();

        registerRecipes((provider) -> {
            if (recipes.add(provider.getId())) {
                RecipeProviderAccessor.callSaveRecipe(cache, provider.serializeRecipe(),
                    path.resolve("data/" + provider.getId().getNamespace() + "/recipes/" + provider.getId().getPath() + ".json"));
                JsonObject json = provider.serializeAdvancement();

                if (json != null) {
                    saveRecipeAdvancement(generator, cache, json,
                        path.resolve("data/" + provider.getId().getNamespace() + "/advancements/" + provider.getAdvancementId().getPath() + ".json"));
                }
            }
            else {
                throw new IllegalStateException("Duplicate recipe " + provider.getId());
            }
        });
    }

    protected abstract void registerRecipes(Consumer<FinishedRecipe> consumer);

    public static void saveRecipeAdvancement(DataGenerator gen, HashCache cache, JsonObject json, Path path) {
        ((RecipeProviderAccessor) new RecipeProvider(gen)).callSaveRecipeAdvancement(cache, json, path);
    }

    protected static void specialRecipe(Consumer<FinishedRecipe> consumer, SimpleRecipeSerializer<?> serializer) {
        ResourceLocation name = Registry.RECIPE_SERIALIZER.getKey(serializer);

        SpecialRecipeBuilder.special(serializer).save(consumer, loc("dynamic/" + Objects.requireNonNull(name).getPath()).toString());
    }

}