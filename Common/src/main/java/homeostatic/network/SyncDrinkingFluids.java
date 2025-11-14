package homeostatic.network;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

import homeostatic.common.fluid.DrinkingFluidManager;

public class SyncDrinkingFluids {

    public byte[] bytes;

    public SyncDrinkingFluids(byte[] bytes) {
        this.bytes = bytes;
    }

    public SyncDrinkingFluids() {
        FriendlyByteBuf tmp = new FriendlyByteBuf(Unpooled.buffer());

        DrinkingFluidManager.write(tmp);
        bytes = new byte[tmp.readableBytes()];
        tmp.readBytes(bytes, 0, bytes.length);
    }

}
