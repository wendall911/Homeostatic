package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.temperature.ThermometerInfo;
import homeostatic.platform.Services;

import static homeostatic.Homeostatic.loc;

public class ThermometerData {

    public boolean hasThermometer;
    public static final ResourceLocation ID = loc("thermometer_data");

    public ThermometerData(ThermometerInfo info) {
        this.hasThermometer = info.hasThermometer();
    }

    public ThermometerData(FriendlyByteBuf buf) {
        this.hasThermometer = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(hasThermometer);
    }

    public static void process(Player player, CompoundTag tag) {
        Services.PLATFORM.getThermometerCapability(player).ifPresent(data -> {
            data.read(tag);
        });
    }

}