package homeostatic.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import homeostatic.common.fluid.DrinkingFluid;
import homeostatic.common.fluid.DrinkingFluidManager;
import homeostatic.data.integration.ModIntegration;
import homeostatic.Homeostatic;

public class DrinkingFluidsProvider implements DataProvider {

    private final Map<Identifier, DrinkingFluid> DRINKING_FLUIDS = new HashMap<>();
    private final PackOutput packOutput;

    public DrinkingFluidsProvider(@NonNull final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected void addDrinkingFluids() {
        add(ModIntegration.mcLoc("water"), 1, 0.0F, 45, 200, 0.2F);
        add(Homeostatic.prefix("purified_water"), 3, 0.7F,  0, 0, 0.0F);
    }

    protected void add(Identifier loc, int amount, float saturation, int potency, int duration, float chance) {
        DRINKING_FLUIDS.put(loc, new DrinkingFluid(loc, amount, saturation, potency, duration, chance));
    }

    @Override
    public @NonNull String getName() {
        return "Homeostatic - Drinking Fluids";
    }

    @Override
    @NonNull
    public CompletableFuture<?> run(@NonNull CachedOutput cache) throws IllegalStateException {
        List<CompletableFuture<?>> recipeList = new ArrayList<>();

        addDrinkingFluids();

        for (Map.Entry<Identifier, DrinkingFluid> entry : DRINKING_FLUIDS.entrySet()) {
            PackOutput.PathProvider pathProvider = getPath(entry.getKey());

            recipeList.add(DataProvider.saveStable(cache,
                    DrinkingFluidManager.parseDrinkingFluid(entry.getValue()),
                    pathProvider.json(entry.getKey())));
        }

        return CompletableFuture.allOf(recipeList.toArray(CompletableFuture[]::new));
    }

    private PackOutput.PathProvider getPath(Identifier loc) {
        return this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "environment/fluids/");
    }

}
