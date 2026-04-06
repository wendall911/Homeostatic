package homeostatic.network;

import org.jspecify.annotations.NonNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import homeostatic.common.wetness.WetnessInfo;

public class Wetness implements IWetness {

    private int wetnessLevel = 0;
    private float moistureLevel = 0.0F;

    @Override
    public void setWetnessLevel(int wetnessLevel) {
        this.wetnessLevel = wetnessLevel;
    }

    @Override
    public void setMoistureLevel(float moistureLevel) {
        this.moistureLevel = moistureLevel;
    }

    @Override
    public void setWetnessData(WetnessInfo wetnessInfo) {
        this.setWetnessLevel(wetnessInfo.getWetnessLevel());
        this.setMoistureLevel(wetnessInfo.getMoistureLevel());
    }

    @Override
    public int getWetnessLevel() {
        return this.wetnessLevel;
    }

    @Override
    public float getMoistureLevel() {
        return this.moistureLevel;
    }

    @Override
    public CompoundTag write(CompoundTag tag) {
        tag.putInt("wetnessLevel", this.getWetnessLevel());
        tag.putFloat("moistureLevel", this.getMoistureLevel());

        return tag;
    }

    @Override
    public ValueOutput write(@NonNull ValueOutput valueOutput) {
        valueOutput.putInt("wetnessLevel", this.getWetnessLevel());
        valueOutput.putFloat("moistureLevel", this.getMoistureLevel());

        return valueOutput;
    }

    @Override
    public void read(CompoundTag tag) {
        this.setWetnessLevel(tag.getInt("wetnessLevel").orElseThrow());
        this.setMoistureLevel(tag.getFloat("moistureLevel").orElseThrow());
    }

    @Override
    public void read(@NonNull ValueInput valueInput) {
        this.setWetnessLevel(valueInput.getIntOr("wetnessLevel", 0));
        this.setMoistureLevel(valueInput.getFloatOr("moistureLevel", 0.0F));
    }

}
