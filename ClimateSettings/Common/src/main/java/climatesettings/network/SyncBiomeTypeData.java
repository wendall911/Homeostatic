package climatesettings.network;

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

import climatesettings.common.biome.BiomeTypeData;
import climatesettings.common.biome.BiomeTypeDataManager;

import static climatesettings.ClimateSettings.prefix;

public record SyncBiomeTypeData(Tag data) implements IPacket {

    public static final Identifier ID = prefix("sync_biome_type_data");
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncBiomeTypeData> CODEC = StreamCodec.composite(
        ByteBufCodecs.TAG,
        SyncBiomeTypeData::data,
        SyncBiomeTypeData::new
    );
    public static final CustomPacketPayload.Type<SyncBiomeTypeData> TYPE = new Type<>(ID);

    @Override
    public void handle(Player player) {
        DataResult<List<BiomeTypeData>> result = Codec.list(BiomeTypeData.CODEC).parse(NbtOps.INSTANCE, data);
        List<BiomeTypeData> biomeTypeData = result.getOrThrow((error) -> {
            throw new RuntimeException("Failed to decode BiomeTypeData list: " + error);
        });

        BiomeTypeDataManager.update(biomeTypeData);
    }

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
