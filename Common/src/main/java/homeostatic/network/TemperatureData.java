package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

import homeostatic.platform.Services;

import static homeostatic.Homeostatic.prefix;

public class TemperatureData {

    public float localTemperature;
    public float skinTemperature;
    public float coreTemperature;
    public double relativeHumidity;
    public static final Identifier ID = prefix("temperature_data");

    public TemperatureData(float localTemperature, float skinTemperature, float coreTemperature, double relativeHumidity) {
        this.localTemperature = localTemperature;
        this.skinTemperature = skinTemperature;
        this.coreTemperature = coreTemperature;
        this.relativeHumidity = relativeHumidity;
    }

    public TemperatureData(FriendlyByteBuf buf) {
        localTemperature = buf.readFloat();
        skinTemperature = buf.readFloat();
        coreTemperature = buf.readFloat();
        relativeHumidity = buf.readDouble();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(localTemperature);
        buf.writeFloat(skinTemperature);
        buf.writeFloat(coreTemperature);
        buf.writeDouble(relativeHumidity);
    }

    public static void process(Player player, CompoundTag tag) {
        Services.PLATFORM.getTemperatureData(player).ifPresent(data -> {
            data.read(tag);
        });
    }

    public String toString() {
        return "local: " + this.localTemperature
            + " rh: " + this.relativeHumidity
            + " skin: " + this.skinTemperature
            + " core: " + this.coreTemperature;
    }

}
