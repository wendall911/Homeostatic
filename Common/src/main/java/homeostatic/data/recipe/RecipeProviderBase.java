package homeostatic.data.recipe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.AdvancedCookingRecipeBuilder;

import static homeostatic.Homeostatic.prefix;

public abstract class RecipeProviderBase {

    public static final ItemStack waterBottle = PotionContents.createItemStack(Items.POTION, Potions.WATER);

    protected static void specialRecipe(RecipeOutput exporter, CustomRecipe.Serializer<?> serializer, Function<CraftingBookCategory, Recipe<?>> recipeFunction) {
        ResourceLocation name = BuiltInRegistries.RECIPE_SERIALIZER.getKey(serializer);

        SpecialRecipeBuilder.special(recipeFunction).save(exporter, prefix("dynamic/" + Objects.requireNonNull(name).getPath()).toString());
    }

    private static Criterion<InventoryChangeTrigger.TriggerInstance> has(HolderLookup.RegistryLookup<Item> itemRegistry, TagKey<Item> pTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemRegistry, pTag).build());
    }

    private static Criterion<InventoryChangeTrigger.TriggerInstance> has(HolderLookup.RegistryLookup<Item> itemRegistry, ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemRegistry, pItemLike).build());
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

    public static ShapedRecipeBuilder leatherFlask(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, HomeostaticItems.LEATHER_FLASK)
            .define('S', Items.STRING)
            .define('L', Items.LEATHER)
            .define('P', ItemTags.PLANKS)
            .pattern("SPS")
            .pattern("L L")
            .pattern("LLL")
            .unlockedBy("has_leather", has(itemRegistry, Items.LEATHER));
    }

    public static ShapedRecipeBuilder waterFilter(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, HomeostaticItems.WATER_FILTER)
            .define('P', Items.PAPER)
            .define('C', Items.CHARCOAL)
            .pattern("P")
            .pattern("C")
            .pattern("P")
            .unlockedBy("has_charcoal", has(itemRegistry, Items.CHARCOAL));
    }

    public static ShapedRecipeBuilder thermometer(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, HomeostaticItems.THERMOMETER)
            .define('N', Items.IRON_NUGGET)
            .define('D', Items.REDSTONE)
            .define('I', Items.IRON_INGOT)
            .pattern("N")
            .pattern("D")
            .pattern("I")
            .unlockedBy("has_redstone", has(itemRegistry, Items.REDSTONE));
    }

    public static ShapelessRecipeBuilder book(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapelessRecipeBuilder.shapeless(itemRegistry, RecipeCategory.MISC, HomeostaticItems.BOOK)
            .requires(Items.DIRT)
            .group("books")
            .unlockedBy("has_dirt", has(itemRegistry, Items.DIRT));
    }

    public static RecipeBuilder cleanWaterFlaskSmelting(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.leatherFlaskSmelting(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            RecipeCategory.MISC,
            HomeostaticItems.LEATHER_FLASK,
            0.15F,
            150
        ).unlockedBy("has_leather_flask", has(itemRegistry, HomeostaticItems.LEATHER_FLASK));
    }

    public static RecipeBuilder cleanWaterFlaskCampfire(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.campfireLeatherFlaskCooking(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            RecipeCategory.MISC,
            HomeostaticItems.LEATHER_FLASK,
            0.15F,
            200
        ).unlockedBy("has_leather_flask", has(itemRegistry, HomeostaticItems.LEATHER_FLASK));
    }

    public static RecipeBuilder cleanWaterFlaskSmoking(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.leatherFlaskSmoking(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            RecipeCategory.MISC,
            HomeostaticItems.LEATHER_FLASK,
            0.15F,
            100
        ).unlockedBy("has_leather_flask", has(itemRegistry, HomeostaticItems.LEATHER_FLASK));
    }

    public static RecipeBuilder cleanWaterBottleSmelting(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.waterBottleSmelting(
            Ingredient.of(waterBottle.getItem()),
            RecipeCategory.MISC,
            HomeostaticItems.PURIFIED_WATER_BOTTLE,
            0.05F,
            75
        ).unlockedBy("has_glass_bottle", has(itemRegistry, Items.GLASS_BOTTLE));
    }

    public static RecipeBuilder cleanWaterBottleCampfire(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.campfireWaterBottleCooking(
            Ingredient.of(waterBottle.getItem()),
            RecipeCategory.MISC,
            HomeostaticItems.PURIFIED_WATER_BOTTLE,
            0.05F,
            100
        ).unlockedBy("has_glass_bottle", has(itemRegistry, Items.GLASS_BOTTLE));
    }

    public static RecipeBuilder cleanWaterBottleSmoking(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.waterBottleSmoking(
            Ingredient.of(waterBottle.getItem()),
            RecipeCategory.MISC,
            HomeostaticItems.PURIFIED_WATER_BOTTLE,
            0.05F,
            50
        ).unlockedBy("has_glass_bottle", has(itemRegistry, Items.GLASS_BOTTLE));
    }

}
