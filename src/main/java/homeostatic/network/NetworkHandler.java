package homeostatic.network;

import java.util.function.Function;

import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import homeostatic.Homeostatic;

public final class NetworkHandler {

    public static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        Homeostatic.loc("main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public static void init() {
        int id = 0;

        registerMessage(id++, TemperatureData.class, TemperatureData::new);
        registerMessage(id++, WaterData.class, WaterData::new);
        registerMessage(id++, DrinkWater.class, DrinkWater::new);
        registerMessage(id++, WetnessData.class, WetnessData::new);
        registerMessage(id++, ThermometerData.class, ThermometerData::new);
    }

    private static <T extends IData> void registerMessage(int idx, Class<T> type, Function<FriendlyByteBuf, T> decoder) {
        INSTANCE.registerMessage(idx, type, IData::toBytes, decoder, (msg, ctx) -> {
            msg.process(ctx);
            ctx.get().setPacketHandled(true);
        });
    }

}