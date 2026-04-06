package homeostatic.common.attachments;

import java.util.Optional;

import org.jspecify.annotations.NonNull;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import net.neoforged.neoforge.common.util.ValueIOSerializable;

import homeostatic.network.Water;

public class WaterData {

    public static Optional<Water> getData(final Player player) {
        return Optional.of(player.getData(AttachmentsRegistry.WATER_DATA.get()));
    }

    public static class WaterDataProvider extends Water implements ValueIOSerializable {

        @Override
        public void serialize(@NonNull ValueOutput valueOutput) {
            write(valueOutput);
        }

        @Override
        public void deserialize(@NonNull ValueInput valueInput) {
            read(valueInput);
        }

    }

}
