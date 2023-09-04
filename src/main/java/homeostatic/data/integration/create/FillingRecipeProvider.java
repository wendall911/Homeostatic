package homeostatic.data.integration.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import homeostatic.common.TagManager;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.data.integration.ModIntegration;
import homeostatic.util.WaterHelper;

import static homeostatic.Homeostatic.loc;

public class FillingRecipeProvider extends ProcessingRecipeGen {

    public FillingRecipeProvider(DataGenerator generator) {
        super(generator);

        createRecipes();
    }

    private void createRecipes() {
        createFlaskRecipe("purified_water", TagManager.Fluids.PURIFIED_WATER, loc("purified_water"));
        createFlaskRecipe("water", FluidTags.WATER, new ResourceLocation("minecraft", "water"));
    }

    private void createFlaskRecipe(String id, TagKey<Fluid> key, ResourceLocation fluid) {
        create(loc(id), b -> b.require(key, 1000)
            .require(HomeostaticItems.LEATHER_FLASK)
            .output(WaterHelper.getFilledItem(new ItemStack(HomeostaticItems.LEATHER_FLASK), fluid, 5000))
            .whenModLoaded(ModIntegration.CREATE_MODID));
    }

    @Override
    public String getName() {
        return "Homeostatic - Create Filling Recipes";
    }

    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.FILLING;
    }

}