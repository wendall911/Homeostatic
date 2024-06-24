package homeostatic.common.fluid;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import homeostatic.Homeostatic;
import homeostatic.platform.Services;

public class DrinkingFluidManager extends SimpleJsonResourceReloadListener {

    private static final Map<Fluid, DrinkingFluid> FLUIDS = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(DrinkingFluid.class, new DrinkingFluid.Serializer()).create();

    public DrinkingFluidManager() {
        super(GSON, "environment/fluids");
    }

    public static JsonElement parseDrinkingFluid(DrinkingFluid drinkingFluid) {
        return GSON.toJsonTree(drinkingFluid);
    }

    public static DrinkingFluid get(Fluid fluid) {
        return FLUIDS.get(fluid);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        FLUIDS.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            try {
                DrinkingFluid drinkingFluid = GSON.fromJson(entry.getValue(), DrinkingFluid.class);
                Fluid fluid = Services.PLATFORM.getFluid(drinkingFluid.loc());

                if (fluid != Fluids.EMPTY && fluid != null) {
                    FLUIDS.put(fluid, drinkingFluid);
                }
            }
            catch (Exception e) {
                Homeostatic.LOGGER.error("Couldn't parse drinking fluid {} {}", entry.getKey(), e);
            }
        }

        Homeostatic.LOGGER.info("Loaded {} drinking fluids", FLUIDS.size());
    }

}
