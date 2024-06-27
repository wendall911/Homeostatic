package homeostatic.network;

import java.util.function.Function;

import net.minecraft.network.FriendlyByteBuf;

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

        registerMessage(id++, NeoForgeDrinkWater.class, NeoForgeDrinkWater::new);
        registerMessage(id++, NeoForgeTemperatureData.class, NeoForgeTemperatureData::new);
        registerMessage(id++, NeoForgeThermometerData.class, NeoForgeThermometerData::new);
        registerMessage(id++, NeoForgeWaterData.class, NeoForgeWaterData::new);
        registerMessage(id++, NeoForgeWetnessData.class, NeoForgeWetnessData::new);
    }

    private static <T extends IData> void registerMessage(int idx, Class<T> type, Function<FriendlyByteBuf, T> decoder) {
        INSTANCE.registerMessage(idx, type, IData::toBytes, decoder, (msg, ctx) -> {
            msg.process(ctx);
            ctx.get().setPacketHandled(true);
        });
    }

}
