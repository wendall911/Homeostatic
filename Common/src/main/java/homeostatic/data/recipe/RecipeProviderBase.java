package homeostatic.data.recipe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.AdvancedCookingRecipeBuilder;

import static homeostatic.Homeostatic.loc;

public abstract class RecipeProviderBase {

    private final PackOutput packOutput;
    private static final ItemStack waterBottle = PotionContents.createItemStack(Items.POTION, Potions.WATER);

    protected RecipeProviderBase(@NotNull final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected static void specialRecipe(RecipeOutput exporter, SimpleCraftingRecipeSerializer<?> serializer, Function<CraftingBookCategory, Recipe<?>> recipeFunction) {
        ResourceLocation name = BuiltInRegistries.RECIPE_SERIALIZER.getKey(serializer);

        SpecialRecipeBuilder.special(recipeFunction).save(exporter, loc("dynamic/" + Objects.requireNonNull(name).getPath()).toString());
    }

    private static Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> pTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pTag).build());
    }

    private static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    private static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... predicates) {
        return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
            new InventoryChangeTrigger.TriggerInstance(
                    Optional.empty(),
                    InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                    List.of(predicates)
            )
        );
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

    public static RecipeBuilder cleanWaterFlaskSmelting() {
        return AdvancedCookingRecipeBuilder.smelting(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            RecipeCategory.MISC,
            HomeostaticItems.LEATHER_FLASK,
            0.15F,
            200
        ).unlockedBy("has_leather_flask", has(HomeostaticItems.LEATHER_FLASK));
    }

    public static RecipeBuilder cleanWaterFlaskCampfire() {
        return AdvancedCookingRecipeBuilder.campfireCooking(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            RecipeCategory.MISC,
            HomeostaticItems.LEATHER_FLASK,
            0.15F,
            200
        ).unlockedBy("has_leather_flask", has(HomeostaticItems.LEATHER_FLASK));
    }

    public static RecipeBuilder cleanWaterFlaskSmoking() {
        return AdvancedCookingRecipeBuilder.smoking(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            RecipeCategory.MISC,
            HomeostaticItems.LEATHER_FLASK,
            0.15F,
            200
        ).unlockedBy("has_leather_flask", has(HomeostaticItems.LEATHER_FLASK));
    }

    public static RecipeBuilder cleanWaterBottleSmelting() {
        return SimpleCookingRecipeBuilder.smelting(
            Ingredient.of(waterBottle),
            RecipeCategory.MISC,
            HomeostaticItems.PURIFIED_WATER_BOTTLE,
            0.05F,
            100
        ).unlockedBy("has_glass_bottle", has(Items.GLASS_BOTTLE));
    }

    public static RecipeBuilder cleanWaterBottleCampfire() {
        return SimpleCookingRecipeBuilder.campfireCooking(
            Ingredient.of(waterBottle),
            RecipeCategory.MISC,
            HomeostaticItems.PURIFIED_WATER_BOTTLE,
            0.05F,
            100
        ).unlockedBy("has_glass_bottle", has(Items.GLASS_BOTTLE));
    }

    public static RecipeBuilder cleanWaterBottleSmoking() {
        return SimpleCookingRecipeBuilder.smoking(
            Ingredient.of(waterBottle),
            RecipeCategory.MISC,
            HomeostaticItems.PURIFIED_WATER_BOTTLE,
            0.05F,
            100
        ).unlockedBy("has_glass_bottle", has(Items.GLASS_BOTTLE));
    }

}