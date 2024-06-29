package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class Thermometer implements IThermometer {

    private boolean hasThermometer = false;

    @Override
    public boolean hasThermometer() {
        return hasThermometer;
    }

    @Override
    public void setHasThermometer(boolean hasThermometer) {
        this.hasThermometer = hasThermometer;
    }

    @Override
    public ListTag write() {
        ListTag listTag = new ListTag();
        CompoundTag tag = new CompoundTag();

        tag.putBoolean("thermometer", this.hasThermometer());

        listTag.add(tag);

        return listTag;
    }

    @Override
    public void read(ListTag nbt) {
        CompoundTag tag = nbt.getCompound(0);

        this.setHasThermometer(tag.getBoolean("thermometer"));
    }

}
