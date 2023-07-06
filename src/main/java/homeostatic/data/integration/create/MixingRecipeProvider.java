package homeostatic.data.integration.create;

import org.jetbrains.annotations.NotNull;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.data.integration.ModIntegration;

import static homeostatic.Homeostatic.loc;

public class MixingRecipeProvider extends ProcessingRecipeGen {

    GeneratedRecipe PURIFIED_WATER = create(loc("purified_water"), b -> b.require(Fluids.WATER, 1000)
        .output(HomeostaticFluids.PURIFIED_WATER, 1000)
        .requiresHeat(HeatCondition.HEATED)
        .whenModLoaded(ModIntegration.CREATE_MODID)
    );

    public MixingRecipeProvider(@NotNull final PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.MIXING;
    }

}
