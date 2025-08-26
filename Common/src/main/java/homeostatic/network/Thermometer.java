package homeostatic.network;

import org.jetbrains.annotations.NotNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

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
    public CompoundTag write(CompoundTag tag) {
        tag.putBoolean("thermometer", this.hasThermometer());

        return tag;
    }

    @Override
    public ValueOutput write(@NotNull ValueOutput valueOutput) {
        valueOutput.putBoolean("thermometer", this.hasThermometer());

        return valueOutput;
    }

    @Override
    public void read(CompoundTag tag) {
        this.setHasThermometer(tag.getBoolean("thermometer").orElseThrow());
    }

    @Override
    public void read(ValueInput valueInput) {
        this.setHasThermometer(valueInput.getBooleanOr("thermometer", false));
    }

}
