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

        write(tag);
        listTag.add(tag);

        return listTag;
    }

    @Override
    public CompoundTag write(CompoundTag tag) {
        tag.putBoolean("thermometer", this.hasThermometer());

        return tag;
    }

    @Override
    public void read(ListTag nbt) {
        read(nbt.getCompound(0));
    }

    @Override
    public void read(CompoundTag tag) {
        this.setHasThermometer(tag.getBoolean("thermometer"));
    }

}
