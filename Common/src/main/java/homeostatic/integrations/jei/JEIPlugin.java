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
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import static net.minecraft.world.item.crafting.RecipeType.CRAFTING;

import homeostatic.common.recipe.ArmorEnhancement;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.recipe.HelmetThermometer;
import homeostatic.config.ConfigHandler;
import homeostatic.Homeostatic;
import homeostatic.integrations.ArmorEnhancementRecipeMaker;
import homeostatic.integrations.HelmetThermometerRecipeMaker;
import homeostatic.integrations.WaterFilterRecipeMaker;

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
        List<RecipeHolder<CraftingRecipe>> allCraftingRecipes = recipeManager.getAllRecipesFor(CRAFTING);
        List<RecipeHolder<CraftingRecipe>> armorEnhancementRecipes = addArmorCraftingRecipes(allCraftingRecipes);

        registration.addRecipes(RecipeTypes.CRAFTING, armorEnhancementRecipes);
        registration.addRecipes(RecipeTypes.CRAFTING, WaterFilterRecipeMaker.getFilterCraftingRecipes("jei"));

        if (!ConfigHandler.Common.requireThermometer()) {
            registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK,
                Collections.singleton(new ItemStack(HomeostaticItems.THERMOMETER)));
        }
    }

    private static List<RecipeHolder<CraftingRecipe>> addArmorCraftingRecipes(List<RecipeHolder<CraftingRecipe>> allCraftingRecipes) {
        Map<Class<? extends CraftingRecipe>, Supplier<List<RecipeHolder<CraftingRecipe>>>> replacers = new IdentityHashMap<>();

        replacers.put(ArmorEnhancement.class, () -> ArmorEnhancementRecipeMaker.createRecipes("jei"));

        if (ConfigHandler.Common.requireThermometer()) {
            replacers.put(HelmetThermometer.class, () -> HelmetThermometerRecipeMaker.createRecipes("jei"));
        }

        return allCraftingRecipes.stream()
            .map(RecipeHolder::value)
            .map(CraftingRecipe::getClass)
            .distinct()
            .filter(replacers::containsKey)
            .limit(replacers.size())
            .flatMap(recipeClass -> {
                Supplier<List<RecipeHolder<CraftingRecipe>>> supplier = replacers.get(recipeClass);

                try {
                    List<RecipeHolder<CraftingRecipe>> results = supplier.get();

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