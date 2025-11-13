package homeostatic.network;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.fluid.DrinkingFluid;
import homeostatic.common.fluid.DrinkingFluidManager;

import static homeostatic.Homeostatic.prefix;

public record SyncDrinkingFluid(Tag data) implements IPacket {

    public static final ResourceLocation ID = prefix("sync_drinking_fluid");
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncDrinkingFluid> CODEC = StreamCodec.composite(
        ByteBufCodecs.TAG,
        SyncDrinkingFluid::data,
        SyncDrinkingFluid::new
    );

    public static final CustomPacketPayload.Type<SyncDrinkingFluid> TYPE = new Type<>(ID);

    @Override
    public void handle(Player player) {
        DataResult<List<DrinkingFluid>> result = Codec.list(DrinkingFluid.CODEC).parse(NbtOps.INSTANCE, data);
        List<DrinkingFluid> drinkingFluids = result.getOrThrow((fluids) -> {
            throw new IllegalStateException("Failed to decode drinking fluids: " + fluids);
        });

        DrinkingFluidManager.update(drinkingFluids);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
