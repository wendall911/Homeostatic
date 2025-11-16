package homeostatic.common.fluid;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
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

    public static void write(FriendlyByteBuf buf) {
        buf.writeInt(FLUIDS.size());

        for (Map.Entry<Fluid, DrinkingFluid> entry : FLUIDS.entrySet()) {
            CompoundTag drinkingFluidTag = DrinkingFluid.serialize(entry.getValue());
            buf.writeResourceLocation(Services.PLATFORM.getFluidResourceLocation(entry.getKey()));
            buf.writeNbt(drinkingFluidTag);
        }
    }

    public static ListTag write() {
        ListTag tag = new ListTag();

        for (Map.Entry<Fluid, DrinkingFluid> entry : FLUIDS.entrySet()) {
            CompoundTag drinkingFluidTag = DrinkingFluid.serialize(entry.getValue());
            tag.add(drinkingFluidTag);
        }

        return tag;
    }

    public static void read(FriendlyByteBuf buf) {
        int size = buf.readVarInt();

        if (size <= 0) {
            return;
        }

        FLUIDS.clear();

        for (int i = 0; i < size; i++) {
            ResourceLocation fluidLoc = buf.readResourceLocation();
            CompoundTag drinkingFluidTag = buf.readNbt();
            Fluid fluid = Services.PLATFORM.getFluid(fluidLoc);

            if (fluid != Fluids.EMPTY && fluid != null && drinkingFluidTag != null) {
                DrinkingFluid drinkingFluid = DrinkingFluid.deserialize(drinkingFluidTag);
                FLUIDS.put(fluid, drinkingFluid);
            }
        }

        Homeostatic.LOGGER.info("Synchronized {} drinking fluids", FLUIDS.size());
    }

    public static void read(ListTag tag) {
        if (tag == null || tag.isEmpty()) {
            return;
        }

        FLUIDS.clear();

        for (int i = 0; i < tag.size(); i++) {
            CompoundTag drinkingFluidTag = tag.getCompound(i);
            DrinkingFluid drinkingFluid = DrinkingFluid.deserialize(drinkingFluidTag);
            Fluid fluid = Services.PLATFORM.getFluid(drinkingFluid.loc());

            if (fluid != Fluids.EMPTY && fluid != null) {
                FLUIDS.put(fluid, drinkingFluid);
            }
        }

        Homeostatic.LOGGER.info("Loaded {} drinking fluids from NBT", FLUIDS.size());
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
