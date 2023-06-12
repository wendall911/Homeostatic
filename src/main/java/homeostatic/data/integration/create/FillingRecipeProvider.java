package homeostatic.data.integration.create;
/*
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
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

    public FillingRecipeProvider(@NotNull final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
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
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.FILLING;
    }

}
 */