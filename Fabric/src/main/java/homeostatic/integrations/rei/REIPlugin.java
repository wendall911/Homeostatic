package homeostatic.integrations.rei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.mojang.datafixers.util.Pair;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.DefaultCampfireDisplay;
import me.shedaniel.rei.plugin.common.displays.cooking.DefaultSmeltingDisplay;
import me.shedaniel.rei.plugin.common.displays.cooking.DefaultSmokingDisplay;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCustomDisplay;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;

import homeostatic.common.item.HomeostaticItems;
import homeostatic.config.ConfigHandler;
import homeostatic.integrations.ArmorEnhancementRecipeMaker;
import homeostatic.integrations.CampfireRecipeMaker;
import homeostatic.integrations.HelmetThermometerRecipeMaker;
import homeostatic.integrations.SmeltingRecipeMaker;
import homeostatic.integrations.SmokerRecipeMaker;
import homeostatic.integrations.WaterFilterRecipeMaker;

public class REIPlugin implements REIClientPlugin {

    // TODO: Figure out if I can show the water recipes correctly in REI. Currently shows Empty Flask as input.
    // TODO: Update when REI is available for 26.1
    @Override
    public void registerDisplays(DisplayRegistry helper) {
        List<Pair<ItemStack, RecipeHolder<CraftingRecipe>>> recipes = ArmorEnhancementRecipeMaker.createRecipes("rei");
        List<RecipeHolder<CampfireCookingRecipe>> campfireRecipes = Stream.concat(
            CampfireRecipeMaker.createFlaskRecipes("rei").stream(),
            CampfireRecipeMaker.createWaterBottleRecipes("rei").stream()
        ).toList();
        List<RecipeHolder<SmokingRecipe>> smokingRecipes = Stream.concat(
            SmokerRecipeMaker.createFlaskRecipes("rei").stream(),
            SmokerRecipeMaker.createWaterBottleRecipes("rei").stream()
        ).toList();
        List<RecipeHolder<SmeltingRecipe>> smeltingRecipes = Stream.concat(
            SmeltingRecipeMaker.createFlaskRecipes("rei").stream(),
            SmeltingRecipeMaker.createWaterBottleRecipes("rei").stream()
        ).toList();

        if (ConfigHandler.Common.requireThermometer()) {
            recipes.addAll(HelmetThermometerRecipeMaker.createRecipes("rei"));
        }

        recipes.addAll(WaterFilterRecipeMaker.getFilterCraftingRecipes("rei"));

        recipes.forEach(recipePair -> {
            List<EntryIngredient> input = new ArrayList<>();

            recipePair.getSecond().value().placementInfo().ingredients().forEach(ingredient -> {
                //input.add(EntryIngredients.ofIngredient(ingredient));
            });


            //helper.add(new DefaultCustomDisplay(input, Collections.singletonList(EntryIngredients.of(recipePair.getFirst())), Optional.of(recipePair.getSecond().id().identifier())));
        });

        campfireRecipes.forEach(recipe -> {
            //helper.add(new DefaultCampfireDisplay(recipe));
        });

        smokingRecipes.forEach(recipe -> {
            //helper.add(new DefaultSmokingDisplay(recipe));
        });

        smeltingRecipes.forEach(recipe -> {
            //helper.add(new DefaultSmeltingDisplay(recipe));
        });
    }

    @Override
    public void registerEntries(EntryRegistry registry) {
        registry.removeEntryIf(this::shouldHideEntry);
    }

    private boolean shouldHideEntry(EntryStack<?> entryStack) {
        /*
        if (entryStack.getType() != VanillaEntryTypes.ITEM) return false;

        ItemStack stack = entryStack.castValue();

        if (!ConfigHandler.Common.requireThermometer()) {
            return stack.getItem() == HomeostaticItems.THERMOMETER;
        }
         */

        return false;
    }
}
