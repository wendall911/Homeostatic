package homeostatic.network;

import homeostatic.Homeostatic;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.temperature.ThermometerInfo;
import homeostatic.platform.Services;

public class ThermometerData {

    public boolean hasThermometer;
    public static final ResourceLocation ID = new ResourceLocation(Homeostatic.MODID, "thermometer_data");

    public ThermometerData(ThermometerInfo info) {
        this.hasThermometer = info.hasThermometer();
    }

    public ThermometerData(FriendlyByteBuf buf) {
        this.hasThermometer = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(hasThermometer);
    }

    public static void process(Player player, ThermometerData thermometerData) {
        Services.PLATFORM.getThermometerCapability(player).ifPresent(data -> {
            data.setHasThermometer(thermometerData.hasThermometer);
        });
    }

}