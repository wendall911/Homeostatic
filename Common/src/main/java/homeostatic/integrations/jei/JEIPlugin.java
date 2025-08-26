package homeostatic.integrations.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.common.Internal;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
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

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return Homeostatic.loc("jei_plugin");
    }

    // TODO: Figure out if I can show the water recipes correctly in JEI. Currently shows Empty Flask as input.
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeMap clientSyncedRecipes = Internal.getClientSyncedRecipes();

        if (clientSyncedRecipes.values().isEmpty()) {
            Homeostatic.LOGGER.error("JEI Recipe Registration failed: No synced recipes");

            return;
        }

        Recipes recipes = new Recipes(clientSyncedRecipes);

        List<RecipeHolder<CraftingRecipe>> craftingRecipes = recipes.getCraftingRecipes();
        List<RecipeHolder<CraftingRecipe>> armorEnhancementRecipes = addArmorCraftingRecipes(craftingRecipes);
        List<RecipeHolder<CampfireCookingRecipe>> allCampfireRecipes = recipes.getCampfireCookingRecipes();
        List<RecipeHolder<CampfireCookingRecipe>> purifiedWaterCampfireRecipes = addCampfireRecipes(allCampfireRecipes);
        List<RecipeHolder<SmokingRecipe>> allSmokingRecipes = recipes.getSmokingRecipes();
        List<RecipeHolder<SmokingRecipe>> purifiedWaterSmokingRecipes = addSmokingRecipes(allSmokingRecipes);
        List<RecipeHolder<SmeltingRecipe>> allSmeltingRecipes = recipes.getSmeltingRecipes();
        List<RecipeHolder<SmeltingRecipe>> purifiedWaterSmeltingRecipes = addSmeltingRecipes(allSmeltingRecipes);

        registration.addRecipes(RecipeTypes.CRAFTING, armorEnhancementRecipes);
        List<RecipeHolder<CraftingRecipe>> waterFilterRecipes = new ArrayList<>();
        WaterFilterRecipeMaker.getFilterCraftingRecipes("jei").forEach(pair -> {
            waterFilterRecipes.add(pair.getSecond());
        });
        registration.addRecipes(RecipeTypes.CRAFTING, waterFilterRecipes);
        registration.addRecipes(RecipeTypes.CAMPFIRE_COOKING, purifiedWaterCampfireRecipes);
        registration.addRecipes(RecipeTypes.SMOKING, purifiedWaterSmokingRecipes);
        registration.addRecipes(RecipeTypes.SMELTING, purifiedWaterSmeltingRecipes);

        if (!ConfigHandler.Common.requireThermometer()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                Collections.singleton(new ItemStack(HomeostaticItems.THERMOMETER)));
        }
    }

    private static List<RecipeHolder<CraftingRecipe>> addArmorCraftingRecipes(List<RecipeHolder<CraftingRecipe>> craftingRecipes) {
        Map<Class<? extends CraftingRecipe>, Supplier<List<RecipeHolder<CraftingRecipe>>>> replacers = new IdentityHashMap<>();
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();
        List<RecipeHolder<CraftingRecipe>> helmetThermometerRecipes = new ArrayList<>();

        ArmorEnhancementRecipeMaker.createRecipes("jei").forEach(pair -> {
            recipes.add(pair.getSecond());
        });

        replacers.put(ArmorEnhancement.class, () -> recipes);

        if (ConfigHandler.Common.requireThermometer()) {
            HelmetThermometerRecipeMaker.createRecipes("jei").forEach(pair -> {
                helmetThermometerRecipes.add(pair.getSecond());
            });
            replacers.put(HelmetThermometer.class, () -> helmetThermometerRecipes);
        }

        return craftingRecipes.stream()
            .map(RecipeHolder::value)
            .map(CraftingRecipe::getClass)
            .distinct()
            .filter(replacers::containsKey)
            .limit(replacers.size())
            .flatMap(recipeClass -> {
                Supplier<List<RecipeHolder<CraftingRecipe>>> supplier = replacers.get(recipeClass);

                try {
                    return supplier.get().stream();
                }
                catch (RuntimeException e) {
                    Homeostatic.LOGGER.error("Failed to create JEI Recipes for {} {}", recipeClass, e);

                    return Stream.of();
                }
            })
            .toList();
    }

    private static List<RecipeHolder<CampfireCookingRecipe>> addCampfireRecipes(List<RecipeHolder<CampfireCookingRecipe>> campfireRecipes) {
        Map<Class<? extends CampfireCookingRecipe>, Supplier<List<RecipeHolder<CampfireCookingRecipe>>>> replacers = new IdentityHashMap<>();

        replacers.put(CampfirePurifiedLeatherFlask.class, () -> CampfireRecipeMaker.createFlaskRecipes("jei"));
        replacers.put(CampfirePurifiedWaterBottle.class, () -> CampfireRecipeMaker.createWaterBottleRecipes("jei"));

        return campfireRecipes.stream()
            .map(RecipeHolder::value)
            .map(CampfireCookingRecipe::getClass)
            .distinct()
            .filter(replacers::containsKey)
            .limit(replacers.size())
            .flatMap(recipeClass -> {
                Supplier<List<RecipeHolder<CampfireCookingRecipe>>> supplier = replacers.get(recipeClass);

                try {
                    return supplier.get().stream();
                }
                catch (RuntimeException e) {
                    Homeostatic.LOGGER.error("Failed to create JEI Recipes for {} {}", recipeClass, e);

                    return Stream.of();
                }
            })
            .toList();
    }

    private static List<RecipeHolder<SmokingRecipe>> addSmokingRecipes(List<RecipeHolder<SmokingRecipe>> smokingRecipes) {
        Map<Class<? extends SmokingRecipe>, Supplier<List<RecipeHolder<SmokingRecipe>>>> replacers = new IdentityHashMap<>();

        replacers.put(SmokingPurifiedLeatherFlask.class, () -> SmokerRecipeMaker.createFlaskRecipes("jei"));
        replacers.put(SmokingPurifiedWaterBottle.class, () -> SmokerRecipeMaker.createWaterBottleRecipes("jei"));

        return smokingRecipes.stream()
            .map(RecipeHolder::value)
            .map(SmokingRecipe::getClass)
            .distinct()
            .filter(replacers::containsKey)
            .limit(replacers.size())
            .flatMap(recipeClass -> {
                Supplier<List<RecipeHolder<SmokingRecipe>>> supplier = replacers.get(recipeClass);

                try {
                    return supplier.get().stream();
                }
                catch (RuntimeException e) {
                    Homeostatic.LOGGER.error("Failed to create JEI Recipes for {} {}", recipeClass, e);

                    return Stream.of();
                }
            })
            .toList();
    }

    private static List<RecipeHolder<SmeltingRecipe>> addSmeltingRecipes(List<RecipeHolder<SmeltingRecipe>> smeltingRecipes) {
        Map<Class<? extends SmeltingRecipe>, Supplier<List<RecipeHolder<SmeltingRecipe>>>> replacers = new IdentityHashMap<>();

        replacers.put(SmeltingPurifiedLeatherFlask.class, () -> SmeltingRecipeMaker.createFlaskRecipes("jei"));
        replacers.put(SmeltingPurifiedWaterBottle.class, () -> SmeltingRecipeMaker.createWaterBottleRecipes("jei"));

        return smeltingRecipes.stream()
            .map(RecipeHolder::value)
            .map(SmeltingRecipe::getClass)
            .distinct()
            .filter(replacers::containsKey)
            .limit(replacers.size())
            .flatMap(recipeClass -> {
                Supplier<List<RecipeHolder<SmeltingRecipe>>> supplier = replacers.get(recipeClass);

                try {
                    return supplier.get().stream();
                }
                catch (RuntimeException e) {
                    Homeostatic.LOGGER.error("Failed to create JEI Recipes for {} {}", recipeClass, e);

                    return Stream.of();
                }
            })
            .toList();
    }

}