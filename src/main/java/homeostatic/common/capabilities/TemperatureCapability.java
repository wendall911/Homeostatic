package homeostatic.common.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class TemperatureCapability {

    public static Tag writeNBT(Capability<TemperatureCapability> capability, TemperatureCapability instance, Direction side) {
        CompoundTag tag = new CompoundTag();

        tag.putFloat("bodyTemperature", instance.getBodyTemperature());
        tag.putFloat("localTemperature", instance.getLocalTemperature());

        return tag;
    }

    public static void readNBT(Capability<TemperatureCapability> capability, TemperatureCapability instance, Direction side, Tag nbt) {
        instance.setBodyTemperature(((CompoundTag) nbt).getFloat("bodyTemperature"));
        instance.setLocalTemperature(((CompoundTag) nbt).getFloat("localTemperature"));
    }

    private float bodyTemperature = 0F;
    private float localTemperature = 0F;

    public void setBodyTemperature(float temperature) {
        this.bodyTemperature = temperature;
    }

    public void setLocalTemperature(float temperature) {
        this.localTemperature = temperature;
    }

    public float getBodyTemperature() {
        return bodyTemperature;
    }

    public float getLocalTemperature() {
        return localTemperature;
    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        @Nonnull
        private final TemperatureCapability instance;

        private final LazyOptional<TemperatureCapability> handler;

        public Provider() {
            instance = new TemperatureCapability();
            handler = LazyOptional.of(this::getInstance);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == null) return LazyOptional.empty();

            return CapabilityRegistry.TEMPERATURE.orEmpty(cap, handler);
        }

        public TemperatureCapability getInstance() {
            return instance;
        }

        @Override
        public Tag serializeNBT() {
            return TemperatureCapability.writeNBT(CapabilityRegistry.TEMPERATURE, instance, null);
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            TemperatureCapability.readNBT(CapabilityRegistry.TEMPERATURE, instance, null, nbt);
        }

    }

}
