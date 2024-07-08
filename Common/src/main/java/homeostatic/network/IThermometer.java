package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public interface IThermometer {

    boolean hasThermometer();

    void setHasThermometer(boolean hasThermometer);

    ListTag write();

    CompoundTag write(CompoundTag tag);

    void read(ListTag tag);

    void read(CompoundTag tag);

}
