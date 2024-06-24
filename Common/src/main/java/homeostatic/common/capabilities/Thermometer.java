package homeostatic.common.capabilities;

import net.minecraft.nbt.CompoundTag;

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
    public CompoundTag write() {
        CompoundTag tag = new CompoundTag();

        tag.putBoolean("thermometer", this.hasThermometer());

        return tag;
    }

    @Override
    public void read(CompoundTag tag) {
        this.setHasThermometer(tag.getBoolean("thermometer"));
    }

}
