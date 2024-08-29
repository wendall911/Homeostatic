package homeostatic.common.attachments;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;

import net.neoforged.neoforge.common.util.INBTSerializable;

import homeostatic.network.Thermometer;

public class ThermometerData {

    public static Optional<Thermometer> getData(final Player player) {
        return Optional.of(player.getData(AttachmentsRegistry.THERMOMETER_DATA.get()));
    }

    public static class ThermometerDataProvider extends Thermometer implements INBTSerializable<ListTag> {

        public ThermometerDataProvider() {}

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