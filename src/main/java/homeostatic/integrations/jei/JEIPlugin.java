package homeostatic.integrations.jei;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import static net.minecraft.world.item.crafting.RecipeType.CRAFTING;

import homeostatic.common.recipe.ArmorEnhancement;
import homeostatic.Homeostatic;

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
    }

    private static List<CraftingRecipe> addArmorCraftingRecipes(List<CraftingRecipe> allCraftingRecipes) {
        Map<Class<? extends CraftingRecipe>, Supplier<List<CraftingRecipe>>> replacers = new IdentityHashMap<>();

        replacers.put(ArmorEnhancement.class, ArmorEnhancementRecipeMaker::createRecipes);

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

}
