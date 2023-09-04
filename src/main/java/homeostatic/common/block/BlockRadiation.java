package homeostatic.common.block;

import java.lang.reflect.Type;
import java.util.Objects;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.ModList;

import homeostatic.data.integration.ModIntegration;
import homeostatic.util.CreateHelper;
import homeostatic.util.TConHelper;

public record BlockRadiation(ResourceLocation loc, double maxRadiation) {

    public double getBlockRadiation(BlockState state, double distance, boolean obscured, int y) {
        double radiation;

        if (distance <= 1) {
            radiation = this.maxRadiation(state);
        }
        else {
            radiation = this.maxRadiation(state) / distance;
        }

        if (y > 0 && y < 5) {
            radiation = radiation * ((4 - y) * 0.25);
        }

        if (obscured) {
            radiation = radiation * 0.9;
        }

        return Math.min(radiation, maxRadiation(state));
    }

    public double getBlockRadiation(BlockState state, double distance, boolean obscured, double amount, int y) {
        double radiation = 0.0;

        if (amount <= 0) return radiation;

        if (distance <= 1) {
            radiation = this.maxRadiation(state);
        }
        else {
            radiation = this.maxRadiation(state) * amount / distance;
        }

        if (y > 0 && y < 5) {
            radiation = radiation * ((4 - y) * 0.25);
        }

        if (obscured) {
            radiation = radiation * 0.9;
        }

        return Math.min(radiation, this.maxRadiation(state));
    }

    public double maxRadiation(BlockState state) {
        Block block = state.getBlock();

        if (ModList.get().isLoaded(ModIntegration.CREATE_MODID) && block.toString().contains(ModIntegration.CREATE_MODID)) {
            return CreateHelper.getBlockRadiation(state, maxRadiation);
        }

        if (ModList.get().isLoaded(ModIntegration.TCON_MODID) && block.toString().contains(ModIntegration.TCON_MODID)) {
            return TConHelper.getBlockRadiation(state, maxRadiation);
        }

        return maxRadiation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BlockRadiation) obj;
        return Objects.equals(this.loc, that.loc) &&
                Double.doubleToLongBits(this.maxRadiation) == Double.doubleToLongBits(that.maxRadiation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loc, maxRadiation);
    }

    @Override
    public String toString() {
        return "BlockRadiation[" +
                "loc=" + loc + ", " +
                "maxRadiation=" + maxRadiation + ']';
    }


    public static class Serializer implements JsonDeserializer<BlockRadiation>, JsonSerializer<BlockRadiation> {

        @Override
        public BlockRadiation deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "data");

            return new BlockRadiation(new ResourceLocation(json.get("block").getAsString()), json.get("max_radiation").getAsDouble());
        }

        @Override
        public JsonElement serialize(BlockRadiation blockRadiation, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();

            json.addProperty("block", blockRadiation.loc().toString());
            json.addProperty("max_radiation", blockRadiation.maxRadiation());

            return json;
        }

    }

}
