package homeostatic.data.integration.create;

import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import homeostatic.Homeostatic;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.material.Fluids;

import homeostatic.common.fluid.HomeostaticFluids;
import homeostatic.data.integration.ModIntegration;

import java.util.concurrent.CompletableFuture;

import static homeostatic.Homeostatic.prefix;

public class MixingRecipeProvider extends MixingRecipeGen {


    GeneratedRecipe PURIFIED_WATER = create(prefix("purified_water"), b -> b.require(Fluids.WATER, 1000)
        .output(HomeostaticFluids.PURIFIED_WATER, 1000)
        .requiresHeat(HeatCondition.HEATED)
        .whenModLoaded(ModIntegration.CREATE_MODID)
    );

    public MixingRecipeProvider(@NotNull final PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(packOutput, provider, Homeostatic.MODID);
    }

}

