package homeostatic.integrations.jei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import homeostatic.config.ConfigHandler;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;

import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import static net.minecraft.world.item.crafting.RecipeType.CRAFTING;

import homeostatic.common.recipe.ArmorEnhancement;
import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.recipe.HelmetThermometer;
import homeostatic.Homeostatic;
import homeostatic.util.WaterHelper;

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

        registration.addRecipes(RecipeTypes.CRAFTING, armorEnhancementRecipes);
        registration.addRecipes(RecipeTypes.CRAFTING, getFilterCraftingRecipes());

        if (!ConfigHandler.Common.requireThermometer()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                    Collections.singleton(new ItemStack(HomeostaticItems.THERMOMETER)));
        }
    }

    private static List<CraftingRecipe> addArmorCraftingRecipes(List<CraftingRecipe> allCraftingRecipes) {
        Map<Class<? extends CraftingRecipe>, Supplier<List<CraftingRecipe>>> replacers = new IdentityHashMap<>();

        replacers.put(ArmorEnhancement.class, ArmorEnhancementRecipeMaker::createRecipes);

        if (ConfigHandler.Common.requireThermometer()) {
            replacers.put(HelmetThermometer.class, HelmetThermometerRecipeMaker::createRecipes);
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
                    Homeostatic.LOGGER.error("Failed to create JEI Recipes for %s %s", recipeClass, e);

                    return Stream.of();
                }
            })
            .toList();
    }

    private static List<CraftingRecipe> getFilterCraftingRecipes() {
        List<CraftingRecipe> recipes = new ArrayList<>();
        Ingredient ingredient = Ingredient.of(HomeostaticItems.WATER_FILTER);
        ItemStack leatherFlaskBase = new ItemStack(HomeostaticItems.LEATHER_FLASK);
        ItemStack leatherFlask = WaterHelper.getFilledItem(leatherFlaskBase, HomeostaticFluids.PURIFIED_WATER, 5000);
        String group = "jei.flask.filter";

        Ingredient baseFlaskIngredient = Ingredient.of(leatherFlaskBase.getItem());
        NonNullList<Ingredient> recipeInputs = NonNullList.of(Ingredient.EMPTY, baseFlaskIngredient, ingredient);

        recipes.add(new ShapelessRecipe(new ResourceLocation(Homeostatic.MODID, group + ".purified_leather_flask"), group, leatherFlask, recipeInputs));

        return recipes;
    }

}