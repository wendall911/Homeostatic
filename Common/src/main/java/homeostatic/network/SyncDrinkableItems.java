package homeostatic.network;

import java.util.List;

import org.jspecify.annotations.NonNull;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

import homeostatic.common.item.DrinkableItem;
import homeostatic.common.item.DrinkableItemManager;

import static homeostatic.Homeostatic.prefix;

public record SyncDrinkableItems(Tag data) implements IPacket {

    public static final Identifier ID = prefix("sync_drinkable_items");
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncDrinkableItems> CODEC = StreamCodec.composite(
        ByteBufCodecs.TAG,
        SyncDrinkableItems::data,
        SyncDrinkableItems::new
    );
    public static final CustomPacketPayload.Type<SyncDrinkableItems> TYPE = new Type<>(ID);

    @Override
    public void handle(Player player) {
        DataResult<List<DrinkableItem>> result = Codec.list(DrinkableItem.CODEC).parse(NbtOps.INSTANCE, data);
        List<DrinkableItem> drinkableItems = result.getOrThrow((items) -> {
            throw new IllegalStateException("Failed to decode drinkable items: " + items);
        });

        DrinkableItemManager.update(drinkableItems);
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
