package homeostatic.data;

import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

public class AdvancedCookingRecipeBuilder implements RecipeBuilder {
   private final Ingredient result;
   private final Ingredient ingredient;
   private final float experience;
   private final int cookingTime;
   private final Advancement.Builder advancement = Advancement.Builder.advancement();
   @Nullable
   private String group;
   private final RecipeSerializer<?> serializer;

   private AdvancedCookingRecipeBuilder(Ingredient pResult, Ingredient pIngredient, float pExperience, int pCookingTime, RecipeSerializer<?> pSerializer) {
      this.result = pResult;
      this.ingredient = pIngredient;
      this.experience = pExperience;
      this.cookingTime = pCookingTime;
      this.serializer = pSerializer;
   }

   public static AdvancedCookingRecipeBuilder cooking(Ingredient pIngredient, Ingredient pResult, float pExperience, int pCookingTime, RecipeSerializer<?> pSerializer) {
      return new AdvancedCookingRecipeBuilder(pResult, pIngredient, pExperience, pCookingTime, pSerializer);
   }

   public static AdvancedCookingRecipeBuilder campfireCooking(Ingredient pIngredient, Ingredient pResult, float pExperience, int pCookingTime) {
      return cooking(pIngredient, pResult, pExperience, pCookingTime, SimpleCookingSerializer.CAMPFIRE_COOKING_RECIPE);
   }

   public static AdvancedCookingRecipeBuilder blasting(Ingredient pIngredient, Ingredient pResult, float pExperience, int pCookingTime) {
      return cooking(pIngredient, pResult, pExperience, pCookingTime, SimpleCookingSerializer.BLASTING_RECIPE);
   }

   public static AdvancedCookingRecipeBuilder smelting(Ingredient pIngredient, Ingredient pResult, float pExperience, int pCookingTime) {
      return cooking(pIngredient, pResult, pExperience, pCookingTime, SimpleCookingSerializer.SMELTING_RECIPE);
   }

   public static AdvancedCookingRecipeBuilder smoking(Ingredient pIngredient, Ingredient pResult, float pExperience, int pCookingTime) {
      return cooking(pIngredient, pResult, pExperience, pCookingTime, SimpleCookingSerializer.SMOKING_RECIPE);
   }

   public AdvancedCookingRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
      this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
      return this;
   }

   public AdvancedCookingRecipeBuilder group(@Nullable String pGroupName) {
      this.group = pGroupName;
      return this;
   }

   public Item getResult() {
      return Arrays.stream(this.result.getItems()).findFirst().get().getItem();
   }

   public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
      this.ensureValid(pRecipeId);
      this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
      pFinishedRecipeConsumer.accept(new AdvancedCookingRecipeBuilder.Result(pRecipeId, this.group == null ? "" : this.group, this.ingredient, this.result, this.experience, this.cookingTime, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/" + pRecipeId.getPath()), (RecipeSerializer<? extends AbstractCookingRecipe>) this.serializer));
   }

   private void ensureValid(ResourceLocation pId) {
      if (this.advancement.getCriteria().isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + pId);
      }
   }

   public static class Result implements FinishedRecipe {
      private final ResourceLocation id;
      private final String group;
      private final Ingredient ingredient;
      private final Ingredient result;
      private final float experience;
      private final int cookingTime;
      private final Advancement.Builder advancement;
      private final ResourceLocation advancementId;
      private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

      public Result(ResourceLocation pId, String pGroup, Ingredient pIngredient, Ingredient pResult, float pExperience, int pCookingTime, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
         this.id = pId;
         this.group = pGroup;
         this.ingredient = pIngredient;
         this.result = pResult;
         this.experience = pExperience;
         this.cookingTime = pCookingTime;
         this.advancement = pAdvancement;
         this.advancementId = pAdvancementId;
         this.serializer = pSerializer;
      }

      public void serializeRecipeData(JsonObject pJson) {
         if (!this.group.isEmpty()) {
            pJson.addProperty("group", this.group);
         }

         pJson.add("ingredient", this.ingredient.toJson());
         pJson.add("result", this.result.toJson());
         pJson.addProperty("experience", this.experience);
         pJson.addProperty("cookingtime", this.cookingTime);
      }

      public RecipeSerializer<?> getType() {
         return this.serializer;
      }

      public ResourceLocation getId() {
         return this.id;
      }

      @Nullable
      public JsonObject serializeAdvancement() {
         return this.advancement.serializeToJson();
      }

      @Nullable
      public ResourceLocation getAdvancementId() {
         return this.advancementId;
      }
   }
}