package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import homeostatic.platform.Services;

import static homeostatic.Homeostatic.loc;

public class TemperatureData {

    public float localTemperature;
    public float skinTemperature;
    public float coreTemperature;
    public static final ResourceLocation ID = loc("temperature_data");

    public TemperatureData(float localTemperature, float skinTemperature, float coreTemperature) {
        this.localTemperature = localTemperature;
        this.skinTemperature = skinTemperature;
        this.coreTemperature = coreTemperature;
    }

    public TemperatureData(FriendlyByteBuf buf) {
        localTemperature = buf.readFloat();
        skinTemperature = buf.readFloat();
        coreTemperature = buf.readFloat();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(localTemperature);
        buf.writeFloat(skinTemperature);
        buf.writeFloat(coreTemperature);
    }

    public static void process(Player player, CompoundTag tag) {
        Services.PLATFORM.getTemperatureData(player).ifPresent(data -> {
            data.read(tag);
        });
    }

    public String toString() {
        return "local: " + this.localTemperature + " skin: " + this.skinTemperature + " core: " + this.coreTemperature;
    }

}
