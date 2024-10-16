package homeostatic.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

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
    public ListTag write() {
        ListTag listTag = new ListTag();
        CompoundTag tag = new CompoundTag();

        write(tag);
        listTag.add(tag);

        return listTag;
    }

    @Override
    public CompoundTag write(CompoundTag tag) {
        tag.putInt("wetnessLevel", this.getWetnessLevel());
        tag.putFloat("moistureLevel", this.getMoistureLevel());

        return tag;
    }

    @Override
    public void read(ListTag nbt) {
        read(nbt.getCompound(0));
    }

    @Override
    public void read(CompoundTag tag) {
        this.setWetnessLevel(tag.getInt("wetnessLevel"));
        this.setMoistureLevel(tag.getFloat("moistureLevel"));
    }

}
