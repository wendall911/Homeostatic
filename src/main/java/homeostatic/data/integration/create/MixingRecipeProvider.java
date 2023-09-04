package homeostatic.data.integration.create;
/*
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.material.Fluids;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.data.integration.ModIntegration;

import static homeostatic.Homeostatic.loc;

public class MixingRecipeProvider extends ProcessingRecipeGen {

    public MixingRecipeProvider(@NotNull final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        create(loc("purified_water"), b -> b.require(Fluids.WATER, 1000)
            .output(HomeostaticFluids.PURIFIED_WATER, 1000)
            .requiresHeat(HeatCondition.HEATED)
            .whenModLoaded(ModIntegration.CREATE_MODID)
        );
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.MIXING;
    }

}
 */