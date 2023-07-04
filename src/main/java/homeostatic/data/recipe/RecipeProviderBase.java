package homeostatic.data.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.ItemLike;

import homeostatic.mixin.RecipeProviderAccessor;

import static homeostatic.Homeostatic.loc;

public abstract class RecipeProviderBase implements DataProvider {

    private final PackOutput packOutput;

    protected RecipeProviderBase(@NotNull final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput cache) throws IllegalStateException {
        final PackOutput.PathProvider pathProvider = this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "recipes");
        final PackOutput.PathProvider advancementPathProvider = this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "advancements");
        Set<ResourceLocation> resourceLocationSet = Sets.newHashSet();
        List<CompletableFuture<?>> recipeList = new ArrayList<>();

        this.registerRecipes((recipe) -> {
            if (!resourceLocationSet.add(recipe.getId())) {
                throw new IllegalStateException("Duplicate recipe " + recipe.getId());
            }
            else {
                recipeList.add(DataProvider.saveStable(cache,
                        recipe.serializeRecipe(),
                        pathProvider.json(recipe.getId())));
                JsonObject advancement = recipe.serializeAdvancement();

                if (advancement != null) {
                    CompletableFuture<?> recipeAdvancement = saveAdvancement(cache, recipe, advancement, advancementPathProvider);

                    if (recipeAdvancement != null) {
                        recipeList.add(recipeAdvancement);
                    }
                }
            }
        });

        return CompletableFuture.allOf(recipeList.toArray(CompletableFuture[]::new));
    }

    protected abstract void registerRecipes(Consumer<FinishedRecipe> consumer);

    @Nullable
    protected CompletableFuture<?> saveAdvancement(CachedOutput cache, FinishedRecipe recipe, JsonObject json, PackOutput.PathProvider path) {
        return DataProvider.saveStable(cache, json, path.json(recipe.getAdvancementId()));
    }

    public static InventoryChangeTrigger.TriggerInstance conditionsFromItem(ItemLike item) {
        return RecipeProviderAccessor.homeostatic_condition(ItemPredicate.Builder.item().of(item).build());
    }

    public static InventoryChangeTrigger.TriggerInstance conditionsFromTag(TagKey<Item> key) {
        return RecipeProviderAccessor.homeostatic_condition(ItemPredicate.Builder.item().of(key).build());
    }

    protected static void specialRecipe(Consumer<FinishedRecipe> consumer, SimpleCraftingRecipeSerializer<?> serializer) {
        ResourceLocation name = BuiltInRegistries.RECIPE_SERIALIZER.getKey(serializer);

        SpecialRecipeBuilder.special(serializer).save(consumer, loc("dynamic/" + Objects.requireNonNull(name).getPath()).toString());
    }

    protected static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> pTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pTag).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... predicates) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY,
                MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, predicates);
    }

}