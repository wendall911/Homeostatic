package homeostatic.common.attachments;

import java.util.Optional;

import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;

import net.neoforged.neoforge.common.util.INBTSerializable;

import homeostatic.network.Thermometer;


public class ThermometerData {

    public static final ThermometerData.ThermometerDataProvider THERMOMETER_DATA_INSTANCE = new ThermometerData.ThermometerDataProvider();

    public static Optional<Thermometer> getData(final Player player) {
        return Optional.of(player.getData(AttachmentsRegistry.THERMOMETER_DATA.get()));
    }

    public static class ThermometerDataProvider extends Thermometer implements INBTSerializable<ListTag> {

        public ThermometerDataProvider() {}

        @Override
        public ListTag serializeNBT() {
            return THERMOMETER_DATA_INSTANCE.write();
        }

        @Override
        public void deserializeNBT(ListTag nbt) {
            THERMOMETER_DATA_INSTANCE.read(nbt);
        }

    }

}