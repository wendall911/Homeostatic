package homeostatic.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.network.NetworkEvent;

import homeostatic.util.WaterHelper;

public class DrinkWater implements IData {

    public DrinkWater() {}

    public DrinkWater(FriendlyByteBuf buf) {}

    @Override
    public void toBytes(FriendlyByteBuf buf) {}

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer sp = ctx.get().getSender();

        if (sp != null) {
            WaterHelper.drinkWater(sp);
        }

    }

}
