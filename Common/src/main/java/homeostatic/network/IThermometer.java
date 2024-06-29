package homeostatic.network;

import net.minecraft.nbt.ListTag;

public interface IThermometer {

    boolean hasThermometer();

    void setHasThermometer(boolean hasThermometer);

    ListTag write();

    void read(ListTag tag);

}
