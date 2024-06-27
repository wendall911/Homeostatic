package homeostatic.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import homeostatic.common.recipe.CampfirePurifiedLeatherFlask;
import homeostatic.common.recipe.SmeltingPurifiedLeatherFlask;
import homeostatic.common.recipe.SmokingPurifiedLeatherFlask;

public class AdvancedCookingRecipeBuilder implements RecipeBuilder {
   private final Item result;
   private final Ingredient ingredient;
   private final RecipeCategory category;
   private final CookingBookCategory bookCategory;
   private final float experience;
   private final int cookingTime;
   @Nullable
   private String group;
   private final AbstractCookingRecipe.Factory<?> factory;
   private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

   private AdvancedCookingRecipeBuilder(RecipeCategory pBookCategory, CookingBookCategory pCookingBookCategory, ItemLike pResult, Ingredient pIngredient, float pExperience, int pCookingTime, AbstractCookingRecipe.Factory<?> pFactory) {
      this.result = pResult.asItem();
      this.ingredient = pIngredient;
      this.experience = pExperience;
      this.cookingTime = pCookingTime;
      this.factory = pFactory;
      this.bookCategory = pCookingBookCategory;
      this.category = pBookCategory;
   }

   public static AdvancedCookingRecipeBuilder cooking(RecipeCategory pBookCategory, CookingBookCategory pCookingBookCategory, ItemLike pResult, Ingredient pIngredient, float pExperience, int pCookingTime, AbstractCookingRecipe.Factory<?> pFactory) {
      return new AdvancedCookingRecipeBuilder(pBookCategory, pCookingBookCategory, pResult, pIngredient, pExperience, pCookingTime, pFactory);
   }

   public static AdvancedCookingRecipeBuilder campfireCooking(Ingredient pIngredient, RecipeCategory recipeCategory, ItemLike pResult, float pExperience, int pCookingTime) {
      return cooking(recipeCategory, CookingBookCategory.MISC, pResult, pIngredient, pExperience, pCookingTime, CampfirePurifiedLeatherFlask::new);
   }

   public static AdvancedCookingRecipeBuilder smelting(Ingredient pIngredient, RecipeCategory recipeCategory, ItemLike pResult, float pExperience, int pCookingTime) {
      return cooking(recipeCategory, CookingBookCategory.MISC, pResult, pIngredient, pExperience, pCookingTime, SmeltingPurifiedLeatherFlask::new);
   }

   public static AdvancedCookingRecipeBuilder smoking(Ingredient pIngredient, RecipeCategory recipeCategory, ItemLike pResult, float pExperience, int pCookingTime) {
      return cooking(recipeCategory, CookingBookCategory.MISC, pResult, pIngredient, pExperience, pCookingTime, SmokingPurifiedLeatherFlask::new);
   }

   @Override
   public @NotNull RecipeBuilder unlockedBy(@NotNull String criterionName, @NotNull Criterion<?> criterion) {
      this.criteria.put(criterionName, criterion);

      return this;
   }

   public @NotNull AdvancedCookingRecipeBuilder group(@Nullable String pGroupName) {
      this.group = pGroupName;

      return this;
   }

   public @NotNull Item getResult() {
      return this.result;
   }

   public void save(RecipeOutput recipeOutput, @NotNull ResourceLocation resourceLocation) {
      this.ensureValid(resourceLocation);
      Advancement.Builder advancement = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation)).rewards(AdvancementRewards.Builder.recipe(resourceLocation)).requirements(AdvancementRequirements.Strategy.OR);
       Objects.requireNonNull(advancement);
      this.criteria.forEach(advancement::addCriterion);
      AbstractCookingRecipe recipe = this.factory.create((String)Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, new ItemStack(this.result), this.experience, this.cookingTime);
      recipeOutput.accept(resourceLocation, recipe, advancement.build(resourceLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")));
   }

   private void ensureValid(ResourceLocation resourceLocation) {
      if (this.criteria.isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + resourceLocation);
      }
   }

}