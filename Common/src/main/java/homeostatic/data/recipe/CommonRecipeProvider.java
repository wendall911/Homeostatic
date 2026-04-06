package homeostatic.data.recipe;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.recipe.ArmorEnhancement;
import homeostatic.common.recipe.HelmetThermometer;
import homeostatic.common.recipe.PurifiedLeatherFlask;
import homeostatic.common.recipe.RemoveArmorEnhancement;
import homeostatic.data.AdvancedCookingRecipeBuilder;

import static homeostatic.Homeostatic.prefix;

public class CommonRecipeProvider extends RecipeProvider {

    RecipeOutput recipeOutput;
    HolderLookup.Provider registries;
    private static ItemStackTemplate waterBottle;

    public CommonRecipeProvider(HolderLookup.Provider registries, RecipeOutput recipeOutput) {
        super(registries, recipeOutput);

        this.recipeOutput = recipeOutput;
        this.registries = registries;
    }

    @Override
    public void buildRecipes() {
        HolderLookup.RegistryLookup<Item> itemRegistry = registries.lookupOrThrow(Registries.ITEM);
        waterBottle = new ItemStackTemplate(
            Items.POTION,
            DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER)).build()
        );

        specialRecipe(
            this.recipeOutput,
            ArmorEnhancement.SERIALIZER,
            ArmorEnhancement::new
        );
        specialRecipe(
            this.recipeOutput,
            PurifiedLeatherFlask.SERIALIZER,
            PurifiedLeatherFlask::new
        );
        specialRecipe(
            this.recipeOutput,
            HelmetThermometer.SERIALIZER,
            HelmetThermometer::new
        );
        specialRecipe(
            this.recipeOutput,
            RemoveArmorEnhancement.SERIALIZER,
            RemoveArmorEnhancement::new
        );
        cleanWaterFlaskSmelting(itemRegistry).save(this.recipeOutput, getKey("furnace_purified_leather_flask"));
        cleanWaterFlaskCampfire(itemRegistry).save(recipeOutput, getKey("campfire_purified_leather_flask"));
        cleanWaterFlaskSmoking(itemRegistry).save(recipeOutput, getKey("smoking_purified_leather_flask"));
        leatherFlask(itemRegistry).save(recipeOutput);
        waterFilter(itemRegistry).save(recipeOutput);
        thermometer(itemRegistry).save(recipeOutput);
        cleanWaterBottleSmelting(itemRegistry).save(recipeOutput, getKey("furnace_purified_water_bottle"));
        cleanWaterBottleCampfire(itemRegistry).save(recipeOutput, getKey("campfire_purified_water_bottle"));
        cleanWaterBottleSmoking(itemRegistry).save(recipeOutput, getKey("smoking_purified_water_bottle"));
        book(itemRegistry).save(recipeOutput, "book_from_dirt");
    }

    private ResourceKey<Recipe<?>> getKey(String id) {
        return ResourceKey.create(Registries.RECIPE, prefix(id));
    }
    private static void specialRecipe(RecipeOutput exporter, RecipeSerializer<? extends CustomRecipe> serializer, Supplier<Recipe<?>> factory) {
        Identifier name = BuiltInRegistries.RECIPE_SERIALIZER.getKey(serializer);

        SpecialRecipeBuilder.special(factory).save(exporter, prefix("dynamic/" + Objects.requireNonNull(name).getPath()).toString());
    }

    private static Criterion<TriggerInstance> has(HolderLookup.RegistryLookup<Item> itemRegistry, ItemLike pItemLike) {
        return invTrigger(ItemPredicate.Builder.item().of(itemRegistry, pItemLike).build());
    }

    private static Criterion<InventoryChangeTrigger.TriggerInstance> invTrigger(ItemPredicate... predicates) {
        return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
            new InventoryChangeTrigger.TriggerInstance(
                Optional.empty(),
                InventoryChangeTrigger.TriggerInstance.Slots.ANY,
                List.of(predicates)
            )
        );
    }

    private RecipeBuilder cleanWaterFlaskSmelting(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.leatherFlaskSmelting(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            RecipeCategory.MISC,
            HomeostaticItems.LEATHER_FLASK,
            0.15F,
            150
        ).unlockedBy("has_leather_flask", has(itemRegistry, HomeostaticItems.LEATHER_FLASK));
    }

    private static ShapedRecipeBuilder leatherFlask(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, HomeostaticItems.LEATHER_FLASK)
            .define('S', Items.STRING)
            .define('L', Items.LEATHER)
            .define('P', ItemTags.PLANKS)
            .pattern("SPS")
            .pattern("L L")
            .pattern("LLL")
            .unlockedBy("has_leather", has(itemRegistry, Items.LEATHER));
    }

    private static ShapedRecipeBuilder waterFilter(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, HomeostaticItems.WATER_FILTER)
            .define('P', Items.PAPER)
            .define('C', Items.CHARCOAL)
            .pattern("P")
            .pattern("C")
            .pattern("P")
            .unlockedBy("has_charcoal", has(itemRegistry, Items.CHARCOAL));
    }

    private static ShapedRecipeBuilder thermometer(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapedRecipeBuilder.shaped(itemRegistry, RecipeCategory.MISC, HomeostaticItems.THERMOMETER)
            .define('N', Items.IRON_NUGGET)
            .define('D', Items.REDSTONE)
            .define('I', Items.IRON_INGOT)
            .pattern("N")
            .pattern("D")
            .pattern("I")
            .unlockedBy("has_redstone", has(itemRegistry, Items.REDSTONE));
    }

    private static ShapelessRecipeBuilder book(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return ShapelessRecipeBuilder.shapeless(itemRegistry, RecipeCategory.MISC, HomeostaticItems.BOOK)
            .requires(Items.DIRT)
            .group("books")
            .unlockedBy("has_dirt", has(itemRegistry, Items.DIRT));
    }


    private static RecipeBuilder cleanWaterFlaskCampfire(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.campfireLeatherFlaskCooking(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            RecipeCategory.MISC,
            HomeostaticItems.LEATHER_FLASK,
            0.15F,
            200
        ).unlockedBy("has_leather_flask", has(itemRegistry, HomeostaticItems.LEATHER_FLASK));
    }

    private static RecipeBuilder cleanWaterFlaskSmoking(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.leatherFlaskSmoking(
            Ingredient.of(HomeostaticItems.LEATHER_FLASK),
            RecipeCategory.MISC,
            HomeostaticItems.LEATHER_FLASK,
            0.15F,
            100
        ).unlockedBy("has_leather_flask", has(itemRegistry, HomeostaticItems.LEATHER_FLASK));
    }

    private static RecipeBuilder cleanWaterBottleSmelting(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.waterBottleSmelting(
            Ingredient.of(waterBottle.item().value()),
            RecipeCategory.MISC,
            HomeostaticItems.PURIFIED_WATER_BOTTLE,
            0.05F,
            75
        ).unlockedBy("has_glass_bottle", has(itemRegistry, Items.GLASS_BOTTLE));
    }

    private static RecipeBuilder cleanWaterBottleCampfire(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.campfireWaterBottleCooking(
            Ingredient.of(waterBottle.item().value()),
            RecipeCategory.MISC,
            HomeostaticItems.PURIFIED_WATER_BOTTLE,
            0.05F,
            100
        ).unlockedBy("has_glass_bottle", has(itemRegistry, Items.GLASS_BOTTLE));
    }

    private static RecipeBuilder cleanWaterBottleSmoking(HolderLookup.RegistryLookup<Item> itemRegistry) {
        return AdvancedCookingRecipeBuilder.waterBottleSmoking(
            Ingredient.of(waterBottle.item().value()),
            RecipeCategory.MISC,
            HomeostaticItems.PURIFIED_WATER_BOTTLE,
            0.05F,
            50
        ).unlockedBy("has_glass_bottle", has(itemRegistry, Items.GLASS_BOTTLE));
    }

}
