package homeostatic.data.integration.create;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.material.Fluids;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.data.integration.ModIntegration;

import static homeostatic.Homeostatic.loc;

public class MixingRecipeProvider extends ProcessingRecipeGen {

    public MixingRecipeProvider(DataGenerator generator) {
        super(generator);

        createRecipes();
    }

    private void createRecipes() {
        create(loc("purified_water"), b -> b.require(Fluids.WATER, 1000)
            .output(HomeostaticFluids.PURIFIED_WATER, 1000)
            .requiresHeat(HeatCondition.HEATED)
            .whenModLoaded(ModIntegration.CREATE_MODID)
        );
    }

    @Override
    public String getName() {
        return "Homeostatic - Create Mixing Recipes";
    }

    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.MIXING;
    }

}