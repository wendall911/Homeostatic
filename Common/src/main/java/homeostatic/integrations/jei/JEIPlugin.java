package homeostatic.integrations.jei;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.recipe.ArmorEnhancement;
import homeostatic.common.recipe.CampfirePurifiedLeatherFlask;
import homeostatic.common.recipe.CampfirePurifiedWaterBottle;
import homeostatic.common.recipe.SmeltingPurifiedLeatherFlask;
import homeostatic.common.recipe.SmeltingPurifiedWaterBottle;
import homeostatic.common.recipe.SmokingPurifiedLeatherFlask;
import homeostatic.common.recipe.SmokingPurifiedWaterBottle;
import homeostatic.common.recipe.HelmetThermometer;
import homeostatic.config.ConfigHandler;
import homeostatic.Homeostatic;
import homeostatic.integrations.ArmorEnhancementRecipeMaker;
import homeostatic.integrations.CampfireRecipeMaker;
import homeostatic.integrations.HelmetThermometerRecipeMaker;
import homeostatic.integrations.SmeltingRecipeMaker;
import homeostatic.integrations.SmokerRecipeMaker;
import homeostatic.integrations.WaterFilterRecipeMaker;

import static net.minecraft.world.item.crafting.RecipeType.CAMPFIRE_COOKING;
import static net.minecraft.world.item.crafting.RecipeType.CRAFTING;
import static net.minecraft.world.item.crafting.RecipeType.SMELTING;
import static net.minecraft.world.item.crafting.RecipeType.SMOKING;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return Homeostatic.loc("jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Minecraft minecraft = Minecraft.getInstance();
        RecipeManager recipeManager = Objects.requireNonNull(minecraft.level).getRecipeManager();
        List<CraftingRecipe> allCraftingRecipes = recipeManager.getAllRecipesFor(CRAFTING);
        List<CraftingRecipe> armorEnhancementRecipes = addArmorCraftingRecipes(allCraftingRecipes);
        List<CampfireCookingRecipe> allCampfireRecipes = recipeManager.getAllRecipesFor(CAMPFIRE_COOKING);
        List<CampfireCookingRecipe> purifiedWaterCampfireRecipes = addCampfireRecipes(allCampfireRecipes);
        List<SmokingRecipe> allSmokingRecipes = recipeManager.getAllRecipesFor(SMOKING);
        List<SmokingRecipe> purifiedWaterSmokingRecipes = addSmokingRecipes(allSmokingRecipes);
        List<SmeltingRecipe> allSmeltingRecipes = recipeManager.getAllRecipesFor(SMELTING);
        List<SmeltingRecipe> purifiedWaterSmeltingRecipes = addSmeltingRecipes(allSmeltingRecipes);

        registration.addRecipes(RecipeTypes.CRAFTING, armorEnhancementRecipes);
        registration.addRecipes(RecipeTypes.CRAFTING, WaterFilterRecipeMaker.getFilterCraftingRecipes("jei"));
        registration.addRecipes(RecipeTypes.CAMPFIRE_COOKING, purifiedWaterCampfireRecipes);
        registration.addRecipes(RecipeTypes.SMOKING, purifiedWaterSmokingRecipes);
        registration.addRecipes(RecipeTypes.SMELTING, purifiedWaterSmeltingRecipes);

        if (!ConfigHandler.Common.requireThermometer()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                Collections.singleton(new ItemStack(HomeostaticItems.THERMOMETER)));
        }
    }

    private static List<CraftingRecipe> addArmorCraftingRecipes(List<CraftingRecipe> allCraftingRecipes) {
        Map<Class<? extends CraftingRecipe>, Supplier<List<CraftingRecipe>>> replacers = new IdentityHashMap<>();

        replacers.put(ArmorEnhancement.class, () -> ArmorEnhancementRecipeMaker.createRecipes("jei"));

        if (ConfigHandler.Common.requireThermometer()) {
            replacers.put(HelmetThermometer.class, () -> HelmetThermometerRecipeMaker.createRecipes("jei"));
        }

        return allCraftingRecipes.stream()
            .map(CraftingRecipe::getClass)
            .distinct()
            .filter(replacers::containsKey)
            .limit(replacers.size())
            .flatMap(recipeClass -> {
                Supplier<List<CraftingRecipe>> supplier = replacers.get(recipeClass);

                try {
                    List<CraftingRecipe> results = supplier.get();

                    return results.stream();
                }
                catch (RuntimeException e) {
                    Homeostatic.LOGGER.error("Failed to create JEI Recipes for {} {}", recipeClass, e);

                    return Stream.of();
                }
            })
            .toList();
    }

    private static List<CampfireCookingRecipe> addCampfireRecipes(List<CampfireCookingRecipe> allCraftingRecipes) {
        Map<Class<? extends CampfireCookingRecipe>, Supplier<List<CampfireCookingRecipe>>> replacers = new IdentityHashMap<>();

        replacers.put(CampfirePurifiedLeatherFlask.class, () -> CampfireRecipeMaker.createFlaskRecipes("jei"));
        replacers.put(CampfirePurifiedWaterBottle.class, () -> CampfireRecipeMaker.createWaterBottleRecipes("jei"));

        return allCraftingRecipes.stream()
            .map(CampfireCookingRecipe::getClass)
            .distinct()
            .filter(replacers::containsKey)
            .limit(replacers.size())
            .flatMap(recipeClass -> {
                Supplier<List<CampfireCookingRecipe>> supplier = replacers.get(recipeClass);

                try {
                    List<CampfireCookingRecipe> results = supplier.get();

                    return results.stream();
                }
                catch (RuntimeException e) {
                    Homeostatic.LOGGER.error("Failed to create JEI Recipes for {} {}", recipeClass, e);

                    return Stream.of();
                }
            })
            .toList();
    }

    private static List<SmokingRecipe> addSmokingRecipes(List<SmokingRecipe> allCraftingRecipes) {
        Map<Class<? extends SmokingRecipe>, Supplier<List<SmokingRecipe>>> replacers = new IdentityHashMap<>();

        replacers.put(SmokingPurifiedLeatherFlask.class, () -> SmokerRecipeMaker.createFlaskRecipes("jei"));
        replacers.put(SmokingPurifiedWaterBottle.class, () -> SmokerRecipeMaker.createWaterBottleRecipes("jei"));

        return allCraftingRecipes.stream()
            .map(SmokingRecipe::getClass)
            .distinct()
            .filter(replacers::containsKey)
            .limit(replacers.size())
            .flatMap(recipeClass -> {
                Supplier<List<SmokingRecipe>> supplier = replacers.get(recipeClass);

                try {
                    List<SmokingRecipe> results = supplier.get();

                    return results.stream();
                }
                catch (RuntimeException e) {
                    Homeostatic.LOGGER.error("Failed to create JEI Recipes for {} {}", recipeClass, e);

                    return Stream.of();
                }
            })
            .toList();
    }

    private static List<SmeltingRecipe> addSmeltingRecipes(List<SmeltingRecipe> allCraftingRecipes) {
        Map<Class<? extends SmeltingRecipe>, Supplier<List<SmeltingRecipe>>> replacers = new IdentityHashMap<>();

        replacers.put(SmeltingPurifiedLeatherFlask.class, () -> SmeltingRecipeMaker.createFlaskRecipes("jei"));
        replacers.put(SmeltingPurifiedWaterBottle.class, () -> SmeltingRecipeMaker.createWaterBottleRecipes("jei"));

        return allCraftingRecipes.stream()
            .map(SmeltingRecipe::getClass)
            .distinct()
            .filter(replacers::containsKey)
            .limit(replacers.size())
            .flatMap(recipeClass -> {
                Supplier<List<SmeltingRecipe>> supplier = replacers.get(recipeClass);

                try {
                    List<SmeltingRecipe> results = supplier.get();

                    return results.stream();
                }
                catch (RuntimeException e) {
                    Homeostatic.LOGGER.error("Failed to create JEI Recipes for {} {}", recipeClass, e);

                    return Stream.of();
                }
            })
            .toList();
    }

}