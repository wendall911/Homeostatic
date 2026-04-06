package homeostatic.common.fluid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jspecify.annotations.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.material.Fluid;

import homeostatic.Homeostatic;
import homeostatic.network.SyncDrinkingFluids;
import homeostatic.platform.Services;

public class DrinkingFluidManager extends SimpleJsonResourceReloadListener<JsonElement> {

    private static final Map<Fluid, DrinkingFluid> FLUIDS = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(DrinkingFluid.class, new DrinkingFluid.Serializer()).create();

    public DrinkingFluidManager() {
        super(ExtraCodecs.JSON, FileToIdConverter.json("environment/fluids"));
    }

    public static JsonElement parseDrinkingFluid(DrinkingFluid drinkingFluid) {
        return GSON.toJsonTree(drinkingFluid);
    }

    public static DrinkingFluid get(Fluid fluid) {
        return FLUIDS.get(fluid);
    }

    public static void update(List<DrinkingFluid> drinkingFluids) {
        FLUIDS.clear();

        for (DrinkingFluid drinkingFluid : drinkingFluids) {
            Optional<Reference<Fluid>> fluid = BuiltInRegistries.FLUID.get(drinkingFluid.loc());

            fluid.ifPresent(reference -> FLUIDS.put(reference.value(), drinkingFluid));
        }

        Homeostatic.LOGGER.info("Updated {} drinking fluids", FLUIDS.size());
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> pObject, @NonNull ResourceManager pResourceManager, @NonNull ProfilerFiller pProfiler) {
        FLUIDS.clear();

        for (Map.Entry<Identifier, JsonElement> entry : pObject.entrySet()) {
            try {
                DrinkingFluid drinkingFluid = GSON.fromJson(entry.getValue(), DrinkingFluid.class);
                Optional<Holder.Reference<Fluid>> fluid = BuiltInRegistries.FLUID.get(drinkingFluid.loc());

                fluid.ifPresent(fluidReference -> FLUIDS.put(fluidReference.value(), drinkingFluid));
            }
            catch (Exception e) {
                Homeostatic.LOGGER.error("Couldn't parse drinking fluid {} {}", entry.getKey(), e);
            }
        }

        Homeostatic.LOGGER.info("Loaded {} drinking fluids", FLUIDS.size());
    }

    public static void syncWithClient(ServerPlayer player) {
        if (player != null) {
            List<DrinkingFluid> drinkingFluids = FLUIDS.values().stream().toList();
            DataResult<Tag> result = Codec.list(DrinkingFluid.CODEC).encodeStart(NbtOps.INSTANCE, drinkingFluids);
            Tag data = result.getOrThrow((fluids) -> {
                throw new IllegalStateException("Failed to encode drinking fluids: " + fluids);
            });

            Services.PLATFORM.sendPacketToPlayer(new SyncDrinkingFluids(data), player);
        }
    }

}
