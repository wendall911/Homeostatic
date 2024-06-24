package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkEvent;

public interface IData {

    void toBytes(FriendlyByteBuf buf);

    void process(Supplier<NetworkEvent.Context> ctx);

}

