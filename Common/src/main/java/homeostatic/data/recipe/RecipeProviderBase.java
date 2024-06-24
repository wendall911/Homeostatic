package homeostatic.data.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.AdvancedCookingRecipeBuilder;

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

    public static ShapedRecipeBuilder leatherFlask() {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HomeostaticItems.LEATHER_FLASK)
            .define('S',Items.STRING)
            .define('L', Items.LEATHER)
            .define('P',ItemTags.PLANKS)
            .pattern("SPS")
            .pattern("L L")
            .pattern("LLL")
            .unlockedBy("has_leather", has(Items.LEATHER));
    }

    public static ShapedRecipeBuilder waterFilter() {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HomeostaticItems.WATER_FILTER)
            .define('P', Items.PAPER)
            .define('C', Items.CHARCOAL)
            .pattern("P")
            .pattern("C")
            .pattern("P")
            .unlockedBy("has_charcoal", has(Items.CHARCOAL));
    }

    public static ShapedRecipeBuilder thermometer() {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, HomeostaticItems.THERMOMETER)
            .define('N', Items.IRON_NUGGET)
            .define('D', Items.REDSTONE)
            .define('I', Items.IRON_INGOT)
            .pattern("N")
            .pattern("D")
            .pattern("I")
            .unlockedBy("has_redstone", has(Items.REDSTONE));
    }

    public static ShapelessRecipeBuilder book() {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, HomeostaticItems.BOOK)
            .requires(Items.DIRT)
            .group("books")
            .unlockedBy("has_dirt", has(Items.DIRT));
    }

    public static AdvancedCookingRecipeBuilder cleanWaterFlaskSmelting() {
        return AdvancedCookingRecipeBuilder.smelting(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            0.15F,
            200
        ).unlockedBy("has_leather_flask", has(HomeostaticItems.LEATHER_FLASK));
    }

    public static AdvancedCookingRecipeBuilder cleanWaterFlaskCampfire() {
        return AdvancedCookingRecipeBuilder.campfireCooking(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            0.15F,
            200
        ).unlockedBy("has_leather_flask", has(HomeostaticItems.LEATHER_FLASK));
    }

    public static AdvancedCookingRecipeBuilder cleanWaterFlaskSmoking() {
        return AdvancedCookingRecipeBuilder.smoking(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            0.15F,
            200
        ).unlockedBy("has_leather_flask", has(HomeostaticItems.LEATHER_FLASK));
    }

}