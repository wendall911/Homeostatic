package homeostatic.network;

import org.jetbrains.annotations.NotNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public interface IThermometer {

    boolean hasThermometer();

    void setHasThermometer(boolean hasThermometer);

    CompoundTag write(CompoundTag tag);

    ValueOutput write(@NotNull ValueOutput tag);

    void read(CompoundTag tag);

    void read(ValueInput tag);

}
