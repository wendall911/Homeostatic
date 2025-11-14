package homeostatic.network;

import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent.Context;

import homeostatic.common.fluid.DrinkingFluidManager;

public class ForgeDrinkingFluidData implements IData {

    private SyncDrinkingFluids drinkingFluids;

    public ForgeDrinkingFluidData(FriendlyByteBuf buf) {
        drinkingFluids = new SyncDrinkingFluids(buf.readByteArray());
    }

    public ForgeDrinkingFluidData() {
        drinkingFluids = new SyncDrinkingFluids();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeByteArray(drinkingFluids.bytes);
    }

    @Override
    public void process(Supplier<Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                DrinkingFluidManager.read(new FriendlyByteBuf(Unpooled.wrappedBuffer(drinkingFluids.bytes)));
            });
        }
    }

}
