package homeostatic.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import static homeostatic.Homeostatic.loc;

public record DrinkWater(int entityId) implements CustomPacketPayload {

    public static final ResourceLocation ID = loc("drink_water");
    public static final Type<DrinkWater> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, DrinkWater> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT,
        DrinkWater::entityId,
        DrinkWater::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
