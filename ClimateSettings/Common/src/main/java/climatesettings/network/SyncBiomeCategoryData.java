package climatesettings.network;

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
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

import climatesettings.common.biome.BiomeCategory;
import climatesettings.common.biome.BiomeCategoryManager;

import static climatesettings.ClimateSettings.prefix;

public record SyncBiomeCategoryData(Tag data) implements IPacket {

    public static final Identifier ID = prefix("sync_biome_category_data");
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncBiomeCategoryData> CODEC = StreamCodec.composite(
        ByteBufCodecs.TAG,
        SyncBiomeCategoryData::data,
        SyncBiomeCategoryData::new
    );
    public static final CustomPacketPayload.Type<SyncBiomeCategoryData> TYPE = new Type<>(ID);

    @Override
    public void handle(Player player) {
        DataResult<List<BiomeCategory>> result = Codec.list(BiomeCategory.CODEC).parse(NbtOps.INSTANCE, data);
        List<BiomeCategory> biomeTypeData = result.getOrThrow((error) -> {
            throw new RuntimeException("Failed to decode BiomeCategory list: " + error);
        });

        BiomeCategoryManager.update(biomeTypeData);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
