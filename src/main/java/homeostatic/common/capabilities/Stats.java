package homeostatic.common.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import homeostatic.common.temperature.BodyTemperature;
import homeostatic.common.water.WaterData;

public class Stats {

    public static Tag writeNBT(Capability<Stats> capability, Stats instance, Direction side) {
        CompoundTag tag = new CompoundTag();

        tag.putFloat("skinTemperature", instance.getSkinTemperature());
        tag.putFloat("coreTemperature", instance.getCoreTemperature());
        tag.putFloat("localTemperature", instance.getLocalTemperature());
        tag.putInt("waterLevel", instance.getWaterLevel());
        tag.putFloat("waterExhaustion", instance.getWaterExhaustionLevel());
        tag.putFloat("waterSaturation", instance.getWaterSaturationLevel());

        return tag;
    }

    public static void readNBT(Capability<Stats> capability, Stats instance, Direction side, Tag nbt) {
        instance.setSkinTemperature(((CompoundTag) nbt).getFloat("skinTemperature"));
        instance.setCoreTemperature(((CompoundTag) nbt).getFloat("coreTemperature"));
        instance.setLocalTemperature(((CompoundTag) nbt).getFloat("localTemperature"));
        instance.setWaterLevel(((CompoundTag) nbt).getInt("waterLevel"));
        instance.setWaterExhaustionLevel(((CompoundTag) nbt).getFloat("waterExhaustion"));
        instance.setWaterSaturationLevel(((CompoundTag) nbt).getFloat("waterSaturation"));
    }

    private float skinTemperature;
    private float coreTemperature;
    private float localTemperature;
    private int waterLevel;
    private float waterSaturationLevel;
    private float waterExhaustionLevel;

    public void setSkinTemperature(float skinTemperature) {
        this.skinTemperature = skinTemperature;
    }

    public void setCoreTemperature(float coreTemperature) {
        this.coreTemperature = coreTemperature;
    }

    public void setLocalTemperature(float temperature) {
        this.localTemperature = temperature;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public void setWaterSaturationLevel(float waterSaturationLevel) {
        this.waterSaturationLevel = waterSaturationLevel;
    }

    public void setWaterExhaustionLevel(float waterExhaustionLevel) {
        this.waterExhaustionLevel = waterExhaustionLevel;
    }

    public void setTemperatureData(BodyTemperature bodyTemperature, float localTemperature) {
        this.setCoreTemperature(bodyTemperature.getCoreTemperature());
        this.setSkinTemperature(bodyTemperature.getSkinTemperature());
        this.setLocalTemperature(localTemperature);
    }

    public void setWaterData(WaterData waterData) {
        this.setWaterLevel(waterData.getWaterLevel());
        this.setWaterSaturationLevel(waterData.getWaterSaturationLevel());
        this.setWaterExhaustionLevel(waterData.getWaterExhaustionLevel());
    }

    public float getSkinTemperature() {
        return this.skinTemperature;
    }

    public float getCoreTemperature() {
        return this.coreTemperature;
    }

    public float getLocalTemperature() {
        return this.localTemperature;
    }

    public int getWaterLevel() {
        return this.waterLevel;
    }

    public float getWaterExhaustionLevel() {
        return this.waterExhaustionLevel;
    }

    public float getWaterSaturationLevel() {
        return this.waterSaturationLevel;
    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        @Nonnull
        private final Stats instance;

        private final LazyOptional<Stats> handler;

        public Provider() {
            instance = new Stats();
            handler = LazyOptional.of(this::getInstance);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == null) return LazyOptional.empty();

            return CapabilityRegistry.STATS_CAPABILITY.orEmpty(cap, handler);
        }

        public Stats getInstance() {
            return instance;
        }

        @Override
        public Tag serializeNBT() {
            return Stats.writeNBT(CapabilityRegistry.STATS_CAPABILITY, instance, null);
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            Stats.readNBT(CapabilityRegistry.STATS_CAPABILITY, instance, null, nbt);
        }

    }

}
