package homeostatic.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import homeostatic.common.fluid.DrinkingFluid;
import homeostatic.common.fluid.DrinkingFluidManager;
import homeostatic.data.integration.ModIntegration;
import homeostatic.Homeostatic;

public class DrinkingFluidsProvider implements DataProvider {

    private final Map<ResourceLocation, DrinkingFluid> DRINKING_FLUIDS = new HashMap<>();
    private final PackOutput packOutput;

    public DrinkingFluidsProvider(@NotNull final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected void addDrinkingFluids() {
        add(ModIntegration.mcLoc("water"), 1, 0.0F, 45, 200, 0.2F);
        add(Homeostatic.loc("purified_water"), 3, 0.7F,  0, 0, 0.0F);
    }

    protected void add(ResourceLocation loc, int amount, float saturation, int potency, int duration, float chance) {
        DRINKING_FLUIDS.put(loc, new DrinkingFluid(loc, amount, saturation, potency, duration, chance));
    }

    @Override
    public String getName() {
        return "Homeostatic - Drinking Fluids";
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput cache) throws IllegalStateException {
        List<CompletableFuture<?>> recipeList = new ArrayList<>();

        addDrinkingFluids();

        for (Map.Entry<ResourceLocation, DrinkingFluid> entry : DRINKING_FLUIDS.entrySet()) {
            PackOutput.PathProvider pathProvider = getPath(entry.getKey());

            recipeList.add(DataProvider.saveStable(cache,
                    DrinkingFluidManager.parseDrinkingFluid(entry.getValue()),
                    pathProvider.json(entry.getKey())));
        }

        return CompletableFuture.allOf(recipeList.toArray(CompletableFuture[]::new));
    }

    private PackOutput.PathProvider getPath(ResourceLocation loc) {
        return this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "environment/fluids/");
    }

}