package homeostatic.common.capabilities;

import net.minecraft.nbt.CompoundTag;

public interface IThermometer {

    boolean hasThermometer();

    void setHasThermometer(boolean hasThermometer);

    CompoundTag write();

    void read(CompoundTag tag);

}
