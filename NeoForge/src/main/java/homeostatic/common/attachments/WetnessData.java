package homeostatic.common.attachments;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import net.neoforged.neoforge.common.util.ValueIOSerializable;

import homeostatic.network.Wetness;

public class WetnessData {

    public static Optional<Wetness> getData(final Player player) {
        return Optional.of(player.getData(AttachmentsRegistry.WETNESS_DATA.get()));
    }

    public static class WetnessDataProvider extends Wetness implements ValueIOSerializable {

        public WetnessDataProvider() {}

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
