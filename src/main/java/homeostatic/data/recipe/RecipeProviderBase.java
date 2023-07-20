package homeostatic.data.recipe;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.ItemLike;

import homeostatic.mixin.RecipeProviderAccessor;

import static homeostatic.Homeostatic.loc;

public abstract class RecipeProviderBase implements DataProvider {

    private final DataGenerator generator;

    protected RecipeProviderBase(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(@Nonnull CachedOutput cache) throws IllegalStateException {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> recipes = Sets.newHashSet();

        registerRecipes((provider) -> {
            if (recipes.add(provider.getId())) {
                JsonObject advancement = provider.serializeAdvancement();

                RecipeProviderAccessor.callSaveRecipe(cache, provider.serializeRecipe(), path.resolve(
                        "data/"
                        + provider.getId().getNamespace()
                        + "/recipes/"
                        + provider.getId().getPath()
                        + ".json"
                ));

                if (advancement != null) {
                    saveRecipeAdvancement(this.generator, cache, advancement, path.resolve(
                        "data/"
                        + provider.getId().getNamespace()
                        + "/advancements/"
                        + provider.getAdvancementId().getPath()
                        + ".json"
                    ));
                }
            }
            else {
                throw new IllegalStateException("Duplicate recipe " + provider.getId());
            }
        });
    }

    protected abstract void registerRecipes(Consumer<FinishedRecipe> consumer);

    public static void saveRecipeAdvancement(DataGenerator gen, CachedOutput cache, JsonObject json, Path path) {
        ((RecipeProviderAccessor) new RecipeProvider(gen)).homeostatic$saveAdvancement(cache, json, path);
    }

    public static InventoryChangeTrigger.TriggerInstance conditionsFromItem(ItemLike item) {
        return RecipeProviderAccessor.homeostatic_condition(ItemPredicate.Builder.item().of(item).build());
    }

    public static InventoryChangeTrigger.TriggerInstance conditionsFromTag(TagKey<Item> key) {
        return RecipeProviderAccessor.homeostatic_condition(ItemPredicate.Builder.item().of(key).build());
    }

    protected static void specialRecipe(Consumer<FinishedRecipe> consumer, SimpleRecipeSerializer<?> serializer) {
        ResourceLocation name = Registry.RECIPE_SERIALIZER.getKey(serializer);

        SpecialRecipeBuilder.special(serializer).save(consumer, loc("dynamic/" + Objects.requireNonNull(name).getPath()).toString());
    }

}