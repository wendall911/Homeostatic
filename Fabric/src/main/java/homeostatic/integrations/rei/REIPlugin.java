package homeostatic.integrations.rei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.config.ConfigHandler;
import homeostatic.integrations.ArmorEnhancementRecipeMaker;
import homeostatic.integrations.HelmetThermometerRecipeMaker;
import homeostatic.integrations.WaterFilterRecipeMaker;

public class REIPlugin implements REIClientPlugin {

    @Override
    public void registerDisplays(DisplayRegistry helper) {
        List<CraftingRecipe> recipes = ArmorEnhancementRecipeMaker.createRecipes("rei");
        RegistryAccess registryAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);

        if (!ConfigHandler.Common.requireThermometer()) {
            recipes.addAll(HelmetThermometerRecipeMaker.createRecipes("rei"));
        }

        recipes.addAll(WaterFilterRecipeMaker.getFilterCraftingRecipes("rei"));

        recipes.forEach(recipe -> {
            List<EntryIngredient> input = new ArrayList<>();

            recipe.getIngredients().forEach(ingredient -> {
                input.add(EntryIngredients.ofIngredient(ingredient));
            });

            helper.add(new DefaultCustomDisplay(null, input, Collections.singletonList(EntryIngredients.of(recipe.getResultItem(registryAccess)))));
        });
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        registry.removeEntryIf(this::shouldHideEntry);
    }

    private boolean shouldHideEntry(EntryStack<?> entryStack) {
        if (entryStack.getType() != VanillaEntryTypes.ITEM) return false;

        ItemStack stack = entryStack.castValue();

        if (!ConfigHandler.Common.requireThermometer()) {
            return stack.getItem() == HomeostaticItems.THERMOMETER;
        }

        return false;
    }
}
