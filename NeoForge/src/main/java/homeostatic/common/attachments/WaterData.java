package homeostatic.common.attachments;

import java.util.Optional;

import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;

import net.neoforged.neoforge.common.util.INBTSerializable;

import homeostatic.network.Water;

public class WaterData {

    public static final WaterData.WaterDataProvider WATER_DATA_INSTANCE = new WaterData.WaterDataProvider();

    public static Optional<Water> getData(final Player player) {
        return Optional.of(player.getData(AttachmentsRegistry.WATER_DATA.get()));
    }

    public static class WaterDataProvider extends Water implements INBTSerializable<ListTag> {

        public WaterDataProvider() {}

        @Override
        public ListTag serializeNBT() {
            return WATER_DATA_INSTANCE.write();
        }

        @Override
        public void deserializeNBT(ListTag nbt) {
            WATER_DATA_INSTANCE.read(nbt);
        }

    }

}