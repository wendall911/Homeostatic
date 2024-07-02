package homeostatic.common.attachments;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;

import net.neoforged.neoforge.common.util.INBTSerializable;

import homeostatic.network.Wetness;

public class WetnessData {

    public static final WetnessData.WetnessDataProvider WETNESS_DATA_INSTANCE = new WetnessData.WetnessDataProvider();

    public static Optional<Wetness> getData(final Player player) {
        return Optional.of(player.getData(AttachmentsRegistry.WETNESS_DATA.get()));
    }

    public static class WetnessDataProvider extends Wetness implements INBTSerializable<ListTag> {

        public WetnessDataProvider() {}

        @Override
        public ListTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            return WETNESS_DATA_INSTANCE.write();
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull ListTag nbt) {
            WETNESS_DATA_INSTANCE.read(nbt);
        }

    }

}