package homeostatic.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import homeostatic.common.recipe.CampfirePurifiedLeatherFlask;
import homeostatic.common.recipe.CampfirePurifiedWaterBottle;
import homeostatic.common.recipe.SmeltingPurifiedLeatherFlask;
import homeostatic.common.recipe.SmeltingPurifiedWaterBottle;
import homeostatic.common.recipe.SmokingPurifiedLeatherFlask;
import homeostatic.common.recipe.SmokingPurifiedWaterBottle;

public class AdvancedCookingRecipeBuilder implements RecipeBuilder {
    private final ItemStackTemplate result;
    private final Ingredient ingredient;
    private final RecipeCategory category;
    private final CookingBookCategory bookCategory;
    private final float experience;
    private final int cookingTime;
    @Nullable
    private String group;
    private final AbstractCookingRecipe.Factory<?> factory;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    private AdvancedCookingRecipeBuilder(RecipeCategory pBookCategory, CookingBookCategory pCookingBookCategory, ItemStackTemplate result, Ingredient pIngredient, float pExperience, int pCookingTime, AbstractCookingRecipe.Factory<?> pFactory) {
        this.result = result;
        this.ingredient = pIngredient;
        this.experience = pExperience;
        this.cookingTime = pCookingTime;
        this.factory = pFactory;
        this.bookCategory = pCookingBookCategory;
        this.category = pBookCategory;
    }

    private AdvancedCookingRecipeBuilder(RecipeCategory pBookCategory, CookingBookCategory pCookingBookCategory, ItemLike result, Ingredient pIngredient, float pExperience, int pCookingTime, AbstractCookingRecipe.Factory<?> pFactory) {
        this(pBookCategory, pCookingBookCategory, new ItemStackTemplate(result.asItem()), pIngredient, pExperience, pCookingTime, pFactory);
    }

    public static AdvancedCookingRecipeBuilder cooking(RecipeCategory pBookCategory, CookingBookCategory pCookingBookCategory, ItemLike pResult, Ingredient pIngredient, float pExperience, int pCookingTime, AbstractCookingRecipe.Factory<?> pFactory) {
        return new AdvancedCookingRecipeBuilder(pBookCategory, pCookingBookCategory, pResult, pIngredient, pExperience, pCookingTime, pFactory);
    }

    public static AdvancedCookingRecipeBuilder campfireLeatherFlaskCooking(Ingredient pIngredient, RecipeCategory recipeCategory, ItemLike pResult, float pExperience, int pCookingTime) {
        return cooking(recipeCategory, CookingBookCategory.MISC, pResult, pIngredient, pExperience, pCookingTime, CampfirePurifiedLeatherFlask::new);
    }

    public static AdvancedCookingRecipeBuilder leatherFlaskSmelting(Ingredient pIngredient, RecipeCategory recipeCategory, ItemLike pResult, float pExperience, int pCookingTime) {
        return cooking(recipeCategory, CookingBookCategory.MISC, pResult, pIngredient, pExperience, pCookingTime, SmeltingPurifiedLeatherFlask::new);
    }

    public static AdvancedCookingRecipeBuilder leatherFlaskSmoking(Ingredient pIngredient, RecipeCategory recipeCategory, ItemLike pResult, float pExperience, int pCookingTime) {
        return cooking(recipeCategory, CookingBookCategory.MISC, pResult, pIngredient, pExperience, pCookingTime, SmokingPurifiedLeatherFlask::new);
    }

    public static AdvancedCookingRecipeBuilder campfireWaterBottleCooking(Ingredient pIngredient, RecipeCategory recipeCategory, ItemLike pResult, float pExperience, int pCookingTime) {
        return cooking(recipeCategory, CookingBookCategory.MISC, pResult, pIngredient, pExperience, pCookingTime, CampfirePurifiedWaterBottle::new);
    }

    public static AdvancedCookingRecipeBuilder waterBottleSmelting(Ingredient pIngredient, RecipeCategory recipeCategory, ItemLike pResult, float pExperience, int pCookingTime) {
        return cooking(recipeCategory, CookingBookCategory.MISC, pResult, pIngredient, pExperience, pCookingTime, SmeltingPurifiedWaterBottle::new);
    }

    public static AdvancedCookingRecipeBuilder waterBottleSmoking(Ingredient pIngredient, RecipeCategory recipeCategory, ItemLike pResult, float pExperience, int pCookingTime) {
        return cooking(recipeCategory, CookingBookCategory.MISC, pResult, pIngredient, pExperience, pCookingTime, SmokingPurifiedWaterBottle::new);
    }

    @Override
    public @NonNull RecipeBuilder unlockedBy(@NonNull String criterionName, @NonNull Criterion<?> criterion) {
        this.criteria.put(criterionName, criterion);

        return this;
    }

    public @NonNull AdvancedCookingRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;

        return this;
    }

    @Override
    public @NonNull ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    public @NonNull Item getResult() {
        return this.result.create().getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> resourceKey) {
        this.ensureValid(resourceKey.identifier());
        Advancement.Builder advancement = recipeOutput.advancement().addCriterion(
            "has_the_recipe",
            RecipeUnlockedTrigger.unlocked(resourceKey)
        ).rewards(AdvancementRewards.Builder.recipe(resourceKey)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(advancement);
        this.criteria.forEach(advancement::addCriterion);
        AbstractCookingRecipe recipe = this.factory.create(
            RecipeBuilder.createCraftingCommonInfo(true),
            new AbstractCookingRecipe.CookingBookInfo(this.bookCategory, Objects.requireNonNullElse(this.group, "")),
            this.ingredient,
            this.result,
            this.experience,
            this.cookingTime
        );
        recipeOutput.accept(resourceKey, recipe, advancement.build(resourceKey.identifier().withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(Identifier resourceLocation) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
        }
    }

}