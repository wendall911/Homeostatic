package homeostatic.data.integration.create;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import homeostatic.common.TagManager;
import homeostatic.common.item.HomeostaticItems;
import homeostatic.common.item.LeatherFlask;
import homeostatic.data.integration.ModIntegration;
import homeostatic.util.WaterHelper;

import static homeostatic.Homeostatic.loc;

public class FillingRecipeProvider extends ProcessingRecipeGen {

    GeneratedRecipe FLASK = createFlaskRecipe("purified_water", TagManager.Fluids.PURIFIED_WATER, loc("purified_water")),
            WATER = createFlaskRecipe("water", FluidTags.WATER, new ResourceLocation("minecraft", "water"));

    public FillingRecipeProvider(@NotNull final PackOutput packOutput) {
        super(packOutput);
    }

    private GeneratedRecipe createFlaskRecipe(String id, TagKey<Fluid> key, ResourceLocation fluid) {
        return create(loc(id), b -> b.require(key, (int) LeatherFlask.LEATHER_FLASK_CAPACITY)
            .require(HomeostaticItems.LEATHER_FLASK)
            .output(WaterHelper.getFilledItem(new ItemStack(HomeostaticItems.LEATHER_FLASK), fluid, (int) LeatherFlask.LEATHER_FLASK_CAPACITY))
            .whenModLoaded(ModIntegration.CREATE_MODID));
    }

    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.FILLING;
    }

}
