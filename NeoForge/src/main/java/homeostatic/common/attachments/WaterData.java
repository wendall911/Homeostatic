package homeostatic.common.attachments;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;

import net.neoforged.neoforge.common.util.INBTSerializable;

import homeostatic.network.Water;

public class WaterData {

    public static Optional<Water> getData(final Player player) {
        return Optional.of(player.getData(AttachmentsRegistry.WATER_DATA.get()));
    }

    public static class WaterDataProvider extends Water implements INBTSerializable<ListTag> {

        public WaterDataProvider() {}

        @Override
        public ListTag serializeNBT(HolderLookup.@NotNull Provider provider) {
            return write();
        }

        @Override
        public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull ListTag nbt) {
            read(nbt);
        }

    }

}