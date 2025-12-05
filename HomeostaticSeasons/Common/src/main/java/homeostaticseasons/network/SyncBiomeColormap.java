package homeostaticseasons.network;

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

import climatesettings.network.IPacket;

import homeostaticseasons.common.biome.BiomeColormap;
import homeostaticseasons.common.biome.BiomeColormapManager;

import static homeostaticseasons.HomeostaticSeasons.prefix;

public record SyncBiomeColormap(Tag data) implements IPacket {

    public static final ResourceLocation ID = prefix("sync_biome_colormap");
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncBiomeColormap> CODEC = StreamCodec.composite(
        ByteBufCodecs.TAG,
        SyncBiomeColormap::data,
        SyncBiomeColormap::new
    );
    public static final CustomPacketPayload.Type<SyncBiomeColormap> TYPE = new Type<>(ID);

    @Override
    public void handle(Player player) {
        DataResult<List<BiomeColormap>> result = Codec.list(BiomeColormap.CODEC).parse(NbtOps.INSTANCE, data);
        List<BiomeColormap> biomeColormaps = result.getOrThrow((error) -> {
            throw new RuntimeException("Failed to decode BiomeColormap list: " + error);
        });

        BiomeColormapManager.update(biomeColormaps);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
