package homeostatic.common.attachments;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import net.minecraft.nbt.ListTag;

import net.neoforged.neoforge.common.util.INBTSerializable;

import homeostatic.network.Temperature;

import java.util.Optional;

public class TemperatureData {

    public static final TemperatureDataProvider TEMPERATURE_DATA_INSTANCE = new TemperatureDataProvider();

    public static Optional<Temperature> getData(final Player player) {
        return Optional.of(player.getData(AttachmentsRegistry.TEMPERATURE_DATA.get()));
    }

    public static class TemperatureDataProvider extends Temperature implements INBTSerializable<ListTag> {

        public TemperatureDataProvider() {}

        @Override
        public ListTag serializeNBT() {
            return TEMPERATURE_DATA_INSTANCE.write();
        }

        @Override
        public void deserializeNBT(@NotNull ListTag nbt) {
            TEMPERATURE_DATA_INSTANCE.read(nbt);
        }

    }

}
