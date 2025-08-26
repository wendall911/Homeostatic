package homeostatic.common.attachments;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import net.neoforged.neoforge.common.util.ValueIOSerializable;

import homeostatic.network.Thermometer;

public class ThermometerData {

    public static Optional<Thermometer> getData(final Player player) {
        return Optional.of(player.getData(AttachmentsRegistry.THERMOMETER_DATA.get()));
    }

    public static class ThermometerDataProvider extends Thermometer implements ValueIOSerializable {

        public ThermometerDataProvider() {}

        @Override
        public void serialize(@NotNull ValueOutput valueOutput) {
            write(valueOutput);
        }

        @Override
        public void deserialize(@NotNull ValueInput valueInput) {
            read(valueInput);
        }

    }

}
